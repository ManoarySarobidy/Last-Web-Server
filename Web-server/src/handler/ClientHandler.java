package handler;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.Vector;
import file.FileHandler;
public class ClientHandler implements Runnable{
	
	Socket connected;

	// asiana html header foana ato

	String defaultPath = "./";	
	String httpHeader;
	String httpContent;
	byte[] serverMessage;
	
	Vector<String> inputs;
	boolean read;
	BufferedReader reader;
	InputStreamReader input;

	public ClientHandler(Socket connect , String message){
		this.setConnected( connect );
		this.setHttpHeader();
		this.setHttpContent();
		this.setServerMessage( message.getBytes() );
	}
	public ClientHandler(Socket connect) throws Exception{
		this.setServerMessage("<b>HAHAHAHAH Sarobidy is the boss</b>".getBytes());
		this.setHttpHeader();
		this.setHttpContent();
		this.setRead(false);
		this.setConnected( connect );
		this.setInputStreamReader();
		this.setBufferedReader();
	}

	public void run(){
		
		try{
			if( !this.wasRead() ){
				this.traitement();
				this.writeContent();
				this.getConnected().close();
				this.setRead(true);
			}
		}catch( Exception e ){
			// e.printStackTrace();
		}

	}

	public void traitement() throws Exception{
		try{
			
			String firstLine = this.getBuffer().readLine();
			String method = getHeaderMethod( firstLine );
			String url = getUrl( firstLine );
			String file = getPathFile(url);
			String post = "";
			String get = this.getAttributes(url);
			if( method.equals("POST") ){
				post = getPostData();
			}

			FileHandler handler = new FileHandler(file , method);
			handler.setPostData(post);
			handler.setGetData(get);
			handler.setDatas();
			this.setHttpContent(handler.getMimeType());
			this.setServerMessage(handler.traitement());
		}catch (Exception e) {
			
		}
	}

	public String getPostData() throws Exception{
		String s = null;
		int count = -5;
		while( (s = this.getBuffer().readLine()) != null && !s.isEmpty() ){
			if( s.contains("Content-Length: ") ){
				count = Integer.valueOf(s.substring(s.indexOf("Content-Length:")+16,s.length()));
			}
		}
		String datas = "";
		if( count > 0 ){
			char[] data = new char[count];
			this.getBuffer().read( data , 0 , count );
			datas = new String(data);
		}
		return datas;
	}

	String getHeaderMethod( String line ) throws Exception{
		return line.split(" ")[0];
	}

	String getUrl( String line ) throws Exception{
		return line.split(" ")[1];
	}
	String getPathFile( String url ){
		return url.split("[?]")[0];
	}
	String getAttributes(String url){
		if( url.split("[?]").length > 1 )
			return url.split("[?]")[1];
		return "";
	}

	void writeContent() throws Exception{
		OutputStream out = this.getConnected().getOutputStream();
		try{
			out.write( this.getHttpHeader().getBytes("UTF-8") );
			out.write( this.getHttpContent().getBytes("UTF-8") );
			out.write( this.getMessage() , 0 , this.getMessage().length);
			out.flush(); 	
		}catch(Exception e){
			out.close();
		}
	}


	public void setRead(boolean b){
		this.read = b;
	}
	public boolean wasRead(){
		return this.read;
	}

	public void setInputs(Vector<String> vec){
		this.inputs = vec;
	}
	public Vector<String> getInputs(){
		return this.inputs;
	}

	public void setConnected( Socket connect ){
		this.connected = connect;
	}
	public Socket getConnected(){
		return this.connected;
	}

	public void setHttpHeader(){
		this.httpHeader = "HTTP/1.1 200  OK\r\n";
	}
	public void setHttpHeader(String head){
		this.httpHeader = head + "\r\n";
	}
	public void setHttpContent(){
		this.httpContent = "Content-Length : " + this.getMessage().length +"\n";
		this.httpContent += "Content-Type: text/html;charset=UTF-8\n\n";
	}
	public void setHttpContent(String contentType){
		this.httpContent = "Content-Length : " + this.getMessage().length +"\n";
		this.httpContent += contentType + "\n\n";
	}
	public void setServerMessage(byte[] message){
		this.serverMessage = message;
	}

	public String getHttpHeader(){
		return this.httpHeader;
	}
	public String getHttpContent(){
		return this.httpContent;
	}
	public byte[] getMessage(){
		return this.serverMessage;
	}

	public void setBufferedReader(  ){
		this.reader = new BufferedReader( this.getInStream() );
	}
	public BufferedReader getBuffer(){
		return this.reader;
	}

	public void setInputStreamReader() throws Exception{
		this.input = new InputStreamReader( this.getConnected().getInputStream() );
	}
	public InputStreamReader getInStream(){
		return this.input;
	}

	
}