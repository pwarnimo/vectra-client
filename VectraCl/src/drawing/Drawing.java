/*
 * VECTRA DRAWING PROGRAM -- CLIENT
 * 
 * This file (Drawing.java) is part of the Vectra Drawing Client.
 * This class stores all the shapes which are fetched from the Vectra Drawing
 * Server.
 */

package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author pwarnimo
 */
public class Drawing {
    private ArrayList<BaseShape> shapes = new ArrayList<>();
    
    public Drawing() {
        
    }
    
    public void addShape(BaseShape shape) {
        shapes.add(shape);
    }
    
    public BaseShape getShape(int id) {
        int i = 0;
        
        while ((i <= shapes.size() -1) && (shapes.get(i).getId() != id)) {
            i++;
        }
        
        if (i <= shapes.size()) {
            return shapes.get(i);
        }
        else {
            return null;
        }
    }
    
    public void drawEverything(Graphics g) {
        for (int i = 0; i <= shapes.size() -1; i++) {
            shapes.get(i).drawShape(g);
        }
    }
}
