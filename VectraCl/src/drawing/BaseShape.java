/*
 * VECTRA DRAWING PROGRAM -- CLIENT
 * 
 * This file (BaseShape.java) is part of the Vectra Drawing Client.
 * This class defines all the properties of a shape which are used by the Line,
 * Oval and Rectangle shapes.
 */

package drawing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author pwarnimo
 */
public class BaseShape {
    protected int id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;
    protected boolean filled;
    
    public BaseShape(int id, int x, int y, int width, int height, Color color, boolean filled) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.filled = filled;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getY() {
        return y;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    
    public boolean isFilled() {
        return filled;
    }
    
    public void drawShape(Graphics g) {
        g.setColor(color);
        
    }
}
