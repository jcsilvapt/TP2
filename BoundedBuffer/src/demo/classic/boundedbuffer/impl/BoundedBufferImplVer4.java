package demo.classic.boundedbuffer.impl;

import demo.classic.boundedbuffer.BoundedBufferBaseImpl;
import demo.classic.boundedbuffer.IBoundedBufferGUI;

public class BoundedBufferImplVer4<E> 
		extends BoundedBufferBaseImpl<E> {

	public BoundedBufferImplVer4(int _size, IBoundedBufferGUI _gui) {
		super(_size, _gui);
	}

	@Override
	public synchronized E get() throws InterruptedException {
		E result = null;

		// ENQUANTO não existir um objecto vou esperar
		while (this.size == 0) {
			this.wait();
		}

		// Já existe um obejcto
		result = this.removeElement();

		// Se existirem tarefas bloqueadas para inserir um objecto
		this.notify();

		return result;
	}

	@Override
	public synchronized void put(E e) throws InterruptedException {

		// ENQUANTO não existir uma posição livre vou esperar
		while (this.size == this.data.length) {
			this.wait();
		}

		// Já existe uma posição livre
		this.addElement(e);

		// Se existirem tarefas bloqueadas para obter um objecto
		this.notify();
	}

	@Override
	public String description() {
		return "Monitores Java - Condição única (Butler W. Lampson & David D. Redell)";
	}

}
