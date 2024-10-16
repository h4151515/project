package ch21.sec03.exam01;

import java.util.Date;

public class Java8Java11SwitchExample {
	public static void method1(Object obj) {		
		if(obj == null) {
			System.out.println("unknown");
		} else if(obj instanceof Integer) {
			int i = (Integer) obj;
			System.out.println(i);
		} else if(obj instanceof String) {
			String s = (String)obj;
			System.out.println("\"" + s + "\"");
		} else {
			System.out.println("unknown");
		}
	}
	
	private static void method2(Object obj) {
		String data;
		if(obj == null) {
			data = "unknown";
		} else if(obj instanceof Integer) {
			data = String.valueOf((Integer) obj);
		} else if(obj instanceof String) {
			data = "\"" + (String)obj + "\"";
		} else {
			data = "unknown";
		}
		System.out.println(data);
	}


	public static void main(String[] args) {
		method1(10);
		method1("10");
		method1(null);
		method1(new Date());
		
		method2(10);
		method2("10");
		method2(null);
		method2(new Date());
	}
}
