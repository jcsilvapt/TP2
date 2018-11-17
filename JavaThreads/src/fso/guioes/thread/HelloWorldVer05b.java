package fso.guioes.thread;

import java.util.Random;

public class HelloWorldVer05b extends Thread {
	
	private boolean isHello;
	
	private String name;
	
	private boolean flag;
	
	public HelloWorldVer05b(
			boolean isHello, 
			String name, 
			boolean flag) {
		this.isHello = isHello;
		
		this.name = name;
		this.flag = flag;
	}
	
	public void run() {
		try {
			if ( this.isHello ) {
				Thread.sleep( (new Random()).nextInt( 250 ) );
				System.out.print( this.name );
				
				this.flag = true;
			}
			else {
				while ( this.flag==false )
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
		
		boolean flag;
		flag = false;
		
		thHello = new HelloWorldVer05b(
				true, 
				"Hello", 
				flag );
		thWorld = new HelloWorldVer05b(
				false,
				" world\n",
				flag );
		
		thWorld.start();
		thHello.start();
	}
}
