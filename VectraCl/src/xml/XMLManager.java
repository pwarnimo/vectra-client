/*
 * VECTRA DRAWING PROGRAM -- CLIENT
 * 
 * This file (XMLManager.java) is part of the Vectra Drawing Client.
 * The XMLManager is used to create the XML code for the specific commands,
 * which are send to the Vectra Drawing Server.
 */

package xml;

import drawing.BaseShape;
import drawing.Line;
import drawing.Oval;
import drawing.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author pwarnimo
 */
public class XMLManager {
    private String drawing;
    private String user;
    
    public XMLManager() {
        System.out.println("XMLManager INIT...");
        
        // INITIALIZATION...
        
        System.out.println("XMLManager OK");
    }
    
    public void setDrawing(String drawing) {
        this.drawing = drawing;
    }
    
    public String getDrawing() {
        return drawing;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getUser() {
        return user;
    }
    
    public void generateCreateDrawingXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("message");
            
            rootElement.setAttribute("drawing", drawing);
            rootElement.setAttribute("user", user);
            rootElement.setAttribute("cmd", "create");
            
            doc.appendChild(rootElement);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            DOMSource source = new DOMSource(doc);
            
            StreamResult result =  new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generateCreateShapeXML(BaseShape shape) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("message");
            
            rootElement.setAttribute("drawing", drawing);
            rootElement.setAttribute("user", user);
            rootElement.setAttribute("cmd", "update");
            
            Element element = doc.createElement("element");
            
            element.setAttribute("id", String.valueOf(-1));
            element.setAttribute("x", String.valueOf(shape.getX()));
            element.setAttribute("y", String.valueOf(shape.getX()));
            element.setAttribute("width", String.valueOf(shape.getWidth()));
            element.setAttribute("height", String.valueOf(shape.getHeight()));
            //element.setAttribute("color", shape.getColor().toString());
            
            int r = shape.getColor().getRed();
            int g = shape.getColor().getGreen();
            int b = shape.getColor().getBlue();
            
            String hex = String.format("#%02x%02x%02x", r, g, b);
            
            element.setAttribute("color", hex);
            
            if (shape.getClass() == Line.class) {
                element.setAttribute("type", String.valueOf(0));
            }
            else if (shape.getClass() == Rectangle.class) {
                element.setAttribute("type", String.valueOf(1));
            }
            else if (shape.getClass() == Oval.class) {
                element.setAttribute("type", String.valueOf(2));
            }
            
            if (shape.isFilled()) {
                element.setAttribute("filled", String.valueOf(1));
            }
            else {
                element.setAttribute("filled", String.valueOf(0));
            }

            element.setAttribute("cmd", String.valueOf(1));
            
            rootElement.appendChild(element);
            
            doc.appendChild(rootElement);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            DOMSource source = new DOMSource(doc);
            
            StreamResult result =  new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
