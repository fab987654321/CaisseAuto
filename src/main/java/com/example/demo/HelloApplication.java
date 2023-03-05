package com.example.demo;

import com.example.demo.espece.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class HelloApplication extends Application {
    private static HelloApplication instance;
    public static HelloApplication getInstance() {
        return instance;
    }
    public static void setInstance(HelloApplication insta) {
        HelloApplication.instance = insta;
    }

    enum Mod {
        Ajout,
        Suppression
    }

    public Mod mode = Mod.Ajout;


private class fxBtn{
    String img;
    String basePath =  System.getProperty("user.dir") + "/img/";
    Consumer<Integer> method = (a)->{System.out.println("Defaut::"+a);} ;

    fxBtn(String image,Consumer<Integer> executee,String cheminBase){
        this.img = image;
        this.method = executee;
        this.basePath = cheminBase;
    }


    public String getFullPath(){
        System.out.print(this.basePath+this.img);
        return this.basePath+this.img;
    }
}
    @Override
    public void start(Stage primaryStage) throws IOException {
        HelloApplication.setInstance(this);
        String basePathMonnaie = System.getProperty("user.dir")+"/img/clavier/espece/";
        String basePathAction = System.getProperty("user.dir")+"/img/clavier/action/";


        List<Button> btns = new ArrayList<>();
        List<fxBtn> fBtns = new ArrayList<>();
        fBtns.add(new fxBtn("1cent.png",(n->{new Cent1();}),basePathMonnaie));
        fBtns.add(new fxBtn("2cent.png",(n->{new Cent2();}),basePathMonnaie));
        fBtns.add(new fxBtn("5cent.png",(n->{new Cent5();}),basePathMonnaie));
        fBtns.add(new fxBtn("10cent.png",(n->{new Cent10();}),basePathMonnaie));
        fBtns.add(new fxBtn("20cent.png",(n->{new Cent20();}),basePathMonnaie));
        fBtns.add(new fxBtn("50cent.png",(n->{new Cent50();}),basePathMonnaie));
        fBtns.add(new fxBtn("1euro.png",(n->{new Piece1();}),basePathMonnaie));
        fBtns.add(new fxBtn("2euro.png",(n->{new Piece2();}),basePathMonnaie));
        fBtns.add(new fxBtn("5euro.png",(n->{new Billet5();}),basePathMonnaie));
        fBtns.add(new fxBtn("10euro.png",(n->{new Billet10();}),basePathMonnaie));
        fBtns.add(new fxBtn("20euro.png",(n->{new Billet20();}),basePathMonnaie));
        fBtns.add(new fxBtn("50euro.png",(n->{new Billet50();}),basePathMonnaie));
        fBtns.add(new fxBtn("100euro.png",(n->{new Billet100();}),basePathMonnaie));
        fBtns.add(new fxBtn("200euro.png",(n->{new Billet200();}),basePathMonnaie));
        fBtns.add(new fxBtn("500euro.png",(n->{new Billet500();}),basePathMonnaie));

        fBtns.add(new fxBtn("ajouter.png",(n->{System.out.println("ajouter");}),basePathAction));
        fBtns.add(new fxBtn("retirer.png",(n->{System.out.println("retirer");}),basePathAction));
        fBtns.add(new fxBtn("supprimer.png",(n->{System.out.println("supprimer");}),basePathAction));

        btns.addAll(imgAvecBouton(fBtns));
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

        //Pour remplir la grille
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
        SplitPane splitPane = new SplitPane();
        Label lMonnaie = new Label("Monnaie");
        splitPane.getItems().addAll(lMonnaie, gridPane);

        Scene scene = new Scene(splitPane, 1280, 720, Color.GREY);
        primaryStage.setTitle("Dev gestion monnaie");
        primaryStage.setScene(scene);

        primaryStage.show();


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
        laPetiteCaisse.ajouter(new Billet500());



        double[] valeurAtester = {121.95,126,126.35,32.26,2.65,8.21,8.25,0};

        for (double val:valeurAtester)
            System.out.println(val+"::" + laPetiteCaisse.decoupageArgent(val));


        System.out.println(laPetiteCaisse);

    }

    public static void main(String[] args) {
        launch();
    }

private void btnMonnaieAction(){
//@TODO Ajouter le billet à la liste des billets
    System.out.println("Ajouter le billet à la liste des billets ");
}
    private List<Button> imgAvecBouton(List<fxBtn> lImg){
        List<Button> btns = new ArrayList<>();
        ImageView tmpImgView ;
        Button tmpButton ;

        //Mets les images dans les boutons
        for (fxBtn elem:lImg) {
            //Créer l'image
            tmpImgView = new ImageView(elem.getFullPath());
            //Force la taille @TODO mettre avec un auto resize
            tmpImgView.setFitHeight(100);
            tmpImgView.setPreserveRatio(true);

            //Créer le bouton avec l'image et le met dans la liste
            tmpButton = new Button();
            tmpButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //elem.method.accept(-1);
                        HelloApplication.getInstance().btnMonnaieAction();
                }
            });
            btns.add(tmpButton);
            btns.get(btns.size() -1).setGraphic(tmpImgView);
        }
        return btns;
    }
}