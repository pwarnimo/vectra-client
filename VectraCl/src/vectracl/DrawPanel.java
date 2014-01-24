/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vectracl;

import drawing.Drawing;
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
        DrawPanel drawpanel;
        
        public CustomListener(DrawPanel drawpanel) {
            this.drawpanel = drawpanel;
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            switch (drawMode) {
                case 0:
                    
                    break;
                    
                case 1:
                    
                    break;
                    
                case 2:
                    
                    break;
            }
        }
    }
}
