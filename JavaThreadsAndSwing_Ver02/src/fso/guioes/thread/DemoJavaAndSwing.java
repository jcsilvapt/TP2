package fso.guioes.thread;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DemoJavaAndSwing implements ITimes {

	private JFrame mainFrame;
	private JTextField textFieldBaseSleepTime;
	private JTextField textFieldSleepTime;
	private JTextField textFieldMessage;
	private JLabel labelBaseSleepTime;
	private JLabel labelSleepTime;
	private JButton buttonCreateNewThread;
	private JLabel labelMessage;
	
	private IMessage appLogger;
	
	private int baseSleepTime;
	
	@Override
	public int getBaseSleepTime() {
		return this.baseSleepTime;
	}
	
	private int sleepTime;

	@Override
	public int getSleepTime() {
		return this.sleepTime;
	}
	
	private void handleBaseSleepTime() {
		try {
			this.baseSleepTime = Integer.parseInt( this.textFieldBaseSleepTime.getText() );
		}
		catch (Exception e) {
			e.printStackTrace( System.err );
		}
	}
	
	private void handleSleepTime() {
		try {
			this.sleepTime = Integer.parseInt( this.textFieldSleepTime.getText() );
		}
		catch (Exception e) {
			e.printStackTrace( System.err );
		}		
	}
	
	private void handleCreateThread() {
		try {
			Thread worker;
			worker = new Worker( this.appLogger, (ITimes)this );
			
			worker.start();
		}
		catch (Exception e) {
			e.printStackTrace( System.err );
		}
	}
	
	private void handleAddMessage() {
		try {
			this.appLogger.show( this.textFieldMessage.getText() );
		}
		catch (Exception e) {
			e.printStackTrace( System.err );
		}
	}
	
	private void handleClearLogMessages() {
		try {
			this.appLogger.clear();
		}
		catch (Exception e) {
			e.printStackTrace( System.err );
		}
	}
	
	private void myInitialize() {
		//this.appLogger = new MessageImplVer01();
		//this.appLogger = new MessageImplVer02();
		//this.appLogger = new MessageImplVer03();
		this.appLogger = new MessageImplVer04();
		
		this.baseSleepTime = 500;
		this.sleepTime = 250;
		
		this.textFieldBaseSleepTime.setText( "" + this.baseSleepTime );
		this.textFieldSleepTime.setText( "" + this.sleepTime );
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new DemoJavaAndSwing();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DemoJavaAndSwing() {
		initialize();
		
		myInitialize();
		
		mainFrame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setTitle("Java Swing and Threads");
		mainFrame.setBounds(100, 100, 450, 243);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		labelBaseSleepTime = new JLabel("Base Sleep Time");
		labelBaseSleepTime.setBounds(12, 13, 150, 16);
		mainFrame.getContentPane().add(labelBaseSleepTime);
		
		textFieldBaseSleepTime = new JTextField();
		textFieldBaseSleepTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleBaseSleepTime();
			}
		});
		textFieldBaseSleepTime.setText("0");
		textFieldBaseSleepTime.setBounds(174, 10, 116, 22);
		mainFrame.getContentPane().add(textFieldBaseSleepTime);
		textFieldBaseSleepTime.setColumns(10);
		
		labelSleepTime = new JLabel("Slepp Time");
		labelSleepTime.setBounds(12, 42, 150, 16);
		mainFrame.getContentPane().add(labelSleepTime);
		
		textFieldSleepTime = new JTextField();
		textFieldSleepTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleSleepTime();
			}
		});
		textFieldSleepTime.setText("0");
		textFieldSleepTime.setBounds(174, 39, 116, 22);
		mainFrame.getContentPane().add(textFieldSleepTime);
		textFieldSleepTime.setColumns(10);
		
		buttonCreateNewThread = new JButton("Create new Thread");
		buttonCreateNewThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleCreateThread();
			}
		});
		buttonCreateNewThread.setBounds(12, 71, 420, 25);
		mainFrame.getContentPane().add(buttonCreateNewThread);
		
		labelMessage = new JLabel("Message");
		labelMessage.setBounds(12, 109, 150, 16);
		mainFrame.getContentPane().add(labelMessage);
		
		textFieldMessage = new JTextField();
		textFieldMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleAddMessage();
			}
		});
		textFieldMessage.setBounds(12, 138, 420, 22);
		mainFrame.getContentPane().add(textFieldMessage);
		textFieldMessage.setColumns(10);
		
		JButton buttonClearLogMessages = new JButton("Clear Log Message");
		buttonClearLogMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleClearLogMessages();
			}
		});
		buttonClearLogMessages.setBounds(12, 173, 420, 25);
		mainFrame.getContentPane().add(buttonClearLogMessages);
		
		mainFrame.setLocationRelativeTo( null );
	}

	
}
