package fso.guioes.thread;

import java.util.Random;

import fso.guioes.thread.utils.ISync;
import fso.guioes.thread.utils.SyncMonitor;
import fso.guioes.thread.utils.SyncSemaphore;
import fso.guioes.thread.utils.SyncTestAndSet;

public class HelloWorldVer06 extends Thread {
	private final int Iterations = 5;

	private String name;

	private ISync previous, next;

	public HelloWorldVer06(String name, ISync previous, ISync next) {
		this.name = name;

		this.previous = previous;
		this.next = next;
	}

	public void run() {
		for (int idxIter = 0; idxIter < this.Iterations; ++idxIter) {
			try {
				this.previous.syncWait();

				Thread.sleep((new Random()).nextInt(250));
				System.out.print(this.name);

				this.next.syncSignal();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			ISync syncHelloToWorld, syncWorldToHello;

			// syncHelloToWorld = new ...
			// syncHelloToWorld = new ...

			//syncHelloToWorld = new SyncTestAndSet();
			//syncWorldToHello = new SyncTestAndSet();

			// syncHelloToWorld = new SyncSemaphore();
			// syncWorldToHello = new SyncSemaphore();

			 syncHelloToWorld = new SyncMonitor();
			 syncWorldToHello = new SyncMonitor();

			Thread thHello, thWorld;

			thHello = new HelloWorldVer06("Hello", syncHelloToWorld, syncWorldToHello);
			thWorld = new HelloWorldVer06(" world.\n", syncWorldToHello, syncHelloToWorld);

			thHello.start();
			thWorld.start();

			syncHelloToWorld.syncSignal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
