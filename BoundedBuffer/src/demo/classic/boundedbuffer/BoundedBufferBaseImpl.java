package demo.classic.boundedbuffer;

/**
 * <p>
 * Implementação base da interface <code>IBoundedBuffer</code>.
 *
 * <p>
 * Copyright (c) 2003-2018
 *
 * <p>
 * Company: ADEETC
 *
 * @author Carlos Gonçalves &amp; Nuno Oliveira
 * @version 1.0
 * 
 * @see demo.classic.boundedbuffer.IBoundedBuffer
 */
public abstract class BoundedBufferBaseImpl<E> implements IBoundedBuffer<E> {

	protected IBoundedBufferGUI gui;

	protected E[] data;

	protected int idxPut = 0;

	protected int idxGet = 0;

	protected int size = 0;

	protected void addElement(E e) {

		this.data[this.idxPut] = e;
		this.gui.PutObjectOnGui(e, this.idxPut);

		++this.size;
		this.gui.ShowSize(this.size);

		++this.idxPut;

		if (this.idxPut == this.data.length) {
			this.idxPut = 0;
		}
		this.gui.SetCursorPut(this.idxPut);
		this.gui.ShowIndexPut(this.idxPut);
	}

	protected E removeElement() {

		E result = this.data[this.idxGet];
		this.gui.ClearObjectOnGui(this.idxGet);

		--this.size;
		this.gui.ShowSize(this.size);

		++this.idxGet;

		if (idxGet == this.data.length) {
			idxGet = 0;
		}
		this.gui.SetCursorGet(this.idxGet);
		this.gui.ShowIndexGet(this.idxGet);

		return result;
	}

	@SuppressWarnings("unchecked")
	public BoundedBufferBaseImpl(int _size, IBoundedBufferGUI _gui) {
		this.data = (E[]) new Object[_size];
		this.gui = _gui;
	}
}
