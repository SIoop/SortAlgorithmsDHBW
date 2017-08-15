package de.dhbw.horb.programmieren.projekt.algorithms;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Quicksort extends RecursiveAction implements SortAlgorithm {

	private static final long serialVersionUID = 1L;
	int[] arr;
	int l, r, delay;
	ForkJoinPool pool;
	boolean cancelSort = false;
	int parallelThreads;
	
	public Quicksort (int [] arr, int l, int r, int delay) {
		this.arr = arr;
		this.l = l;
		this.r = r;
	}
	
	public Quicksort() {
	}
	
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
	protected void compute() {
		
		if(delay!=0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (l<r) {
			int p = divide(l, r);
			invokeAll(new Quicksort(arr, l, p, delay), new Quicksort(arr, p + 1, r, delay));
		}
		
	}
	
	
	private int divide(int l, int r) {
		
		int pivot = arr[l];
		int i = l - 1;
		int j = r + 1;
		while(true) {
			do {
				i++;
			} while (arr[i] < pivot);
			do {
				j--;
			} while (arr[j] > pivot);
			if (i >= j) return j;
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
	}

	@Override
	public void startSingleThreaded(int[] inputArray, int delay) {
		
		this.arr = inputArray;
		this.delay = delay;
		quicksort(0,inputArray.length - 1);
	}

	@Override
	public void startMultiThreaded(int[] inputArray, int parallelThreads, int delay) {
		
		this.parallelThreads = parallelThreads;
		this.delay = delay;
		this.arr = inputArray;
		this.l = 0;
		this.r = inputArray.length - 1;
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
