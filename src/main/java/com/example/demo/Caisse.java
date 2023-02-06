package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Caisse {

public static abstract class Espece{
    private int valeur;
    private String description;
    private int qt;

    //Setters
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setQt(int qt) {
        this.qt = qt;
    }
    //Getters
    public int getValeur() {
        return valeur;
    }
    public int getQt() {
        return qt;
    }
    public String getDescription() {
        return description;
    }
    //Constructeur
    Espece(int val,String des,int quantite){
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
public static class Piece2 extends Espece{

    Piece2(int qt){
        super(2,"Piece de 2€",qt);
    }
}

public static class Billet5 extends Espece{
    Billet5(int qt){
        super(5,"Billet de 5€",qt);
    }
}

public static class Billet10 extends Espece{

    Billet10(int qt){
        super(10,"Billet de 10€",qt);
    }
}


public class Noeud{
    private Espece esp;
    private boolean rejete = false;

    //TODO pensé lors d'un rollback à stocker dedans
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
    lesSous.add(new Billet10(0));
    lesSous.add(new Billet5(0));
    lesSous.add(new Piece2(0));
}
    public List<Noeud> decoupageMonnaie(double aDecouper){
    //ListeChaîné instanciation et sommet de l'arbre
    LinkedList<Noeud> noeuds = new LinkedList<>();
    noeuds.add(new Noeud( new Espece(0,"0",0) {}));

    //Info fonction de la situation
    int tailleListe = lesSous.size();
    int nbEntiteEspece = getTotalQte();

    //Variables
    boolean looping = true;
    int valChoisie;
    Espece tempoCibleEsp;
    double valeurRestante = aDecouper;

        //FIXME n'ira pas forcément a la fin de la branche car il y a dépilage transfoermer en while ?
        //Boucle sur la longueur max d'une branche
        while (looping) {
        //Boucle sur les possibilités pour chaque noeud 2,5,10
        for (int j = 0; j < tailleListe; j++) {
            System.out.println("Boucle");
            //Recup l'espece cible
            tempoCibleEsp = lesSous.get(j);

            //Si toutes les possibilitées on été faite alors on retire le noeud
            if (noeuds.getLast().toutIsRejete()){
                System.out.println("tout visité");
                //Remet la valeur de la monnaie retiré
                valeurRestante += noeuds.getLast().esp.getValeur();
                //Retir la monnaie de la liste
                noeuds.removeLast();
                break;
            }
            System.out.println("Reste a visiter");

            //Si déjà rejeté on passe à un autre type d'espece plus petit
            if (noeuds.getLast().isRejet(tempoCibleEsp))continue;
            System.out.println("Non rejeté");
            //si pas disponible dans la caisse || plus accé pour incrémenter on rejete
            if (tempoCibleEsp.getQt() == 0 || !especeIsIncrementable(noeuds,tempoCibleEsp)) {
                noeuds.getLast().isRejet(tempoCibleEsp);
                continue;
            }
            System.out.println("Incrémentable");

            //Recup valeur de la monnaie
            valChoisie = tempoCibleEsp.getValeur();


            //Si valeurRestante - choisie < 0 alors on quite et on marque comme rejeté 
            if (valeurRestante - valChoisie < 0) {
                noeuds.getLast().addRejet(tempoCibleEsp);
                continue;
            }
            //Si valeurRestante - choisie > 0 on ajoute a la linkedList et on break
            else if(valeurRestante - valChoisie > 0){
                noeuds.add(new Noeud(tempoCibleEsp));
                valeurRestante -= valChoisie;
                break;
            }
            //Si valeurRestante - valChoisie == 0 alors solution trouvé, on quite tt car solution la plus à gauche possible
            else if (valeurRestante - valChoisie == 0) {
                noeuds.add(new Noeud(tempoCibleEsp));
                valeurRestante -= valChoisie;
                looping = false;
                break;
            }

            //TODO SI tou visité pour le sommet alors pas de solution
            //SI linkedList vide alors pas de solution
            if(noeuds.isEmpty() )
                System.err.println("Pas de modèels");
            else if (false) {

            }

            //TODO liste chaîné avec les élément Espece, lorsque l'on est bloqué dans la branche on recule d'un cran en se souvenant du pressédent résultat pour prendre une valeur plus à droite (plus faible)
            // et si tt est utilisé on remonte d'un cran et idem jusqu'à trouvé une solution ou bien jusqu'à arrivé sur un non solvable
            }//For
        //looping = false;
    }//While

        //Possible de mettre une autre monnaie ? Si oui alors on prend la plus grande, y a-t-il suffisament de monnaie ?
        return noeuds.stream().toList();
    }//Methode
    /**
     * Reoturn true si il y a moins d'espece utilisé que disponible avec (utilisé < dispo )
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

    public int getTotal(){
    int ret = 0;
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

    public void ajouter(Espece unTruc){
        for (Espece esp: lesSous)
            if (unTruc.nomClass() == esp.nomClass()){
                esp.ajouter(unTruc.qt);
                break;
            }
    }
    public String toString(){
        String ret ="La caisse dispose de:\n";
        for (Espece esp:lesSous) {
            ret += esp.getDescription() + ":" + esp.getQt() + " // " + esp.getQt() * esp.getValeur() + "\n";
        }
        ret += "Pour un total de: " + this.getTotal() +"€ \n";
                
        return ret;
    }
}
