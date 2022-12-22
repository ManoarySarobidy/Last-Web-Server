package main;
import server.*;
import frame.Frame;
public class WebServer{
	public static void main(String[] args){
		try{
			Frame serverFrame = new Frame();
			Thread frameThread = new Thread(serverFrame);
			frameThread.start();
		}catch(Exception exception){
			System.out.println(exception);
		}
	}
}