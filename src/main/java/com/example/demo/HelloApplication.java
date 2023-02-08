package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        //Gestion de la monnaie
        Caisse laPetiteCaisse = new Caisse();
        //Centimes
        laPetiteCaisse.ajouter(new Caisse.Cent1(1));
        laPetiteCaisse.ajouter(new Caisse.Cent2(1));
        laPetiteCaisse.ajouter(new Caisse.Cent5(0));
        laPetiteCaisse.ajouter(new Caisse.Cent10(6));
        laPetiteCaisse.ajouter(new Caisse.Cent20(2));
        laPetiteCaisse.ajouter(new Caisse.Cent50(6));

        //Pieces euros
        laPetiteCaisse.ajouter(new Caisse.Piece1(3));
        laPetiteCaisse.ajouter(new Caisse.Piece2());

        //Billets
        laPetiteCaisse.ajouter(new Caisse.Billet5(2));
        laPetiteCaisse.ajouter(new Caisse.Billet10(7));
        laPetiteCaisse.ajouter(new Caisse.Billet20());
        laPetiteCaisse.ajouter(new Caisse.Billet50(2));
        laPetiteCaisse.ajouter(new Caisse.Billet100());
        laPetiteCaisse.ajouter(new Caisse.Billet200());


        System.out.println(laPetiteCaisse.decoupageArgent(121.95));

        //Traitement
        System.out.println(laPetiteCaisse.decoupageArgent(126));
        System.out.println(laPetiteCaisse.decoupageArgent(126.2));
        System.out.println(laPetiteCaisse.decoupageArgent(126.35));
        System.out.println(laPetiteCaisse.decoupageArgent(32.26));
        System.out.println(laPetiteCaisse.decoupageArgent(2.65));
        System.out.println(laPetiteCaisse.decoupageArgent(8.21));
        System.out.println(laPetiteCaisse.decoupageArgent(8.25));


        System.out.println(laPetiteCaisse);
    }

    public static void main(String[] args) {
        launch();
    }
}