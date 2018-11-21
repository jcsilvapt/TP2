package demo.classic.boundedbuffer.actors;

import java.util.*;
import javax.swing.*;

import demo.classic.boundedbuffer.IActorLifeCycle;
import demo.classic.boundedbuffer.IBoundedBuffer;

/**
 * <p>
 * Classe <code>ThreadProducer</code>.
 *
 * <p>
 * Esta classe representa a implementação do actor "Produtor".
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
public class ThreadProducer extends BaseActor {
	/**
	 * <p>
	 * Constructor. Limita-se a chamar o constructor da classe base.
	 *
	 * @param threadIndex         index do actor
	 * @param lifeCycleManagement notificação do fim do ciclo de vida do actor
	 * @param buffer              contentor partilhado pelos actores
	 * @param stateDisplay        componente onde é afixado o estado do actor
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#BaseActor(int,
	 *      IActorLifeCycle, IBoundedBuffer, JTextField)
	 */
	public ThreadProducer(int threadIndex, IActorLifeCycle lifeCycleManagement, IBoundedBuffer<Character> buffer,
			JTextField stateDisplay) {
		super(threadIndex, lifeCycleManagement, buffer, stateDisplay);
	}

	/**
	 * <p>
	 * Comportamento do Produtor.
	 */
	public void run() {
		Random rnd = new Random(this.hashCode());

		while (this.stayRunning) {
			try {
				Character object = new Character((char) ('A' + this.threadIndex));

				this.showState("Waiting");

				this.buffer.put(object);

				this.showState("Working");

				Thread.sleep(rnd.nextInt(this.sleepTime));
			} catch (Exception ex) {
				showException(ex);
			}
		}

		this.showState("");
		this.lifeCycleManagement.producerEnded(this.threadIndex);
	}
}
