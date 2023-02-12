package com.example.demo;

import com.example.demo.espece.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Caisse extends Thread{




    public class Noeud{
        private Espece esp;
        private List<String> dejaTeste;
        public Espece getEsp(){
            return esp;
        }

        /** Savoir si le type a déjà été testé (tupe espece) déjà testé */
        public boolean isRejet(String className){
            boolean ret = false;
            for (String nunu: dejaTeste)
                if (nunu == className) {
                    ret = true;
                    break;
                }

            return ret;
        }
        public boolean isRejet(Espece esp){
            return this.isRejet(esp.nomClass());
        }

        /**
         * Compare avec les éléments possible et si tous présent dans rejeté alors retourne vrai
         * @return vrai si tout à été rejeté
         */
        public boolean toutIsRejete(){
            boolean ret = false;
            if (dejaTeste.size() == lesSous.size())
                ret = true;

            return ret;
        }
        /**
         * Compare avec les éléments possible en prennant en compte de décalage (par la gauche vers la droite) et si tous présent dans rejeté alors retourne vrai
         * @return vrai si tout à été rejeté
         */
        public boolean toutIsRejete(int decal){
            boolean ret = false;
            if (dejaTeste.size() + decal == lesSous.size())
                ret = true;

            return ret;
        }

        /**Ajoute un noeud au rejeté pour avoir une trace*/
        public void addRejet(String nomClass){
            this.dejaTeste.add(nomClass);
        }
        public void addRejet(Espece esp){
            this.addRejet(esp.nomClass());
        }

        Noeud(Espece e){this.esp = e; this.dejaTeste = new ArrayList<>();}
        @Override
        public String toString(){
            return this.esp.nomClass();
        }
    }
    private List<Espece> lesSous;

    Caisse(){
        lesSous= new ArrayList<>();
        //Definie les valeurs de base et ce qu'elle accepte comme entité
        //Attention le sens d'ajout détermine la "prioritée" lors du parcours
        //lesSous.add(new Billet10(0));
        //lesSous.add(new Billet5(0));
        //lesSous.add(new Piece2(0));
    }
    private List<Noeud> decoupageMonnaie(double aDecouper,List<Espece> lSous){
        //ListeChaîné instanciation et sommet de l'arbre
        LinkedList<Noeud> noeuds = new LinkedList<>();
        noeuds.add(new Noeud( new Espece(0,"0",0) {public String nomClass(){return "Pas de solution";}}));

        //Info fonction de la situation
        int tailleListe = lSous.size();

        //Variables
        boolean looping = true;
        double valChoisie;
        Espece tempoCibleEsp;
        double valeurRestante = Math.round(aDecouper*100.0)/100.0;
        int decalage = 0;


        //Boucle sur la longueur max d'une branche
        while (looping) {
            //Recup pos type d'espece pour effectuer le décalage
            decalage = (lSous.indexOf(noeuds.getLast().getEsp()) > 0)? (lSous.indexOf(noeuds.getLast().getEsp()))-1: 0;

            //Boucle sur les possibilités pour chaque noeud 2,5,10
            for (int j = 0; j < tailleListe; j++) {

                //Si le décalge et j nouss font sortir de la lsite alors on arrête de boucler
                if (j + decalage >= lSous.size())
                    break;

                //Recup type d'espece à tester
                tempoCibleEsp = lesSous.get(j + decalage) ;

                //Si toutes les possibilitées on été faites alors on retire le noeud
                if (noeuds.getLast().toutIsRejete(decalage)){
                    //Si le somme à fait toute ses possibiltées on quite
                    if (noeuds.getLast() == noeuds.getFirst())
                        looping = false;
                    else {
                        //Remet la valeur de la monnaie retiré
                        valeurRestante += Math.round(noeuds.getLast().esp.getValeur()*100.0)/100.0;
                        //Retir le noeud cul de sac
                        Noeud ntmp = noeuds.removeLast();
                        //Informe le noeud parent de la décision de rejet de la branche
                        noeuds.getLast().addRejet(ntmp.getEsp());
                    }
                    break;
                }
                //Si déjà rejeté on passe à un autre type d'espece plus petit
                else if (noeuds.getLast().isRejet(tempoCibleEsp))continue;

                //si pas disponible dans la caisse || plus accés pour incrémenter on rejete
                if (tempoCibleEsp.getQt() == 0 || !especeIsIncrementable(noeuds,tempoCibleEsp)) {
                    noeuds.getLast().addRejet(tempoCibleEsp);
                    continue;
                }

                //Recup valeur de la monnaie
                valChoisie = Math.round(tempoCibleEsp.getValeur()*100.0)/100.0;


                //Si valeurRestante - valChoisie == 0 alors solution trouvé, on quite tt car solution la plus à gauche possible
                //else if ((valeurRestante - valChoisie)%.2F == 0.00) {
                if ( -0.009 < (valeurRestante - valChoisie) && (valeurRestante - valChoisie) < 0.009)  {
                    //writer.println(eccritLogCalcul(" ==0 ",valeurRestante,valChoisie));

                    noeuds.add(new Noeud(tempoCibleEsp));
                    valeurRestante -= valChoisie;
                    looping = false;
                    break;
                }
                //Si valeurRestante - choisie < 0 alors on quite et on marque comme rejeté
                else if ((valeurRestante - valChoisie)%.2F < 0.00) {
                    //writer.println(eccritLogCalcul(" <0 ",valeurRestante,valChoisie));

                    noeuds.getLast().addRejet(tempoCibleEsp);
                    continue;
                }
                //Si valeurRestante - choisie > 0 on ajoute a la linkedList et on break
                else if((valeurRestante - valChoisie)%.2F > 0.00){
                    //writer.println(eccritLogCalcul(" >0 ",valeurRestante,valChoisie));

                    noeuds.add(new Noeud(tempoCibleEsp));
                    valeurRestante -= valChoisie;
                    break;
                }

            }//For
        }//While

        //Si un modèle existe on retir le sommet qui ne sert plus
        if (noeuds.size() > 1) noeuds.removeFirst();


        return noeuds.stream().toList();
    }//Methode

    public List<Noeud> decoupageArgent(double aDecouper){
        List<Espece> cpSous = new ArrayList<>();
        ///////// Pour "cloner" la liste sans conserver la même référence
        try {
            Class clasz;
            Constructor constructor;
        for (Espece esp : this.lesSous) {
            //Recup de la classe
            clasz = Class.forName(esp.getClass().getName());
            //Recup d'un constructeur qui correspond a ce type d'arg
            constructor = clasz.getConstructor(new Class[]{int.class});
            //Ajoute l'obj à la liste avec le bon nombre d'élement dans la liste
            cpSous.add((Espece) constructor.newInstance(esp.getQt()));
        }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            //@TODO faire un truc un peu mieux au nivea ude la gestion des pb
            throw new RuntimeException(e);
        }
        /////////
        List<Noeud> lCent = this.decoupageCent(aDecouper,cpSous);
        List<Noeud> lEuros;
        List<Noeud> ret = new ArrayList<>();


        //Cas où supérieur à 0
        if (aDecouper > 0.0) {
            //Si découpage des centimes possible
            if (lCent.get(0).getEsp().getValeur() != 0 || (aDecouper - (int)aDecouper ) == 0) {
                //Retirer de la caisse les centimes utilisés
                for (Noeud n : lCent)
                    cpSous = this.retirer(n.getEsp(),cpSous);

                //Découpage avec les euros
                lEuros = this.decoupageEuro(aDecouper,cpSous);

                //Si découpage possible, retire les euros de la caisse
                if (lEuros.get(0).getEsp().getValeur() > 0) {
                    //Retirer les Euros
                    for (Noeud n : lEuros)
                        cpSous = this.retirer(n.getEsp(),cpSous);

                    //Ajouter a la liste à retourner
                    ret.addAll(lEuros);
                    if ((aDecouper - (int)aDecouper ) > 0)
                        ret.addAll(lCent);
                }
                //Cas "Pas de solution"
                else
                    ret.add(new Noeud(new Espece(0, "0", 0) {public String nomClass() {return "Pas de solution";}}));

            }
            else
                ret = lCent;
        }
        //Retourne "un billet de 0"
        else
            ret.add(new Noeud( new Espece(0,"0",0) {public String nomClass(){return "Zero";}}));

        return ret;
    }
    private List<Noeud> decoupageCent(double aDecouper,List<Espece> lEsp) {
        return  this.decoupageMonnaie( aDecouper - (int)aDecouper,lEsp);
    }

    private List<Noeud> decoupageEuro(double aDecouper,List<Espece> lEsp) {
        return  this.decoupageMonnaie((int)aDecouper,lEsp);
    }


    private String eccritLogCalcul(String msg,double valeurRestante,double valChoisie ){
        return (String)((valeurRestante%.2F - valChoisie%.2F)%.2F+msg+ " : "+valChoisie%.2F);
    }
    /**
     * Return true si il y a moins d'espece utilisé que disponible avec (utilisé < dispo )
     * */
    private boolean especeIsIncrementable(LinkedList<Noeud> lnoeud, String nomClass){
        boolean ret = false;
        int nbElement = 0;
        for (Noeud noued:lnoeud)
            if (noued.esp.nomClass() == nomClass) nbElement++;

        if (getTotalQte(nomClass) > nbElement)
            ret = true;

        return ret;
    }

    private boolean especeIsIncrementable(LinkedList<Noeud> lnoeud, Espece esp){
        return this.especeIsIncrementable(lnoeud,esp.nomClass());
    }

    public double getTotal(){
        double ret = 0;
        for (Espece t:lesSous)
            ret += t.getQt() * t.getValeur();

        return ret;
    }

    /**
     * @return le nombre d'élément dans la caisse*/
    public int getTotalQte(){
        int ret = 0;
        for (Espece t:lesSous)
            ret += t.getQt();
        return ret;
    }

    /**
     * @return Qte d'un type de monnaie dans la caisse*/
    public int getTotalQte(String espName){
        int ret = 0;
        for (Espece t:lesSous)
            if (espName == t.nomClass()){
                ret = t.getQt();
                break;
            }
        return ret;
    }
    public int getTotalQte(Espece esp){return this.getTotalQte(esp.nomClass());}

    /*Ajoute des especes à la caisse */
    public void ajouter(Espece unTruc){
        for (Espece esp: lesSous)
            if (unTruc.nomClass() == esp.nomClass()){
                esp.ajouter(unTruc.getQt());
                return;
            }
        lesSous.add(unTruc);
        lesSous.sort(Comparator.comparing(Espece::getValeur));
        Collections.reverse(lesSous);
    }

    /** Retire de la monnaie de la caisse
     * @TODO thows erreur: QT inf à 0, espece inexistante
     * */
    public void retirer(Espece esp){
        this.retirer(esp,this.lesSous);
    }

    /**
     * Retir la monnaie de la liste donnée
     * @return nouvelle list*/
    public List<Espece> retirer(Espece esp,List<Espece> lEsp){
        for (Espece t:lEsp)
            if (esp.nomClass() == t.nomClass()){
                t.setQt(t.getQt() - esp.getQt());
                break;
            }
        return lEsp;
    }

    public String toString(){
        String ret ="La caisse dispose de:\n";
        for (Espece esp:lesSous) {
            ret += esp.getDescription() + ":" + esp.getQt() + " // " + (float)(esp.getQt() * esp.getValeur()) + "\n";
        }
        ret += "Pour un total de: " + this.getTotal() +"€ \n";

        return ret;
    }
}
