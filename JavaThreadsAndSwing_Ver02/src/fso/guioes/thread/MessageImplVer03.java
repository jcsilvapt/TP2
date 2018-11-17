package fso.guioes.thread;

import javax.swing.SwingUtilities;

public class MessageImplVer03 extends MessageImplVer01 {

	private static final long serialVersionUID = 7993201781142726828L;

	protected String getLoggerTitle() {
		return "Message Logger - Thread Safe - With wait Ver 2";
	}

	public MessageImplVer03() {
		super();
	}

	@Override
	public void show(String message, Object... args) {
		String messageToShow;
		messageToShow = String.format( "[%s] %s\n", Thread.currentThread().getName(), String.format(message, args) );
		
		Runnable update = new Runnable() {
			@Override
			public void run() {
				textAreaMessages.append( messageToShow );
			}
		};
		
		SwingUtilities.invokeLater( update );
	}

	@Override
	public void clear() {
		Runnable update = new Runnable() {
			@Override
			public void run() {
				textAreaMessages.setText( "" );
			}
		};
		
		try {
			SwingUtilities.invokeAndWait( update );
		}
		catch (Exception e) {
			e.printStackTrace( System.err );
		}
	}
}
