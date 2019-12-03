package yyl.example.demo.poi.xls;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * XLSX文件解析
 */
public class XlsxParser {

	private Map<Integer, SheetReader> sheetReaders = new ConcurrentHashMap<>();

	public void setSheetReader(int index, SheetReader reader) {
		sheetReaders.put(index, reader);
	}

	public void parse(File file) throws IOException {
		parse(FileUtils.openInputStream(file));
	}

	public void parse(InputStream input) throws IOException {
		try {
			doParse(input);
		} catch (IOException e) {
			throw e;
		} catch (Throwable e) {
			throw new IOException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	private void doParse(InputStream input) throws IOException, OpenXML4JException, SAXException {
		OPCPackage pkg = OPCPackage.open(input);
		XSSFReader xssfReader = new XSSFReader(pkg);
		SharedStringsTable table = xssfReader.getSharedStringsTable();
		XlsxHandler handler = new XlsxHandler(table);
		XMLReader parser = createXMLReader(table, handler);
		Iterator<InputStream> sheets = xssfReader.getSheetsData();
		int sheetIndex = 0;
		while (sheets.hasNext()) {
			handler.reader = sheetReaders.get(sheetIndex++);
			try (InputStream sheet = sheets.next()) {
				if (handler.reader != null) {
					InputSource source = new InputSource(sheet);
					parser.parse(source);
				}
			}
		}
	}

	private static XMLReader createXMLReader(SharedStringsTable sst, DefaultHandler handler) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();//
		parser.setContentHandler(handler);
		return parser;
	}

	public static interface SheetReader {
		void readRow(int row, List<String> data);
	}

	private static class XlsxHandler extends DefaultHandler {

		private SharedStringsTable table;
		private List<String> rowData = new ArrayList<String>();
		private StringBuilder buffer = new StringBuilder();
		private boolean isSST = false;
		private int row = 0;
		private SheetReader reader;

		public XlsxHandler(SharedStringsTable table) {
			this.table = table;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => 单元格
			if ("c".equals(name)) {
				String cellType = attributes.getValue("t");
				if (cellType != null && cellType.equals("s")) {
					isSST = true;
				} else {
					isSST = false;
				}
			}
			buffer = new StringBuilder();
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			if (isSST) {
				try {
					int idx = Integer.parseInt(buffer.toString());
                    buffer = new StringBuilder(table.getItemAt(idx).getString());
				} catch (Exception e) {
				}
			}
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引    
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符    
			if (name.equals("v")) {
				String value = StringUtils.defaultString(buffer.toString().trim(), " ");
				rowData.add(value);
			} else {
				// 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法    
				if (name.equals("row")) {
					reader.readRow(row, rowData);
					row++;
					rowData.clear();
				}
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			buffer.append(ch, start, length);
		}
	}

}
