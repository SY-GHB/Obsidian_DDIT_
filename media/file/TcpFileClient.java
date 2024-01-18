package kr.or.ddit.basic.Tcp0109;

import java.awt.Panel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TcpFileClient {
	private Socket socket;
	private BufferedInputStream bin;
	private BufferedOutputStream  bout;
	private DataOutputStream dout;
	
	public static void main(String[] args) {
		new TcpFileClient().clientStart();
	}
	
	public void clientStart() {
		//전송할 파일 정보를 갖는 File 객체 생성
//		File file = new File("e:/d_other/펭귄.jpg");
		File file = chooseFile("OPEN");
		
		if(file==null) {
			System.out.println("전송할 파일을 선택해야 합니다.");
			return;
		}
		
		String fileName = file.getName();
		if(!file.exists()) {
			//전송할 파일이 있나없나를 검사함
			System.out.println(fileName + "파일이 존재하지 않습니다.");
			System.out.println("파일 전송 작업을 중단합니다..");
			return;
		}
		
		try {
			socket = new Socket("localhost", 7777);
			System.out.println("파일 전송 시작");
			
			//서버에 접속하면 첫번째로 '파일명'을 전송한다.
			dout = new DataOutputStream(socket.getOutputStream());
			dout.writeUTF(fileName);
			
			// 파일을 읽어서 Socket으로 전송(출력)하기
			
			// - 파일 읽기용 스트림 객체 생성
			bin = new BufferedInputStream(new FileInputStream(file));
			
			//전송용 스트림 객체 생성
			bout = new BufferedOutputStream(socket.getOutputStream());
			
			//파일을 읽어서 Socket으로 전송(출력)하기. byte배열로 하면 조금 더 빠르다
			byte[] temp = new byte[1024];
			int length = 0;
			while((length=bin.read(temp))!=-1) {
				bout.write(temp,0,length);
			}
			
			bout.flush();
			
			System.out.println("파일 전송 완료");
			
		} catch (Exception e) {
			System.out.println("파일 전송 실패");
			e.printStackTrace();
		}finally {
			if(bin!=null) try {bin.close();} catch (Exception e2) { }
			if(bout!=null) try {bout.close();} catch (Exception e2) { }
			if(dout!=null) try {dout.close();} catch (Exception e2) { }
			if(socket!=null) try {socket.close();} catch (Exception e2) { }
		}
		
		
	}

	public File chooseFile(String option) {
		// SWING의 파일 열기, 저장 창 연습
		JFileChooser chooser = new JFileChooser();
		
		// 선택할 파일의 확장자 설정하기
		FileNameExtensionFilter txt = new FileNameExtensionFilter("text파일(*.txt)", "txt");
		FileNameExtensionFilter img = new FileNameExtensionFilter("이미지파일", new String[]{"png", "jpg", "gif"});
		FileNameExtensionFilter doc = new FileNameExtensionFilter("MS워드", "doc", "docx");
		
		chooser.addChoosableFileFilter(txt);
		chooser.addChoosableFileFilter(img);
		chooser.addChoosableFileFilter(doc);
		
		// 확장자 목록 중 기본적으로 선택될 확장자 지정하기
		chooser.setFileFilter(img);
		
		//  Dialog 창에 보여줄 기본 디렉토리(폴더) 설정
		chooser.setCurrentDirectory(new File("d:/d_other"));
		
		int result;
		if("SAVE".equals(option.toUpperCase())) {
			result = chooser.showSaveDialog(new Panel()); // 저장용 창
		}else if("OPEN".equals(option.toUpperCase())) {
			result = chooser.showOpenDialog(new Panel()); // 열기용 창
		}else {
			System.out.println("Option은 'SAVE' 또는 'OPEN'만 가능합니다. ");
			return null;
		}
		
		File selectedFile = null;
		// dialog 창에서 '열기' 또는 '저장' 버튼을 눌렀을 경우...
		if(result == JFileChooser.APPROVE_OPTION) {
			// Dialog 창에서 선택한 파일 정보를 가져와  
			// 실제 '저장' 또는 '읽기' 작업을 수행한다.
			selectedFile = chooser.getSelectedFile();
		}
		return selectedFile;
	}

}
