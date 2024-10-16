package ch13.sec03.exam02;

public class GenericExample {
	//제네릭 메소드
	public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
		boolean keyCompare = p1.getKey().equals(p2.getKey()) ;
		boolean valueCompare = p1.getValue().equals(p2.getValue());
	    return keyCompare && valueCompare;
	}	
	
	public static void main(String[] args) {
		Pair<Integer, String> p1 = new Pair<>(1, "사과");
		Pair<Integer, String> p2 = new Pair<>(1, "사과");
		//제네릭 메소드 호출
		boolean result1 = compare(p1, p2);
		System.out.println(result1);

		Pair<String, String> p3 = new Pair<>("user1", "홍길동");
		Pair<String, String> p4 = new Pair<>("user2", "홍길동");
		//제네릭 메소드 호출
		boolean result2 = compare(p3, p4);
		System.out.println(result2);
	}
}
