/*
 * VECTRA DRAWING PROGRAM -- CLIENT
 * 
 * This file (DrawPanel.java) is part of the Vectra Drawing Client.
 * The DrawPanel is used to draw, add, edit and display all the shapes which
 * are fetched from the Vectra Drawing Server.
 */

package vectracl;

import drawing.Drawing;
import drawing.Line;
import drawing.Oval;
import drawing.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author pwarnimo
 */
public class DrawPanel extends JPanel {
    private int drawMode = 0;
    private Drawing drw0;
    
    public DrawPanel() {
        super();
        
        drw0 = new Drawing();
        
        this.addMouseListener(new CustomListener(this));
        this.addMouseMotionListener(new CustomListener(this));
        
        setBackground(Color.white);
        setForeground(Color.black);
    }
    
    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }
    
    @Override
    public void paintComponent(Graphics g) { 
        super.paintComponent(g);
        
        g.drawString("VECTRA CLIENT 0.1", 2, 12);
        drw0.drawEverything(g);
    }
    
    public class CustomListener extends MouseAdapter {
        private int startX;
        private int startY;
        
        DrawPanel drawpanel;
        
        public CustomListener(DrawPanel drawpanel) {
            this.drawpanel = drawpanel;
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("SX=>" + startX + " SY=>" + startY + " EX=>" + e.getX() + " EY=>" + e.getY());
            
            int width = e.getX() - startX;
            int height = e.getY() - startY;
            
            System.out.println("W=>" + width + " H=>" + height);
            
            switch (drawMode) {
                case 0:
                    Line ln0 = new Line(-1, startX, startY, e.getX(), e.getY(), Color.black, false);
                    drw0.addShape(ln0);
                    
                    break;
                 
                case 1:
                    Rectangle rect0 = new Rectangle(-1, startX, startY, width, height, Color.black, false);
                    drw0.addShape(rect0);
                    
                    break;
                    
                case 2:
                    Oval oval0 = new Oval(-1, startX, startY, width, height, Color.black, false);
                    drw0.addShape(oval0);
                    
                    break;
            }
            
            drawpanel.repaint();
        }
    }
}
