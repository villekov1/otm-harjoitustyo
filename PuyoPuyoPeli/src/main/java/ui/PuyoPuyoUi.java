package ui;

import database.Database;
import database.ScoreDao;
import domain.GameLogic;
import domain.Puyo;
import domain.Score;
import domain.Colour;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class PuyoPuyoUi extends Application {
    int width = 6;
    int height = 13;
    GameLogic situation = new GameLogic(width, height);
    
    int radius = 20;
    boolean pause = true;
    int speed = 0;
    String name = "";
    
    static File dbFile = new File("huipputulokset.db");
    static Database database = new Database("jdbc:sqlite:"+dbFile.getAbsolutePath());
    static ScoreDao scoreDao = new ScoreDao(database);
    static List<Score> huipputulokset = findByPoints();
    static String huipputulosteksti = "Huipputulokset:\n\n";
    
    public void start(Stage window) {
        //Alkuvalikko
        GridPane start = new GridPane();
        start.setHgap(5);
        start.setVgap(10);
        start.setMinSize(2*radius + 2*radius*width, 2*radius + 2*radius*height);
        Scene startView = new Scene(start);
        
        //Label ohjeteksti = new Label();
                
        String ohje = "Ohjeet:\nAnna nimesi tekstikenttään ja paina Aloita-näppäintä.\n"
            + "\n"
            + "Yritä tehdä neljän samanvärisen Puyon sarjoja, jolloin ne katoavat ja antavat pisteitä!\n"
            + "Voit siirtää nuolinäppäimillä tippuvia Puyoja sivusuunnassa, ja Enterillä voit kääntää niitä myötäpäivään.\n"
            + "Ylös-näppäin tiputtaa Puyot nopeasti maahan.\n"
            + "Pysäytä-näppäin pysäyttää pelin, ja Aloita alusta -näppäin aloittaa pelin alusta.\n"
            + "Voit aina palata aloitusruutuun Alkuvalikkoon-näppäimellä.\n"
            + "\nHuipputulokset-näppäintä painamalla pääset huipputulosnäkymään. "
            + "Siellä voit tarkastella tuloksia joko pisteiden tai järjestyksen perusteella järjestettynä. "
            + "Voit poistaa tuloksen klikkaamalla ensin tulosta ja sen jälkeen poista-näppäintä.";
        
        TextArea ta = new TextArea(ohje);
        ta.setEditable(false);
        ta.setWrapText(true);        
        
        this.updateHighScoreText();
        Label kolmenkarki = new Label(huipputulosteksti);
        
        TextField field = new TextField();
        Button aloita = new Button("Aloita");
        Button huipputuloksiin = new Button("Huipputulokset");
        
        start.add(kolmenkarki, 1, 0);
        start.add(field, 1, 1);
        start.add(aloita, 1, 2);
        start.add(huipputuloksiin, 1, 3);
        start.add(ta, 1, 4);
        
        //Pelinäkymä
        GridPane komponentit = new GridPane();
        Canvas ruutu = new Canvas(2*radius + 2*radius*width, 2*radius + 2*radius*height);
        komponentit.setMinSize(2*radius + 2*radius*width + 100, 2*radius + 2*radius*height);
        GraphicsContext piirturi = ruutu.getGraphicsContext2D();
        
        Button paluunappi = new Button("Alkuvalikkoon");
        Button paluunappiTuloksissa = new Button("Alkuvalikkoon");
        Button reset = new Button("Aloita alusta");
        Button pysayta = new Button("Pysäytä");
        Label pisteteksti = new Label("Pisteitä: "+situation.getPoints());
        
        HBox ylapalkki = new HBox();
        ylapalkki.setSpacing(15);
        ylapalkki.getChildren().add(paluunappi);
        ylapalkki.getChildren().add(reset);
        ylapalkki.getChildren().add(pysayta);
        
        komponentit.add(ylapalkki, 0, 0);
        komponentit.add(ruutu, 0, 1);
        komponentit.add(pisteteksti, 1, 0);
        
        Scene pelinakyma = new Scene(komponentit);
        
        //Huipputulosnäkymä
        Button delete = new Button("Poista");
        RadioButton nappi1 = new RadioButton("Pisteiden perusteella");
        RadioButton nappi2 = new RadioButton("Nimen perusteella");
        ToggleGroup joukko = new ToggleGroup();
        nappi1.setToggleGroup(joukko);
        nappi2.setToggleGroup(joukko);
        nappi1.setSelected(true);
        VBox nappijoukko = new VBox();
        nappijoukko.setSpacing(5);
        nappijoukko.getChildren().add(nappi1);
        nappijoukko.getChildren().add(nappi2);
        nappijoukko.getChildren().add(delete);
        
        ListView<String> list = new ListView<String>();
        ObservableList<String> lista = FXCollections.observableArrayList();
        
        nappi1.setOnAction((event) -> {
            lista.clear();
            findByPoints().stream().forEach(tulos -> { 
                lista.add(tulos.toString());
                list.setItems(lista);
            });
        });
        nappi2.setOnAction((event) -> {
            lista.clear();
            findByName().stream().forEach(tulos -> { 
                lista.add(tulos.toString());
                list.setItems(lista);
            });
        });
        
        delete.setOnAction((event) -> {
            String teksti = list.getSelectionModel().getSelectedItem();
            int index = teksti.indexOf(":");
            String nimi = teksti.substring(0, index);
            int tulos = Integer.parseInt(teksti.substring(index+2));
            Score score = new Score(-1, tulos, nimi);
            
            System.out.println("\nNimi: " + nimi + ", Pisteet: " + tulos);
            
            try {
                int id = scoreDao.findId(score);
                System.out.println("id: " + id);
                if(id != -1){
                    scoreDao.delete(id);
                    System.out.println("Poisto onnistui!");
                }    
            } catch(Exception e) {
                System.out.println("Jotain meni pieleen poistamisessa! Virhe: "+e.getMessage());
            }
            
            huipputuloksiin.fire();
        });
        
        GridPane tulosruutu = new GridPane();
        tulosruutu.setVgap(10);
        tulosruutu.setHgap(10);
        
        tulosruutu.add(paluunappiTuloksissa, 0, 0);
        tulosruutu.add(list, 1, 1);
        tulosruutu.add(nappijoukko, 0, 1);
        
        Scene tulosnakyma = new Scene(tulosruutu);
        
        //Lisätään painetut napit HashMappiin
        HashMap<KeyCode, Boolean> painetutNapit = new HashMap<>();
        pelinakyma.setOnKeyPressed(event -> {
            painetutNapit.put(event.getCode(), Boolean.TRUE);
            if (event.getCode().equals(KeyCode.LEFT)) {
                situation.moveLeft();
            } 
            if (event.getCode().equals(KeyCode.RIGHT)) {
                situation.moveRight();
            }
            if (event.getCode().equals(KeyCode.ENTER)) {
                situation.kaannaOikealle();
            }
            if (event.getCode().equals(KeyCode.W)) {
                situation.addPoints(1000);
            }
            //ns. "hard drop"
            if (event.getCode().equals(KeyCode.UP)) {
                while(!situation.isTheSpaceFilled(situation.getFalling().getPositionX(), situation.getFalling().getPositionY())
                    || !situation.isTheSpaceFilled(situation.getFallingAxis().getPositionX(), situation.getFallingAxis().getPositionY())) {
                    situation.dropFalling();
                }
            }
            if (event.getCode().equals(KeyCode.P)) {
                if (pause == true) {
                    pause = false;
                } else {
                    pause = true;
                }
            }

        });
        
        pelinakyma.setOnKeyReleased(event -> {
            painetutNapit.put(event.getCode(), Boolean.FALSE);
        });
        
        aloita.setOnAction((event) -> {
            if(!field.getText().isEmpty() && !field.getText().matches("( )*")){
                this.name = field.getText();
                window.setScene(pelinakyma);
                pause = false;
            }
            
        });
        paluunappi.setOnAction((event) -> {
            window.setScene(startView);
            pause = true;
        });
        paluunappiTuloksissa.setOnAction((event) -> {
            updateHighScoreText();
            kolmenkarki.setText(huipputulosteksti);
            window.setScene(startView);
            pause = true;
        });
        reset.setOnAction((event) -> {
            this.situation = new GameLogic(width, height);
        });
        pysayta.setOnAction((event) -> {
            if(this.pause == false) {
                this.pause = true;
            }else {
                this.pause = false;
            }
            
        });
        
        huipputuloksiin.setOnAction((event) -> {
            lista.clear();
            if (nappi1.isSelected()) {
                findByPoints().stream().forEach(tulos -> { 
                    lista.add(tulos.toString());
                });
            } else {
                findByName().stream().forEach(tulos -> { 
                    lista.add(tulos.toString());
                });
            }
        
            list.setItems(lista);
            window.setScene(tulosnakyma);
        });
        
        
        
        new AnimationTimer(){
            long edellinen = 0;
            
            @Override
            public void handle(long nykyhetki) {
                speed = (int)situation.getPoints()/1000;
                piirturi.setFill(Color.WHITE);
                piirturi.fillRect(0, 0, 2*(radius + 2)*width, 2*(radius + 2)*height);
                
                for (int i=0; i<situation.getPuyos().size(); i++) {
                    Puyo puyo = situation.getPuyos().get(i);

                    if (puyo.getColour() == Colour.RED) {
                        piirturi.setFill(Color.TOMATO);
                    } else if (puyo.getColour() == Colour.YELLOW) {
                        piirturi.setFill(Color.YELLOW);
                    } else if (puyo.getColour() == Colour.GREEN) {
                        piirturi.setFill(Color.LAWNGREEN);
                    } else if (puyo.getColour() == Colour.BLUE) {
                        piirturi.setFill(Color.CORNFLOWERBLUE);
                    } else if (puyo.getColour() == Colour.PURPLE) {
                        piirturi.setFill(Color.BLUEVIOLET);
                    }

                    piirturi.fillOval(0.5*radius + 2*radius*puyo.getPositionX(), radius+2*radius*puyo.getPositionY(), 2*radius, 2*radius);
                }
                
                if (nykyhetki - edellinen < 1000000000 - speed*100000000) {
                    return;
                }
                if (pause == false) {
                    situation.update();
                    pisteteksti.setText("Pisteitä: "+situation.getPoints());
                    
                    if (situation.gameOver()) {
                        Score score = new Score(-1, situation.getPoints(), name);
                        
                        try {
                            if(!name.matches("( )*") && situation.getPoints() != 0){
                                scoreDao.saveIfNotInTheDatabase(score);
                            }
                            
                        } catch(Exception e){
                            System.out.println("Jotain meni vikaan tiedon tallentamisessa.");
                        }
                        updateHighScoreText();
                        kolmenkarki.setText(huipputulosteksti);
                        window.setScene(startView);
                        field.clear();
                        name = "";
                        situation = new GameLogic(width, height);
                        pause = true;
                    }
                }
               
                this.edellinen = nykyhetki;
            }
            
            
        }.start();
        
        
        window.setScene(startView);
        window.show();
    }
    
    public static void main(String[] args) throws Exception {
        //huipputulokset = tulosDao.findAllInOrderByPoints();
        launch(PuyoPuyoUi.class);
    }
    
    public static List<Score> findByName() {
        List<Score> tulokset = new ArrayList<>();
        
        try {
            tulokset = scoreDao.findAllInOrderByName();
        } catch(Exception e) {
            System.out.println("Ei voitu etsiä tuloksia tai niitä ei ole.");
        }
        
        return tulokset;
    }
    
    public static List<Score> findByPoints() {
        List<Score> tulokset = new ArrayList<>();
        
        try{
            tulokset = scoreDao.findAllInOrderByPoints();
        } catch(Exception e) {
            System.out.println("Ei voitu etsiä tuloksia tai niitä ei ole.");
        }
        
        return tulokset;
    }
    
    public void updateHighScoreText(){
        huipputulokset = findByPoints();
        huipputulosteksti = "Huipputulokset:\n\n";
        if (this.huipputulokset.size()>=1) {
            Score tulos1 = this.huipputulokset.get(0);
            huipputulosteksti += "1. "+tulos1+"\n";
        }
        if (this.huipputulokset.size()>=2) {
            Score tulos2 = this.huipputulokset.get(1);
            huipputulosteksti += "2. "+tulos2+"\n";
        }
        if (this.huipputulokset.size()>=3) {
            Score tulos3 = this.huipputulokset.get(2);
            huipputulosteksti += "3. "+tulos3+"\n";
        }
        
    }
    
    public void init() throws SQLException {
        try {
            File dbFile = new File("huipputulokset.db");
            if(dbFile.exists()){
                Connection conn = DriverManager.getConnection("jdbc:sqlite:huipputulokset.db");
                PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Tulos("
                    + "id integer PRIMARY KEY, nimi varchar(200), tulos integer)");
                stmt.execute();
                stmt.close();
                conn.close();
                return;
            }
        } catch(Exception e) {
            System.out.println("Virhe: "+e.getMessage());
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:huipputulokset.db")) {
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
        } catch(SQLException e) {
            System.out.println("Virhe: " + e.getMessage());
        }
    }
}
