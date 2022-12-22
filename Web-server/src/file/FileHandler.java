package file;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
public class FileHandler{

	String defaultPath ;
	String defaultFileName;
	
	String NOT_FOUND ;
	String requestFile;
	
	File script;
	
	String datas;
	String postData;
	String getData;
	String method;

	String defaultDirectory;


	public FileHandler(){

		this.setDefaultPath();
		this.setNotFound();
		this.setRequestFile(this.getNotFound());
		this.setDefaultDirectory();
	}

	public FileHandler( String file , String method ){
		this.setDefaultPath();
		this.setMethod(method);
		this.setNotFound();
		this.setScript();
		this.setDefaultDirectory();
		this.setRequestFile( this.getDefaultDirectory() + file.replace("%20" , " ") );
	}

	public String getExtension(){
		String[] splited = this.getRequestFile().split("\\.");
		return splited[splited.length-1];
	}

	public String getHeaders(){
		String extension = getExtension();
		if( extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("gif") ){
			return "";
		}
		return "HTTP/1.1 200  OK";
	}

	public String getMimeType() {
		String extension = getExtension();
		if( extension.equalsIgnoreCase("html") || extension.equalsIgnoreCase("php") ){
			return "Content-Type: text/html; charset=UTF-8;";
		}else if( extension.equalsIgnoreCase("css") ){
			return "Content-Type: text/css; charset=UTF-8;";
		}else if( extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("gif") ){
			return "Content-Type: image/"+ (( extension .equalsIgnoreCase("jpg") ) ? "jpeg" : extension) ;
		}else if( isDocument() ){
			return "Content-Type: text/plain" ;
		}
		return "Content-Type: text/html; charset=UTF-8;";
	}

	String directoryContent(){
		String content = "<h2 class=\"text-center text-decoration-underline\"> Index </h2>";
		content += this.createFolderLink();
		return content;
	}

	void createPage( String title , String content ) throws Exception{
		try{
			FileInputStream template = new FileInputStream("../no-content.html");
			FileOutputStream temp = new FileOutputStream("../temp.tmp",true);
			BufferedReader reader = new BufferedReader( new InputStreamReader( template ) );
			this.createTempFile("../temp.tmp");
			BufferedWriter writer = new BufferedWriter( new PrintWriter( temp ) );
			String s = null;
			while( (s = reader.readLine()) != null ){
				if( s.contains("<title>") ) s += title;
				if( s.contains("row contents") ) s += content;
				writer.write(s);
			}
			reader.close();
			writer.close();
		}catch (Exception e) {
			System.out.println(" Erreur dans la fonction : createErrorPage() =>> " + e.getMessage());
		}
	}
	
	byte[] readErrorPage() throws Exception{
		this.createPage("Error" , errorToHtml());
		this.setRequestFile("../temp.tmp");
		byte[] file = this.readFile();
		this.deleteTempFile();
		return file;
	}	

