package kr.or.ddit.basic.Udp0110;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpClient {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			// 소켓 생성
			DatagramSocket socket = new DatagramSocket();
			
			//상대방의 주소 정보 생성
			//서버입장에서는 누가 클라이언트가 될지 모르니 수신한 패킷을 이용해 클라이언트의 ip정보, 포트번호를 구했는데
			//클라이언트는 서버의 ip주소를 아니까 접속하겠지...
			InetAddress address = InetAddress.getByName("localhost");
			
			while(true) {
				// 전송할 메세지 입력
				System.out.println("전송할 메세지를 입력하세요 >> ");
				String msg = sc.nextLine();
				
				//전송용 패킷 객체 생성
				DatagramPacket outpPacket = new DatagramPacket(msg.getBytes("utf-8"), msg.getBytes("utf-8").length, address, 8888);
				
				//전송하기
				socket.send(outpPacket);
				
				//종료하기, 반복문 탈출
				if("/end".equals(msg)) {
					break;
				}
				
				// -----------------------------------------------------------------------
				
				// 상대방이 보낸메세지 받기
				byte[] inMsg = new byte[1024];
				
				//수신용 패킷 객체 생성
				 DatagramPacket inPacket = new DatagramPacket(inMsg, inMsg.length);
				
				//데이터 수신하기
				 socket.receive(inPacket);
				 
				 String receiveMsg = new String(inMsg, 0, inPacket.getLength(), "utf-8");
				 
				 System.out.println("서버의 응답 메세지 : " + receiveMsg);
				 System.out.println();
				 
				 //서버랑 처리 순서가 반대??????라는데요 아 클라이언트니까 반대겠구나 ㅇㅇ Udp는 많이 사용하지 않는다고함..
			}
			
			System.out.println("수신 끝");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
