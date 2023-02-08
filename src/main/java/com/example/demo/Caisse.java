package com.example.demo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Caisse extends Thread{

public static abstract class Espece{
    private double valeur;
    private String description;
    private int qt;

    //Setters
    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setQt(int qt) {
        this.qt = qt;
    }
    //Getters
    public double getValeur() {
        return valeur;
    }
    public int getQt() {
        return qt;
    }
    public String getDescription() {
        return description;
    }
    //Constructeur
    Espece(double val,String des,int quantite){
        valeur = val;
        description = des;
        qt = quantite;
    }
    public String nomClass(){
        return this.getClass().getSimpleName();
    }
    //Methodes
    public void ajouter(int quantitee){
        if (quantitee > 0)
        this.qt += quantitee;
    }
    public void retirer(int quantitee){
        if (quantitee < 0)
            this.qt -= quantitee;
    }
}
    public static class Cent1 extends Espece{

        Cent1(){
            super(0.01,"Piece de 0.01€",1);
        }
        Cent1(int qt){
            super(0.01,"Piece de 0.01€",qt);
        }
    }
    public static class Cent2 extends Espece{

        Cent2(){
            super(0.02,"Piece de 0.02€",1);
        }
        Cent2(int qt){
            super(0.02,"Piece de 0.02€",qt);
        }
    }
    public static class Cent5 extends Espece{

        Cent5(){
            super(0.05,"Piece de 0.05€",1);
        }
        Cent5(int qt){
            super(0.05,"Piece de 0.05€",qt);
        }
    }
    public static class Cent10 extends Espece{

        Cent10(){
            super(0.1,"Piece de 0.10€",1);
        }
        Cent10(int qt){
            super(0.1,"Piece de 0.10€",qt);
        }
    }
    public static class Cent20 extends Espece{

        Cent20(){
            super(0.2,"Piece de 0.20€",1);
        }
        Cent20(int qt){
            super(0.2,"Piece de 0.20€",qt);
        }
    }
    public static class Cent50 extends Espece{

        Cent50(){
            super(0.5,"Piece de 0.50€",1);
        }
        Cent50(int qt){
            super(0.5,"Piece de 0.50€",qt);
        }
    }
    public static class Piece1 extends Espece{

        Piece1(){
            super(1,"Piece de 1€",1);
        }
        Piece1(int qt){
            super(1,"Piece de 1€",qt);
        }
    }
    public static class Piece2 extends Espece{

    Piece2(){
        super(2,"Piece de 2€",1);
    }
    Piece2(int qt){
        super(2,"Piece de 2€",qt);
    }
}
    public static class Billet5 extends Espece{
    Billet5(){
        super(5,"Billet de 5€",1);
    }
    Billet5(int qt){
        super(5,"Billet de 5€",qt);
    }
}
    public static class Billet10 extends Espece{

    Billet10(){
        super(10,"Billet de 10€",1);
    }
    Billet10(int qt){
        super(10,"Billet de 10€",qt);
    }
}
    public static class Billet20 extends Espece{

    Billet20() {super(20,"Billet de 20€", 1);}
    Billet20(int quantite) {super(20,"Billet de 20€", quantite);}
}
    public static class Billet50 extends Espece{
        Billet50() {super(50,"Billet de 50€", 1);}
        Billet50(int quantite) {super(50,"Billet de 50€", quantite);}
    }
    public static class Billet100 extends Espece{
        Billet100() {super(100,"Billet de 100€", 1);}
        Billet100(int quantite) {super(100,"Billet de 100€", quantite);}
    }
    public static class Billet200 extends Espece{
        Billet200() {super(200,"Billet de 200€", 1);}
        Billet200(int quantite) {super(200,"Billet de 200€", quantite);}
    }


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
    public List<Noeud> decoupageMonnaie(double aDecouper){
    long tempsDeCalcul = System.currentTimeMillis();

        //ListeChaîné instanciation et sommet de l'arbre
    LinkedList<Noeud> noeuds = new LinkedList<>();
    noeuds.add(new Noeud( new Espece(0,"0",0) {public String nomClass(){return "Pas de solution";}}));

    //Info fonction de la situation
    int tailleListe = lesSous.size();

    //Variables
    boolean looping = true;
    double valChoisie;
    Espece tempoCibleEsp;
    double valeurRestante = Math.round(aDecouper*100.0)/100.0;
    int decalage = 0;

    //FICHIER pour les logs
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("logs.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.println("------------------");



        //Boucle sur la longueur max d'une branche
        while (looping) {
            //writer.println(noeuds.stream().toList());
            //System.out.println(lesSous.indexOf(noeuds.getLast().getEsp()));
            //Recup pos type d'espece pour effectuer le décalage
            decalage = (lesSous.indexOf(noeuds.getLast().getEsp()) > 0)? (lesSous.indexOf(noeuds.getLast().getEsp()))-1: 0;

        //Boucle sur les possibilités pour chaque noeud 2,5,10
        for (int j = 0; j < tailleListe; j++) {

            //Si le décalge et j nouss font sortir de la lsite alors on arrête de boucler
            if (j + decalage >= lesSous.size())
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

        System.out.println("Temps de calcul pour:"+(float)aDecouper+"::"+(System.currentTimeMillis() - tempsDeCalcul) + "ms");

        writer.println(noeuds.stream().toList());
        writer.close();
        return noeuds.stream().toList();
    }//Methode

    public List<Noeud> decoupageArgent(double aDecouper){
    List<Noeud> lCent = this.decoupageCent(aDecouper);
    List<Noeud> lEuros ;
    List<Noeud> ret = new ArrayList<>();


    //Si découpage des centimes possible
    if (lCent.get(0).getEsp().getValeur() != 0) {
        //Retirer de la caisse les centimes utilisés
        for (Noeud n:lCent)
            this.retirer(n.getEsp());

        lEuros = this.decoupageEuro(aDecouper);
        //Si découpage impossible alors on remet les centimes dans la caisse
        if (lEuros.get(0).getEsp().getValeur() == 0) {
            for (Noeud n : lCent)
                this.ajouter(n.getEsp());

            ret.add(new Noeud( new Espece(0,"0",0) {public String nomClass(){return "Pas de solution";}}));
        }
        else {
            /*
            //Retirer les Euros
            for (Noeud n : lEuros)
                this.retirer(n.getEsp());
            */
            //Ajouter a la liste à retourner
            ret.addAll(lEuros);
            ret.addAll(lCent);
        }


    }else {
        ret = lCent;
    }

    return ret;
    }
    public List<Noeud> decoupageCent(double aDecouper) {
        return  this.decoupageMonnaie( aDecouper - (int)aDecouper);
    }

    public List<Noeud> decoupageEuro(double aDecouper) {
        return  this.decoupageMonnaie((int)aDecouper);
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
                esp.ajouter(unTruc.qt);
                return;
            }
        lesSous.add(unTruc);
        lesSous.sort(Comparator.comparing(Espece::getValeur));
        Collections.reverse(lesSous);
    }

    /** Retire de la monnaie de la caisse
     * @TODO thows erreur: QT inf à 0, espece inexistante */
    public void retirer(Espece esp){
        for (Espece t:lesSous)
            if (esp.nomClass() == t.nomClass()){
                t.setQt(t.getQt() - esp.getQt());
                break;
            }
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
