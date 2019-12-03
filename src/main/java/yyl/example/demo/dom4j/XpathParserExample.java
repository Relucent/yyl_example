package yyl.example.demo.dom4j;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import yyl.example.basic.util.IoUtil;

public class XpathParserExample {
    public static void main(String[] args) throws DocumentException {
        String xml = IoUtil.getResourceAsString(XpathParserExample.class, "demo.xml");
        Document document = DocumentHelper.parseText(xml);
        List<Node> nodes = document.selectNodes("/root/node[1]/node[@name='node1.2']");
        for (Node node : nodes) {
            System.out.println(node.asXML());
        }
    }
}
