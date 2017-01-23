package yyl.example.demo.jacob;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Window-XP
 */
public class ActiveXComponentDemo {
	public static void main(String[] s) {
		ActiveXComponent sC = new ActiveXComponent("ScriptControl");
		Dispatch sControl = sC.getObject();
		Dispatch.put(sControl, "Language", "VBScript");
		// use COM component in same thread
		Variant v = Dispatch.call(sControl, "Eval", "1+2*3");
		System.out.println(v.toString());
	}
}
