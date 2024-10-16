package ch21.sec02.exam01;

public class Java8Java11SwitchExample {
	private static void method(String s) {
		if (s == null) {
			System.out.println("null");
			return;
		}
		switch (s) {
			case "a":
			case "b":
				System.out.println("a or b");
				break;
			case "c":
				System.out.println("c");
				break;
			default:
				System.out.println("d");
		}
	}

	public static void main(String[] args) {
		method(null);
	}
}
