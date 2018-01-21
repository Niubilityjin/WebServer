package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * nnnn
 * 该线程任务提供WebServer使用,负责处理指定客户端的交互
 * @author Administrator
 *
 */
public class ClientHandler implements Runnable{
	private Socket socket;
	public  ClientHandler(Socket socket){
		this.socket=socket;
	}
	public void run(){
		try {
			/*
			 * 测试读取客户端发送过来的Http请求内容
			 */
			InputStream in=socket.getInputStream();
			int d=-1;
			while((d=in.read())!=-1){
				System.out.print((char)d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
