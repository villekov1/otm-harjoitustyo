package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
* A class that contains the information about the positions that are filled by Puyos.
*/
public class FilledMap {
    private HashMap<Integer, HashMap<Integer, Boolean>> filled;
    private int width;
    private int height;
    
    /**
    * A constructor of the class FilledMap.
    * @param   width   The width of the game grid
    * @param   height   The height of the game grid
    */
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
    
    /**
    * The method updates the HashMap according to the list of Puyos given by the parameter.
    * @param   puyos       The list of Puyos
    * @param   falling     A Puyo that is called falling in the GameLogic class. The space is not regarded as filled.
    * @param fallingAxis   A Puyo that is called fallingAxis in the GameLogic class. The space is not regarded as filled.
    * @return   The HashMap filled
    */
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
    
    /**
    * The method checks if the space is filled by Puyos.
    * @param   x   The x-coordinate
    * @param   y   The y-coordinate
    * @return   Boolean that tells if the space is filled or not
    */
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
