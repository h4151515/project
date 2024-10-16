package ch21.sec01;

public class VarExample1 {	
	public static void main(String[] args) {
		String x = getData();
		var name = getData();
	}
	
	public static String getData() {
		return "홍길동";
	}	
}

