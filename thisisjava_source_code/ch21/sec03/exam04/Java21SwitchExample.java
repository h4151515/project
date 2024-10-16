package ch21.sec03.exam04;

public class Java21SwitchExample {
	private static void method1(Integer obj) {
		switch (obj) {
			case 0 -> System.out.println("0");
			case Integer i when i > 0 -> System.out.println("positive number");
			case Integer i -> System.out.println("negative number");
		}
	}

	private static class A {
	}

	private static class B extends A {
	}

	private static void method2(Object obj) {
		switch (obj) {
			case B b -> System.out.println("obj is B type");
			case A a -> System.out.println("obj is A type");
			case null, default -> System.out.println("obj is null or unknown type");
		}
	}

	public static void main(String[] args) {
		method1(5);
		method2(new A());
	}
}
