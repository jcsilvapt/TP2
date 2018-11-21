package demo.classic.boundedbuffer.impl;

import java.util.concurrent.Semaphore;

import demo.classic.boundedbuffer.BoundedBufferBaseImpl;
import demo.classic.boundedbuffer.IBoundedBufferGUI;

public class BoundedBufferImplVer1<E> 
		extends BoundedBufferBaseImpl<E> {

	private Semaphore semMutex;

	private Semaphore semPut;

	private Semaphore semGet;

	public BoundedBufferImplVer1(int _size, IBoundedBufferGUI _gui) {
		super(_size, _gui);

		this.semMutex = new Semaphore(1);
		this.semPut = new Semaphore(_size);
		this.semGet = new Semaphore(0);
	}

	@Override
	public E get() throws InterruptedException {
		E result = null;

		// Esperar at� existir um objecto
		this.semGet.acquire();

		// Obter exclus�o aos dados
		this.semMutex.acquire();
		try {
			result = this.removeElement();
		} finally {
			// Libertar a exclus�o aos dados
			this.semMutex.release();
		}

		// Indicar que existe mais uma posi��o livre
		this.semPut.release();

		return result;
	}

	@Override
	public void put(E e) throws InterruptedException {

		// Esperar at� existir uma posi��o livre
		this.semPut.acquire();

		// Obter exclus�o aos dados
		this.semMutex.acquire();
		try {
			this.addElement(e);
		} finally {
			// Libertar a exclus�o aos dados
			this.semMutex.release();
		}

		// Indicar que existe mais um objecto no contentor
		this.semGet.release();
	}

	@Override
	public String description() {
		return "Sem�foros contadores";
	}
}
