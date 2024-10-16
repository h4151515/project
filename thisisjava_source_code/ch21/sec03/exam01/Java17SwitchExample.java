package ch21.sec03.exam01;

import java.util.Date;

public class Java17SwitchExample {
	public static void method1(Object obj) {
		if(obj == null) {
			System.out.println("unknown");
		} else if(obj instanceof Integer i) {
			System.out.println(i);
		} else if(obj instanceof String s) {
			System.out.println("\"" + s + "\"");
		} else {
			System.out.println("unknown");
		}
	}	
	
	private static void method2(Object obj) {
		String data;
		if(obj == null) {
			data = "unknown";
		} else if(obj instanceof Integer i) {
			data = String.valueOf(i);
		} else if(obj instanceof String s) {
			data = "\"" + s + "\"";
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

