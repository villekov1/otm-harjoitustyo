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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
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

/**
 * A class that draws the user interface of the game. This class doesn't contain any game logic.
 */
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
    static List<Score> highscores = findByPoints();
    static String highScoresText = "TOP-3: \n\n";
    
    public void start(Stage window) {
        window.setTitle("Puyo Puyo -peli");
        //Alkuvalikko
        GridPane start = new GridPane();
        start.setHgap(5);
        start.setVgap(10);
        start.setMinSize(2*radius + 2*radius*width, 2*radius + 2*radius*height);
        Scene startView = new Scene(start);
                
        String ohje = "Ohjeet:\nAnna nimesi tekstikenttään ja paina Aloita-näppäintä. "
            + "Voit halutessasi säätää pelikentän kokoa, mutta suosituskoko on 6x13.\n"
            + "\n"
            + "Yritä tehdä neljän samanvärisen Puyon sarjoja, jolloin ne katoavat ja antavat pisteitä!\n"
            + "Voit siirtää nuolinäppäimillä tippuvia Puyoja sivusuunnassa.\n"
            + "Enterillä tai S-näppäimellä voit kääntää niitä myötäpäivään, ja Backspace- tai A-näppäimellä vastapäivään.\n"
            + "Ylös-näppäin tiputtaa Puyot nopeasti maahan.\n"
            + "Pysäytä-näppäin pysäyttää pelin, ja Aloita alusta -näppäin aloittaa pelin alusta.\n"
            + "Voit aina palata aloitusruutuun Alkuvalikkoon-näppäimellä. Huomaa, että tällöin edistymisesi katoaa!\n"
            + "\nHuipputulokset-näppäintä painamalla pääset huipputulosnäkymään. "
            + "Siellä voit tarkastella tuloksia joko pisteiden tai järjestyksen perusteella järjestettynä. "
            + "Voit poistaa tuloksen klikkaamalla ensin tulosta ja sen jälkeen poista-näppäintä.";
        
        TextArea ta = new TextArea(ohje);
        ta.setEditable(false);
        ta.setWrapText(true);        
        
        this.updateHighScoreText();
        Label kolmenkarki = new Label(highScoresText);
        
        TextField field = new TextField();
        Button startButton = new Button("Aloita");
        Button highScoreButton = new Button("Huipputulokset");
        
        VBox sliderbar = new VBox();
        Slider sliderX = new Slider();
        sliderX.setMin(3);
        sliderX.setMax(12);
        sliderX.setValue(6);
        Slider sliderY = new Slider();
        sliderY.setMin(6);
        sliderY.setMax(16);
        sliderY.setValue(13);
        
        Label widthText = new Label("Leveys: " + (int)sliderX.getValue());
        Label heightText = new Label("Korkeus: " + (int)sliderY.getValue());
        
        sliderbar.getChildren().add(widthText);
        sliderbar.getChildren().add(sliderX);
        sliderbar.getChildren().add(heightText);
        sliderbar.getChildren().add(sliderY);
        
        start.add(kolmenkarki, 1, 0);
        start.add(sliderbar, 2, 1);
        start.add(field, 1, 1);
        start.add(startButton, 1, 2);
        start.add(highScoreButton, 1, 3);
        start.add(ta, 1, 4);
        
        //Gameview
        GridPane components = new GridPane();
        Canvas ruutu = new Canvas(4*radius + 2*radius*width, 2*radius + 2*radius*height);
        Canvas nextPuyos = new Canvas(3*radius, 5*radius);
        components.setMinSize(2*radius + 2*radius*width + 100, 2*radius + 2*radius*height);
        GraphicsContext piirturi = ruutu.getGraphicsContext2D();
        GraphicsContext nextDrawer = nextPuyos.getGraphicsContext2D();
        
        Button paluunappi = new Button("Alkuvalikkoon");
        Button paluunappiTuloksissa = new Button("Alkuvalikkoon");
        Button reset = new Button("Aloita alusta");
        Button pysayta = new Button("Pysäytä");
        Label pisteteksti = new Label("Pisteitä: " + situation.getPoints());
        
        HBox ylapalkki = new HBox();
        ylapalkki.setSpacing(15);
        ylapalkki.getChildren().add(paluunappi);
        ylapalkki.getChildren().add(reset);
        ylapalkki.getChildren().add(pysayta);
        
        components.setHgap(10);
        components.add(ylapalkki, 0, 0);
        components.add(ruutu, 0, 1);
        components.add(pisteteksti, 1, 0);
        components.add(nextPuyos, 1, 1);
        
        Scene gameView = new Scene(components);
        
        //Highscore view
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
        
        sliderX.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                width = new_val.intValue();
                widthText.setText("Leveys: "+new_val.intValue());  
            }
        });
        sliderY.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                height = new_val.intValue();
                heightText.setText("Korkeus: "+new_val.intValue());
            }
        });
        nappi1.setOnAction((event) -> {
            lista.clear();
            int i = 0;
            findByPoints().stream().forEach(tulos -> { 
                lista.add((lista.size() + 1) + ". " + tulos.toString());
                list.setItems(lista);
            });
        });
        nappi2.setOnAction((event) -> {
            lista.clear();
            findByName().stream().forEach(tulos -> { 
                lista.add((lista.size() + 1) + ". " + tulos.toString());
                list.setItems(lista);
            });
        });
        delete.setOnAction((event) -> {
            String teksti = list.getSelectionModel().getSelectedItem();
            int index0 = teksti.indexOf(".");
            int index = teksti.indexOf(":");
            String nimi = teksti.substring(index0 + 2, index);
            int tulos = Integer.parseInt(teksti.substring(index + 2));
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
                System.out.println("Jotain meni pieleen poistamisessa! Virhe: " + e.getMessage());
            }
            
            highScoreButton.fire();
        });
        
        GridPane tulosruutu = new GridPane();
        tulosruutu.setVgap(10);
        tulosruutu.setHgap(10);
        
        tulosruutu.add(paluunappiTuloksissa, 0, 0);
        tulosruutu.add(list, 1, 1);
        tulosruutu.add(nappijoukko, 0, 1);
        
        Scene highScoreView = new Scene(tulosruutu);
        
        //Let's add the pressed buttons to the HashMap
        HashMap<KeyCode, Boolean> painetutNapit = new HashMap<>();
        gameView.setOnKeyPressed(event -> {
            painetutNapit.put(event.getCode(), Boolean.TRUE);
            if (event.getCode().equals(KeyCode.LEFT)) {
                situation.moveLeft();
            } 
            if (event.getCode().equals(KeyCode.RIGHT)) {
                situation.moveRight();
            }
            if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.S)) {
                situation.turnRight();
            }
            if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.A)) {
                situation.turnLeft();
            }
            if (event.getCode().equals(KeyCode.W)) {
                //This is only for testing purposes
                situation.addPoints(1000);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                situation.hardDrop();
            }
            if (event.getCode().equals(KeyCode.P)) {
                if (pause == true) {
                    pause = false;
                } else {
                    pause = true;
                }
            }
        });
        
        gameView.setOnKeyReleased(event -> {
            painetutNapit.put(event.getCode(), Boolean.FALSE);
        });
        
        startButton.setOnAction((event) -> {
            if(!field.getText().isEmpty() && !field.getText().matches("( )*")){
                this.name = field.getText();
                situation = new GameLogic(width, height);
                //2*(radius + 2)*width, 2*(radius + 2)*height
                ruutu.setWidth(2 * radius + 2 * (radius + 2) * width);
                ruutu.setHeight(2 * radius + 2 * (radius + 2) * height);
                window.setScene(gameView);
                window.setMinWidth(2 * (radius + 2) * width + 150);
                window.setMinHeight(2 * (radius + 2) * height + 100);
                pause = false;
            }
            
        });
        paluunappi.setOnAction((event) -> {
            window.setScene(startView);
            window.setMinWidth(600);
            pause = true;
        });
        paluunappiTuloksissa.setOnAction((event) -> {
            updateHighScoreText();
            kolmenkarki.setText(highScoresText);
            window.setScene(startView);
            window.setMinWidth(600);
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
        
        highScoreButton.setOnAction((event) -> {
            lista.clear();
            if (nappi1.isSelected()) {
                findByPoints().stream().forEach(tulos -> {
                    lista.add((lista.size() + 1) + ". " + tulos.toString());
                });
            } else {
                findByName().stream().forEach(tulos -> { 
                    lista.add((lista.size() + 1) + ". " + tulos.toString());
                });
            }
        
            list.setItems(lista);
            window.setScene(highScoreView);
        });
        
        new AnimationTimer(){
            long edellinen = 0;
            
            @Override
            public void handle(long nykyhetki) {
                speed = (int)situation.getPoints()/1000;
                piirturi.setFill(Color.FLORALWHITE);
                piirturi.fillRect(0, 0, 2 * radius + 2 * (radius + 2) * width, 2 * (radius + 2) * height);
                
                ArrayList<Puyo> drawablePuyos = new ArrayList<>();
                situation.getPuyos().stream().forEach(puyo -> { 
                    drawablePuyos.add(puyo);
                });
                drawablePuyos.addAll(situation.nextPuyos());
                
                for (int i = 0; i < drawablePuyos.size(); i++) {
                    Puyo puyo = drawablePuyos.get(i);
                    
                    piirturi.setFill(Color.GRAY);
                    if(puyo != situation.nextAxis && puyo != situation.nextFalling){
                        piirturi.fillOval(0.5*radius + 2*radius*puyo.getPositionX(), radius+2*radius*puyo.getPositionY(), 2*radius, 2*radius);
                    }
                    
                    if (puyo.getColour() == Colour.RED) {
                        piirturi.setFill(Color.TOMATO);
                    } else if (puyo.getColour() == Colour.YELLOW) {
                        piirturi.setFill(Color.color(255 / 255, 255 / 255, 102 / 255));
                    } else if (puyo.getColour() == Colour.GREEN) {
                        piirturi.setFill(Color.LAWNGREEN);
                    } else if (puyo.getColour() == Colour.BLUE) {
                        piirturi.setFill(Color.CORNFLOWERBLUE);
                    } else if (puyo.getColour() == Colour.PURPLE) {
                        piirturi.setFill(Color.BLUEVIOLET);
                    }

                    if (puyo == situation.nextFalling) {
                        piirturi.fillOval(2 * (radius + 1.5) * width, radius, 2*radius, 2*radius);
                    } else if (puyo == situation.nextAxis) {
                        piirturi.fillOval(2 * (radius + 1.5) * width, 3*radius, 2*radius, 2*radius);
                    } else {
                        piirturi.fillOval(0.5*radius + 2*radius*puyo.getPositionX() -1 , radius+2*radius*puyo.getPositionY() - 1, 2*radius - 1, 2*radius - 1);
                    }
                }

                if (nykyhetki - edellinen < 1000000000 - speed*100000000) {
                    return;
                }
                if (pause == false) {
                    situation.update();
                    pisteteksti.setText("Pisteitä: "+situation.getPoints());
                    
                    if (situation.gameOver()) {
                        pause = true;
                        Score score = new Score(-1, situation.getPoints(), name);
                        
                        try {
                            if (!name.matches("( )*") && situation.getPoints() != 0) {
                                scoreDao.saveIfNotInTheDatabase(score);
                            }
                            
                        } catch(Exception e){
                            System.out.println("Jotain meni vikaan tiedon tallentamisessa.");
                        }
                        updateHighScoreText();
                        kolmenkarki.setText(highScoresText);
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
    
    /**
    * The method calls scoreDao and finds all of the scores ordered by name
    * @return   List that contains all of the scores ordered by name
    */
    public static List<Score> findByName() {
        List<Score> tulokset = new ArrayList<>();
        try {
            tulokset = scoreDao.findAllInOrderByName();
        } catch(Exception e) {
            System.out.println("Ei voitu etsiä tuloksia tai niitä ei ole.");
        }
        return tulokset;
    }
    
    /**
    * The method that calls scoreDao and finds all of the scores ordered by points.
    * @return   List that contains all of the scores ordered by points.
    */
    public static List<Score> findByPoints() {
        List<Score> tulokset = new ArrayList<>();        
        try{
            tulokset = scoreDao.findAllInOrderByPoints();
        } catch(Exception e) {
            System.out.println("Ei voitu etsiä tuloksia tai niitä ei ole.");
        }
        return tulokset;
    }
    
    /**
    * The method updates the Highscoretext that is shown on the main menu.
    */
    public void updateHighScoreText(){
        highscores = findByPoints();
        highScoresText = "TOP-3: \n\n";
        if (this.highscores.size() >= 1) {
            Score tulos1 = this.highscores.get(0);
            highScoresText += "1. "+tulos1+"\n";
        }
        if (this.highscores.size() >= 2) {
            Score tulos2 = this.highscores.get(1);
            highScoresText += "2. "+tulos2+"\n";
        }
        if (this.highscores.size() >= 3) {
            Score tulos3 = this.highscores.get(2);
            highScoresText += "3. "+tulos3+"\n";
        } 
    }
    
    @Override
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
                stmt.close();
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("Virhe: " + e.getMessage());
        }
    }
}
