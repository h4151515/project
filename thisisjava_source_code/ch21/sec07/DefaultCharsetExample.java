package ch21.sec07;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;

public class DefaultCharsetExample {
	public static void main(String[] args) throws Exception {
		// 자바 기본 문자셋 확인
		Charset javaCharset = Charset.defaultCharset();
		System.out.println("Java 기본 문자셋: " + javaCharset.toString());

		// 한글을 byte[] 배열로 변환
		//byte[] bytes = "자바".getBytes();
		byte[] bytes = "자바".getBytes(Charset.forName("UTF-8"));
		System.out.println("바이트 수: " + bytes.length + " bytes");

		// 한글을 파일에 저장
		File file = new File("C:/Temp/file.txt");
		//FileWriter writer = new FileWriter(file);
		FileWriter writer = new FileWriter(file, Charset.forName("UTF-8"));
		
		writer.write("한글");
		writer.flush();
		writer.close();
		System.out.println("파일 크기: " + file.length() + " bytes");
	}
}