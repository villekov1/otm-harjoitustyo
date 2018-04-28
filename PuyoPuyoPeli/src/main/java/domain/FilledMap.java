package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FilledMap {
    private HashMap<Integer, HashMap<Integer, Boolean>> filled;
    private int width;
    private int height;
    
    public FilledMap(int width, int height) {
        filled = new HashMap<>();
        this.width = width;
        this.height = height;
        
        for (int i = 0; i < width; i++) {
            filled.put(i, new HashMap<>());
            for (int j = 0; j < height; j++) {
                filled.get(i).put(j, false);
            }
        }
    }
    
    public HashMap<Integer, HashMap<Integer, Boolean>> updateFilled(List<Puyo> puyos, Puyo falling, Puyo fallingAxis) {
        //Let's make sure there aren't any duplicates
        ArrayList<Puyo> cutDownList = puyos.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        puyos = cutDownList;
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                filled.get(i).put(j, false);
            }
        }
        
        puyos.stream().forEach(puyo -> {
            if (puyo != falling && puyo != fallingAxis) {
                filled.get(puyo.getPositionX()).put(puyo.getPositionY(), true);
            }
        });
        
        return filled;
    }
    
    public boolean isTheSpaceFilled(int x, int y) {
        if (y >= height || y < 0 || x < 0 || x >= width) {
            return true;
        }
        if (this.filled.get(x).get(y) == true) {
            return true;
        }
        
        return false;
    }
}
