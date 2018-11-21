package demo.classic.boundedbuffer.gui;

import javax.swing.*;

import demo.classic.boundedbuffer.IBoundedBufferGUI;

/**
 * <p>
 * Classe <b>SharedBufferGUIImplException</b>.
 *
 * <p>
 * Excep��o que � gerada quando se tenta construir um objecto que sabe mostrar a
 * representa��o de um contentor partilhado mas a dimens�o do array de
 * componentes que suporta a visualiza��o � diferente da constante
 * <code>DIM_BUFFER</code> especificada na interface <code>SharedBuffer</code>.
 *
 * <p>
 * Copyright (c) 2003-2018
 *
 * <p>
 * Company: ADEETC
 *
 * @author Carlos Gon�alves &amp; Nuno Oliveira
 * @version 1.0
 *
 * @see demo.classic.boundedbuffer.IBoundedBufferGUI
 * @see demo.classic.boundedbuffer.IBoundedBuffer
 * @see demo.classic.boundedbuffer.IBoundedBuffer#DefaultDimBuffer
 */
class SharedBufferGUIImplException extends Exception {
	private static final long serialVersionUID = 1L;

	public SharedBufferGUIImplException() {
	}

	public SharedBufferGUIImplException(String s) {
		super(s);
	}
}

/**
 * <p>
 * Classe <b>SharedBufferGUIImpl</b>.
 *
 * <p>
 * Implementa��o de um objecto que sabe mostrar o estado de um contentor.
 *
 * <p>
 * Copyright (c) 2003-2018
 *
 * <p>
 * Company: ADEETC
 *
 * @author Carlos Gon�alves &amp; Nuno Oliveira
 * @version 1.0
 *
 * @see demo.classic.boundedbuffer.IBoundedBufferGUI
 */
public class IBoundedBufferGUIImpl implements IBoundedBufferGUI {
	/**
	 * <p>
	 * Classe privada para actualizar um objecto (colocar) na interface do
	 * contentor.
	 *
	 * <p>
	 * A actualiza��o � feita de um mode <i>thread safe</i>.
	 */
	private class UpdateGuiOnPut implements Runnable {
		private JTextField object;

		public void setOutput(JTextField newOutput) {
			this.object = newOutput;
		}

		private String textToOutput;

		public void setTextToOutput(String newTextToOutput) {
			this.textToOutput = newTextToOutput;
		}

		public UpdateGuiOnPut() {
		}

		public void run() {
			this.object.setText(this.textToOutput);
		}
	};

	/**
	 * <P>
	 * Dimens�o do contentor associado a este visualizador
	 */
	private int capacity;

	/**
	 * <p>
	 * Obter a capacidade m�xima do contentor associado a este visualizador
	 *
	 * @return a capacidade m�xima do contentor associado a este visualizador
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * <p>
	 * Objecto utilizado na actualiza��o do aspecto (colocar) do contentor.
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl.UpdateGuiOnPut
	 */
	private UpdateGuiOnPut updateGuiOnPut = new UpdateGuiOnPut();

	/**
	 * <p>
	 * Classe privada para actualizar um objecto (retirar) na interface do
	 * contentor.
	 *
	 * <p>
	 * A actualiza��o � feita de um mode <i>thread safe</i>.
	 */
	private class UpdateGuiOnGet implements Runnable {
		private JTextField object;

		public void setOutput(JTextField newOutput) {
			this.object = newOutput;
		}

		public UpdateGuiOnGet() {
		}

		public void run() {
			this.object.setText("");
		}
	};

	/**
	 * <p>
	 * Objecto utilizado na actualiza��o do aspecto (retirar) do contentor.
	 *
	 * @see <{UpdateGuiOnGet}>
	 */
	private IBoundedBufferGUIImpl.UpdateGuiOnGet updateGuiOnGet = new UpdateGuiOnGet();

	/**
	 * <p>
	 * O cursor de <code>put</code> � representado por uma <i>slide bar</i>.
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#setSliderIndex(JSlider,
	 *      int )
	 */
	private JSlider putSlider;

	/**
	 * <p>
	 * O cursor de <code>get</code> � representado por uma <i>slide bar</i>.
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#setSliderIndex(JSlider,
	 *      int )
	 */
	private JSlider getSlider;

