package demo.classic.boundedbuffer.actors;

import javax.swing.*;

import demo.classic.boundedbuffer.IActorLifeCycle;
import demo.classic.boundedbuffer.IBoundedBuffer;

/**
 * <p>
 * Classe <code>BaseActor</code>.
 *
 * <p>
 * Esta classe representa a base dos actores envolvidos no problema dos
 * produtores consumidores.
 *
 * <p>
 * A classe permite controlar o ciclo de vida dos actores e o tempo de sleep dos
 * actores.
 *
 * <p>
 * Esta classe disponibiliza ainda um método que permite mostrar o estado de um
 * actor.
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
 * @see demo.classic.boundedbuffer.actors.ThreadConsumer
 * @see demo.classic.boundedbuffer.actors.ThreadProducer
 * @see demo.classic.boundedbuffer.IBoundedBuffer
 * 
 * @see demo.classic.boundedbuffer.app.ProducerConsumerFrame
 */
public abstract class BaseActor extends Thread {
	/**
	 * <p>
	 * Index de criação deste actor
	 */
	protected int threadIndex;

	/**
	 * <p>
	 * Notificação do fim do ciclo de vida do actor
	 */
	protected IActorLifeCycle lifeCycleManagement;

	/**
	 * <p>
	 * Contentor partilhado entre os consumidores e os produtores.
	 *
	 * @see demo.classic.boundedbuffer.IBoundedBuffer
	 */
	protected IBoundedBuffer<Character> buffer;

	/**
	 * <p>
	 * Controle do ciclo de vida dos actores. Por cada iteracção é testada esta
	 * variavel.
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#actorStop()
	 */
	protected boolean stayRunning = true;

	/**
	 * <p>
	 * Terminar o ciclo de vida do actor.
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#stayRunning
	 */
	public void actorStop() {
		this.stayRunning = false;
	}

	/**
	 * <p>
	 * Tempo que o actor esta a dormir depois de aceder ao contentor.
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#setSleepTime(int)
	 */
	protected int sleepTime = 750;

	/**
	 * <p>
	 * Modificar o tempo que o actor dorme depois de aceder ao contentor.
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#sleepTime
	 *
	 * @param newSleepTime novo tempo de sleep para o actor
	 */
	public void setSleepTime(int newSleepTime) {
		this.sleepTime = newSleepTime;
	}

	/**
	 * <p>
	 * Componente onde é afixado o estado do actor.
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#showState(String)
	 */
	private JTextField stateDisplay;

	/**
	 * <p>
	 * Mostrar o estado do actor.
	 *
	 * <p>
	 * Este método é <i>thread safe</i>.
	 *
	 * @param state String que descreve o estado do actor
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#stateDisplay
	 */
	protected void showState(final String state) {
		try {
			Runnable updateGui = new Runnable() {
				public void run() {
					stateDisplay.setText(state);
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
	 * Mostrar os detalhes de uma excepção.
	 *
	 * @param ex excepção a mostar
	 */
	protected void showException(Exception ex) {
		StringBuilder sBuilder;
		sBuilder = new StringBuilder();

		sBuilder.append(ex.getMessage());
		for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
			sBuilder.append("\r\n");
			sBuilder.append(stackTraceElement.toString());
		}
		JOptionPane.showMessageDialog(null, sBuilder.toString(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * <p>
	 * Constructor.
	 *
	 * @param threadIndex         index da tarefa
	 * @param lifeCycleManagement notificação do fim do ciclo de vida do actor
	 * @param buffer              contentor partilhado pelos actores
	 * @param stateDisplay        componente onde é afixado o estado do actor
	 *
	 * @see demo.classic.boundedbuffer.actors.BaseActor#buffer
	 * @see demo.classic.boundedbuffer.actors.BaseActor#stateDisplay
	 */
	public BaseActor(int threadIndex, IActorLifeCycle lifeCycleManagement, IBoundedBuffer<Character> buffer,
			JTextField stateDisplay) {
		this.threadIndex = threadIndex;
		this.lifeCycleManagement = lifeCycleManagement;
		this.buffer = buffer;
		this.stateDisplay = stateDisplay;
	}
}
