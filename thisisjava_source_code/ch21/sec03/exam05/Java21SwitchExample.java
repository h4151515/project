package ch21.sec03.exam05;

public class Java21SwitchExample {	
	//그룹핑된 enum 타입을 레이블로 사용
	private static void method1(Drawable drawable) {
		String result = switch(drawable) {
			case Shape s when s == Shape.LINE -> "선을 그립니다.";
			case Shape s when s==Shape.TRIANGLE -> "삼각형을 그립니다."; 
			case Shape s when s==Shape.RECTANGLE  -> "사각형을 그립니다."; 
			case Image i when i ==Image.JPEG -> "JPEG 이미지를 그립니다.";
			case Image i when i==Image.PNG -> "PNG 이미지를 그립니다.";
			//Drawable이 봉인되지 않았다면 default 필수
			//default -> "도형을 그리지 않습니다."; 
		};
		System.out.println(result);
	} 
	
	//그룹핑된 enum 타입의 상수를 레이블로 사용
	private static void method2(Drawable drawable) {
		String result = switch(drawable) {
			case Shape.LINE -> "선을 그립니다.";
			case Shape.TRIANGLE -> "삼각형을 그립니다."; 
			case Shape.RECTANGLE -> "사각형을 그립니다."; 
			case Image.JPEG -> "JPEG 이미지를 그립니다.";
			case Image.PNG -> "PNG 이미지를 그립니다.";
			//Shape과 Image 타입 패턴이 없기 때문에 필수로 작성
			default -> "도형을 그리지 않습니다."; 
		};
		System.out.println(result);
	} 
	
	//그룹핑된 enum 타입과 상수를 혼용해서 레이블로 사용
	private static void method3(Drawable drawable) {
		String result = switch(drawable) {
			case Shape.LINE -> "선을 그립니다.";
			case Shape.TRIANGLE -> "삼각형을 그립니다."; 
			case Shape.RECTANGLE -> "사각형을 그립니다."; 
			case Image i -> "이미지를 그립니다.";
			//Shape 타입 패턴이 없기 때문에 필수로 작성
			default -> "도형을 그리지 않습니다."; 
		};
		System.out.println(result);
	} 	
	
	public static void main(String[] args) {
		method1(Shape.TRIANGLE);
		method2(Image.JPEG);
		method3(Image.PNG);
	}
}

