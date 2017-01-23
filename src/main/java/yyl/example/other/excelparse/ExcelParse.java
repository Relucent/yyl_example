package yyl.example.other.excelparse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelParse {

	private final static Object PLACEHOLDER = new Object();
	private ArrayList<Object> templetlist = new ArrayList<Object>();
	private ArrayList<Object> mappinglist = new ArrayList<Object>();

	/**
	 * 
	 * @param templetFileName 模版文件路径
	 * @throws IOException 如果模版文件错误则抛出异常
	 */
	public ExcelParse(String templetFileName) throws Exception {
		initTemplet(templetFileName);
	}

	/**
	 * 初始化模版数据
	 * @param fileName 模版文件路径
	 * @throws IOException 如果模版文件错误则抛出异常
	 */
	private void initTemplet(String fileName) throws Exception {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			while (line != null) {
				T2: while (true) {
					int offset = line.indexOf("<@");
					if (offset < 0) {
						templetlist.add(line);
						break T2;
					}
					templetlist.add(line.substring(0, offset));
					line = line.substring(offset + 2);

					offset = line.indexOf(">");
					if (offset >= 2) {
						mappinglist.add(line.substring(0, offset));
						templetlist.add(PLACEHOLDER);
					}
					line = line.substring(offset + 1);
				}
				line = br.readLine();
				templetlist.add("\n");
			}
		} catch (Exception e) {
			throw new Exception(e.toString());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析excel文件，并按照模版生成对应内容
	 * @param excelFile excel文件路径
	 * @return excel对应的的字符串 如果解析过程中出现错误则返回NULL
	 */
	public String getResult(String excelFile) {
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new FileInputStream(excelFile));
			Sheet sheet = workbook.getSheet(0);
			StringBuilder sbr = new StringBuilder();
			int indexPlace = 0;
			for (int i = 0; i < templetlist.size(); i++) {
				Object obj = templetlist.get(i);
				if (obj == PLACEHOLDER) {
					String locName = (String) mappinglist.get(indexPlace++);
					try {
						String cellStr = sheet.getCell(locName).getContents();
						sbr.append(cellStr);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					sbr.append(obj);
				}
			}
			return sbr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return null;
	}
}
