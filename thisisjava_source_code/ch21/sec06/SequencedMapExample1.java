package ch21.sec06;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SequencedMap;

public class SequencedMapExample1 {
	public static void main(String[] args) {
		//순서있는 Map 컬렉션 생성
		SequencedMap<Integer, String> map = new LinkedHashMap<>();
		
		//첫번째 요소로 저장
		map.putFirst(1, "김길동");
		map.putFirst(2, "박길동");
		map.putFirst(3, "이길동");
		
		//마지막 요소로 저장
		map.putLast(2, "마길동");
		map.putLast(4, "탁길동");
		map.putLast(5, "홍길동");
		
		//첫번째 엔트리 가져오기
		Map.Entry<Integer, String> firstEntry = map.firstEntry();
		System.out.println("첫번째 엔트리: " + firstEntry.getKey() + ", " + firstEntry.getValue());
		
		//첫번째 엔트리를 가져오고, 제거
		firstEntry = map.pollFirstEntry();
		System.out.println("첫번째 엔트리: " + firstEntry.getKey() + ", " + firstEntry.getValue());
		 
		//마지막 엔트리 가져오기
		Map.Entry<Integer, String> lastEntry = map.lastEntry();
		System.out.println("마지막 엔트리: " + lastEntry.getKey() + ", " + lastEntry.getValue());
		
		//마지막 엔트리를 가져오고, 제거
		lastEntry =map.pollLastEntry();
		System.out.println("마지막 엔트리: " + lastEntry.getKey() + ", " + lastEntry.getValue());
		
		System.out.println();
		
		//순서대로 엔트리를 일괄처리
		for(Map.Entry<Integer, String> entry : map.sequencedEntrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		
		System.out.println();
		
		//엔트리의 순서를 뒤바꾸기
		SequencedMap<Integer, String> reversedMap = map.reversed();
		
		//순서대로 엔트리를 일괄처리
		for(int key : reversedMap.sequencedKeySet()) {
			System.out.println(key + ", " + reversedMap.get(key));
		}
	}
}
