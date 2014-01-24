/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
