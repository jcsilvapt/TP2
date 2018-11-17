package Comportamentos;

public abstract class Comportamentos extends Thread {

	private String ThreadName;
	private int baseSleepTime;
	private int sleepTime;
	
	private boolean avoidOn;
	
	public Comportamentos(String ThreadName, int baseSleepTime, int sleepTime, boolean avoid) {
		this.ThreadName = ThreadName;
		this.baseSleepTime = baseSleepTime;
		this.sleepTime = sleepTime;
		this.avoidOn = avoid;
	}
	
	public abstract void run();
	
	public int getBaseSleepTime() {
		return this.baseSleepTime;
	}
	
	public int getSleepTime() {
		return this.sleepTime;
	}
	
	public String getThreadName() {
		return this.ThreadName;
	}
	
	public void setAvoid(boolean value) {
		this.avoidOn = value;
	}
	
	public boolean getAvoid() {
		return avoidOn;
	}
}
