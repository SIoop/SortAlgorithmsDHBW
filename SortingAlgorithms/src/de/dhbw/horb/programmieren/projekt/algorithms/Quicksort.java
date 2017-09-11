package de.dhbw.horb.programmieren.projekt.algorithms;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Diese Klasse stellt eine Implementierung des Quicksort dar, die
 * sowohl mit einem als auch mit mehreren Threads arbeiten kann.
 * 
 * @author Alexander Lepper
 *
 */
public class Quicksort extends RecursiveAction implements SortAlgorithm {

	private static final long serialVersionUID = 1L;
	int[] array;
	int lowerLimit, upperLimit, delay;
	ForkJoinPool pool;
	boolean cancelSort = false;
	int parallelThreads;
	
	/**
	 * 
	 * @param arr Array to sort
	 * @param l Lower Limit
	 * @param r Upper Limit
	 * @param delay Verzögerung
	 */
	public Quicksort (int [] arr, int l, int r, int delay) {
		this.array = arr;
		this.lowerLimit = l;
		this.upperLimit = r;
	}
	
	public Quicksort() {}
	
	/**
	 * Single Thread Sorting Methode
	 * 
	 * @param left Unteres Limit
	 * @param right Oberes limit
	 */
	private void quicksort (int left, int right) {
		if(cancelSort)return;
		if(delay!=0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (left<right) {
			int pivotPos = divide(left, right);
			quicksort(left, pivotPos);
			quicksort(pivotPos + 1, right);
		}
	}

	@Override
	/**
	 * Diese Methode wird vom Fork-Join-Framework bei der
	 * Ausführung mit mehreren Threads aufgerufen. Sie
	 * entspricht  in ihrer Logik der quicksort-Methode.
	 */
	protected void compute() {
		
		if(delay!=0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (lowerLimit<upperLimit) {
			int pivotPos = divide(lowerLimit, upperLimit);
			invokeAll(new Quicksort(array, lowerLimit, pivotPos, delay), new Quicksort(array, pivotPos + 1, upperLimit, delay));
		}
		
	}
	
	/**
	 * Diese Methode teilt das Array in zwei Arrays auf,
	 * eines davon ist das linke, dessen Inhalt jeweils kleiner als das Pivot-
	 * Element ist, und das rechte, desssen Inhalt größer ist.
	 * Als Pivot-Element wird das erste des Arraybereichs gewählt.
	 * @param l Unteres Limit
	 * @param r Oberes Limit
	 * @return Endgültige Position des Pivot-Elements
	 */
	private int divide(int l, int r) {
		
		int pivot = array[l];
		int leftCounter = l - 1;
		int rightCounter = r + 1;
		while(true) {
			
			//Wähle größeres Element auf linker Seite
			do {
				leftCounter++;
			} while (array[leftCounter] < pivot);
			
			//Wähle kleineres Element auf rechter Seite
			do {
				rightCounter--;
			} while (array[rightCounter] > pivot);
			
			if (leftCounter >= rightCounter) return rightCounter;
			
			//Tausche Elemente
			int temp = array[leftCounter];
			array[leftCounter] = array[rightCounter];
			array[rightCounter] = temp;
		}
	}

	@Override
	public void startSingleThreaded(int[] inputArray, int delay) {
		
		this.array = inputArray;
		this.delay = delay;
		quicksort(0,inputArray.length - 1);
	}

	@Override
	public void startMultiThreaded(int[] inputArray, int parallelThreads, int delay) {
		
		this.parallelThreads = parallelThreads;
		this.delay = delay;
		this.array = inputArray;
		this.lowerLimit = 0;
		this.upperLimit = inputArray.length - 1;
		pool = new ForkJoinPool (parallelThreads);
		pool.execute(this);
		
	}

	@Override
	public void waitForEnd() throws InterruptedException, ExecutionException, CancellationException {
			this.get();
	}

	@Override
	public void cancel() {
		
		if (parallelThreads > 1) {
			pool.shutdownNow();
		} else {
			cancelSort = true;
		}
	}
}
