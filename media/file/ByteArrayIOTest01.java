package kr.or.ddit.basic.IO0103;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ByteArrayIOTest01 {
	public static void main(String[] args) {
		byte[] inSrc = {0,1,2,3,4,5,6,7,8,9};
		byte[] outSrc = null;
		
		//입력용 스트림 객체 생성
		ByteArrayInputStream bin = new ByteArrayInputStream(inSrc);
		//출력용 스트림 객체 생성
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		int data; //읽어온 자료가 저장될 변수
		
		//.read() 메서드 ==> 한번에 1바이트씩 읽어오고 더이상 읽어올 게 없으면 -1을 반환한다.
		//읽어올수있는만큼 읽어와라. 처음부터 끝까지 읽어오는 방법.
		while((data = bin.read()) != -1) {
			// 읽어온 데이터를 처리는 명령을 반복문 안에 기술하면 된다.
			bout.write(data);	// 출력하기
 		}
		
		// 출력된 스트림값을 배열로 변환해서 저장하기
		outSrc = bout.toByteArray();
		
		
		// 스트림 작업이 끝나면 스트림을 닫아준다. 스트림작업에는 exception처리 필요
		try {
			bin.close();
			bout.close();
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		//읽어온 그대로 출력했기대문에 같은 결과값이 나온다.
		System.out.println("inSrc => " + Arrays.toString(inSrc));
		System.out.println("outSrc => " + Arrays.toString(outSrc));
		
	}
}
