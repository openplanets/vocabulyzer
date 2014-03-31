/**
 * Created by artur on 3/31/14.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.w3c.dom.Attr;

public class Controller {
    Configurator config;
    SAXReader reader;
    public Controller(){
        reader = new SAXReader();
        config = new Configurator("");
    }
    public Controller(String pathToVocabulary){
        reader = new SAXReader();
        config = new Configurator(pathToVocabulary);
    }
    public void Execute(String input, String output) {

        Document doc = null;
        try {
            doc = reader.read(input);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        treeWalk(doc);
        save(doc, output);
    }

    private void save(Document doc, String output) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer;
        try {
            writer = new XMLWriter(new FileWriter(output), format);
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void treeWalk(Document document) {
        treeWalk( document.getRootElement() );
    }

    public void treeWalk(Element element) {
        if (config.getPropertyNames().contains(element.getName())){
            element.attributes().set(0, new DefaultAttribute("uri", config.getPropertyValueByName(element.getName())));
        }
        for ( int i = 0, size = element.attributeCount(); i < size; i++ ) {
            Attribute attribute = element.attribute(i);
            String property = attribute.getName();
            String value = attribute.getValue();

            if (config.getPropertyNames().contains(property)){
                element.attributes().set(i + 1, new DefaultAttribute("uri", config.getPropertyValueByName(property)));
                size = element.attributeCount();
            } else if (config.getPropertyNames().contains(value)){
                element.attributes().set(i + 1, new DefaultAttribute("uri", config.getPropertyValueByName(value)));
                size = element.attributeCount();
            }
        }
        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
            Node node = element.node(i);
            if ( node instanceof Element ) {
                treeWalk( (Element) node );
            }

        }
    }
}


