package parser.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class SaxParser implements Parser {
    private final static String SCHEMA_LOCATION = "MyXMLSchema.xsd";
    private Document document;
    SAXParserFactory factory = SAXParserFactory.newInstance();
    MyDefaultHandler handler = new MyDefaultHandler();

    @Override
    public Document parse(String fileName) {
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(fileName), handler);
            document = new Document(handler.getRoot());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("File read error");
        }
        return document;
    }

    @Override
    public Document parse(String fileName, boolean validation) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        Validator validator = null;
        try {
            Schema schema = schemaFactory.newSchema(new File(SCHEMA_LOCATION));
            validator = schema.newValidator();
            validator.validate(new StreamSource(new File(fileName)));
        } catch (SAXException | IOException e) {
            System.out.println("Scheme is not valid!");
            return null;
        }
        return parse(fileName);
    }

}
