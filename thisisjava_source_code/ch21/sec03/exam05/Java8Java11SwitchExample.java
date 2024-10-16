package ch21.sec03.exam05;

public class Java8Java11SwitchExample {	
	private static void method(Shape shape) {
		String result;
		switch(shape) {
			case LINE:
				result = "선을 그립니다.";
				break;
			case TRIANGLE: 	
				result = "삼각형을 그립니다."; 
				break;
			case RECTANGLE:
				result = "사각형을 그립니다."; 
				break;
			default: //result 변수를 초기화시켜야 하기 때문에 default가 있어야함
				result = "도형을 그리지 않습니다.."; 
				break;
		}
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		method(Shape.TRIANGLE);
	}
}
