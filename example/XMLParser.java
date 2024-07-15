import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.StringReader;
import org.xml.sax.InputSource;

public class XMLParser {
    public void parseXML(String xmlInput) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Disable XML external entity processing to prevent XXE attacks
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlInput));
            Document doc = builder.parse(is);
            
            // Process the XML document
            // ...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}