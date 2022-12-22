package frame;

import javax.swing.*;
import java.awt.*;
import listener.*;

public class FramePanel extends JPanel{

	JLabel appName, jLabel1, jLabel2, jLabel4, status;
    JPanel applicationName, buttons, clientAction, footer, server, serverInfo, serverStatus;
    JButton folderButton, startButton, stopButton, webButton;
    ClientActionListener clientListener;
    boolean start;

    public FramePanel(){
        this.init();
        this.setClientListener();
        this.initComponents();
    }

    public void init(){
        this.initPanels();
        this.initButtons();
        this.setStart( false );
        this.alternateButtons();
        this.initLabels();
    }

    /**
     *  call all init* function 
     *  @return void
     * 
     * */

    public void initComponents(){
        this.initApplicationPanel();
        this.initButtonsPanel();
        this.initServerStatusPanel();
        this.initServerPanel();
        this.initClientActionPanel();
        this.initFooterPanel();
        this.initLayout();
    }

// set all elements
    public void initPanels(){
        // this.setServerInfo();
        this.setApplicationName();
        this.setServer();
        this.setButtons();
        this.setServerStatus();
        this.setClientAction();
        this.setFooter();
    }
    public void initButtons(){
        this.setStartButton("Start Server"); // start Button
        this.setStopButton("Stop Server"); // stop Button
        this.setFolderButton("Open root folder"); // folder button
        this.setWebButton("Open web Browser"); // web button
    }
    public void initLabels(){
        this.setStatus("Status");
        this.setAppName( "Local web Server Project" );
        this.setJLabel1("Status");
        this.setJLabel2("Made by ETU 002032");
        this.setJLabel4("Actions");
        this.setStatus("Server Status : " + (( this.isStarted() ) ? "connected" : "disconnected"));
    }
    public void paint(Graphics g){
        super.paint(g);
    }
    public void update(){
        this.getStatus().setText( "" );
        this.getStatus().setText( "Server Status : " + (( this.isStarted() ) ? "connected" : "disconnected") );
        this.alternateButtons();
        this.repaint();
    }

