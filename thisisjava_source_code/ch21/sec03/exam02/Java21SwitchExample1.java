package ch21.sec03.exam02;

public class Java21SwitchExample1 {
	private static void method(Object obj) {
		int score = switch (obj) {
			case Integer i -> {
				int value;
				if (i == 1)
					value = 90;
				else if (i == 2)
					value = 80;
				else
					value = 70;
				yield value;
			}
			case String s -> {
				int value;
				if (s.equals("a"))
					value = 90;
				else if (s.equals("b"))
					value = 80;
				else
					value = 70;
				yield value;
			}
			case null, default -> 0;
		};
		System.out.println("score: " + score);
	}

	public static void main(String[] args) {
		method(1);
		method(2);
		method(3);
		method("a");
		method("b");
		method("c");
		method(null);
	}
}
