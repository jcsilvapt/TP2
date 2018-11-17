package fso.guioes.thread;

import java.util.Random;

public class HelloWorldVer05c extends Thread {
	
	private static boolean flag;
	
	private boolean isHello;
	
	private String name;

	public HelloWorldVer05c(
			boolean isHello, 
			String name) {
		this.isHello = isHello;
		
		this.name = name;
	}
	
	public void run() {
		try {
			if ( this.isHello ) {
				Thread.sleep( (new Random()).nextInt( 250 ) );
				System.out.print( this.name );
				
				HelloWorldVer05c.flag = true;
			}
			else {
				while ( HelloWorldVer05c.flag==false )
					Thread.sleep(1);
				
				System.out.println( " mundo" );
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} 

	public static void main(String[] args) {
		Thread thHello, thWorld;
		
		HelloWorldVer05c.flag = false;
		
		thHello = new HelloWorldVer05c(
				true, 
				"Hello" );
		thWorld = new HelloWorldVer05c(
				false,
				" world\n" );
		
		thWorld.start();
		thHello.start();
	}
}
