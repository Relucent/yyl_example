package yyl.example.demo.poi.word;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import yyl.example.basic.util.IoUtil;

public class WordExample {

    public static void main(String[] args) throws IOException {

        String[] samples = {"sample_doc", "sample_docx"};
        for (String sample : samples) {
            byte[] content = IoUtil.getResourceAsByteArray(WordExample.class, sample);
            System.out.println("Name    : " + sample + "");
            System.out.println("Magic   : " + ofMagic(content));
            System.out.println("Content : ");
            System.out.println(toString(content));
        }
    }

    public static String toString(byte[] content) throws IOException {
        try (InputStream input = new ByteArrayInputStream(content)) {
            FileMagic magic = ofMagic(content);
            // DOC:OLE2
            if (FileMagic.OLE2.equals(magic)) {
                try (WordExtractor extractor = new WordExtractor(input)) {
                    return extractor.getText();
                }
            }
            // DOCX:OOXML
            OPCPackage opcPackage = OPCPackage.open(input);
            try (POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage)) {
                return extractor.getText();
            }
        } catch (XmlException | OpenXML4JException e) {
            throw new IOException(e);
        }
    }

    public static FileMagic ofMagic(byte[] content) throws IOException {
        try {
            return FileMagic.valueOf(content);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
