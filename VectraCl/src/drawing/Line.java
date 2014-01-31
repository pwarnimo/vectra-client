/*
 * VECTRA DRAWING PROGRAM -- CLIENT
 * 
 * This file (Line.java) is part of the Vectra Drawing Client.
 * This class defines the line shape.
 */

package drawing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author pwarnimo
 */
public class Line extends BaseShape {
    public Line(int id, int x, int y, int width, int height, Color color, boolean filled) {
        super(id, x, y, width, height, color, filled);
    }
    
    @Override
    public void drawShape(Graphics g) {
        g.setColor(color);
        g.drawLine(x, y, width, height);
    }
}
