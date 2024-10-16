package ch21.sec04;

public class Java21RecordExample2 {
	private static void area(Object obj) {
		switch(obj) {
			case Rectangle(int width, int height) -> System.out.println("area:  " + (width * height));
			case null, default -> System.out.println("unknown");
		}
	}

	public static void main(String[] args) {
		Rectangle rect = new Rectangle(10, 5);
		area(rect);
	}
}
