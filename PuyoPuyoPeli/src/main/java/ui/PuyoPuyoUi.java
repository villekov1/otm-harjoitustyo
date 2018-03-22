package ui;

import domain.Pelitilanne;
import domain.Puyo;
import domain.Vari;
import java.util.ArrayList;
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class PuyoPuyoUi extends Application{
    Pelitilanne tilanne = new Pelitilanne();
    int leveys = tilanne.palautaLeveys();
    int korkeus = tilanne.palautaKorkeus();
    int sade = 20;
    
    
    public void start(Stage ikkuna){
        GridPane komponentit = new GridPane();
        
        Canvas ruutu = new Canvas(sade+2*sade*leveys, sade+2*sade*korkeus);
        GraphicsContext piirturi = ruutu.getGraphicsContext2D();
        
        Button paluunappi = new Button("Alkuvalikkoon");
        
        komponentit.add(ruutu, 0, 1);
        komponentit.add(paluunappi, 0, 0);
        
        new AnimationTimer(){
            long edellinen = 0;
            
            @Override
            public void handle(long nykyhetki){
                if(nykyhetki - edellinen < 1000000000){
                    return;
                }
                tilanne.paivita();
                
                piirturi.setFill(Color.WHITE);
                piirturi.fillRect(0, 0, sade+2*sade*leveys, sade+2*sade*korkeus); 
                
                for(int i=0; i<leveys; i++){
                    for(int j=0; j<korkeus; j++){
                        if(tilanne.onkoTaynna(i, j) == true){
                            Puyo puyo = tilanne.palautaTilanne().get(i).get(j);
                            
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

                            piirturi.fillOval(2*sade+2*sade*puyo.getSijaintiX(), 2*sade+2*sade*puyo.getSijaintiY(), 2*sade, 2*sade);

                        }


                    }
                }
                this.edellinen = nykyhetki;
            }
            
            
        }.start();
        
        Scene pelinakyma = new Scene(komponentit);
        ikkuna.setScene(pelinakyma);
        ikkuna.show();
    }
    
    public static void main(String[] args) {
        
        launch(PuyoPuyoUi.class);
    }
}
