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
import xml.XMLManager;

/**
 *
 * @author pwarnimo
 */
public class Drawing {
    private ArrayList<BaseShape> shapes = new ArrayList<>();
    private XMLManager xmlMgr;
    
    public Drawing() {
        System.out.println("Drawing INIT...");
        xmlMgr = new XMLManager();
        //xmlMgr.setServerName("http://vectra.local/index.php");
        //xmlMgr.setUser("cassie");
        //xmlMgr.setDrawing("CassieHicks");
        System.out.println("Drawing OK");
    }
    
    public void setDrawingName(String drawingName) {
        //shapes.clear();
        xmlMgr.setDrawing(drawingName);
        //shapes = xmlMgr.loadDrawingXML();
    }
    
    public String getDrawingName() {
        return xmlMgr.getDrawing();
    }
    
    public void setUser(String user) {
        xmlMgr.setUser(user);
    }
    
    public String getUser() {
        return xmlMgr.getUser();
    }
    
    public void setServer(String server) {
        xmlMgr.setServerName(server);
    }
    
    public void newDrawing(String drawingName, String user) {
        //xmlMgr.setDrawing(drawingName);
        xmlMgr.setUser(user);
        
        shapes.clear();
        
        System.out.println("> Creating new drawing \"" + drawingName + "\"...");
        xmlMgr.createDrawingXML(drawingName);
    }
    
    public void addShape(BaseShape shape) {
        System.out.println("> Adding shape to drawing \"" + xmlMgr.getDrawing() + "\"...");
        System.out.println(xmlMgr.createShapeXML(shape));
        shapes.add(shape);
    }
    
    public void loadDiff() { 
        //ArrayList<BaseShape> tmpShapes = xmlMgr.diffDrawingXML();
        
        System.out.println("TIMER->Loading Diff...");
        //this.shapes = tmpShapes;
        xmlMgr.diffDrawingXML(shapes);
        System.out.println("TIMER->DONE!");
    }
    
    public void loadDrawing() {
        ArrayList<BaseShape> tmpShapes = xmlMgr.loadDrawingXML();
        
        this.shapes = tmpShapes;
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
    
    public ArrayList<String> getDrawings() {
        return xmlMgr.getDrawings();
    }
    
    public void drawEverything(Graphics g) {
        for (int i = 0; i <= shapes.size() -1; i++) {
            shapes.get(i).drawShape(g);
        }
    }
}
