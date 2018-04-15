package domain;


public class Tulos {
    private int id;
    private int score;
    private String name;
    
    public Tulos(int id, int score, String name) {
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
    public int getId(){
        return this.id;
    }
    
    public String toString() {
        return this.name + ": " + this.score;
    }
}
