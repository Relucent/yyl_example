package yyl.example.demo.jodd;

import java.util.Date;

import jodd.typeconverter.TypeConverterManager;

/**
 * Jodd是一个Java便捷的开源迷你框架，包含工具类、实用功能的集合<br>
 */
public class JoddDemo2 {
    public static void main(String[] args) {
        // Jodd自带的类型转换只能转换规范的数据格式
        TypeConverterManager typeConverterManager = TypeConverterManager.get();
        Date date = typeConverterManager.convertType("2023-06-01T09:00:00", Date.class);
        System.out.println(date);
        String string = typeConverterManager.convertType(date, String.class);
        System.out.println(string);
    }
}
