package yyl.example.basic.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XmlParserExample {

    public static void main(String[] args) throws Exception {
        try (InputStream input = XmlParserExample.class.getResourceAsStream("demo.xml")) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(input);
            Element root = document.getDocumentElement();
            foreach(root);
        }

    }

    private static void foreach(Node node) {
        String nodeName = node.getNodeName();
        System.out.append("<").append(nodeName);
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0, length = attributes == null ? 0 : attributes.getLength(); i < length; i++) {
            Node attribute = attributes.item(i);
            System.out.append(" \"").append(attribute.getNodeName()).append("\"=\"").append(attribute.getNodeValue()).append("\"");
        }
        System.out.append(">");
        NodeList childNodes = node.getChildNodes();
        for (int i = 0, length = childNodes == null ? 0 : childNodes.getLength(); i < length; i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                foreach(childNode);
            } else if (childNode instanceof Text) {
                Text text = (Text) childNode;
                System.out.append(text.getNodeValue());
            }
        }
        System.out.append("</").append(nodeName).append(">");
    }
}
