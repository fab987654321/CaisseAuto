package com.example.demo.espece;

public class Billet100 extends Espece {
    public Billet100() {
        super(100, "Billet de 100€", 1);
    }

    public Billet100(int quantite) {
        super(100, "Billet de 100€", quantite);
    }
}
