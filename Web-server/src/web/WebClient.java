package web;
import java.util.*;
import java.net.*;
import java.io.*;

public class WebClient implements Runnable{
	URL urlConnection;
	String host;
	int port;
	public WebClient(){
		this.setHost("0.0.0.0");
		this.setPort(33005);
	}
	public void setHost( String host ){
		this.host = host;
	}
	public void setPort( int p ){
		this.port = p;
	}
	public void setUrl() throws Exception{
		this.urlConnection = new URL("https" , this.getHost() , this.getPort() ,"index.html");
	}
	public URL getUrl(){
		return this.urlConnection;
	}
	public String getHost(){
		return this.host;
	}
	public int getPort(){
		return this.port;
	}

	public void run(){
			System.out.println("Yesss successs");
		try{
			this.setUrl();
			URLConnection connection = this.getUrl().openConnection();
			connection.setDoOutput( true );
			connection.connect();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}