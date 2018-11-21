package demo.classic.boundedbuffer.impl;

import java.util.concurrent.Semaphore;

import demo.classic.boundedbuffer.BoundedBufferBaseImpl;
import demo.classic.boundedbuffer.IBoundedBufferGUI;

public class BoundedBufferImplVer3<E> 
		extends BoundedBufferBaseImpl<E> {

	private Semaphore semMutex;

	private Semaphore semPut;

	private int numThPut;

	private Semaphore semGet;

	private int numThGet;

	public BoundedBufferImplVer3(int _size, IBoundedBufferGUI _gui) {
		super(_size, _gui);

		this.semMutex = new Semaphore(1);

		this.semPut = new Semaphore(0);
		numThPut = 0;

		this.semGet = new Semaphore(0);
		numThGet = 0;
	}

	@Override
	public E get() throws InterruptedException {
		E result = null;

		this.semMutex.acquire();

		// ENQUANTO n�o existir um objecto vou esperar
		while (this.size == 0) {
			++numThGet;
			this.semMutex.release();
			this.semGet.acquire();
			this.semMutex.acquire();
			--numThGet;
		}

		// J� existe um obejcto
		result = this.removeElement();

		// Se existirem tarefas bloqueadas para inserir um objecto
		if (this.numThPut > 0) {
			this.semPut.release();
		}

		// Libertar a exclus�o
		this.semMutex.release();

		return result;
	}

	@Override
	public void put(E e) throws InterruptedException {

		this.semMutex.acquire();

		// ENQUANTO n�o existir uma posi��o livre vou esperar
		while (this.size == this.data.length) {
			++numThPut;
			this.semMutex.release();
			this.semPut.acquire();
			this.semMutex.acquire();
			--numThPut;
		}

		// J� existe uma posi��o livre
		this.addElement(e);

		// Se existirem tarefas bloqueadas para obter um objecto
		if (this.numThGet > 0) {
			this.semGet.release();
		}

		// Libertar a exclus�o
		this.semMutex.release();
	}

	@Override
	public String description() {
		return "Sem�foros como monitores - Butler W. Lampson & David D. Redell";
	}

}
