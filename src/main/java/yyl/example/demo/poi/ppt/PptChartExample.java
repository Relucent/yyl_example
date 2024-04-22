package yyl.example.demo.poi.ppt;

import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.chart.AxisCrossBetween;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.BarGrouping;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * POI创建PPT图表(柱状图)
 */
public class PptChartExample {

	public static void main(String[] args) throws IOException {
		// 创建一个PPT
		try (XMLSlideShow ppt = new XMLSlideShow()) {
			// 创建了一个空白的幻灯片
			XSLFSlide slide = ppt.createSlide();

			createChartToSlide(slide, ppt);

			// 保存PPT
			ppt.write(new FileOutputStream("D:/example_" + System.currentTimeMillis() + ".pptx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建图表(柱状图)
	 * @param slide 幻灯片
	 * @param ppt PPT
	 */
	public static void createChartToSlide(XSLFSlide slide, XMLSlideShow ppt) {

		// 创建一个工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 写入数据
		XSSFSheet sheet = workbook.createSheet();
		XSSFRow row0 = sheet.createRow(0);
		row0.createCell(1).setCellValue("系列1");
		row0.createCell(2).setCellValue("系列2");
		for (int i = 0; i < 4; i++) {
			// 设置每一行的字段标题和数据
			XSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue("类目" + (i + 1));
			row.createCell(1).setCellValue(3);
			row.createCell(2).setCellValue(4);
		}

		// 创建一个图表
		XSLFChart chart = ppt.createChart();
		// 把工作簿放到图表里，这样可以方便文件更新
		chart.setWorkbook(workbook);

		// 图表头
		chart.setTitleText("柱状图");

		// 生成图表底部的示例的
		XDDFChartLegend legend = chart.getOrAddLegend();
		legend.setPosition(LegendPosition.BOTTOM);

		// X轴(底部)
		XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
		// Y轴(左侧)
		XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT);

		// 创建图表数据(指定是什么图表 柱状图或者饼图，折线图)，
		XDDFChartData data = chart.createData(ChartTypes.BAR, xAxis, yAxis);

		// 底部类别的数据源(可以从数组读，也可以从指定一个EXCEL范围)
		XDDFCategoryDataSource xddfCategoryDataSource = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, 4, 0, 0));

		// 第一组柱状图的数据源
		XDDFNumericalDataSource<Double> source1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 4, 1, 1));
		// 第二组柱状图的数据源
		XDDFNumericalDataSource<Double> source2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 4, 2, 2));

		// 第一组柱状图添加到图表数据里，返回一个系列数据
		XDDFChartData.Series series1 = data.addSeries(xddfCategoryDataSource, source1);
		// 第二组柱状图添加到图表数据里，返回一个系列数据
		XDDFChartData.Series series2 = data.addSeries(xddfCategoryDataSource, source2);

		// 设置系列的名称
		XSSFCell titleCell1 = sheet.getRow(0).getCell(1);
		series1.setTitle(titleCell1.getStringCellValue(), new CellReference(titleCell1));

		XSSFCell titleCell2 = sheet.getRow(0).getCell(2);
		series2.setTitle(titleCell2.getStringCellValue(), new CellReference(titleCell2));

		// 数据源转为BARCHART
		XDDFBarChartData bar = (XDDFBarChartData) data;
		// 设置值轴的交叉点，即值轴与类别轴（通常是 X 轴）的交点位置。
		yAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
		// 是否设置不同的颜色
		bar.setVaryColors(false);
		// 柱状图的方向
		bar.setBarDirection(BarDirection.COL);
		// 设置柱状图的分组方式
		bar.setBarGrouping(BarGrouping.STANDARD);
		// 可以设置间隙宽度
		bar.setGapWidth(200);

		// 绘制图表
		chart.plot(data);

		// 图表在幻灯片中的位置和大小，Rectangle2D 对象的坐标和尺寸是EMU(English Metric Units)为单位的。
		// EMU = 厘米 × 360000
		Rectangle2D.Double rect = new Rectangle2D.Double(3 * 360000D, 3 * 360000D, 20 * 360000D, 12 * 360000D);

		// 把图表添加加到幻灯片
		slide.addChart(chart, rect);
	}
}
