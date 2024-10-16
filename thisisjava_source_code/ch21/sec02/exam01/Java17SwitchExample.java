package ch21.sec02.exam01;

public class Java17SwitchExample {
	private static void method(String s) {
		if (s == null) {
			System.out.println("null");
			return;
		}
		switch (s) {
			case "a", "b" -> System.out.println("a or b");
			case "c" -> System.out.println("c");
			default -> System.out.println("d");
		}
	}

	public static void main(String[] args) {
		method(null);
	}
}
