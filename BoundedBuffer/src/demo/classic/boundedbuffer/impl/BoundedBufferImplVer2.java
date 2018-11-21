package demo.classic.boundedbuffer.impl;

import java.util.concurrent.Semaphore;

import demo.classic.boundedbuffer.BoundedBufferBaseImpl;
import demo.classic.boundedbuffer.IBoundedBufferGUI;

public class BoundedBufferImplVer2<E> 
		extends BoundedBufferBaseImpl<E> {

	private Semaphore semMutex;

	private Semaphore semPut;

	private int numThPut;

	private Semaphore semGet;

	private int numThGet;

	public BoundedBufferImplVer2(int _size, IBoundedBufferGUI _gui) {
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

		// SE n�o existir um objecto vou esperar
		if (this.size == 0) {
			++numThGet;
			this.semMutex.release();
			this.semGet.acquire();
			--numThGet;
		}

		// J� existe um obejcto
		result = this.removeElement();

		// Se existirem tarefas bloqueadas para inserir um objecto
		if (this.numThPut > 0) {
			this.semPut.release();
		} else {
			// N�o existem tarefas em espera pode-se libertar a exclus�o
			this.semMutex.release();
		}

		return result;
	}

	@Override
	public void put(E e) throws InterruptedException {

		this.semMutex.acquire();

		// SE n�o existir uma posi��o livre vou esperar
		if (this.size == this.data.length) {
			++numThPut;
			this.semMutex.release();
			this.semPut.acquire();
			--numThPut;
		}

		// J� existe uma posi��o livre
		this.addElement(e);

		// Se existirem tarefas bloqueadas para obter um objecto
		if (this.numThGet > 0) {
			this.semGet.release();
		} else {
			// N�o existem tarefas em espera pode-se libertar a exclus�o
			this.semMutex.release();
		}
	}

	@Override
	public String description() {
		return "Sem�foros como monitores -  Brinch-Hansen & Tony Hoare";
	}
}
