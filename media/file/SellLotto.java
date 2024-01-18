package kr.or.ddit.basic;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class SellLotto {
	static Scanner sc = new Scanner(System.in);
	public static void printLotto() {
		System.out.println("==========================\r\n" + 
				"    Lotto 프로그램\r\n" + 
				"--------------------------\r\n" + 
				"    1. Lotto 구입\r\n" + 
				"    2. 프로그램 종료\r\n" + 
				"==========================");
	}

	public static void main(String[] args) {
		while(true) {
			printLotto();
			
			System.out.println("선택: ");
			int sel = sc.nextInt();
			
			switch(sel) {
			case 1: 
				System.out.println("구매금액을 입력해주세요.");
				int price = sc.nextInt();
				int checkPrice = checkPrice(price);
				sellLotto(checkPrice);
				continue;
			}
			System.out.println("로또프로그램을 종료합니다. 감사합니다.");
			break;
		}
		
	}
	
	public static void sellLotto(int price) {
		System.out.println("로또 구입 시작\r\n1000원에 로또번호 하나입니다.");
		System.out.println();
		int amt = (price/1000);
		
		for (int i = 1; i < amt+1; i++) {
			//반복 돌 때마다 한 줄 발급, 가격만큼 반복.
			HashSet<Integer> lotto = new HashSet<Integer>();
			for (int j = 0; j < 6; j++) {
				//로또숫자는 1부터 45까지라고 치고
				//6개를 뽑은 걸 로또 한 줄이라고 하자.
				
				//가끔 5개가 뽑히는 경우가 있던데 왜인거지?????
				lotto.add((int)(Math.random()*45 +1));
				
			}
			
			ArrayList<Integer> lottoList = new ArrayList<Integer>(lotto);
			System.out.println(i+"번 로또번호"+lottoList);
		}
		System.out.println();
	}
	
	public static int checkPrice(int price) {
		if(price>100000) {
			
			System.out.println("구매금액이 너무 많습니다."
					+ "\r\n한번에 100장(10만원)이 최대입니다.\r\n다시 구매금액을 입력해주세요.");
			price = sc.nextInt();
			return price;
			
		}else if(price<1000) {
			
			System.out.println("구매금액이 너무 적습니다."
					+ "\r\n한번에 1장(1000원)이 최소입니다.\r\n다시 구매금액을 입력해주세요.");
			price = sc.nextInt();
			return price;
		}else {
			return price;
		}
	}
}
