package yyl.example.demo.jacob;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 使用JACOB调用Excel
 */
// 'net.sf.jacob-project:jacob:jar:1.14.3'
public class ExcelInvokeDemo {
	
	public static void main(String[] s) {
		ComThread.InitSTA();
		// 获取ACTIVEX组件实例
		ActiveXComponent xl = new ActiveXComponent("Excel.Application");
		try {
			System.out.println("version=" + xl.getProperty("Version"));
			// 获取X1对象中version属性的值
			System.out.println("version=" + Dispatch.get(xl, "Version"));
			// 将true值赋给x1对象中的Visible属性
			Dispatch.put(xl, "Visible", new Variant(true));
			// 获得x1对象中的Workbooks属性，并将转为对象
			Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
			// 获得workbooks对象的add属性
			Dispatch workbook = Dispatch.get(workbooks, "Add").toDispatch();
			// 获得workbooks对象的ActiveSheet属性
			Dispatch sheet = Dispatch.get(workbook, "ActiveSheet").toDispatch();
			// 对sheet对象的Range属性执行其GET方法，再将Range属性的值设为A1
			Dispatch a1 = Dispatch.invoke(sheet, "Range", Dispatch.Get, new Object[] { "A1" }, new int[1]).toDispatch();
			// 同上
			Dispatch a2 = Dispatch.invoke(sheet, "Range", Dispatch.Get, new Object[] { "A2" }, new int[1]).toDispatch();
			// 将a1对象中的Value属性设为"123.456"
			Dispatch.put(a1, "Value", "123.456");
			Dispatch.put(a2, "Formula", "=A1*2");
			System.out.println("a1 from excel:" + Dispatch.get(a1, "Value"));
			System.out.println("a2 from excel:" + Dispatch.get(a2, "Value"));
			Variant f = new Variant(false);
			System.out.println(f);
			// Dispatch.call(workbook, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			xl.invoke("Quit", new Variant[] {});
			ComThread.Release();
		}
	}
}
