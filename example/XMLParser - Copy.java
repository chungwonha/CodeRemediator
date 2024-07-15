import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.StringReader;
import org.xml.sax.InputSource;

public class XMLParser {
    public void parseXML(String xmlInput) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
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
