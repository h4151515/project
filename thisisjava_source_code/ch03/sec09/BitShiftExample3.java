package ch03.sec09;

public class BitShiftExample3 {
	public static void main(String[] args) {
		int value = 772;   //<-- 이 숫자를 변경해 보세요.
		
		System.out.println("int ��:\t" + getIntBits(value));
		System.out.println();
		
		byte byte1 = (byte) (value >>> 24);
		System.out.println("byte1:\t" + getByteBits(byte1));
		
		byte byte2 = (byte)	(value >>> 16);
		System.out.println("byte2:\t" + getByteBits(byte2));
		
		byte byte3 = (byte) (value >>> 8);
		System.out.println("byte3:\t" + getByteBits(byte3));
		
		byte byte4 = (byte) value;		
		System.out.println("byte4:\t" + getByteBits(byte4));
	}
	
	public static String getIntBits(int value) {
		String str = Integer.toBinaryString(value);
		while(str.length() < 32) { str = "0" + str; }
		String intBits = "";
		for(int i=0; i<str.length(); i+=8) {
			String byteStr = str.substring(i, i+8);
			intBits += "[" + byteStr + "] ";
		}
		return intBits;
	}	
	
	public static String getByteBits(byte value) {	
		String str = Integer.toBinaryString(value);
		if(str.length() > 8) {
			str = str.substring(str.length()-8, str.length());
		} else {
			while(str.length() < 8) { str = "0" + str; }
		}
		return "[" + str + "] ";
	}
}
