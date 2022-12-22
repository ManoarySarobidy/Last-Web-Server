
package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import listener.*;

public class Frame extends JFrame implements Runnable{

    // Variables declaration - do not modify                     
    FramePanel panel;
    ClientActionListener listener;

    ServerListener server;
    // End of variables declaration

    /**
     * Creates new form Frame
     */
    public Frame() {
    	this.setTitle("Web server Mr Naina");
    	this.setFramePanel();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        this.pack();
        this.setClientListener();
        this.setServerListener();
        this.setButtonsListener();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    public void setButtonsListener(){
        this.getFramePanel().getStartButton().addMouseListener(this.getServerListener());
        this.getFramePanel().getStopButton().addMouseListener(this.getServerListener());
        this.getFramePanel().getWebButton().addMouseListener(this.getClientListener());
        this.getFramePanel().getFolderButton().addMouseListener(this.getClientListener());
    }
    public void initComponents() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.getFramePanel(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.getFramePanel(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
    }// </editor-fold>                        

    public void run(){
    	this.setVisible(true);
    }
// getters and setters

    public void setFramePanel(){
 		this.panel = new FramePanel();  	
    }
    public FramePanel getFramePanel(){
    	return this.panel;
    }
    public void setClientListener(){
        this.listener = new ClientActionListener( this.getFramePanel() );
    }
    public ClientActionListener getClientListener(){
        return this.listener;
    }
    public void setServerListener(){
        this.server = new ServerListener( this );
    }
    public ServerListener getServerListener(){
        return this.server;
    }

   	
}
