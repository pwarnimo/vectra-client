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
            
            
            //String xml = "";
            
            DOMSource source = new DOMSource(doc);
            
            //TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Transformer transformer = transformerFactory.newTransformer();
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            //transformer.setOutputProperty(OutputKeys.ENCODING, "US-ASCII");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xml = writer.getBuffer().toString().replaceAll("\n|\r", "");
            
            //String xml = "";
            
            
            //StreamResult result =  new StreamResult(xml);
            //transformer.transform(source, result);

            String parameters = xml;
             
            System.out.println(">>REQ=" + xml);
            
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
                
                /*InputSource is2;
                is2 = new InputSource(new StringReader(response.toString().trim()));
                
                //System.out.println(response.toString().trim());
                
                doc = docBuilder.parse(is2);
                //doc.getDocumentElement().normalize();
                
                NodeList nList = doc.getElementsByTagName("drawing");
                
                /*for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
 
			drawings.add(eElement.getAttribute("id"));
                    }
                }*/
                
                //return drawings;
                //return response.toString().trim();
            } catch (MalformedURLException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
    
    
    public void genLoadDrawingXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("message");
            
            rootElement.setAttribute("drawing", drawing);
            rootElement.setAttribute("user", user);
            rootElement.setAttribute("cmd", "load");
            
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
    
    public void genDiffDrawingXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("message");
            
            rootElement.setAttribute("drawing", drawing);
            rootElement.setAttribute("user", user);
            rootElement.setAttribute("cmd", "load");
            
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
            element.setAttribute("y", String.valueOf(shape.getX()));
            element.setAttribute("width", String.valueOf(shape.getWidth()));
            element.setAttribute("height", String.valueOf(shape.getHeight()));
            //element.setAttribute("color", shape.getColor().toString());
            
            /*int r = shape.getColor().getRed();
            int g = shape.getColor().getGreen();
            int b = shape.getColor().getBlue();
            
            String hex = String.format("#%02x%02x%02x", r, g, b);*/
            
            String rgb = Integer.toHexString(shape.getColor().getRGB());
            rgb = "#" + rgb.substring(2, rgb.length());
            
            element.setAttribute("color", rgb);
            
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
            
            //TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Transformer transformer = transformerFactory.newTransformer();
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            //transformer.setOutputProperty(OutputKeys.ENCODING, "US-ASCII");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xml = writer.getBuffer().toString().replaceAll("\n|\r", "");
            
            //String xml = "";
            
            DOMSource source = new DOMSource(doc);
            
            //StreamResult result =  new StreamResult(xml);
            //transformer.transform(source, result);

            String parameters = xml;
             
            System.out.println(">>REQ=" + xml);
            
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
                
                return response.toString().trim();
                //return response.toString().trim();
            } catch (MalformedURLException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
    
    public Shape xmlToShape(String xml) {
        return null;
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
            
            //TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Transformer transformer = transformerFactory.newTransformer();
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            //transformer.setOutputProperty(OutputKeys.ENCODING, "US-ASCII");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xml = writer.getBuffer().toString().replaceAll("\n|\r", "");
            
            //String xml = "";
            
            DOMSource source = new DOMSource(doc);
            
            //StreamResult result =  new StreamResult(xml);
            //transformer.transform(source, result);

            String parameters = xml;
             
            System.out.println(">>REQ=" + xml);
            
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
                
                InputSource is2;
                is2 = new InputSource(new StringReader(response.toString().trim()));
                
                //System.out.println(response.toString().trim());
                
                doc = docBuilder.parse(is2);
                //doc.getDocumentElement().normalize();
                
                NodeList nList = doc.getElementsByTagName("drawing");
                
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
 
			drawings.add(eElement.getAttribute("id"));
                    }
                }
                
                return drawings;
                //return response.toString().trim();
            } catch (MalformedURLException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
}
