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
import java.awt.Color;
import java.awt.Shape;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author pwarnimo
 */
public class XMLManager {
    private String drawing;
    private String user;
    private String servername;
    
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
    
    public void setServerName(String servername) {
        this.servername = servername;
    }
    
    public String getServername() {
        return servername;
    }
    
    public String postData(String parameters) {
        System.out.println(">>REQ=" + parameters);
            
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(servername);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml");

            connection.setRequestProperty("Content-Length", Integer.toString(parameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return response.toString().trim();
        } catch (MalformedURLException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String createDrawingXML(String drawing) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("message");
            
            rootElement.setAttribute("drawing", drawing);
            rootElement.setAttribute("user", user);
            rootElement.setAttribute("cmd", "create");
            
            doc.appendChild(rootElement);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xml = writer.getBuffer().toString().replaceAll("\n|\r", "");
            
            postData(xml);
            
            return null;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public ArrayList<BaseShape> loadDrawingXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("message");
            
            rootElement.setAttribute("drawing", drawing);
            rootElement.setAttribute("user", user);
            rootElement.setAttribute("cmd", "load");
            
            doc.appendChild(rootElement);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xml = writer.getBuffer().toString().replaceAll("\n|\r", "");
            
            InputSource is;
            is = new InputSource(new StringReader(postData(xml)));
            
            doc = docBuilder.parse(is);
       
            NodeList nList = doc.getElementsByTagName("element");
                
            ArrayList<BaseShape> tmp = new ArrayList<>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    int type = Integer.valueOf(eElement.getAttribute("type"));
                    Boolean filled; // = Boolean.valueOf(eElement.getAttribute("filled"));
                    
                    if (eElement.getAttribute("filled").equals("1")) {
                        filled = true;
                    }
                    else {
                        filled = false;
                    }

                    switch (type) {
                        case 0 :
                            System.out.println("Creating line...");
                            Line ln0 = new Line(Integer.valueOf(eElement.getAttribute("id")), 
                                    Integer.valueOf(eElement.getAttribute("x")), 
                                    Integer.valueOf(eElement.getAttribute("y")),
                                    Integer.valueOf(eElement.getAttribute("width")), 
                                    Integer.valueOf(eElement.getAttribute("height")), 
                                    Color.decode(eElement.getAttribute("color")), 
                                    filled);

                            tmp.add(ln0);

                            break;

                        case 1 : 
                            System.out.println("Creating rectangle...");
                            Rectangle rect0 = new Rectangle(Integer.valueOf(eElement.getAttribute("id")), 
                                    Integer.valueOf(eElement.getAttribute("x")), 
                                    Integer.valueOf(eElement.getAttribute("y")),
                                    Integer.valueOf(eElement.getAttribute("width")), 
                                    Integer.valueOf(eElement.getAttribute("height")), 
                                    Color.decode(eElement.getAttribute("color")), 
                                    filled);

                            tmp.add(rect0);

                            break;

                        case 2 :
                            System.out.println("Creating oval...");
                            Oval oval0 = new Oval(Integer.valueOf(eElement.getAttribute("id")), 
                                    Integer.valueOf(eElement.getAttribute("x")), 
                                    Integer.valueOf(eElement.getAttribute("y")),
                                    Integer.valueOf(eElement.getAttribute("width")), 
                                    Integer.valueOf(eElement.getAttribute("height")), 
                                    Color.decode(eElement.getAttribute("color")), 
                                    filled);

                            tmp.add(oval0);

                            break;
                    }
                }
            }

            return tmp;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList<BaseShape> diffDrawingXML(ArrayList<BaseShape> tmp) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("message");
            
            rootElement.setAttribute("drawing", drawing);
            rootElement.setAttribute("user", user);
            rootElement.setAttribute("cmd", "diff");
            
            doc.appendChild(rootElement);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xml = writer.getBuffer().toString().replaceAll("\n|\r", "");
            
            InputSource is;
            is = new InputSource(new StringReader(postData(xml)));
     
            doc = docBuilder.parse(is);
      
            NodeList nList = doc.getElementsByTagName("element");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    int type = Integer.valueOf(eElement.getAttribute("type"));
                    Boolean filled; // = Boolean.valueOf(eElement.getAttribute("filled"));
                    
                    if (eElement.getAttribute("filled").equals("1")) {
                        filled = true;
                    }
                    else {
                        filled = false;
                    }

                    switch (type) {
                        case 0 :
                            System.out.println("Creating line...");
                            Line ln0 = new Line(Integer.valueOf(eElement.getAttribute("id")), 
                                    Integer.valueOf(eElement.getAttribute("x")), 
                                    Integer.valueOf(eElement.getAttribute("y")),
                                    Integer.valueOf(eElement.getAttribute("width")), 
                                    Integer.valueOf(eElement.getAttribute("height")), 
                                    Color.decode(eElement.getAttribute("color")), 
                                    filled);

                            tmp.add(ln0);

                            break;

                        case 1 : 
                            System.out.println("Creating rectangle...");
                            Rectangle rect0 = new Rectangle(Integer.valueOf(eElement.getAttribute("id")), 
                                    Integer.valueOf(eElement.getAttribute("x")), 
                                    Integer.valueOf(eElement.getAttribute("y")),
                                    Integer.valueOf(eElement.getAttribute("width")), 
                                    Integer.valueOf(eElement.getAttribute("height")), 
                                    Color.decode(eElement.getAttribute("color")), 
                                    filled);

                            tmp.add(rect0);

                            break;

                        case 2 :
                            System.out.println("Creating oval...");
                            Oval oval0 = new Oval(Integer.valueOf(eElement.getAttribute("id")), 
                                    Integer.valueOf(eElement.getAttribute("x")), 
                                    Integer.valueOf(eElement.getAttribute("y")),
                                    Integer.valueOf(eElement.getAttribute("width")), 
                                    Integer.valueOf(eElement.getAttribute("height")), 
                                    Color.decode(eElement.getAttribute("color")), 
                                    filled);

                            tmp.add(oval0);

                            break;
                    }
                }
            }
                
            return null;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String createShapeXML(BaseShape shape) {
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
            element.setAttribute("y", String.valueOf(shape.getY()));
            element.setAttribute("width", String.valueOf(shape.getWidth()));
            element.setAttribute("height", String.valueOf(shape.getHeight()));
            
            String rgb = Integer.toHexString(shape.getColor().getRGB());
            rgb = "#" + rgb.substring(2, rgb.length());
            
            element.setAttribute("color", rgb);
            
            if (shape.getClass() == Line.class) {
                element.setAttribute("type", "1");
            }
            else if (shape.getClass() == Rectangle.class) {
                element.setAttribute("type", "2");
            }
            else if (shape.getClass() == Oval.class) {
                element.setAttribute("type", "3");
            }
            
            if (shape.isFilled()) {
                element.setAttribute("filled", "1");
            }
            else {
                element.setAttribute("filled", "0");
            }

            element.setAttribute("cmd", "1");
            
            rootElement.appendChild(element);
            
            doc.appendChild(rootElement);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xml = writer.getBuffer().toString().replaceAll("\n|\r", "");
            
            return postData(xml);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList<String> getDrawings() {
        try {
            ArrayList<String> drawings = new ArrayList<>();
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("message");
            
            rootElement.setAttribute("drawing", drawing);
            rootElement.setAttribute("user", user);
            rootElement.setAttribute("cmd", "drawings");
            
            doc.appendChild(rootElement);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xml = writer.getBuffer().toString().replaceAll("\n|\r", "");
                  
            InputSource is;     
            is = new InputSource(new StringReader(postData(xml)));

            doc = docBuilder.parse(is);

            NodeList nList = doc.getElementsByTagName("drawing");
                
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    drawings.add(eElement.getAttribute("id"));
                }
            }
                
            return drawings;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (TransformerException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String editShapeXML(BaseShape shape) {
        
        return null;
    }
}
