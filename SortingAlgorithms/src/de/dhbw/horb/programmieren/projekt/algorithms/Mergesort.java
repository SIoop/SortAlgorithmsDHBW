package de.dhbw.horb.programmieren.projekt.algorithms;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Mergesort extends RecursiveAction implements SortAlgorithm  {

	private static final long serialVersionUID = 1L;
	int[] array;
	int lo, hi;
	int delay;
	ForkJoinPool pool;
	private int parallelThreads;
	private boolean cancelSort;

	Mergesort(int[] array, int lo, int hi, int delay) {
		this.array = array;
		this.lo = lo;
		this.hi = hi;
	}

	public Mergesort() {
	}
	
	public void mergesort(int l, int r) {
		if(cancelSort)return;
		if(delay!=0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
        if (l < r) {
            int q = ( l + r ) / 2;
            mergesort(l, q);
            mergesort(q + 1 , r);
            merge(l, q, r);
            
        }
    }

	protected void compute() {
		if (lo < hi) {
			if(delay!=0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			int mid =(lo + hi) / 2;
			invokeAll(new Mergesort(array, lo, mid, delay), new Mergesort(array, mid+1, hi, delay));
			merge(lo, mid, hi);
		}
	}

	void merge(int lo, int m, int hi)
	{
	    int i, j, k;
	    int[] b = new int[m-lo+1];

	    i=0; j=lo;
	    while (j<=m)
	        b[i++]=array[j++];

	    i=0; k=lo;
	    while (k<j && j<=hi)
	        if (b[i]<=array[j])
	            array[k++]=b[i++];
	        else
	            array[k++]=array[j++];

	    while (k<j)
	        array[k++]=b[i++];
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
		this.lo = 0;
		this.hi = inputArray.length - 1;
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
