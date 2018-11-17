package fso.guioes.thread;

public interface IMessage {
	public void show(String message, Object... args);
	
	public void clear();
}
