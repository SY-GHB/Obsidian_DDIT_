package kr.or.ddit.basic.homework;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//JSON표기 생성
public class JSONExample01 {
	public static void main(String[] args) {
		
		//JSON 객체 생성
		JSONObject root = new JSONObject();
		
		
		//속성 추가
		root.put("name", "홍길동");
		root.put("age", 20);
		root.put("adult", true);
		
		//객체 속성 추가
		JSONObject tel = new JSONObject();
		tel.put("phone", "010-0000-0000");
		tel.put("home", "055-000-0000");
		root.put("tel", tel);
		
		//배열 속성 추가
		JSONArray addr = new JSONArray();
		addr.put("서울");
		addr.put("대전");
		addr.put("대구");
		addr.put("부산");
		root.put("addr", addr);
		
		//JSON문자열로 변환
		String json = root.toString();
		
		//콘솔에 출력
		System.out.println(json);
		
		//파일로 저장
		try {
			FileWriter fw = new FileWriter("e:/d_other/JSONexample.json");
			fw.write(json);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
