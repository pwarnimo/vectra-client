/*
 * VECTRA DRAWING PROGRAM -- CLIENT
 * 
 * This file (Rectangle.java) is part of the Vectra Drawing Client.
 * This class defines the rectangle shape.
 */

package drawing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author pwarnimo
 */
public class Rectangle extends BaseShape {
    public Rectangle(int id, int x, int y, int width, int height, Color color, boolean filled) {
        super(id, x, y, width, height, color, filled);
    }
    
    @Override
    public void drawShape(Graphics g) {
        if (isFilled()) {
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        else {
            g.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
