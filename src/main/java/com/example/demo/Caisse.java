package com.example.demo;

import java.util.ArrayList;
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
    private List<Noeud> dejaTeste;
    public Espece getEsp(){
        return esp;
    }

    /** Savoir si valeur noeud déjà testé */
    public boolean isTeste(String className){
        boolean ret = false;
        for (Noeud nunu: dejaTeste)
            if (nunu.getEsp().getClass().getSimpleName() == className)
                ret = true;

        return ret;

    }

    /**Ajoute un noeud au rejeté pour avoir une trace*/
    public void addRejet(Noeud noeudEnfant){
        this.dejaTeste.add(noeudEnfant);
    }

    Noeud(Espece e){this.esp = e; ;}

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
    //ListeChaîné
    Noeud noeuds;

    //Info fonction de la situation
    int tailleListe = lesSous.size();
    int nbEspece = getTotalQte();

    //Variables temporaire qui varient bcp
    int choisie;
    double valeurRestante = aDecouper;

        //FIXME n'ira pas forcément a la fin de la branche car il y a dépilage transfoermer en while ?
        //Boucle sur la longueur max d'une branche
        for (int i = 0; i < nbEspece; i++) {
        //Boucle sur les possibilités
        for (int j = 0; j < tailleListe; j++) {
            //TODO avec la linkedList.get() ou un truc du genre
            //Si déjà rejeté on passe à une autre sous banche
            if (false)continue;

            //Recup valeur de notre potentiel piece/billet
            choisie = lesSous.get(j).getValeur();

            //Si valeurRestante - choisie < 0 alors on quite et on marque comme rejeté 
            if (valeurRestante - choisie < 0) {
                //Marquer comme rejeté
                //Dépiler de la liste chaîné
            }
            //Si valeurRestante - choisie > 0 et pas de possibilité en suite car pas de disponible alors on quite et on marque comme rejeté
            if(false){
                //TODO a voir si ça se fait pas lors de la prochaine boucle
            }
            //Si espece choisie plus en quantité suffisante alors on rejete
            if(false){
                //A voir si on fusionne pas avec un autre if a par pour mettre des msg d'erreur
            }
            //Attention si le premier est dépilé alors ce pb est non solvable avec notre état actuel
            if(false){
                //SI la linkedList est vide alors ya pas de solution donc on retourne une erreur
            }
            //TODO liste chaîné avec les élément Espece, lorsque l'on est bloqué dans la branche on recule d'un cran en se souvenant du pressédent résultat pour prendre une valeur plus à droite (plus faible)
            // et si tt est utilisé on remonte d'un cran et idem jusqu'à trouvé une solution ou bien jusqu'à arrivé sur un non solvable
            }


    }

            //Possible de mettre une autre monnaie ? Si oui alors on prend la plus grande, y a-t-il suffisament de monnaie ?


        //
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
            if (unTruc.getClass().getSimpleName() == esp.getClass().getSimpleName()){
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
