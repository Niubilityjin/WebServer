package Http;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {
	/**
	 * mm
	 * 用于读取客户端发送过来数据的输入流,该流应该是通过Socket获取的输入流.
	 */
	private InputStream in;
	/*
	 * 请求行相关信息
	 */
	private String method;
	//请求资源路径
	private String url;
	//请求使用的HTTP协议版本
	private String protocl;
	
	public HttpRequest(InputStream in) {
		System.out.println("HttpRequest构造方法执行");
		this.in=in;
		parseRequestLine();
		System.out.println("HttpRequest构造方法执行完毕");
	}
	private void parseRequestLine() {
		System.out.println("开始解析请求行");
		//读取请求行内容
		String requestLine=readLine();
		/*
		 * 将请求行中的三部分分别存到method,url,protocl属性上
		 */
		String [] array=requestLine.split("\\s");
		method=array[0];
		
		url=array[1];
		
		protocl=array[2];
		
		System.out.println("请求行:"+requestLine);
	}
	/**
	 * 读取一行字符串(以CRLF作为结尾),返回的字符串中不包含最后的CRLF
	 * @return
	 */
	private String readLine() {
		StringBuilder builder=new StringBuilder();
		try {
			int d=-1;
			char c1='a';
			char c2='a';
			while((d=in.read())!=-1) {
				c2=(char)d;
				if(c1==13&&c2==10) {
					break;
				}
				builder.append(c2);
				c1=c2;
			}
			return builder.toString().trim();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return "";
	}

}
