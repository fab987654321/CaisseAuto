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

    /**Ajoute un noeud au rejeté pour avoir une trace*/
    public void addRejet(String nomClass){
        this.dejaTeste.add(nomClass);
    }
    public void addRejet(Espece esp){
        this.addRejet(esp.nomClass());
    }

    Noeud(Espece e){this.esp = e; this.dejaTeste = new ArrayList<>();}

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
    public void decoupageMonnaie(double aDecouper){
    //ListeChaîné instanciation et sommet de l'arbre
    LinkedList<Noeud> noeuds = new LinkedList<>();
    noeuds.add(new Noeud(null));

    //Info fonction de la situation
    int tailleListe = lesSous.size();
    int nbEspece = getTotalQte();

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
            //Recup l'espece cible
            tempoCibleEsp = lesSous.get(j);

            //Si déjà rejeté on passe à un autre type d'espece plus petit
            if (noeuds.getLast().isRejet(tempoCibleEsp))continue;
            //si pas disponible dans la caisse || plus accé pour incrémenter on rejete
            else if (tempoCibleEsp.getQt() == 0 || especeIsIncrementable(noeuds,tempoCibleEsp)) {
                noeuds.getLast().isRejet(tempoCibleEsp);
                continue;
            }

            //Recup valeur de la monnaie
            valChoisie = tempoCibleEsp.getValeur();


            //Si valeurRestante - choisie < 0 alors on quite et on marque comme rejeté 
            if (valeurRestante - valChoisie < 0) {
                noeuds.getLast().addRejet(tempoCibleEsp);
                continue;
            }
            //Si valeurRestante - choisie > 0 et pas de possibilité en suite car pas de disponible alors on quite et on marque comme rejeté
            else if(false){
                //TODO a voir si ça se fait pas lors de la prochaine boucle
            }
            //Si valeurRestante - valChoisie == 0 alors solution trouvé, on quite tt car solution la plus à gauche possible
            else if (valeurRestante - valChoisie == 0) {
                noeuds.add(new Noeud(tempoCibleEsp));
                looping = false;
                break;
            }

            //Si la linkedList est vide alors ya pas de solution
            //SI tou visité pour le sommet alors pas de solution
            //Attention si le premier est dépilé alors ce pb est non solvable avec notre état actuel
            if(false){
                //retourne une erreur/msg
            }
            //TODO liste chaîné avec les élément Espece, lorsque l'on est bloqué dans la branche on recule d'un cran en se souvenant du pressédent résultat pour prendre une valeur plus à droite (plus faible)
            // et si tt est utilisé on remonte d'un cran et idem jusqu'à trouvé une solution ou bien jusqu'à arrivé sur un non solvable
            }//For
            looping = false;

    }//While

            //Possible de mettre une autre monnaie ? Si oui alors on prend la plus grande, y a-t-il suffisament de monnaie ?

    }//Methode
    /**
     * Reoturn true si il y a moins d'espece utilisé que disponible avec (utilisé < dispo )
     * */
    private boolean especeIsIncrementable(LinkedList<Noeud> lnoeud, String nomClass){
    boolean ret = false;
    int nbElement = 0;
        for (Noeud noued:lnoeud)
             if (noued.esp.nomClass() == nomClass) nbElement++;

        if (getTotalQte() > nbElement)
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

    public int getTotalQte(){
        int ret = 0;
        for (Espece t:lesSous)
            ret += t.getQt();

        return ret;
    }

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
