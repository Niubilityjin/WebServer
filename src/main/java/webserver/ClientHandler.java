package webserver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import Http.HttpRequest;

/**
 * 
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
			//打桩
			System.out.println("ClientHandler:开始处理请求");
			
			//解析请求信息
			HttpRequest request= new HttpRequest(socket.getInputStream());
			//打桩
			System.out.println("CilentHandler:url:"+request.getUrl());
			File file=new File("webapps"+request.getUrl());
			System.out.println("文件路径:"+"webapps"+request.getUrl());
			//打桩
			System.out.println("文件是否存在:"+file.exists());
			if(file.exists()){
				//将该文件发送回给客户端
				OutputStream out= socket.getOutputStream();
				/*
				 * 回复HTTP响应
				 */
				//1 发送状态行
				String line="HTTP/1.1 200 OK";
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);
				out.write(10);
				//打桩
				System.out.println("状态行发送完毕");
				
				//2 发送响应头
				line="Content-Type:text/html";
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);
				out.write(10);
				
				//打桩
				System.out.println("响应头发送完毕");
				
				//3 发送响应正文
				
				line="Content-Length:"+file.length();
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);
				out.write(10);
				//单独发送CRLF表示响应头部分发送完毕
				out.write(13);
				out.write(10);
				//打桩
				System.out.println("响应头部分发送完毕.");
				
				
				//发送响应正文
				FileInputStream fis=new FileInputStream(file);
				BufferedInputStream bis=new BufferedInputStream(fis);
				byte [] buf=new byte[1024*10];
				int len=-1;
				while((len=bis.read(buf))!=-1){
					out.write(buf,0,len);
				}
				bis.close();
				//打桩
				System.out.println("正文响应完毕");
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
