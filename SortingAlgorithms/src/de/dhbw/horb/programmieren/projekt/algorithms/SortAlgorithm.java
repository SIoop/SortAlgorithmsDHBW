package de.dhbw.horb.programmieren.projekt.algorithms;

import java.util.concurrent.ExecutionException;

public interface SortAlgorithm {

	/**
	 * Startet den Sortieralgorithmus mit einem Thread.
	 * @param inputArray Zu sortierendes Array
	 * @param delay Verzögerung
	 */
	public void startSingleThreaded(int[] inputArray, int delay);
	
	/**
	 * Startet den Sortieralgorithmus mit einer angegeben Anzahl an Threads.
	 * @param inputArray Zu sortierendes Array
	 * @param parallelThreads Anzahl an zu nutzenden Threads
	 * @param delay Verzögerung
	 */
	public void startMultiThreaded(int [] inputArray, int parallelThreads, int delay);
	
	/**
	 * Bricht die Sortierung ab
	 */
	public void cancel();
	
	/**
	 * Blockiert den aufrufenden Thread bis der Sortierlagorithmus beendet ist.
	 * @throws InterruptedException Warten wurde unterbrochen
	 * @throws ExecutionException Während dem Warten ist ein Fehler aufgetreten
	 */
	public void waitForEnd() throws InterruptedException, ExecutionException;
}