    public void alternateButtons(){
        if( this.isStarted() ){
            this.getStartButton().setEnabled(false);
            this.getStopButton().setEnabled(true);
        }else{
            this.getStartButton().setEnabled(true);
            this.getStopButton().setEnabled(false);
        }
    }

// set group layout for each panels
    public void initApplicationPanel(){
        GroupLayout applicationNameLayout = new GroupLayout(this.getApplicationName());
        this.getApplicationName().setLayout(applicationNameLayout);
        applicationNameLayout.setHorizontalGroup(
            applicationNameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, applicationNameLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(this.getAppName(), GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
        );
        applicationNameLayout.setVerticalGroup(
            applicationNameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.getAppName(), GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );
    }
    public void initButtonsPanel(){
        GroupLayout buttonsLayout = new GroupLayout(this.getButtons());
        this.getButtons().setLayout(buttonsLayout);
        buttonsLayout.setHorizontalGroup(
            buttonsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(this.getStopButton(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(this.getStartButton(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, buttonsLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(this.getJLabel4())
                .addGap(33, 33, 33))
        );
        buttonsLayout.setVerticalGroup(
            buttonsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(buttonsLayout.createSequentialGroup()
                .addComponent(this.getJLabel4())
                .addGap(4, 4, 4)
                .addComponent(this.getStartButton(), GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(this.getStopButton(), GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addContainerGap())
        );
    }
    public void initServerStatusPanel(){
        GroupLayout serverStatusLayout = new GroupLayout(this.getServerStatus());
        this.getServerStatus().setLayout(serverStatusLayout);
        serverStatusLayout.setHorizontalGroup(
            serverStatusLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, serverStatusLayout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(this.getJLabel1(), GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(serverStatusLayout.createSequentialGroup()
                .addComponent(this.getStatus(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        serverStatusLayout.setVerticalGroup(
            serverStatusLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(serverStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.getJLabel1(), GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(this.getStatus(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

    }
    public void initServerPanel(){
        GroupLayout serverLayout = new GroupLayout(this.getServer());
        this.getServer().setLayout(serverLayout);
        serverLayout.setHorizontalGroup(
            serverLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, serverLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.getServerStatus(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(this.getButtons(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        serverLayout.setVerticalGroup(
            serverLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, serverLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(serverLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(this.getServerStatus(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(this.getButtons(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }
    public void initClientActionPanel(){
        GroupLayout clientActionLayout = new GroupLayout(this.getClientAction());
        this.getClientAction().setLayout(clientActionLayout);
        clientActionLayout.setHorizontalGroup(
            clientActionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, clientActionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.getWebButton(), GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.getFolderButton(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        clientActionLayout.setVerticalGroup(
            clientActionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(clientActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientActionLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(this.getWebButton(), GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(this.getFolderButton(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 6, Short.MAX_VALUE))
        );
    }
    public void initFooterPanel(){
        this.getFooter().setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        GroupLayout footerLayout = new GroupLayout(this.getFooter());
        this.getFooter().setLayout(footerLayout);
        footerLayout.setHorizontalGroup(
            footerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, footerLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(this.getJLabel2(), GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.getJLabel2(), GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );
    }
    public void initLayout(){
        GroupLayout serverInfoLayout = new GroupLayout(this);
        this.setLayout(serverInfoLayout);
        serverInfoLayout.setHorizontalGroup(
            serverInfoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.getApplicationName(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(this.getServer(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(this.getFooter(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(this.getClientAction(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        serverInfoLayout.setVerticalGroup(
            serverInfoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(serverInfoLayout.createSequentialGroup()
                .addComponent(this.getApplicationName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(this.getServer(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(this.getClientAction(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.getFooter(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

    } 

// getters and setters
    public JLabel createLabel( String text ){
        JLabel label = new JLabel( text );
        return label;
    }
    public void setAppName( String text ){
        this.appName = createLabel( text );
    }
    public JLabel getAppName(){
        return this.appName;
    }
    public void setJLabel1( String text ){
        this.jLabel1 = createLabel( text );
    }
    public JLabel getJLabel1(){
        return this.jLabel1;
    }
    public void setJLabel2( String text ){
        this.jLabel2 = createLabel( text );
    }
    public JLabel getJLabel2(){
        return this.jLabel2;
    }
    public void setJLabel4( String text ){
        this.jLabel4 = createLabel( text );
    }
    public JLabel getJLabel4(){
        return this.jLabel4;
    }
    public void setStatus(String text){
        this.status = createLabel(text);
    }
    public JLabel getStatus(){
        return this.status;
    }

    public JButton createButton( String text ){
        JButton button = new JButton( text );
        return button;
    }
    public void setFolderButton(String text){
        this.folderButton = createButton(text);
    }
    public JButton getFolderButton(){
        return this.folderButton;
    }
    public void setStartButton(String text){
        this.startButton = createButton(text);
    }
    public JButton getStartButton(){
        return this.startButton;
    }
    public void setStopButton(String text){
        this.stopButton = createButton(text);
    }
    public JButton getStopButton(){
        return this.stopButton;
    }
    public void setWebButton(String text){
        this.webButton = createButton(text);
    }
    public JButton getWebButton(){
        return this.webButton;
    }

    public void setApplicationName(){
        this.applicationName = new JPanel();
    }
    public JPanel getApplicationName(){
        return this.applicationName;
    }
    public void setButtons(){
        this.buttons = new JPanel();
    }
    public JPanel getButtons(){
        return this.buttons;
    }
    public void setClientAction(){
        this.clientAction = new JPanel();
    }
    public JPanel getClientAction(){
        return this.clientAction;
    }
    public void setFooter(){
        this.footer = new JPanel();
    }
    public JPanel getFooter(){
        return this.footer;
    }
    public void setServer(){
        this.server = new JPanel();
    }
    public JPanel getServer(){
        return this.server;
    }
    public void setServerInfo(){
        this.serverInfo = new JPanel();
    }
    public JPanel getServerInfo(){
        return this.serverInfo;
    }
    public void setServerStatus(){
        this.serverStatus = new JPanel();
    }
    public JPanel getServerStatus(){
        return this.serverStatus;
    }

    public void setClientListener(){
        this.clientListener = new ClientActionListener(this);
    }
    public ClientActionListener getClientListener(){
        return this.clientListener;
    }

    public void setStart( boolean s ){
        this.start = s;
    }
    public boolean isStarted(){
        return this.start;
    }

}

