package ch21.sec06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SequencedCollection;

public class UnmodifiableExample {
	public static void method1() {
		//순차 컬렉션 생성
		SequencedCollection<String> collection = new ArrayList<>();
		
		//요소 추가
		collection.addFirst("데이터1");
		collection.addFirst("데이터2");
		collection.addFirst("데이터3");
		
		//다른 메소드로 수정할 수 없는 순차 컬렉션 전달
		method2(Collections.unmodifiableSequencedCollection(collection));
	}
	
	public static void method2(SequencedCollection<String> collection) {
		//모든 요소를 일괄 처리
		for(String data : collection) {
			System.out.println(data);
		}
		
		//첫번째 요소를 제거 (실행 예외 발생: UnsupportedOperationException)
		collection.removeFirst();
	}
	
	public static void main(String[] args) {
		method1();
	}
}
