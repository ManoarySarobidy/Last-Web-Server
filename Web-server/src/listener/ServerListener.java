package listener;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JButton;
import frame.Frame;
import server.Server;
public class ServerListener implements MouseListener{
	
	Frame frame;
	Server server;
	Thread serverThread;

	public ServerListener( Frame frame ){
		this.setFrame( frame );
	}

	public void setFrame( Frame frame ){
		this.frame = frame;
	}
	public Frame getFrame(){
		return this.frame;
	}

	public void mousePressed( MouseEvent e ){}
	public void mouseClicked( MouseEvent e ){
		if( e.getSource() instanceof JButton ){
			if( ((JButton)e.getSource()).getText().equalsIgnoreCase("Start server") ){
				this.getFrame().getFramePanel().setStart(true);
				this.getFrame().getFramePanel().update();
				
				try{
					this.startServer();
					this.setServerThread();
					this.getThread().start();					
				}catch(Exception ex){
					ex.printStackTrace();
				}

			}else{
				this.getFrame().getFramePanel().setStart(false);
				this.getFrame().getFramePanel().update();
				try{
					this.getServer().getServer().close();
					this.getThread().interrupt();
					// System.exit(0);
				}catch( Exception ex ){
					ex.printStackTrace();
				}
			}
		}
	}
	public void mouseEntered( MouseEvent e ){}
	public void mouseReleased( MouseEvent e ){}
	public void mouseExited( MouseEvent e ){}

	public void setServerThread(){
		this.serverThread = new Thread( this.getServer() , " Server Main Thread " );
	}

	public Thread getThread(){
		return this.serverThread;
	}

	public void startServer(  ){
		this.server = new Server();
	}
	public Server getServer( ){
		return this.server;
	}
}