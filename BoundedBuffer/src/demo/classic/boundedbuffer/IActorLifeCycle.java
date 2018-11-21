package demo.classic.boundedbuffer;

/**
 * <p>
 * Interface <code>IActorLifeCycle</code>.
 *
 * <p>
 * Interface para gerir o fim do ciclo de vida de um
 * ator(<code>BaseActor</code>).
 *
 * <p>
 * Copyright (c) 2018
 *
 * <p>
 * Company: ADEETC
 *
 * @author Carlos Gonçalves
 * @version 1.0
 */
public interface IActorLifeCycle {

	public void consumerEnded(int actorIndex);

	public void producerEnded(int actorIndex);
}
