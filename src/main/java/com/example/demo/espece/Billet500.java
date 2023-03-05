package com.example.demo.espece;

public class Billet500 extends Espece {
    public Billet500() {
        super(500, "Billet de 500€", 1);
    }

    public Billet500(int quantite) {
        super(500, "Billet de 500€", quantite);
    }
}
