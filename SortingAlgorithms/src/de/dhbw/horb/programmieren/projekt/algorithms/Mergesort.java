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
	/**
	 * Das Array der zu sortierenden Zahlen
	 */
	int[] array;
	/**
	 * Die untere Grenze des zu sortierenden Bereiches
	 */
	int lowerLimit;
	/**
	 * Die obere Grenze des zu sortierenden Bereiches
	 */
	int upperLimit;
	/**
	 * Die Verz�gerung in ms pro Durchlauf. Nur bei Sortierung mit Animation angeben, damit diese sichtbar ist.
	 */
	int delay;
	ForkJoinPool pool;
	/**
	 * Die Anzahl der Threads, die zur Sortierung benutzt werden.
	 */
	private int parallelThreads;
	/**
	 * Gibt an, ob die Sortierung abgebrochen wurde.
	 */
	private boolean cancelSort;
	
	public Mergesort() {}
	
	/**
	 * F�hrt Mergesort mit einem Threads aus.
	 * Dabei wird die Verz�gerung beachtet.
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
	 * Ausf�hrung mit mehreren Threads aufgerufen. Sie
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
			Mergesort mergesort1 = new Mergesort().setAttributes(array, lowerLimit, middle, delay);
			Mergesort mergesort2 = new Mergesort().setAttributes(array, middle+1, upperLimit, delay);
			invokeAll(mergesort1, mergesort2);
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

		//Baue tempor�res Array auf
		counterFromZero = 0;
		counterFromLeft = left;
		while (counterFromLeft <= middle) {
			tempArray[counterFromZero++] = array[counterFromLeft++];
		}

		//F�hre Rei�verschlussverfahren durch
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
	
	public Mergesort setAttributes (int[] array, int lowerLimit, int upperLimit, int delay) {
		
		this.array = array;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.delay = delay;
		return this;
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