	/**
	 * <p>
	 * Actualiza��o de um cursor numa <i>slide bar</i>.
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#putSlider
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#getSlider
	 *
	 * @param slider slider do indice
	 * @param index  posi��o a afectar ao slider
	 */
	private void setSliderIndex(final JSlider slider, final int index) {
		try {
			Runnable updateSlider = new Runnable() {
				public void run() {
					slider.setValue((slider.getMaximum() / getCapacity()) * index
							+ (slider.getMaximum() / (getCapacity() * 2)));
				}
			};

			if (SwingUtilities.isEventDispatchThread())
				updateSlider.run();
			else
				SwingUtilities.invokeAndWait(updateSlider);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Os objectos s�o representados num array de componentes do tipo
	 * <code>JTextField</code>.
	 */
	private JTextField[] objectsDisplay;

	/**
	 * <p>
	 * Limpar todos os elementos contidos no contentor.
	 *
	 * <p>
	 * Este m�todo � <i>thread safe</i>.
	 */
	private void clearDisplay() {
		try {
			Runnable updateOutput = new Runnable() {
				public void run() {
					for (int indexDisplay = 0; indexDisplay < objectsDisplay.length; ++indexDisplay)
						objectsDisplay[indexDisplay].setText("");
				}
			};

			if (SwingUtilities.isEventDispatchThread())
				updateOutput.run();
			else
				SwingUtilities.invokeAndWait(updateOutput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Componente onde � afixado o valor do �ndice de <code>put</code>.
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl.UpdateGuiOnPut
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#updateGuiOnPut
	 */
	private JTextField displayIndicePut;

	/**
	 * <p>
	 * Componente onde � afixado o valor do �ndice de <code>get</code>.
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl.UpdateGuiOnGet
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#updateGuiOnGet
	 */
	private JTextField displayIndiceGet;

	/**
	 * <p>
	 * Componente onde � afixado o n�mero de elementos que est�o contidos no
	 * contentor.
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl.UpdateGuiOnGet
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#updateGuiOnGet
	 */
	private JTextField displaySize;

	public IBoundedBufferGUIImpl(JSlider putSlider, JSlider getSlider, JTextField[] objectsDisplay,
			JTextField displayIndicePut, JTextField displayIndiceGet, JTextField displaySize, int capacity)
			throws SharedBufferGUIImplException {

		if (objectsDisplay.length != capacity)
			throw new SharedBufferGUIImplException("Dimens�o do buffer de output inv�lida");

		this.capacity = capacity;

		this.putSlider = putSlider;
		this.getSlider = getSlider;
		this.objectsDisplay = objectsDisplay;

		this.displayIndicePut = displayIndicePut;
		this.displayIndiceGet = displayIndiceGet;
		this.displaySize = displaySize;

		this.resetDisplay();
	}

	public void resetDisplay() {
		this.clearDisplay();

		this.SetCursorGet(0);
		this.SetCursorPut(0);

		this.ShowIndexPut(0);
		this.ShowIndexGet(0);
		this.ShowSize(0);
	}

	/**
	 * <p>
	 * Modificar a posi��o do cursor de <code>put</code>.
	 *
	 * @param index nova posi��o do cursor de <i>put</i>
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#setSliderIndex(JSlider,
	 *      int)
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#putSlider
	 */
	public void SetCursorPut(int index) {
		this.setSliderIndex(this.putSlider, index);
	}

	/**
	 * <p>
	 * Modificar a posi��o do cursor de <code>get</code>.
	 *
	 * @param index nova posi��o do cursor de <i>get</i>
	 *
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#setSliderIndex(JSlider,
	 *      int)
	 * @see demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl#getSlider
	 */
	public void SetCursorGet(int index) {
		this.setSliderIndex(this.getSlider, index);
	}

	/**
	 * <p>
	 * Mostrar um objecto na interface gr�fica.
	 *
	 * @param object objecto a ser mostrado
	 * @param index  posi��o onde deve ser mostrado o objecto
	 *
	 *               <p>
	 *               Este m�todo � <i>thread safe</i>
	 */
	public void PutObjectOnGui(Object object, int index) {
		try {
			this.updateGuiOnPut.setOutput(this.objectsDisplay[index]);
			this.updateGuiOnPut.setTextToOutput(object.toString());

			if (SwingUtilities.isEventDispatchThread())
				this.updateGuiOnPut.run();
			else
				SwingUtilities.invokeAndWait(this.updateGuiOnPut);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Retirar um objecto da interface gr�fica.
	 *
	 * @param index posi��o de onde deve ser retirado o objecto
	 *
	 *              <p>
	 *              Este m�todo � <i>thread safe</i>
	 */
	public void ClearObjectOnGui(int index) {
		try {
			this.updateGuiOnGet.setOutput(this.objectsDisplay[index]);

			if (SwingUtilities.isEventDispatchThread())
				this.updateGuiOnGet.run();
			else
				SwingUtilities.invokeAndWait(this.updateGuiOnGet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Mostrar o valor do �ndice de <i>put</i>.
	 *
	 * @param indexPut valor do �ndice de <i>put</i>
	 */
	public void ShowIndexPut(final int indexPut) {
		try {
			Runnable updateIndexPut = new Runnable() {
				public void run() {
					displayIndicePut.setText("" + indexPut);
				}
			};

			if (SwingUtilities.isEventDispatchThread())
				updateIndexPut.run();
			else
				SwingUtilities.invokeAndWait(updateIndexPut);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Mostrar o valor do �ndice de <i>get</i>.
	 *
	 * @param indexGet valor do �ndice de <i>get</i>
	 */
	public void ShowIndexGet(final int indexGet) {
		try {
			Runnable updateIndexGet = new Runnable() {
				public void run() {
					displayIndiceGet.setText("" + indexGet);
				}
			};

			if (SwingUtilities.isEventDispatchThread())
				updateIndexGet.run();
			else
				SwingUtilities.invokeAndWait(updateIndexGet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Mostrar o valor do n�mero de elementos contidos no buffer.
	 *
	 * @param size n�mero de elementos contidos no contentor
	 */
	public void ShowSize(final int size) {
		try {
			Runnable updateIndexSize = new Runnable() {
				public void run() {
					displaySize.setText("" + size);
				}
			};

			if (SwingUtilities.isEventDispatchThread())
				updateIndexSize.run();
			else
				SwingUtilities.invokeAndWait(updateIndexSize);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
