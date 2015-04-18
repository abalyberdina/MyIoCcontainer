package parser.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyDefaultHandler extends DefaultHandler {
    private MyBeanDefinition root;
    private MyBeanDefinition currentNode;

    // private List<Node> elementStack;

    public MyDefaultHandler() {
        root = null;
        currentNode = root;
        // elementStack = new LinkedList<>();
    }

    public MyBeanDefinition getRoot() {
        return root;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        MyBeanDefinition newNode = new MyBeanDefinition();
        newNode.setName(qName);
        for (int i = 0; i < attributes.getLength(); i++) {
            newNode.addAtribute(attributes.getQName(i), attributes.getValue(i));
        }
        if (currentNode == null) {
            root = newNode;
        } else {
            currentNode.addChild(newNode);
        }
        currentNode = newNode;
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        currentNode = currentNode.getParent();
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String s = new String(ch, start, length).trim();
        currentNode.setData(s);
    }

}
