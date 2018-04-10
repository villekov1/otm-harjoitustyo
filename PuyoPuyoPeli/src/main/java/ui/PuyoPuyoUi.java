package ui;

import domain.Pelitilanne;
import domain.Puyo;
import domain.Tulos;
import domain.Vari;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import static javafx.application.Application.launch;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class PuyoPuyoUi extends Application{
    Pelitilanne tilanne = new Pelitilanne();
    int leveys = tilanne.palautaLeveys();
    int korkeus = tilanne.palautaKorkeus();
    int sade = 20;
    boolean paussilla = true;
    int nopeusluokka=0;
    
    Tulos tulos1 = new Tulos(100000, "Ville");
    Tulos tulos2 = new Tulos(50000, "Jaakko");
    Tulos tulos3 = new Tulos(10000, "Anni");
    
    public void start(Stage ikkuna){
        //Alkuvalikko
        GridPane alku = new GridPane();
        alku.setHgap(5);
        alku.setVgap(10);
        alku.setMinSize(2*sade+2*sade*leveys, 2*sade+2*sade*korkeus);
        Scene alkunakyma = new Scene(alku);
        
        Label huipputulokset = new Label("Huipputulokset: \n 1. "+tulos1+" \n 2. "+tulos2+"\n 3. "+tulos3);
        Button aloita = new Button("Aloita");
        Button huipputuloksiin = new Button("Huipputulokset");
        
        alku.add(aloita, 1, 1);
        alku.add(huipputulokset, 1, 0);
        alku.add(huipputuloksiin, 1, 2);
        
        //Pelinäkymä
        GridPane komponentit = new GridPane();
        Canvas ruutu = new Canvas(2*sade+2*sade*leveys, 2*sade+2*sade*korkeus);
        komponentit.setMinSize(2*sade+2*sade*leveys +100, 2*sade+2*sade*korkeus);
        GraphicsContext piirturi = ruutu.getGraphicsContext2D();
        
        Button paluunappi = new Button("Alkuvalikkoon");
        Button reset = new Button("Aloita alusta");
        Button pysayta = new Button("Pysäytä");
        Label pisteteksti = new Label("Pisteitä: "+tilanne.getPisteet());
        
        HBox ylapalkki = new HBox();
        ylapalkki.setSpacing(15);
        ylapalkki.getChildren().add(paluunappi);
        ylapalkki.getChildren().add(reset);
        ylapalkki.getChildren().add(pysayta);
        
        komponentit.add(ylapalkki, 0, 0);
        komponentit.add(ruutu, 0, 1);
        komponentit.add(pisteteksti, 1, 0);
        
        Scene pelinakyma = new Scene(komponentit);
        HashMap<KeyCode, Boolean> painetutNapit = new HashMap<>();
        
        //Lisätään painetut napit HashMappiin
        pelinakyma.setOnKeyPressed(event -> {
            painetutNapit.put(event.getCode(), Boolean.TRUE);
            if (event.getCode().equals(KeyCode.LEFT)) {
                tilanne.siirraVasemmalle();
            } 
            if(event.getCode().equals(KeyCode.RIGHT)){
                tilanne.siirraOikealle();
            }
            if(event.getCode().equals(KeyCode.ENTER)){
                tilanne.kaannaOikealle();
            }
            if(event.getCode().equals(KeyCode.W)){
                tilanne.lisaaPisteita(1000);
            }
            if(event.getCode().equals(KeyCode.UP)){
                while(!tilanne.onkoTaynna(tilanne.getTippuva().getSijaintiX(), tilanne.getTippuva().getSijaintiY())
                    || !tilanne.onkoTaynna(tilanne.getTippuvanAkseli().getSijaintiX(), tilanne.getTippuvanAkseli().getSijaintiY())){
                    tilanne.tiputaTippuvat();
                }
            }
            if(event.getCode().equals(KeyCode.P)){
                if(paussilla==true){
                    paussilla = false;
                }else{
                    paussilla = true;
                }
            }

        });
        
        pelinakyma.setOnKeyReleased(event -> {
            painetutNapit.put(event.getCode(), Boolean.FALSE);
        });
        
        aloita.setOnAction((event) -> {
            ikkuna.setScene(pelinakyma);
            paussilla = false;
        });
        paluunappi.setOnAction((event) -> {
            ikkuna.setScene(alkunakyma);
            paussilla = true;
        });
        reset.setOnAction((event) -> {
            this.tilanne = new Pelitilanne();
        });
        pysayta.setOnAction((event) -> {
            if(this.paussilla == false){
                this.paussilla = true;
            }else{
                this.paussilla = false;
            }
            
        });
        
        new AnimationTimer(){
            long edellinen = 0;
            
            @Override
            public void handle(long nykyhetki){
                nopeusluokka = (int)tilanne.getPisteet()/1000;
                piirturi.setFill(Color.WHITE);
                piirturi.fillRect(0, 0, 2*(sade+2)*leveys, 2*(sade+2)*korkeus);
                
                for(int i=0; i<tilanne.getPuyot().size(); i++){
                    Puyo puyo = tilanne.getPuyot().get(i);

                    if(puyo.getVari() == Vari.PUNAINEN){
                        piirturi.setFill(Color.RED);
                    }else if(puyo.getVari() == Vari.KELTAINEN){
                        piirturi.setFill(Color.YELLOW);
                    }else if(puyo.getVari() == Vari.VIHREA){
                        piirturi.setFill(Color.GREEN);
                    }else if(puyo.getVari() == Vari.SININEN){
                        piirturi.setFill(Color.BLUE);
                    }else if(puyo.getVari() == Vari.VIOLETTI){
                        piirturi.setFill(Color.PURPLE);
                    }

                    piirturi.fillOval(0.5*sade + 2*sade*puyo.getSijaintiX(), sade+2*sade*puyo.getSijaintiY(), 2*sade, 2*sade);
                }
                
                if(nykyhetki - edellinen < 1000000000-nopeusluokka*100000000){
                    return;
                }
                if(paussilla==false){
                    tilanne.paivita();
                    pisteteksti.setText("Pisteitä: "+tilanne.getPisteet());
                }
               
                this.edellinen = nykyhetki;
            }
            
            
        }.start();
        
        
        ikkuna.setScene(alkunakyma);
        ikkuna.show();
    }
    
    public static void main(String[] args) {
        
        launch(PuyoPuyoUi.class);
    }
}
