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
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import xml.XMLManager;

/**
 *
 * @author pwarnimo
 */
public class DrawPanel extends JPanel {
    private int drawMode = 0;
    private Drawing drw0;
    private Color CurrentForegroundColor = Color.cyan;
    private Color CurrentBackgroundColor = Color.cyan;
    private XMLManager mgrXML = new XMLManager();
    boolean filled = false;
    
    public DrawPanel() {       
        super();
        
        System.out.println("DrawPanel INIT...");
        
        drw0 = new Drawing();
        
        this.addMouseListener(new CustomListener(this));
        this.addMouseMotionListener(new CustomListener(this));
        
        setBackground(Color.white);
        setForeground(Color.black);
        
        //setCursor(new Cursor(Cursor.TEXT_CURSOR));
        /*Toolkit toolkit = Toolkit.getDefaultToolkit();
        URL img = this.getClass().getResource("resource/pen.png");
        ImageIcon image = new ImageIcon(img);*/
        
        System.out.println("DrawPanel OK");
    }
    
    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }
    
    @Override
    public void paintComponent(Graphics g) { 
        super.paintComponent(g);
        
        //g.clearRect(0, 0, this.getWidth(), this.getHeight());
        //setForeground(Color.blue);
        g.drawString("VECTRA CLIENT 1.0 - You are " + drw0.getUser() + " and you are drawing on " + drw0.getDrawingName(), 2, 12);
        drw0.drawEverything(g);
    }
    
    public void setCurrentForegroundColor(Color color) {
        this.CurrentForegroundColor = color;
    }
    
    public Color getCurrentForegroundColor() {
        return CurrentForegroundColor;
    }
    
    public void setCurrentBackgroundColor(Color color) {
        this.CurrentBackgroundColor = color;
    }
    
    public Color getCurrentBackgroundColor() {
        return CurrentBackgroundColor;
    }
    
    public void setDrawingName(String drawingName) {
        drw0.setDrawingName(drawingName);
        //drw0.loadDrawing();
        repaint();
    }
    
    public String getDrawingName() {
        return drw0.getDrawingName();
    }
    
    public void setUser(String user) {
        drw0.setUser(user);
    }
    
    public String getUser() {
        return drw0.getUser();
    }
    
    public void setServer(String server) {
        drw0.setServer(server);
    }
    
    public ArrayList<String> getDrawings() {
        return drw0.getDrawings();
    }
    
    public void loadDrawing() {
        drw0.loadDrawing();
        repaint();
    }
    
    public void loadDiff() {
        drw0.loadDiff();
        this.repaint();
    }
    
    public void newDrawing(String drawingName) {
        drw0.newDrawing(drawingName, drw0.getUser());
        repaint();
    }
    
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    
    public boolean getFilled() {
        return filled;
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
                    Line ln0 = new Line(-1, startX, startY, e.getX(), e.getY(), drawpanel.getCurrentForegroundColor(), filled);           
                    drw0.addShape(ln0);
                    
                    break;
                 
                case 1:
                    Rectangle rect0 = new Rectangle(-1, startX, startY, width, height, drawpanel.getCurrentForegroundColor(), filled);
                    drw0.addShape(rect0);
                    
                    break;
                    
                case 2:
                    Oval oval0 = new Oval(-1, startX, startY, width, height, drawpanel.getCurrentForegroundColor(), filled);
                    drw0.addShape(oval0);
                    
                    break;
            }
            
            drawpanel.repaint();
        }
    }
}