	String errorToHtml() throws Exception{
		BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( "../src/exception/phpError.html" ) ) );
		String e = null;
		String valiny = "<table class=\"table text-white\"> ";
		while( (e = reader.readLine()) != null ){
			String tr = "<tr><td>" + e + "</td></tr>";
			valiny += tr;
		}
		valiny += "</table>";
		return valiny;
	}

	// create the folder temp
	String createFolderLink(){
		this.setRequestFile(this.getRequestFile().replace("%20"," "));
		File file = new File(this.getRequestFile());
		File[] files = file.listFiles();
		String list = "";
		for( File f : files ){
			if( !f.isHidden() ){
				String div = "<div class=\"links " + ((f.isDirectory()) ? "directory" : "file") + "\">";
				div += "<a href=./" + this.escape(f.getName()) + ((f.isDirectory()) ? "/" : "") + ">" + f.getName() + "</a>";
				div += "</div>";
				list+= div;

			}
		}
		return list;
	}

	String escape(String toEscape){
		return toEscape.replace(" ","%20");
	}

	void readDirectory() throws Exception{
		try{
			this.createPage("List directories" , directoryContent());
			this.setRequestFile("../temp.tmp");
		}catch (Exception e) {
			throw new Exception("Error while reading directories : " + e.getMessage());
		}
	}


	// read simple html file
	
	public byte[] readFile( ) throws Exception{
		FileInputStream file = null;
		boolean b = addDefaultFile();
		if( isDirectory() && b ){ //izany hoe dossier ilay izy
			this.readDirectory();
		}
		file = new FileInputStream( this.getRequestFile() );
		byte[] valiny = this.read( file );
		this.deleteTempFile();
		return valiny;
	}

	public byte[] traitement() throws Exception{
		if( this.getRequestFile().endsWith("/") ){
			return this.checkExtension();
		}else{
			return checkExist();
		}
	}

	public boolean addDefaultFile(){ // for a folder
		File file1 = new File( this.getRequestFile() + "index.html" );
		File file2 = new File( this.getRequestFile() + "index.php" );
		if( file1.exists() ){
			this.setRequestFile( this.getRequestFile() + "index.html" );
			return false;
		}
		else if( file2.exists() ){
			this.setRequestFile( this.getRequestFile() + "index.php" );	
			return false;
		}else{
			return true;
		}
	}

	public void addScript() throws Exception{
		String temporaryFile = "../src/temp/tempFile.tmp";
		File tempScript = this.getScript();
		File requested = new File(this.getRequestFile());
		this.createTempFile(temporaryFile);
		File tempFile = new File(temporaryFile);
		try{
			this.writeTo( tempFile , script );
			this.writeTo( tempFile , requested );
			this.setRequestFile(temporaryFile);
		}catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public void writeTo( File toWrite , File toRead) throws Exception{
		FileOutputStream wri = new FileOutputStream(toWrite , true);
		FileInputStream inp = new FileInputStream(toRead);
		BufferedReader reader = new BufferedReader( new InputStreamReader( inp ) );
		BufferedWriter writer = new BufferedWriter( new PrintWriter( wri ) );
		String line = null;
		while( (line = reader.readLine()) != null ){
			writer.write( line );
		}
		reader.close();
		writer.close();
	}

	public byte[] checkExist() throws Exception{
		try{
			File file = new File( this.getRequestFile() );
			return checkExtension();
		}catch( Exception files ){
			// files.printStackTrace();
			this.setRequestFile( this.getNotFound() );
			return readFile();
		}
	}

	public byte[] checkExtension() throws Exception{
		String[] splitted = this.getRequestFile().split("\\.");
		String extension = splitted[ splitted.length - 1 ];
		if( splitted.length == 1 ){
			throw new IOException("Not a valid file : " + this.getRequestFile());
		}
		if( extension.equalsIgnoreCase("php") ){
			this.extractError();
			if( this.error() ) return readErrorPage();
			return readPhpFile();
			
		}else if( isHtmlOrCss() || isImage() || isDirectory() || isDocument() ){
			return readFile();
		}
		throw new IOException("Not a valid file : " + this.getRequestFile());
	}

// type part
	boolean isDirectory(){
		return this.getRequestFile().endsWith("/");
	}

	boolean isHtmlOrCss(){
		String ex = getExtension();
		return ex.equalsIgnoreCase("html") || ex.equalsIgnoreCase("css");
	}

	boolean isDocument(){
		String extension = getExtension();
		return extension.equalsIgnoreCase("sh") || extension.equalsIgnoreCase("txt") ;
	}

	boolean isImage(){
		String ex = getExtension();
		return ex.equalsIgnoreCase("ico") || ex.equalsIgnoreCase("jpg") || ex.equalsIgnoreCase("png") || ex.equalsIgnoreCase("gif") || ex.equalsIgnoreCase("jpeg") ;
	}

// php part
	public byte[] readPhpFile() throws Exception{
		this.addScript();
		Process process = Runtime.getRuntime().exec("php -f " + this.getRequestFile() + " " + this.getData() );
		InputStream stream = process.getInputStream();
		byte[] valiny = this.read(stream); 
		this.deleteTempFile();
		return valiny;
	}

// error part
	public void extractError() throws Exception{
		try{
			String[] commands = { "./script/post.sh" , this.getMethod() , this.getRequestFile().substring(3) , this.getPostData() , this.getGetData() };
			ProcessBuilder builder = new ProcessBuilder(commands);
			Process p = builder.start();
			p.waitFor();
		}catch(Exception ex){
			// ex.printStackTrace();
			throw ex;
		}

	}
	
	public boolean error(){
		File phpError = new File("../src/exception/phpError.html");
		return Files.exists(phpError.toPath()) && phpError.length() > 0;
	}

// read from inputStream Part
	public byte[] read( InputStream stream ) throws Exception{
		File file = new File(this.getRequestFile());
		long len = file.length();
		byte[] response = new byte[(int)len];
		DataInputStream dis = new DataInputStream(stream);
		dis.read(response , 0 , (int)len);
		dis.close();
		stream.close();
		return response;
	}

// file manipulation part
	public void createTempFile( String path ) throws Exception{
		File temp = new File(path);
		if( !Files.exists(temp.toPath()) ){
			Files.createFile(temp.toPath());
		}
	}
	void deleteTempFile() throws Exception{
		String exception = this.getDefaultDirectory()+"/src/exception/phpError.html";
		deleteTemp();
		File ex = new File(exception);
		File e = new File("../temp.tmp");
		Files.deleteIfExists(ex.toPath());
		Files.deleteIfExists(e.toPath());
	}

	void deleteTemp() throws Exception{
		String directory = "../src/temp/";
		File file = new File(directory);
		File[] files = file.listFiles();
		for(File f : files){
			if( !f.getName().contains(".gitignore") ){
				Files.deleteIfExists(f.toPath());
			}
		}
	}

// getters and setters

	public void setDatas( ){
		this.datas = getPostData() + getGetData();
	}

	public void setPostData( String data ){
		this.postData = data;
	}
	public void setGetData( String data ){
		this.getData = data;
	}

	void setMethod(String method){
		this.method = method;
	}
	String getMethod(){
		return this.method;
	}

	String getPostData(){
		return this.postData;
	}
	String getGetData(){
		return this.getData;
	}

	String getData(){
		return this.datas;
	}

	void setRequestFile(String path){
		this.requestFile = path;
	}
	String getRequestFile(){
		return this.requestFile;
	}

	void setDefaultPath(){
		this.defaultPath = "..";
	}
	String getDefaultPath(){
		return this.defaultPath;
	}
	void setNotFound(){
		this.NOT_FOUND = "../PAGE_NOT_FOUND.html";
	}
	String getNotFound(){
		return this.NOT_FOUND;
	}
	void setScript(){
		this.script = new File( "../script.php" );
	}
	File getScript(){
		return this.script;
	}

	void setDefaultDirectory(){
		this.defaultDirectory = "../root/";
	}
	String getDefaultDirectory(){
		return this.defaultDirectory;
	}

}