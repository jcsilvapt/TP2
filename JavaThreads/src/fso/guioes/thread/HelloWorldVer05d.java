package fso.guioes.thread;

import java.util.Random;

class HelloWorldSync {
	public boolean flag;
}

public class HelloWorldVer05d extends Thread {

	private final int Iterations = 5;

	private boolean isHello;

	private String name;

	private HelloWorldSync myFlag_M_O;
	private HelloWorldSync myFlag_O_M;

	public HelloWorldVer05d(boolean isHello, String name, HelloWorldSync myFlag_M_O, HelloWorldSync myFlag_O_M) {
		this.isHello = isHello;

		this.name = name;

		this.myFlag_M_O = myFlag_M_O;
		this.myFlag_O_M = myFlag_O_M;
	}

	public void run() {
		try {
			for (int i = 0; i < Iterations; ++i) {
				if (this.isHello) {
					while (this.myFlag_M_O.flag == false)
						Thread.sleep(1);
					this.myFlag_M_O.flag = false;

					Thread.sleep((new Random()).nextInt(250));
					System.out.print(this.name);

					this.myFlag_O_M.flag = true;
				} else {
					while (this.myFlag_O_M.flag == false)
						Thread.sleep(1);
					this.myFlag_O_M.flag = false;

					Thread.sleep((new Random()).nextInt(250));
					System.out.print(this.name);

					this.myFlag_M_O.flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Thread thHello, thWorld;

		HelloWorldSync myFlag_O_M, myFlag_M_O;
		myFlag_O_M = new HelloWorldSync();
		myFlag_M_O = new HelloWorldSync();

		myFlag_M_O.flag = true;
		myFlag_O_M.flag = false;

		thHello = new HelloWorldVer05d(true, "Hello", myFlag_M_O, myFlag_O_M);
		thWorld = new HelloWorldVer05d(false, " world\n", myFlag_M_O, myFlag_O_M);

		thWorld.start();

		thHello.start();
	}
}
