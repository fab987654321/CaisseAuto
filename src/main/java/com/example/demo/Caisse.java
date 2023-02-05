package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
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
    Noeud precedent;
    Noeud(Espece e,Noeud parent){this.esp = e;this.precedent = precedent; ;}

}

    private List<Espece> lesSous;

Caisse(){
    lesSous= new ArrayList<>();
    //Definie les valeurs de base et ce qu'elle accepte comme entité
    lesSous.add(new Piece2(0));
    lesSous.add(new Billet5(0));
    lesSous.add(new Billet10(0));
}
    public void decoupageMonnaie(double aDecouper){
    boolean looping = true;
        //Boucle sur qq chose
    while (looping){
        //Commencer par le plus grand/a gauche de l'arbre
break;
    }

            //Commencer par le plus grand/a gauche
            //Si valeurRestante - choisie < 0 alors on quite et on marque comme rejeté
            //Possible de mettre une autre monnaie ? Si oui alors on prend la plus grande, y a-t-il suffisament de monnaie ?


        //
    }
    public int getTotal(){
    int ret = 0;
        for (Espece t:lesSous)
            ret += t.getQt() * t.getValeur();

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
