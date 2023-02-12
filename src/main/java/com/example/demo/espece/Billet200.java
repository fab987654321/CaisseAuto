package com.example.demo.espece;

public class Billet200 extends Espece {
    public Billet200() {
        super(200, "Billet de 200€", 1);
    }

    public Billet200(int quantite) {
        super(200, "Billet de 200€", quantite);
    }
}
