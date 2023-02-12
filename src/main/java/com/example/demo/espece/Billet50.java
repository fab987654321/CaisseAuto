package com.example.demo.espece;

public class Billet50 extends Espece {
    public Billet50() {
        super(50, "Billet de 50€", 1);
    }

    public Billet50(int quantite) {
        super(50, "Billet de 50€", quantite);
    }
}
