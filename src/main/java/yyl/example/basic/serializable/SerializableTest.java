package yyl.example.basic.serializable;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 序列化
 */
public class SerializableTest {

	public static void main(String[] args) throws Exception {
		ArrayList<Serializable> list = new ArrayList<Serializable>();
		list.add("String");
		list.add(Double.class);
		list.add(new Date());

		Object objsrc = list;
		byte[] src = SerializableUtil.writeObject(objsrc);
		Object objdest = SerializableUtil.readObject(src);

		System.out.println(src);

		System.out.println("objsrc  :	" + objsrc);
		System.out.println("objdest :	" + objdest);
		System.out.println("objsrc.equals(objdest):	" + objsrc.equals(objdest));
		System.out.println("objsrc == objdest:	" + (objsrc == objdest));

		System.out.println(SerializableUtil.writeObject(""));

		Singleton singletonCopy = (Singleton) SerializableUtil.readObject(//
				SerializableUtil.writeObject(Singleton.INSTANCE));
		System.out.println("Singleton.INSTANCE == singletonCopy -> " + (Singleton.INSTANCE == singletonCopy));

		ResolveSingleton resolveSingletonCopy = (ResolveSingleton) SerializableUtil.readObject(//
				SerializableUtil.writeObject(ResolveSingleton.INSTANCE));
		System.out.println("ResolveSingleton.INSTANCE == resolveSingletonCopy -> " + (ResolveSingleton.INSTANCE == resolveSingletonCopy));

	}

	@SuppressWarnings("serial")
	public static class Singleton implements Serializable {
		static final Singleton INSTANCE = new Singleton();

		private Singleton() {
		}
	}

	@SuppressWarnings("serial")
	public static class ResolveSingleton implements Serializable {
		static final ResolveSingleton INSTANCE = new ResolveSingleton();

		private ResolveSingleton() {
		}

		//防止序列化+反序列化生成新的对象
		private Object readResolve() throws ObjectStreamException {
			return INSTANCE;
		}
	}
}
