package demo.classic.boundedbuffer.actors;

import java.util.*;
import javax.swing.*;

import demo.classic.boundedbuffer.IActorLifeCycle;
import demo.classic.boundedbuffer.IBoundedBuffer;

/**
 * <p>
 * Classe <code>ThreadConsumer</code>.
 *
 * <p>
 * Esta classe representa a implementação do actor "Consumidor".
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
 * @see demo.classic.boundedbuffer.actors.BaseActor
 * @see demo.classic.boundedbuffer.IBoundedBuffer
 */
public class ThreadConsumer extends BaseActor {
	/**
	 * <p>
	 * Componente onde é afixado o objecto retirado do contentor partilhado.
	 *
	 * @see demo.classic.boundedbuffer.IBoundedBuffer
	 * @see demo.classic.boundedbuffer.actors.ThreadConsumer#showObject(Object)
	 */
	private JTextField output;

	/**
	 * <p>
	 * Mostrar o objecto retirado do contentor partilhado.
	 *
	 * <p>
	 * Este método é <i>thread safe</i>
	 *
	 * @see demo.classic.boundedbuffer.actors.ThreadConsumer#output
	 *
	 * @param object objecto que se vai mostrar
	 */
	private void showObject(final Object object) {
		try {
			Runnable updateGui = new Runnable() {
				public void run() {
					output.setText(object.toString());
				}
			};

			if (SwingUtilities.isEventDispatchThread())
				updateGui.run();
			else
				SwingUtilities.invokeAndWait(updateGui);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Constructor. Chama o constructor da classe base.
	 *
	 * @param threadIndex         index do actor
	 * @param lifeCycleManagement notificação do fim do ciclo de vida do actor
	 * @param buffer              contentor partilhado pelos actores
	 * @param stateDisplay        componente onde é afixado o estado do actor
	 * @param output              componente onde é afixado o objecto retirado do
	 *                            contentor partilhado
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#BaseActor(int,
	 *      IActorLifeCycle, IBoundedBuffer, JTextField)
	 */
	public ThreadConsumer(
			int threadIndex, 
			IActorLifeCycle lifeCycleManagement, IBoundedBuffer<Character> buffer,
			JTextField stateDisplay, JTextField output) {
		super(threadIndex, lifeCycleManagement, buffer, stateDisplay);

		this.output = output;
	}

	/**
	 * <p>
	 * Comportamento do consumidor
	 */
	public void run() {
		Random rnd = new Random(this.hashCode());

		while (this.stayRunning) {
			try {
				this.showState("Waiting");

				Object object = this.buffer.get();

				this.showObject(object);

				this.showState("Working");

				Thread.sleep(rnd.nextInt(this.sleepTime));
			} catch (Exception ex) {
				showException(ex);
			}
		}

		this.showState("");
		this.lifeCycleManagement.consumerEnded(this.threadIndex);
	}
}
