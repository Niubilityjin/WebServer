package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Webserver web服务端主类
 * 
 * @author Administrator
 *
 */
public class WebServer {
/**
 * 新建一个用于负责和客户端进行TCP连接的ServerSocket
 * @param args
 */
	private ServerSocket server;
	/**
	 * 构造方法，用来初始化服务器
	 * @param args
	 */
	public WebServer(){
		try {
			server=new ServerSocket(8088);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void start() {
		try {
			while(true) {
				System.out.println("等待客户端连接");
				Socket socket=server.accept();
				ClientHandler handler=new ClientHandler(socket);
				Thread t=new Thread(handler);
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		WebServer server=new WebServer();
		server.start();

	}

}
