package ui;

import database.Database;
import database.TulosDao;
import domain.Pelitilanne;
import domain.Puyo;
import domain.Tulos;
import domain.Vari;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    Pelitilanne tilanne = new Pelitilanne(6, 13);
    int leveys = tilanne.palautaLeveys();
    int korkeus = tilanne.palautaKorkeus();
    int sade = 20;
    boolean paussilla = true;
    int nopeusluokka=0;
    
    static File dbFile = new File("huipputulokset.db");
    static Database database = new Database("jdbc:sqlite:"+dbFile.getAbsolutePath());
    static TulosDao tulosDao = new TulosDao(database);
    static List<Tulos> huipputulokset = new ArrayList<>();
    
    public void start(Stage ikkuna){
        //Alkuvalikko
        GridPane alku = new GridPane();
        alku.setHgap(5);
        alku.setVgap(10);
        alku.setMinSize(2*sade+2*sade*leveys, 2*sade+2*sade*korkeus);
        Scene alkunakyma = new Scene(alku);
        
        Label ohjeteksti = new Label("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n"
                + "Ohjeet:\nYritä tehdä neljän samanvärisen Puyon sarjoja, jolloin ne katoavat ja antavat pisteitä!\n"
                + "Nuolinäppäimillä tippuvia Puyoja voidaan siirtää sivusuunnassa ja Enterillä niitä voi kääntää myötäpäivään.\n"
                + "Ylös-näppäin tiputtaa Puyot nopeasti maahan.\n"
                + "Pysäytä-näppäin pysäyttää pelin, ja Aloita alusta -näppäin aloittaa pelin alusta.\n"
                + "Voit aina palata aloitusruutuun Alkuvalikkoon-näppäimellä.");
        
        String huipputulosteksti = "Huipputulokset:\n";
        
        //Tämä vaatii ehkä hieman hienosäätöä...
        Tulos tulos1 = new Tulos(0, 0, "");
        Tulos tulos2 = new Tulos(0, 0, "");
        Tulos tulos3 = new Tulos(0, 0, "");
        
        if(this.huipputulokset.size()>=1){
            tulos1 = this.huipputulokset.get(0);
            huipputulosteksti += "1. "+tulos1+"\n";
        }
        if(this.huipputulokset.size()>=2){
            tulos2 = this.huipputulokset.get(1);
            huipputulosteksti += "2. "+tulos2+"\n";
        }
        if(this.huipputulokset.size()>=3){
            tulos3 = this.huipputulokset.get(2);
            huipputulosteksti += "3. "+tulos3+"\n";
        }
        Label kolmenkarki = new Label(huipputulosteksti);
        
        Button aloita = new Button("Aloita");
        Button huipputuloksiin = new Button("Huipputulokset");
        
        alku.add(aloita, 1, 1);
        alku.add(kolmenkarki, 1, 0);
        alku.add(huipputuloksiin, 1, 2);
        alku.add(ohjeteksti, 1, 3);
        
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
            this.tilanne = new Pelitilanne(6, 13);
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
    
    public static void main(String[] args) throws Exception{
        huipputulokset = tulosDao.findAll();
        launch(PuyoPuyoUi.class);
    }
    
    public void init() throws SQLException{
        try{
            File dbFile = new File("huipputulokset.db");
            if(dbFile.exists()){
                return;
            }
        }catch(Exception e){
            System.out.println("Virhe: "+e.getMessage());
        }
        
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:huipputulokset.db")){
            if (conn != null) {
                System.out.println("Uusi tietokanta on luotu.");
                
                PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Tulos("
                        + "id integer PRIMARY KEY, nimi varchar(200), tulos integer)");
                stmt.execute();
                
                PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO Tulos(nimi, tulos)"
                        + " VALUES('Ville', 2000)");
                PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO Tulos(nimi, tulos)"
                        + " VALUES('Pekka', 1500)");
                PreparedStatement stmt4 = conn.prepareStatement("INSERT INTO Tulos(nimi, tulos)"
                        + " VALUES('Anni', 1200)");
                stmt2.execute();
                stmt3.execute();
                stmt4.execute();
                
                stmt.close();
                stmt2.close();
                stmt3.close();
                stmt4.close();
                conn.close();
            }
        }catch(SQLException e){
            System.out.println("Virhe: " + e.getMessage());
        }
    }
}
