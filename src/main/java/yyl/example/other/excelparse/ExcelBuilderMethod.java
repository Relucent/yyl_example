package yyl.example.other.excelparse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class ExcelBuilderMethod {

	/**
	 * 读取Excel并按照模板生成文本文件
	 * @param excelPath Excel文件路径
	 * @param outFolderPath 文件输出路径
	 * @param templetPath 路径模板
	 */
	protected static void outExcel(String excelPath, String outFolderPath, String templetPath, JTextArea txtDebug) {
		try {
			ExcelParse builder = new ExcelParse(templetPath);
			File excelFile = new File(excelPath);
			excelPath = excelFile.getAbsolutePath();

			ArrayList<File> xlsFiles = new ArrayList<File>();
			consXlsPaths(excelFile, xlsFiles);
			int len = xlsFiles.size();

			txtDebug.append("共找到" + len + "个xls文件");
			txtDebug.append("\n");
			txtDebug.append("开始导出xls");
			txtDebug.append("\n");

			for (int i = 0; i < len; i++) {
				File xlsFile = (File) xlsFiles.get(i);

				String outFileName = xlsFile.getAbsolutePath();
				outFileName = outFileName.substring(outFileName.indexOf(excelPath) + excelPath.length());
				outFileName = outFileName.substring(0, outFileName.length() - 4);
				outFileName = outFolderPath + File.separator + outFileName + ".html";

				txtDebug.append("读取文件：");
				txtDebug.append(xlsFile.getAbsolutePath());
				txtDebug.append("\n");

				if (outputFile(outFileName, builder.getResult(xlsFile.getAbsolutePath()))) {
					txtDebug.append("导出文件：");
					txtDebug.append(outFileName);
					txtDebug.append("\n");
					txtDebug.append((int) ((i + 1) * 100 / len) + "%");
					txtDebug.append("\n");
				} else {
					txtDebug.append("文件导出失败");
					txtDebug.append("\n");
				}

			}
			txtDebug.append("导出完成");
		} catch (Exception e) {
			e.printStackTrace();
			txtDebug.append("程序发生错误");
			txtDebug.append(e.toString());
			txtDebug.append("程序终止：");
		}
	}

	/**
	 * 获得文件目录下所有Xls文件
	 * @param ofile 文件目录
	 * @param paths 所有xls文件目录
	 */
	private static void consXlsPaths(File ofile, List<File> paths) {
		if (ofile.isDirectory()) {
			File[] cs = ofile.listFiles();
			for (int i = 0; i < cs.length; i++)
				consXlsPaths(cs[i], paths);
		} else {
			if (ofile.getName().endsWith(".xls")) {
				paths.add(ofile);
			}
		}
	}

	/**
	 * 根据内容和名称输出文件
	 * @param fileName 文件名称
	 * @param content 文件内容
	 * @return 如果文件输出成功返回true，否则返回false
	 */
	private static boolean outputFile(String fileName, String content) {
		if (content == null) {
			return false;
		}

		File file = new File(fileName);
		File pfile = file.getParentFile();

		if (!pfile.exists()) {
			pfile.mkdirs();
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
