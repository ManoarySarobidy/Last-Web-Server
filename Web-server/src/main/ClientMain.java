package main;
import web.*;

public class ClientMain{
	public static void main(String[] args) {
		try{
			WebClient client = new WebClient();
			Thread thread = new Thread(client);
			thread.start(); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}