package yyl.example.demo.protobuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * _Protostuff 序列化和反序列化
 */
public class SerializeUtil {

	public static <T> byte[] encode(T object) {
		@SuppressWarnings("unchecked")
		Schema<T> schema = RuntimeSchema.getSchema((Class<T>) object.getClass());
		return ProtobufIOUtil.toByteArray(object, schema, LinkedBuffer.allocate(512));
	}

	public static <T> T decode(byte[] data, Class<T> clazz) {
		Schema<T> schema = RuntimeSchema.getSchema(clazz);
		T message = (T) schema.newMessage();
		ProtobufIOUtil.mergeFrom(data, message, schema);
		return message;
	}

}
