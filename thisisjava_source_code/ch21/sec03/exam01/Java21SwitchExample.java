package ch21.sec03.exam01;

import java.util.Date;

public class Java21SwitchExample {
	public static void method1(Object obj) {
		switch(obj) {
			case Integer i -> System.out.println(i);
			case String s -> System.out.println("\"" + s + "\"");
			case null, default ->  System.out.println("unknown");
		};
	}	
	
	private static void method2(Object obj) {
		String data = switch(obj) {
			case Integer i -> String.valueOf(i);
			case String s -> "\"" + s + "\""; 
			case null, default -> "unknown";
		};
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
