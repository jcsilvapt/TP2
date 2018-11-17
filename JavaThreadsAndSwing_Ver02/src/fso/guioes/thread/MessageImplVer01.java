package fso.guioes.thread;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessageImplVer01 extends JFrame implements IMessage {

	private static final long serialVersionUID = 8676943179093161347L;
	
	private JScrollPane scrollPaneMessages;
	
	protected JTextArea textAreaMessages;
	
	protected String getLoggerTitle() {
		return "Message Logger";
	}

	public MessageImplVer01() {
		setTitle( getLoggerTitle() );
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		getContentPane().setLayout( new BorderLayout() );
		
		textAreaMessages = new JTextArea();
		textAreaMessages.setRows( 8 );
		textAreaMessages.setColumns( 50 );
		
		
		scrollPaneMessages = new JScrollPane();
		scrollPaneMessages.setAutoscrolls(true);
		scrollPaneMessages.setViewportView( textAreaMessages );
		
		getContentPane().add(scrollPaneMessages, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible( true );
	}

	@Override
	public void show(String message, Object... args) {
		String messageToShow;
		messageToShow = String.format( "[%s] %s\n", Thread.currentThread().getName(), String.format(message, args) );
		
		this.textAreaMessages.append( messageToShow );
	}

	@Override
	public void clear() {
		this.textAreaMessages.setText( "" );
	}

}
