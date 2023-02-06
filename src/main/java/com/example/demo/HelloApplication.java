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
        Caisse laPetiteCaisse = new Caisse();
        laPetiteCaisse.ajouter(new Caisse.Billet5(2));
        laPetiteCaisse.ajouter(new Caisse.Billet10(1));
        System.out.println(laPetiteCaisse.decoupageMonnaie(26));
        System.out.println(laPetiteCaisse);
    }

    public static void main(String[] args) {
        launch();
    }
}