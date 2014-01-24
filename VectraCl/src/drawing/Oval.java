/*
 * VECTRA DRAWING PROGRAM -- CLIENT
 * 
 * This file (Oval.java) is part of the Vectra Drawing Client.
 * This class defines the oval shape.
 */

package drawing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author pwarnimo
 */
public class Oval extends BaseShape {
    public Oval(int id, int x, int y, int width, int height, Color color, boolean filled) {
        super(id, x, y, width, height, color, filled);
    }
    
    @Override
    public void drawShape(Graphics g) {
        if (isFilled()) {
            g.fillOval(getX(), getY(), getWidth(), getHeight());
        }
        else {
            g.drawOval(getX(), getY(), getWidth(), getHeight());
        }
    }
}
