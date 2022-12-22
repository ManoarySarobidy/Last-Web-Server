package listener;
import java.awt.event.*;
import java.awt.*;
import java.net.URI;
import frame.FramePanel;
import javax.swing.JButton;
public class ClientActionListener extends MouseAdapter{
	// omena azy ny panel enoina
	FramePanel panel;

	public ClientActionListener( FramePanel panels ){
		this.setFramePanel( panels );
	}
	public ClientActionListener(){

	}

	public void mouseClicked( MouseEvent e ){
		// testeko ny nom de boutton na ny texte ao anatiny
		if( e.getSource() instanceof JButton ){
			if( ((JButton) e.getSource()).getText().equalsIgnoreCase("Open web Browser")  ){
				try{
					Desktop desktop = Desktop.getDesktop();
					URI localhost = new URI("http://localhost:33005");
					desktop.browse(localhost);
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}else if( ((JButton) e.getSource()).getText().equalsIgnoreCase("Open root folder") ){
				try{
					Runtime runtime = Runtime.getRuntime();
					runtime.exec("open ../root/");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}

	}
	public void mousePressed( MouseEvent e ){

	}
	public void mouseEntered( MouseEvent e ){

	}
	public void mouseExited( MouseEvent e ){

	}
	public void mouseReleased( MouseEvent e ){

	}

	public void mouseDragged(MouseEvent e){

	}
	public void mouseMoved(MouseEvent e){

	}


	public void setFramePanel(FramePanel panels){
		this.panel = panels;
	}
	public FramePanel getFramePanel(){
		return this.panel;
	}
}