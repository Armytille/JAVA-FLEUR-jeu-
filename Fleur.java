package essai;

/* Le but de ce "jeu" est de cliquer sur un carré de couleur,
   de la comparer aux différents éléments qui composent une fleur
   (dans l'ordre : Centre,Petale,Tige,Feuille)
   puis de cliquer sur la case correspondante pour la remplir,
   juste un test pour les yeux en sommes...
*/

/* Zarca Dan 13402677 L2-B */

import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Fleur extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {

       primaryStage.setResizable(false);
       primaryStage.setFullScreen(false);
       Scene scene = new Scene(new Group(), 1080, 720);
       scene.setFill(Color.AZURE); // Le ciel

       /* Les collections ou sont stockées les couleurs utilisées */
       List<Color> listPetale = new ArrayList<>();
       List<Color> listFeuille = new ArrayList<>();
       List<Color> listTige = new ArrayList<>();
       List<Color> listCentre = new ArrayList<>();

       Group root = (Group) scene.getRoot();

       Circle soleil = new Circle(80,Color.web("YELLOW", 0.7)); // Un "soleil" bien inutile.
       soleil.setCenterX(scene.getWidth()- 80);
       soleil.setCenterY(80);

       double RANGE = 12;
       double height = scene.getHeight()*0.83-0.707*15;
       for(double y = 0; y < 22 ;y++){

           /* Les couleurs sont crées pseudo-aléatoirement en utilisant Math.random */
            Color petale = new Color(Math.random(),Math.random(),Math.random(),1.0);
            Color center = new Color(Math.random(),Math.random(),Math.random(),1.0);
            Color tigeC = new Color(Math.random(),Math.random(),Math.random(),1.0);
            Color feuille = new Color(Math.random(),Math.random(),Math.random(),1.0);

            /* Les couleurs crées sont stockées dans leur liste respective*/
            listTige.add(tigeC);
            listPetale.add(petale);
            listFeuille.add(feuille);
            listCentre.add(center);

            /* Création des feuilles*/
            Ellipse ellG = new Ellipse(18+ 50*y-0.707*15,height,15,5);
            ellG.getTransforms().add(new Rotate(45,18+ 50*y-0.707*15,height));
            ellG.setFill(feuille);
            root.getChildren().add(ellG);

            Ellipse ellD = new Ellipse(22+ 50*y+0.707*15,height,5,15);
            ellD.getTransforms().add(new Rotate(45,22+ 50*y+0.707*15,height));
            ellD.setFill(feuille);
            root.getChildren().add(ellD);

            for(double x = 0 ; x < RANGE; x++){

                Rectangle tige = new Rectangle(18+ 50*y, scene.getHeight()*0.7745, 4, scene.getHeight()*0.83);
                tige.setFill(tigeC);

                Circle circle = new Circle(RANGE/2,petale);
                Circle centerCircle = new Circle(RANGE/2,center);

                circle.setCenterX((20 + (y * 50)  + RANGE * Math.cos(x*2*Math.PI/RANGE)));
                circle.setCenterY((scene.getHeight()*0.75 + RANGE * Math.sin(x*2*Math.PI/RANGE)));

                centerCircle.setCenterX(20 + (y * 50));
                centerCircle.setCenterY(scene.getHeight() * 0.75 );

                root.getChildren().add(circle);
                root.getChildren().add(centerCircle);
                root.getChildren().add(tige);

            }
       }
        Rectangle ground = new Rectangle(0, scene.getHeight()*0.83, scene.getWidth()+20,scene.getHeight());

        ground.setFill(Color.GREEN);
        root.getChildren().add(ground);
        root.getChildren().add(soleil);

        for(int x =0 ; x < 22 ; x++){
           /* Création des cases à remplir */
           Rectangle formulaire = new Rectangle(8.5+50*x, 408, 23, 23);
           formulaire.setFill(Color.WHITE);
           formulaire.setStroke(Color.BLACK);
           formulaire.setStrokeWidth(3);
           formulaire.setOnMousePressed(rectangleOnMousePressedEventHandler);
           formulaire.setId(listCentre.get(x).toString()); // Une manière d'ajouter un "tag" sur un rectangle pour lui associer une Color.
           root.getChildren().add(formulaire);

           Rectangle formulaire2 = new Rectangle(8.5+50*x, 431, 23, 23);
           formulaire2.setFill(Color.WHITE);
           formulaire2.setStroke(Color.BLACK);
           formulaire2.setStrokeWidth(3);
           formulaire2.setId(listPetale.get(x).toString());
           formulaire2.setOnMousePressed(rectangleOnMousePressedEventHandler);
           root.getChildren().add(formulaire2);

           Rectangle formulaire3 = new Rectangle(8.5+50*x, 454, 23, 23);
           formulaire3.setFill(Color.WHITE);
           formulaire3.setStroke(Color.BLACK);
           formulaire3.setStrokeWidth(3);
           formulaire3.setId(listTige.get(x).toString());
           formulaire3.setOnMousePressed(rectangleOnMousePressedEventHandler);
           root.getChildren().add(formulaire3);

           Rectangle formulaire4 = new Rectangle(8.5+50*x, 473, 23, 23);
           formulaire4.setFill(Color.WHITE);
           formulaire4.setStroke(Color.BLACK);
           formulaire4.setStrokeWidth(3);
           formulaire4.setId(listFeuille.get(x).toString());
           formulaire4.setOnMousePressed(rectangleOnMousePressedEventHandler);
           root.getChildren().add(formulaire4);
        }

        /* Les collections de Color sont shuffle pour ajouter une difficulté supplémentaire...*/
        Collections.shuffle(listCentre);
        Collections.shuffle(listTige);
        Collections.shuffle(listPetale);
        Collections.shuffle(listFeuille);

        for(int x =0;x<22;x++){
            /* Création des rectangles contenant les couleurs contenues dans les collections*/
           Rectangle testF = new Rectangle(20*x+ 500, 270, 20, 20);
           testF.setFill(listFeuille.get(x));
           testF.setOnMousePressed(rectangleOnMousePressedEventHandler);

           root.getChildren().add(testF);

           Rectangle testC = new Rectangle(20*x+500, 210, 20, 20);
           testC.setFill(listCentre.get(x));
           testC.setOnMousePressed(rectangleOnMousePressedEventHandler);
           root.getChildren().add(testC);

           Rectangle testP = new Rectangle(20*x+500, 230, 20, 20);
           testP.setFill(listPetale.get(x));
           testP.setOnMousePressed(rectangleOnMousePressedEventHandler);
           root.getChildren().add(testP);

           Rectangle testT = new Rectangle(20*x+500, 250, 20, 20);
           testT.setFill(listTige.get(x));
           testT.setOnMousePressed(rectangleOnMousePressedEventHandler);
           root.getChildren().add(testT);

           /* On vide les Collections de Color après usage*/
           if(x==21){
                listTige.clear();
                listPetale.clear();
                listFeuille.clear();
                listCentre.clear();
           }
        }

        primaryStage.setTitle("Un jeu bien ennuyeux!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    Paint premierClic = null;
    Rectangle tmp;

   EventHandler<MouseEvent> rectangleOnMousePressedEventHandler =
        new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {

            /* On récupère la couleur du 1er rectangle cliqué qui a un ID égal à null */
            if(premierClic == null && ((Rectangle)(t.getSource())).getId() == null){
                premierClic = ((Rectangle)(t.getSource())).getFill();
                ((Rectangle)(t.getSource())).mouseTransparentProperty().setValue(Boolean.TRUE); // Le rectangle ne sera plus cliquable.
                tmp =  ((Rectangle)(t.getSource()));
            }

            else
                /* Maintenant c'est l'inverse, on clique sur une rectangle avec un ID != null puis on compare la couleur du premierClic avec la couleur convertie de l'ID du rectangle cliqué */
                if(premierClic != null && ((Rectangle)(t.getSource())).getId() != null && Color.valueOf(((Rectangle)(t.getSource())).getId()).equals(Color.valueOf(premierClic.toString())) ){
                     ((Rectangle)(t.getSource())).setFill(premierClic); // On change la couleur du rectangle cliqué.
                     tmp.visibleProperty().setValue(Boolean.FALSE); // Le rectangle ne sera plus visible.
                       premierClic=null;    // premierClic retrouve sa valeur null et peux donc reservir.
                }
        }
    };
}
