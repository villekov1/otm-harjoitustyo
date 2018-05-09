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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    String ohje = "Ohjeet:\nAnna nimesi tekstikenttään ja paina Aloita-näppäintä. "
        + "Voit halutessasi säätää pelikentän kokoa, mutta suosituskoko on 6x13.\n"
        + "\n"
        + "Yritä tehdä neljän samanvärisen Puyon sarjoja, jolloin ne katoavat ja antavat pisteitä!\n"
        + "Voit siirtää nuolinäppäimillä tippuvia Puyoja sivusuunnassa.\n"
        + "Enterillä tai S-näppäimellä voit kääntää niitä myötäpäivään, ja Backspace- tai A-näppäimellä vastapäivään.\n"
        + "Alaspäin-näppäin tiputtaa Puyoja nopeampaan tahtiin alas. Ylös-näppäin puolestaan tiputtaa Puyot välittömästi maahan.\n"
        + "Pysäytä-näppäin pysäyttää pelin, ja Aloita alusta -näppäin aloittaa pelin alusta.\n"
        + "Voit aina palata aloitusruutuun Alkuvalikkoon-näppäimellä. Huomaa, että tällöin edistymisesi katoaa!\n"
        + "\nHuipputulokset-näppäintä painamalla pääset huipputulosnäkymään. "
        + "Siellä voit tarkastella tuloksia joko pisteiden tai järjestyksen perusteella järjestettynä. "
        + "Voit poistaa tuloksen klikkaamalla ensin tulosta ja sen jälkeen poista-näppäintä.";
    String name = "";
    
    HashMap<KeyCode, Boolean> painetutNapit = new HashMap<>();
    static File dbFile = new File("huipputulokset.db");
    static Database database = new Database("jdbc:sqlite:"+dbFile.getAbsolutePath());
    static ScoreDao scoreDao = new ScoreDao(database);
    static List<Score> highscores = findByPoints();
    static String highScoresText = "TOP-3: \n\n";
    
    //UI-elements
    static GridPane start = new GridPane();
    static Scene startView;
    static TextArea ta;
    static Label topThree;
    static TextField field;
    static Button startButton;
    static Button highScoreButton;
    static VBox sliderbar;
    static Slider sliderX;
    static Slider sliderY;
    static Label widthText;
    static Label heightText;
    static GridPane components;
    static Canvas ruutu;
    static Canvas nextPuyos;
    static Button returnButton;
    static Button returnButtonInScores;
    static Button reset;
    static Button stop;
    static Label scoreText;
    static HBox topBar;
    static Scene gameView;
    static Button delete;
    static RadioButton button1;
    static RadioButton button2;
    static ToggleGroup group;
    static VBox verticalButtons;
    static ListView<String> list;
    static ObservableList<String> observableList;
    static GridPane scoreGrid;
    static Scene highScoreView;
    
    public void start(Stage window) {
        window.setTitle("Puyo Puyo -peli");
        this.initializeStartMenu();
        this.initializeStartButtonsAndFields();
        this.initializeSliders();
        this.addComponentsToStartMenu();
        this.initializeGameView();
        
        GraphicsContext drawer = ruutu.getGraphicsContext2D();
        GraphicsContext nextDrawer = nextPuyos.getGraphicsContext2D();

        this.addComponentsToGameView();
        this.initializeHighScoreView();
        
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
        button1.setOnAction((event) -> {
            observableList.clear();
            int i = 0;
            findByPoints().stream().forEach(tulos -> { 
                observableList.add((observableList.size() + 1) + ". " + tulos.toString());
                list.setItems(observableList);
            });
        });
        button2.setOnAction((event) -> {
            observableList.clear();
            findByName().stream().forEach(tulos -> { 
                observableList.add((observableList.size() + 1) + ". " + tulos.toString());
                list.setItems(observableList);
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
        
        this.addComponentsToHighScoreView();
        
        //Let's add the pressed buttons to the HashMap
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
            if (event.getCode().equals(KeyCode.DOWN)) {
                this.speed = 8;
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
        returnButton.setOnAction((event) -> {
            window.setScene(startView);
            window.setMinWidth(600);
            pause = true;
        });
        returnButtonInScores.setOnAction((event) -> {
            updateHighScoreText();
            topThree.setText(highScoresText);
            window.setScene(startView);
            window.setMinWidth(600);
            pause = true;
        });
        reset.setOnAction((event) -> {
            this.situation = new GameLogic(width, height);
        });
        stop.setOnAction((event) -> {
            if(this.pause == false) {
                this.pause = true;
            }else {
                this.pause = false;
            }  
        });
        
        highScoreButton.setOnAction((event) -> {
            observableList.clear();
            if (button1.isSelected()) {
                findByPoints().stream().forEach(tulos -> {
                    observableList.add((observableList.size() + 1) + ". " + tulos.toString());
                });
            } else {
                findByName().stream().forEach(tulos -> { 
                    observableList.add((observableList.size() + 1) + ". " + tulos.toString());
                });
            }
        
            list.setItems(observableList);
            window.setScene(highScoreView);
        });
        
        new AnimationTimer(){
            long previous = 0;
            
            @Override
            public void handle(long current) {
                if(painetutNapit.get(KeyCode.DOWN) == Boolean.TRUE){
                    speed = 8;
                } else {
                    speed = (int)situation.getPoints()/1000;
                }
                
                drawer.setFill(Color.FLORALWHITE);
                drawer.fillRect(0, 0, 2 * radius + 2 * (radius + 2) * width, 2 * (radius + 2) * height);
                
                ArrayList<Puyo> drawablePuyos = new ArrayList<>();
                situation.getPuyos().stream().forEach(puyo -> { 
                    drawablePuyos.add(puyo);
                });
                drawablePuyos.addAll(situation.nextPuyos());
                
                for (int i = 0; i < drawablePuyos.size(); i++) {
                    Puyo puyo = drawablePuyos.get(i);
                    
                    drawer.setFill(Color.GRAY);
                    if(puyo != situation.nextAxis && puyo != situation.nextFalling){
                        drawer.fillOval(0.5*radius + 2*radius*puyo.getPositionX(), radius+2*radius*puyo.getPositionY(), 2*radius, 2*radius);
                    }
                    
                    if (puyo.getColour() == Colour.RED) {
                        drawer.setFill(Color.TOMATO);
                    } else if (puyo.getColour() == Colour.YELLOW) {
                        drawer.setFill(Color.color(255 / 255, 255 / 255, 102 / 255));
                    } else if (puyo.getColour() == Colour.GREEN) {
                        drawer.setFill(Color.LAWNGREEN);
                    } else if (puyo.getColour() == Colour.BLUE) {
                        drawer.setFill(Color.CORNFLOWERBLUE);
                    } else if (puyo.getColour() == Colour.PURPLE) {
                        drawer.setFill(Color.BLUEVIOLET);
                    }

                    if (puyo == situation.nextFalling) {
                        drawer.fillOval(2 * (radius + 1.5) * width, radius, 2*radius, 2*radius);
                    } else if (puyo == situation.nextAxis) {
                        drawer.fillOval(2 * (radius + 1.5) * width, 3*radius, 2*radius, 2*radius);
                    } else {
                        drawer.fillOval(0.5*radius + 2*radius*puyo.getPositionX() -1 , radius+2*radius*puyo.getPositionY() - 1, 2*radius - 1, 2*radius - 1);
                    }
                }

                if (current - previous < 1000000000 - speed*100000000) {
                    return;
                }
                if (pause == false) {
                    situation.update();
                    scoreText.setText("Pisteitä: "+situation.getPoints());
                    
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
                        topThree.setText(highScoresText);
                        window.setScene(startView);
                        field.clear();
                        name = "";
                        situation = new GameLogic(width, height);
                        pause = true;
                    }
                }
                this.previous = current;
            }             
        }.start();
        
        window.setScene(startView);
        window.show();
    }
    
    public static void main(String[] args) throws Exception {
        launch(PuyoPuyoUi.class);
    }
    
    /**
    * The method calls scoreDao and finds all of the scores ordered by name
    * @return   List that contains all of the scores ordered by name
    */
    public static List<Score> findByName() {
        List<Score> scores = new ArrayList<>();
        try {
            scores = scoreDao.findAllInOrderByName();
        } catch(Exception e) {
            System.out.println("Ei voitu etsiä tuloksia tai niitä ei ole.");
        }
        return scores;
    }
    
    /**
    * The method that calls scoreDao and finds all of the scores ordered by points.
    * @return   List that contains all of the scores ordered by points.
    */
    public static List<Score> findByPoints() {
        List<Score> scores = new ArrayList<>();        
        try{
            scores = scoreDao.findAllInOrderByPoints();
        } catch(Exception e) {
            System.out.println("Ei voitu etsiä tuloksia tai niitä ei ole.");
        }
        return scores;
    }
    
    /**
    * The method updates the Highscoretext that is shown on the main menu.
    */
    public void updateHighScoreText(){
        highscores = findByPoints();
        highScoresText = "TOP-3: \n\n";
        if (this.highscores.size() >= 1) {
            Score score1 = this.highscores.get(0);
            highScoresText += "1. " + score1 + "\n";
        }
        if (this.highscores.size() >= 2) {
            Score score2 = this.highscores.get(1);
            highScoresText += "2. " + score2 + "\n";
        }
        if (this.highscores.size() >= 3) {
            Score score3 = this.highscores.get(2);
            highScoresText += "3. " + score3 + "\n";
        } 
    }
    
    /**
     * The method initializes start-menu
     */
    public void initializeStartMenu() {
        start.setHgap(5);
        start.setVgap(10);
        start.setMinSize(2*radius + 2*radius*width, 2*radius + 2*radius*height);
        startView = new Scene(start);
    }
    
    /**
     * The method initializes start-menus buttons and textfields. It doesn't initialize sliders.
     */
    public void initializeStartButtonsAndFields() {
        ta = new TextArea(ohje);
        ta.setEditable(false);
        ta.setWrapText(true);        
        
        this.updateHighScoreText();
        topThree = new Label(highScoresText);
        
        field = new TextField();
        startButton = new Button("Aloita");
        highScoreButton = new Button("Huipputulokset");
    }
    
    /**
     * The method initializes sliders in the start-menu.
     */
    public void initializeSliders() {
        sliderbar = new VBox();
        sliderX = new Slider();
        sliderX.setMin(3);
        sliderX.setMax(12);
        sliderX.setValue(6);
        
        sliderY = new Slider();
        sliderY.setMin(6);
        sliderY.setMax(16);
        sliderY.setValue(13);
        
        widthText = new Label("Leveys: " + (int)sliderX.getValue());
        heightText = new Label("Korkeus: " + (int)sliderY.getValue());
        
        sliderbar.getChildren().add(widthText);
        sliderbar.getChildren().add(sliderX);
        sliderbar.getChildren().add(heightText);
        sliderbar.getChildren().add(sliderY);
    }
    
    /**
     * The method adds all of the components to start-menu.
     */
    public void addComponentsToStartMenu() {
        start.add(topThree, 1, 0);
        start.add(sliderbar, 2, 1);
        start.add(field, 1, 1);
        start.add(startButton, 1, 2);
        start.add(highScoreButton, 1, 3);
        start.add(ta, 1, 4);
    }
    
    /**
     * The method initializes game-view.
     */
    public void initializeGameView() {
        components = new GridPane();
        ruutu = new Canvas(4*radius + 2*radius*width, 2*radius + 2*radius*height);
        nextPuyos = new Canvas(3*radius, 5*radius);
        components.setMinSize(2*radius + 2*radius*width + 100, 2*radius + 2*radius*height);
        returnButton = new Button("Alkuvalikkoon");
        returnButtonInScores = new Button("Alkuvalikkoon");
        reset = new Button("Aloita alusta");
        stop = new Button("Pysäytä");
        scoreText = new Label("Pisteitä: " + situation.getPoints());
        topBar = new HBox();
        topBar.setSpacing(15);
        topBar.getChildren().add(returnButton);
        topBar.getChildren().add(reset);
        topBar.getChildren().add(stop);
    }
    
    /**
     * The method adds components to game-view.
     */
    public void addComponentsToGameView() {
        components.setHgap(10);
        components.add(topBar, 0, 0);
        components.add(ruutu, 0, 1);
        components.add(scoreText, 1, 0);
        components.add(nextPuyos, 1, 1);
        gameView = new Scene(components);
    }
    
    /**
     * The method initializes highscore-view.
     */
    public void initializeHighScoreView() {
        delete = new Button("Poista");
        button1 = new RadioButton("Pisteiden perusteella");
        button2 = new RadioButton("Nimen perusteella");
        group = new ToggleGroup();
        button1.setToggleGroup(group);
        button2.setToggleGroup(group);
        button1.setSelected(true);
        verticalButtons = new VBox();
        verticalButtons.setSpacing(5);
        verticalButtons.getChildren().add(button1);
        verticalButtons.getChildren().add(button2);
        verticalButtons.getChildren().add(delete);
        
        list = new ListView<String>();
        observableList = FXCollections.observableArrayList();
    }
    
    /**
     * The method adds components to highscore-view.
     */
    public void addComponentsToHighScoreView() {
        scoreGrid = new GridPane();
        scoreGrid.setVgap(10);
        scoreGrid.setHgap(10);
        
        scoreGrid.add(returnButtonInScores, 0, 0);
        scoreGrid.add(list, 1, 1);
        scoreGrid.add(verticalButtons, 0, 1);
        
        highScoreView = new Scene(scoreGrid);
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
