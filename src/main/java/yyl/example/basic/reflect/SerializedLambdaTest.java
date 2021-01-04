package yyl.example.basic.reflect;

import yyl.example.basic.reflect.SerializedLambda.SerializableGetFunction;
import yyl.example.basic.reflect.SerializedLambda.SerializableSetFunction;

public class SerializedLambdaTest {

    public static void main(String[] args) {
        SerializableGetFunction<SerializedLambdaTest, String> getLambda = SerializedLambdaTest::get;
        SerializableSetFunction<SerializedLambdaTest, String> setLambda = SerializedLambdaTest::set;
        System.out.println(SerializedLambda.resolve(getLambda));
        System.out.println(SerializedLambda.resolve(setLambda));
    }

    public String get() {
        return null;
    }

    public void set(String value) {
    }

}
