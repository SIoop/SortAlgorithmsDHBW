package de.dhbw.horb.programmieren.projekt.algorithms;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 
 * Diese Klasse stellt eine Implentierung des Mergesort dar, die sowohl mit
 * einem als auch mit mehreren Threads arbeiten kann.
 * 
 * @author Alexander Lepper
 *
 */
public class Mergesort extends RecursiveAction implements SortAlgorithm  {

	private static final long serialVersionUID = 1L;
	int[] array;
	int lowerLimit, upperLimit;
	int delay;
	ForkJoinPool pool;
	private int parallelThreads;
	private boolean cancelSort;

	/**
	 * 
	 * @param array Zu sortierendes Array
	 * @param low Untere Grenze des zu sortierendes Bereichs
	 * @param high Obere Grenze des zu sortierenden Bereichs
	 * @param delay Verzögerung in ms pro Durchlauf
	 */
	Mergesort(int[] array, int low, int high, int delay) {
		this.array = array;
		this.lowerLimit = low;
		this.upperLimit = high;
	}
	
	public Mergesort() {}
	
	/**
	 * Führt Mergesort mit einem Threads aus.
	 * Dabei wird die Verzögerung beachtet.
	 * 
	 * @param left Linke Grenze
	 * @param right Rechte grenze
	 */
	public void mergesort(int left, int right) {
		if(cancelSort)return;
		if(delay!=0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
        if (left < right) {
            int middle = ( left + right ) / 2;
            mergesort(left, middle);
            mergesort(middle + 1 , right);
            merge(left, middle, right);
            
        }
    }

	
	/**
	 * Diese Methode wird vom Fork-Join-Framework bei der
	 * Ausführung mit mehreren Threads aufgerufen. Sie
	 * entspricht  in ihrer Logik der mergesort-Methode.
	 */
	protected void compute() {
		if (lowerLimit < upperLimit) {
			if(delay!=0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			int middle =(lowerLimit + upperLimit) / 2;
			invokeAll(new Mergesort(array, lowerLimit, middle, delay), new Mergesort(array, middle+1, upperLimit, delay));
			merge(lowerLimit, middle, upperLimit);
		}
	}

	/**
	 * Merget das Array von left bis middle mit dem Array von middle bis right.
	 * 
	 * @param left Linke Grenze des unteren Arrays
	 * @param middle Rechte Grenze des unteren Arrays/ Linke Grenze des oberen Arrays
	 * @param right Rechte Grenze des oberen Arrays
	 */
	void merge(int left, int middle, int right)
	{
		int counterFromZero, counterFromLeft, counterSortedArray;
		int[] tempArray = new int[middle - left + 1];

		//Baue temporäres Array auf
		counterFromZero = 0;
		counterFromLeft = left;
		while (counterFromLeft <= middle) {
			tempArray[counterFromZero++] = array[counterFromLeft++];
		}

		//Führe Reißverschlussverfahren durch
		counterFromZero = 0;
		counterSortedArray = left;
		while (counterSortedArray < counterFromLeft && counterFromLeft <= right) {
			if (tempArray[counterFromZero] <= array[counterFromLeft]) {
				array[counterSortedArray++] = tempArray[counterFromZero++];
			}
			else {
				array[counterSortedArray++] = array[counterFromLeft++];
			}
		}

		while (counterSortedArray < counterFromLeft) {
			array[counterSortedArray++] = tempArray[counterFromZero++];
		}
	}
	@Override
	public void startSingleThreaded(int[] inputArray, int delay) {

		this.array = inputArray;
		this.delay = delay;
		mergesort(0, inputArray.length-1);
	}

	@Override
	public void startMultiThreaded(int[] inputArray, int parallelThreads, int delay) {
		
		this.parallelThreads = parallelThreads;
		pool = new ForkJoinPool (parallelThreads);
		this.delay = delay;
		this.array = inputArray;
		this.lowerLimit = 0;
		this.upperLimit = inputArray.length - 1;
		pool.execute(this);
	}

	@Override
	public void waitForEnd() throws InterruptedException, ExecutionException {
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
