package de.dhbw.horb.programmieren.projekt.algorithms;

import java.util.concurrent.ExecutionException;

public interface SortAlgorithm {

	public void startSingleThreaded(int[] inputArray, int delay);
	public void startMultiThreaded(int [] inputArray, int parallelThreads, int delay);
	public void waitForEnd() throws InterruptedException, ExecutionException;
}
