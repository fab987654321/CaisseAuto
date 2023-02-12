package com.example.demo;

import com.example.demo.espece.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
        laPetiteCaisse.ajouter(new Cent1(1));
        laPetiteCaisse.ajouter(new Cent2(4));
        laPetiteCaisse.ajouter(new Cent5(0));
        laPetiteCaisse.ajouter(new Cent10(6));
        laPetiteCaisse.ajouter(new Cent20(2));
        laPetiteCaisse.ajouter(new Cent50(6));

        //Pieces euros
        laPetiteCaisse.ajouter(new Piece1(3));
        laPetiteCaisse.ajouter(new Piece2());

        //Billets
        laPetiteCaisse.ajouter(new Billet5(2));
        laPetiteCaisse.ajouter(new Billet10(7));
        laPetiteCaisse.ajouter(new Billet20());
        laPetiteCaisse.ajouter(new Billet50(2));
        laPetiteCaisse.ajouter(new Billet100());
        laPetiteCaisse.ajouter(new Billet200());

        System.out.println(laPetiteCaisse);

        double[] valeurAtester = {121.95,126,126.35,32.26,2.65,8.21,8.25,0};

        for (double val:valeurAtester)
            System.out.println(val+"::" + laPetiteCaisse.decoupageArgent(val));


        System.out.println(laPetiteCaisse);
    }

    public static void main(String[] args) {
        launch();
    }
}