package yyl.example.basic.reflect;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.Function;

import yyl.example.basic.util.ClassUtil;
import yyl.example.basic.util.SerializationUtil;

/**
 * Custom Serialized Lambda <br>
 * @see java.lang.invoke.SerializedLambda
 */
public class SerializedLambda implements Serializable {

    /** CustomSerialized.serialVersionUID */
    private static final long serialVersionUID = 8025925345765570181L;

    public Class<?> capturingClass;
    public String functionalInterfaceClass;
    public String functionalInterfaceMethodName;
    public String functionalInterfaceMethodSignature;
    public String implClass;
    public String implMethodName;
    public String implMethodSignature;
    public int implMethodKind;
    public String instantiatedMethodType;
    public Object[] capturedArgs;

    public static <T, R> SerializedLambda resolve(SerializableGetFunction<T, R> lambda) {
        return resolveLambda((Object) lambda);
    }

    public static <T, P> SerializedLambda resolve(SerializableSetFunction<T, P> lambda) {
        return resolveLambda((Object) lambda);
    }

    private static <T, R> SerializedLambda resolveLambda(Object lambda) {

        if (!lambda.getClass().isSynthetic()) {
            throw new IllegalArgumentException("The argument must be a composite class produced by a lambda expression");
        }

        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(SerializationUtil.serialize(lambda))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz;
                try {
                    clazz = ClassUtil.toClassConfident(objectStreamClass.getName());
                } catch (Exception ex) {
                    clazz = super.resolveClass(objectStreamClass);
                }
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalArgumentException("unknown error", e);
        }
    }

    @Override
    public String toString() {
        String interfaceName = functionalInterfaceClass;
        String implName = implClass;
        return String.format("%s -> %s::%s", interfaceName.substring(interfaceName.lastIndexOf('/') + 1),
                implName.substring(implName.lastIndexOf('/') + 1), implMethodName);
    }

    @FunctionalInterface
    public static interface SerializableGetFunction<T, R> extends Function<T, R>, Serializable {
    }

    @FunctionalInterface
    public static interface SerializableSetFunction<T, P> extends BiConsumer<T, P>, Serializable {
    }
}
