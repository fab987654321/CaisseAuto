package com.example.demo;

import com.example.demo.espece.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        String basePath = System.getProperty("user.dir");
        String[] lImgMonnaie = {"1cent.png","2cent.png","5cent.png","10cent.png","20cent.png","50cent.png","1euro.png","2euro.png","5euro.png","10euro.png","20euro.png","50euro.png","100euro.png","200euro.png","500euro.png"};
        String[] lImgAction = {"ajouter.png","retirer.png","supprimer.png"};
        List<Button> btns = new ArrayList<>();

        btns.addAll(imgAvecBouton(lImgMonnaie,basePath+"/img/clavier/espece/"));
        btns.addAll(imgAvecBouton(lImgAction,basePath+"/img/clavier/action/"));


        //Creating a Grid Pane
        GridPane gridPane = new GridPane();

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        ////////Pour découper au mieux la grille
        //nbElements % x, chercher le plus proche de 0 (ou 0) avec 1 < x < nbElement
        HashMap<Integer, Integer> hashGrillage = new HashMap<Integer, Integer>();
        int nbElement = btns.size();
        int tmpGrillage ;

        for (int i = 2; i < nbElement; i++) {
            tmpGrillage = nbElement % i;

            hashGrillage.put(i,tmpGrillage);

            if (tmpGrillage != 0)
                break;
        }

        //Recupère la plus petite valeur de la liste
        int maxCol = hashGrillage.entrySet()
                .stream()
                .filter(entry -> Collections.min(hashGrillage.values()) == entry.getValue())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(0);

        //Pour créer la grille
        int colonne = 0;
        int ligne = 0;
        //Ajoute les éléments à la grille
        for (Button b:btns) {
            if (colonne > maxCol) {
                colonne = 0;
                ligne++;
            }
            gridPane.add(b,colonne,ligne);
            colonne++;
        }
        ////////

        VBox  root = new VBox ();
        Label lMonnaie = new Label("Monnaie");
        root.getChildren().addAll(lMonnaie,gridPane);

        Scene scene = new Scene(root, 1280, 720, Color.GREY);
        primaryStage.setTitle("Dev gestion monnaie");
        primaryStage.setScene(scene);

        primaryStage.show();


        /*
        //Gestion de la monnaie
        Caisse laPetiteCaisse = new Caisse();
        //Centimes
        laPetiteCaisse.ajouter(new Cent1(1));
        laPetiteCaisse.ajouter(new Cent2(0));
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



        double[] valeurAtester = {121.95,126,126.35,32.26,2.65,8.21,8.25,0};

        for (double val:valeurAtester)
            System.out.println(val+"::" + laPetiteCaisse.decoupageArgent(val));


        System.out.println(laPetiteCaisse);
         */
    }

    public static void main(String[] args) {
        launch();
    }

    private List<Button> imgAvecBouton(String[] lImg,String Path){

        List<Button> btns = new ArrayList<>();
        ImageView tmpImgView ;
        Button tmpButton ;
        //Mets les images dans les boutons
        for (String strPath:lImg) {
            //Créer l'image
            tmpImgView = new ImageView(Path+strPath);
            //Force la taille @TODO mettre avec un auto resize
            tmpImgView.setFitHeight(100);
            tmpImgView.setPreserveRatio(true);

            //Créer le bouton avec l'image et le met dans la liste
            tmpButton = new Button();
            tmpButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Hi there! You clicked me!");
                }
            });
            btns.add(tmpButton);
            btns.get(btns.size() -1).setGraphic(tmpImgView);
        }
        return btns;
    }
}