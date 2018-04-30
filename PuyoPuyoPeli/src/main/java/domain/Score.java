package domain;

/**
 * A class that represents a score in the game.
 * It contains the information about the id, the score and the name of the player.
 */
public class Score {
    private int id;
    private int score;
    private String name;
    
    /**
    * A constructor of the class Score.
    * @param   id      The id of the Score
    * @param   score   The amount points
    * @param   name    The name of the Score
    */
    public Score(int id, int score, String name) {
        this.id = id;
        this.score = score;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    public int getScore() {
        return this.score;
    }
    public int getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return this.name + ": " + this.score;
    }
}
