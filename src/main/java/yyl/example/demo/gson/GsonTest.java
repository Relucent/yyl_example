package yyl.example.demo.gson;

import java.util.Arrays;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTest {

	public static void main(String[] args) {

		Gson gson = new GsonBuilder()
				//禁止转义HTML标签
				.disableHtmlEscaping()
				//禁止序列化内部类
				.disableInnerClassSerialization()
				//设置日期时间格式
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				//序列化NULL
				.serializeNulls()
				//对JSON结果格式化
				.setPrettyPrinting()
				//生成不可执行的JSON，会在JSON头部增加   )]}' 这4个字符
				.generateNonExecutableJson()
				//创建GSON
				.create();//

		Object[] o = { 1, "2", new Date(), null };

		String json = gson.toJson(o);

		System.out.println(json);

		System.out.println(Arrays.toString(gson.fromJson(json, Object[].class)));

	}

}
