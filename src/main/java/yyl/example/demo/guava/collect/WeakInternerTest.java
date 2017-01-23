package yyl.example.demo.guava.collect;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * 返回对象的规范化表示形式(类似String.intern)方法。 <br>
 * 但是这个字符串池是用WeakInterner维护的<br>
 * 能保证相同(equals判断)对象经过 intern 返回的对象是同一个(==判断)<br>
 */
public class WeakInternerTest {

	public static void main(String[] args) throws Exception {
		Interner<Integer> interner = Interners.newWeakInterner();

		Integer v1 = new Integer(1);
		Integer v2 = new Integer(1);
		Integer v3 = interner.intern(v1);
		Integer v4 = interner.intern(v2);

		System.out.println("v1==v2 : " + (v1 == v2));
		System.out.println("v1==v3 : " + (v1 == v3));
		System.out.println("v3==v4 : " + (v1 == v4));
	}
}
