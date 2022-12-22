package server;
import java.io.*;
import java.net.*;
import java.util.Vector;
import handler.*;
import java.util.StringTokenizer;
public class Server implements Runnable {
	ServerSocket server;
	boolean status;
	int port;
	Vector<ClientHandler> clients;

	String defaultFile;

	public Server(){
		this.setPort( 33005 );
		this.setClients( new Vector<ClientHandler>() );
		this.setDefaultFile();
	}
	public void setPort( int port ){
		this.port = port;
	}
	public int getPort(){
		return this.port;
	}

	public void setServer( ServerSocket server ) throws Exception{
		this.server = server;
	}
	public ServerSocket getServer(){
		return this.server;
	}

	public void setClients( Vector<ClientHandler> cli ){
		this.clients = cli;
	}
	public Vector<ClientHandler> getClients(){
		return this.clients;
	}
	public void addClients( ClientHandler socket ){
		this.getClients().add( socket );
	}
	public void setDefaultFile(){
		this.defaultFile = "./index.html";
	}
	public String getDefaultFile(){
		return this.defaultFile;
	}
	public void run(){
		try{
			this.setServer( new ServerSocket( this.getPort() ) );
			this.getServer().setReuseAddress(true);

			System.out.println("Server listening");
			
			while( true ){
				Socket client = null;
				client = this.getServer().accept();
				ClientHandler handler = new ClientHandler( client );
				this.addClients(handler);
				Thread handle = new Thread(handler);
				handle.start();
				
			}

		}catch( Exception e ){
			System.out.println("Server is closed");
		}
	}

}