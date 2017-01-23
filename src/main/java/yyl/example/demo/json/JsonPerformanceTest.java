package yyl.example.demo.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * 测试一下 fastjson和jackson序列化反序列化的性能<br>
 * 目前测试结果是对象转JSON两者性能差不多，但是JSON转对象fastjson比jaskson要快1倍。<br>
 * 但是总体差异没有网上传的10倍那么悬殊。<br>
 * 备注：初始化jackson-ObjectMapper需要耗时，但是因为ObjectMapper可以写成单例的，所以只需要初始化一次。<br>
 */
public class JsonPerformanceTest {

	private static final int DATA_SIZE = 10000;

	public static void main(String[] args) throws IOException {
		List<T> data = build();

		{
			ObjectMapper objectMapper = new ObjectMapper();
			long l = System.currentTimeMillis();
			List<String> jsons = new ArrayList<String>();
			for (T t : data) {
				jsons.add(objectMapper.writeValueAsString(t));
			}
			for (String json : jsons) {
				objectMapper.readValue(json, T.class);
			}
			System.out.println("jackson:encode->" + (System.currentTimeMillis() - l));
		}
		{
			long l = System.currentTimeMillis();
			List<String> jsons = new ArrayList<String>();
			for (T t : data) {
				jsons.add(JSON.toJSONString(t));
			}
			for (String json : jsons) {
				JSON.parseObject(json, T.class);
			}
			System.out.println("fastjson:encode->" + (System.currentTimeMillis() - l));
		}
		{
			Gson gson = new Gson();

			long l = System.currentTimeMillis();
			List<String> jsons = new ArrayList<String>();
			for (T t : data) {
				jsons.add(gson.toJson(t));
			}
			for (String json : jsons) {
				gson.fromJson(json, T.class);
			}
			System.out.println("gson:encode->" + (System.currentTimeMillis() - l));
		}
	}

	private static List<T> build() {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < DATA_SIZE; i++) {
			T t = new T();
			t.id = i;
			t.value = i + "";
			list.add(t);
		}
		return list;
	}

	static class T {
		private int id;
		private String value;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
