package yyl.example.demo.reflectasm;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;;

/**
 * ReflectASM 使用字节码生成的方式实现了更为高效的反射机制。<br>
 */
public class ReflectasmTest {

    static class A {

        public String name;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    public static void main(String[] args) {
        ConstructorAccess<A> constructorAccess = ConstructorAccess.get(A.class);
        FieldAccess fieldAccess = FieldAccess.get(A.class);
        MethodAccess methodAccess = MethodAccess.get(A.class);
        A a = constructorAccess.newInstance();
        fieldAccess.set(a, "name", "asm");
        methodAccess.invoke(a, "setValue", "hello");
        System.out.println(a.getValue());
        System.out.println(a.name);
    }
}
