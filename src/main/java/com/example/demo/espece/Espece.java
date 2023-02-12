package com.example.demo.espece;

public abstract class Espece{
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
    public Espece(double val, String des, int quantite){
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
