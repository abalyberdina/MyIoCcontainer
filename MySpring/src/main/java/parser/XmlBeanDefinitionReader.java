package parser;

import parser.xml.Document;
import parser.xml.DomParser;
import parser.xml.ParserTypes;
import parser.xml.SaxParser;
import parser.xml.StaxParser;

public class XmlBeanDefinitionReader {
    private boolean defaultValidating = false;
    private ParserTypes defaultParserType = ParserTypes.SAX;
    private GenericXmlApplicationContext currentContext;

    public XmlBeanDefinitionReader(GenericXmlApplicationContext context) {
        currentContext = context;
    }

    public void loadBeanDefinitions(String name) {
        /*
         * if parserType = DOM document = new DomParser().parse(name);
         */
        Document document = null;
        switch (defaultParserType) {
        case DOM: {
            document = new DomParser().parse(name, defaultValidating);
            break;
        }
        case SAX: {
            document = new SaxParser().parse(name, defaultValidating);
            break;
        }
        case StAX: {
            document = new StaxParser().parse(name, defaultValidating);
            break;
        }
        }
        ((ConcreteBeanFactory) currentContext.getBeanFactory())
                .setDocument(document);
    }

    public void setValidating(boolean validating) {
        this.defaultValidating = validating;
    }

    public void setParserType(ParserTypes parserType) {
        this.defaultParserType = parserType;
    }
}
