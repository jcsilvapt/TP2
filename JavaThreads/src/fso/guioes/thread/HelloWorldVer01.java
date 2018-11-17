package fso.guioes.thread;

import java.util.Random;

public class HelloWorldVer01 extends Thread {
	
	private String name;
	
	public HelloWorldVer01(String name) {
		this.name = name;
	}
	
	public void run() {
		try {
			Thread.sleep( (new Random()).nextInt( 250 ) );
			System.out.print( this.name );
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread thHello, thWorld;
		
		thHello = new HelloWorldVer01( "Hello" );
		thWorld = new HelloWorldVer01( " world.\n" );
		
		thHello.start();
		thWorld.start();
	}
}
