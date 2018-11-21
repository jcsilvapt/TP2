package demo.classic.boundedbuffer;

/**
 * <p>
 * Interface <code>IBoundedBufferGUI</code>.
 *
 * <p>
 * Interface necessária para mostrar o estado de um contentor partilhado.
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
public interface IBoundedBufferGUI {

	public int getCapacity();

	/**
	 * <p>
	 * Mostrar um objecto na interface gráfica.
	 *
	 * <p>
	 * Mostrar a representação em <code>String</code> do objecto contido na posição
	 * <code>index</code> do contentor.
	 *
	 * @param object objecto a representar
	 * @param index  posição do objecto
	 */
	public void PutObjectOnGui(Object object, int index);

	/**
	 * <p>
	 * Apagar um objecto da interface gráfica.
	 *
	 * <p>
	 * Apagar da interface o objecto que se encontra na posição <code>index</code>.
	 *
	 * @param index do objecto que se pretende apagar
	 */
	public void ClearObjectOnGui(int index);

	/**
	 * <p>
	 * Afixar o cursor de put.
	 *
	 * <p>
	 * O cursor de <code>put</code> indica visualmente a posição onde vai ser
	 * inserido o próximo objecto no contentor.
	 *
	 * @param index posição do cursor de put
	 */
	public void SetCursorPut(int index);

	/**
	 * <p>
	 * Afixar o cursor de get.
	 *
	 * <p>
	 * O cursor de <code>get</code> indica visualmente a posição de onde vai ser
	 * retirado o próximo objecto do contentor.
	 *
	 * @param index posição do cursor de get
	 */
	public void SetCursorGet(int index);

	/**
	 * <p>
	 * Mostrar o valor do cursor de <code>put</code>.
	 *
	 * @param indexPut valor do cursor de put
	 */
	public void ShowIndexPut(int indexPut);

	/**
	 * <p>
	 * Mostrar o valor do cursor de <code>get</code>.
	 *
	 * @param indexGet valor do cursor de get
	 */
	public void ShowIndexGet(int indexGet);

	/**
	 * <p>
	 * Mostrar o valor do número de elementos contidos no contentor.
	 *
	 * @param size numero de elementos que o contentor contém actualmente
	 */
	public void ShowSize(int size);

	/**
	 * <p>
	 * Efectuar reset o contentor gráfico.
	 *
	 */
	public void resetDisplay();
}
