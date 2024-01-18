package kr.or.ddit.basic.Udp0110;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 	UDP방식 ==> 비연결 지향, 데이터에 대한 신뢰성이 없다. 데이터가 순서대로 도착한다는 보장이 없다.
 			그러나 TCP방법보다 속도가 빠르다.
 	DatagramSocket객체와 DatagramPacket객체를 이용하여 통신한다.
 	- DatagramSocket : 데이터의 송수신과 관련된 작업을 수행한다.(우체부 역할)
 	- DatagramPacket : 주고받는 데이터와 관련된 작업을 수행한다. (소포)
 						==>  수신용 생성자와 송신용 생성자를 따로 제공한다.
 						
 	TCP방식은  Stream을 이용해서 송수신하지만
 	UDP방식은 Datagram을 이용해서 송수신한다.
 			  
 */

public class UdpServer {
	public static void main(String[] args) {
		try {
			// DatagramSocket 객체 생성 ==> 포트번호를 지정해서 생성한다.
			DatagramSocket socket = new DatagramSocket(8888);
			System.out.println("서버 실행 중");
			
			while(true) {
				// 클라이언트가 보낸 메세지를 받기 위해 수신한 메세지가 저장될 변수를 선언한다.  ==> byte형 배열
				byte[] inMsg = new byte[1024];
				
				//수신용 패킷 객체 생성 ==> 데이터가 저장될 byte혀여 배열, 이 배열의 길이를 인수로 지정하여 생성한다.
				DatagramPacket inPacket = new DatagramPacket(inMsg, inMsg.length);
				
				//데이터 수신하기 ==>  Socket객체의 receive()메서드 이용
				// -receive()메서드는 데이터가 올 때까지 기다린다.
				// - 수신된 데이터의 패킷정보는 인수로 지정한 패킷변수(inPacket)에 저장된다.
				socket.receive(inPacket);
				
				// 받은 패킷에서 상대방의 IP정보, 포트번호 등을 알 수 있다.
				InetAddress address = inPacket.getAddress();
				int port = inPacket.getPort();
				
				System.out.println("상대방의 IP정보: " + address);
				System.out.println("상대방의 Port번호: " + port);
				System.out.println();
				
				//상대방이 보낸 메세지 출력하기
				// - 수신패킷.getData() ==> 실제 읽어온 데이터를 byte형 배열로 반환한다.
				// - 수신패킷을 생성할 때 인수로 지정한 byte형 배열에도 데이터가 저장된다.
				// - 수신패킷.getLength(); ==> 실제 읽어온 데이터의 길이를 반환한다.
				
				// byte형 배열의 자료를 String객체로 변환하기
				// 1024짜리 배열을 줬는데 1024가 전부 다 안 채워졌으면 빈부분은 공백이다.
				// 그걸 String으로 바꾸면 공백부분까지 String으로 만들어진다.
				// 그래서 실제 읽어온 데이터까지만 변환하도록 시켰다. 위아래는 똑같다.
				
//				String msg = new String(inMsg, 0, inPacket.getLength(), "utf-8");
				String msg = new String(inPacket.getData(), 0, inPacket.getLength(), "utf-8");
				
				if("/end".equals(msg)) {
					break;
				}
				
				System.out.println("상대방이 보낸 메시지 : " + msg );
				System.out.println("────────────────────────────────");
				System.out.println();
				
				//──────────────────────────────────────────────────────────────────
				
				//상대방에게 메세지 보내기 ==> 수신받은 메세지를 그대로 송신한다.
				
				// 송신할 데이터를 byte형 배열로 변환한다.
				byte[] sendMsg = msg.getBytes("utf-8");
						
				// 송신용 패킷객체를 생성한다.	 ==> 송신할 데이터가 저장된 byte형 배열,
				//						==> 송신할 자료의 길이(배열의 길이), 상대방의 주소정보
				///                     ==> 상대방의 포트번호까지 4가지를 인수로 지정하여 생성한다.
				DatagramPacket outPackt = new DatagramPacket(sendMsg, sendMsg.length, address, port);
				
				// 메세지 전송하기 ==> Socket객체의 send()메서드를 이용한다.
				// 이 send()메서드에 송신용 패킷객체를 인수로 넣어서 실행한다.
				socket.send(outPackt);
				System.out.println("송신 완료");
				System.out.println();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

