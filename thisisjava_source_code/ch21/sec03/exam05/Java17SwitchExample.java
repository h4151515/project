package ch21.sec03.exam05;

public class Java17SwitchExample {	
	private static void method(Shape shape) {
		String result = switch(shape) {
			case LINE -> "선을 그립니다.";
			case TRIANGLE -> "삼각형을 그립니다."; 
			case RECTANGLE -> "사각형을 그립니다."; 
		};
		System.out.println(result);
	}

	public static void main(String[] args) {
		method(Shape.TRIANGLE);
	}
}
