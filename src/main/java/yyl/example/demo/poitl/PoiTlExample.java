package yyl.example.demo.poitl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.deepoove.poi.XWPFTemplate;

/**
 * POI-TL（POI template language）是一个Word模板引擎，可以基于Microsoft Word模板和数据生成新的文档。<br>
 * <a href="http://deepoove.com/">deepoove.com/poi-t</a>
 */
public class PoiTlExample {
	public static void main(String[] args) throws IOException {
		String outputPath = "D:/output" + System.currentTimeMillis() + ".docx";
		try (InputStream input = PoiTlExample.class.getResourceAsStream("sample_template_docx");
				XWPFTemplate template = XWPFTemplate.compile(input)) {
			Map<String, Object> model = new HashMap<>();
			model.put("name", "Poi-TL");
			template.render(model);
			try (FileOutputStream output = new FileOutputStream(outputPath)) {
				template.write(output);
				output.flush();
			}
		}
	}
}
