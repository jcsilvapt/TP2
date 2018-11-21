package demo.classic.boundedbuffer;

/**
 * <p>
 * Interface <code>IBoundedBuffer</code>.
 *
 * <p>
 * Interface do contentor <i>bounded buffer</i>.
 *
 * <p>
 * Copyright (c) 2003-2018
 *
 * <p>
 * Company: ADEETC
 *
 * @author Carlos Gonçalves &amp; Nuno Oliveira
 * @version 1.0
 */
public interface IBoundedBuffer<E> {

	public static final int DefaultDimBuffer = 32;

	public void put(E e) throws InterruptedException;

	public E get() throws InterruptedException;

	public String description();
}
