package fso.guioes.thread;

import javax.swing.SwingUtilities;

public class MessageImplVer04 extends MessageImplVer01 {

	private static final long serialVersionUID = 7993201781142726828L;

	protected String getLoggerTitle() {
		return "Message Logger - Thread Safe - With wait Ver 2";
	}

	public MessageImplVer04() {
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
			if ( !SwingUtilities.isEventDispatchThread() ) {
				SwingUtilities.invokeAndWait( update );
			}
			else {
				update.run();
			}
		}
		catch (Exception e) {
			e.printStackTrace( System.err );
		}
	}
}
