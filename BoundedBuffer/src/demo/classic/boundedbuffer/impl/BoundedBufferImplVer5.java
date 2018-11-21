package demo.classic.boundedbuffer.impl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import demo.classic.boundedbuffer.BoundedBufferBaseImpl;
import demo.classic.boundedbuffer.IBoundedBufferGUI;

public class BoundedBufferImplVer5<E> 
		extends BoundedBufferBaseImpl<E> {

	private Lock theLock;

	private Condition bufferFull;
	private Condition bufferEmpty;

	public BoundedBufferImplVer5(int _size, IBoundedBufferGUI _gui) {
		super(_size, _gui);

		this.theLock = new ReentrantLock();

		this.bufferFull = this.theLock.newCondition();
		this.bufferEmpty = this.theLock.newCondition();
	}

	@Override
	public E get() throws InterruptedException {
		E result = null;

		// Obter a exclusão
		this.theLock.lock();
		try {
			// ENQUANTO não existir um objecto vou esperar
			while (this.size == 0) {
				this.bufferEmpty.await();
			}

			// Já existe um obejcto
			result = this.removeElement();

			// Se existirem tarefas bloqueadas para inserir um objecto
			this.bufferFull.signal();
		} finally {
			// Libertar a exclusão
			this.theLock.unlock();
		}

		return result;
	}

	@Override
	public void put(E e) throws InterruptedException {
		// Obter a exclusão
		this.theLock.lock();
		try {
			// ENQUANTO não existir um objecto vou esperar
			while (this.size == this.data.length) {
				this.bufferFull.await();
			}

			// Já existe um obejcto
			this.addElement(e);

			// Se existirem tarefas bloqueadas para inserir um objecto
			this.bufferEmpty.signal();
		} finally {
			// Libertar a exclusão
			this.theLock.unlock();
		}
	}

	@Override
	public String description() {
		return "Monitores Java - Multiplas condições (Butler W. Lampson & David D. Redell)";
	}

}
