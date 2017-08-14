package de.dhbw.horb.programmieren.projekt.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import de.dhbw.horb.programmieren.projekt.algorithms.SortAlgorithm;
import de.dhbw.horb.programmieren.projekt.controller.Algorithm;
import de.dhbw.horb.programmieren.projekt.events.EventType;
import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import de.dhbw.horb.programmieren.projekt.events.SortingListener;

public class SortingService {

	int[] array;
	int threads;
	int delay;
	int runs;
	SortAlgorithm algorithm;
	
	public void startNewSort (ArrayList<SortingListener> listeners) {
		
		int[] sorterArray = array;
		SortingEvent event = new SortingEvent("Array generiert", EventType.ARRAYGENERATED, 0);
		event.setCurrentArray(sorterArray);
		listeners.forEach((l) -> {l.handle(event);});
		long avgTime = 0;
		for (int i = 0; i < runs; i++) {
			sorterArray = Arrays.copyOf(array, array.length);
			long startTime = System.nanoTime();
			if (threads == 1) {
				algorithm.startSingleThreaded(sorterArray, delay);
			} else {
				algorithm.startMultiThreaded(sorterArray, threads, delay);
				try {
					algorithm.waitForEnd();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			avgTime += System.nanoTime() - startTime;
		}
		avgTime /= runs;
		SortingEvent eventEnd = new SortingEvent("Sortierung beendet!", EventType.SORTINGENDED, avgTime);
		eventEnd.setCurrentArray(sorterArray);
		listeners.forEach((l) -> {l.handle(eventEnd);});
	}

	public SortingService(int[] array, int threads, int delay, int runs, Algorithm algorithm) {
		super();
		this.array = array;
		this.threads = threads;
		this.delay = delay;
		this.runs = runs;
		this.algorithm = algorithm.getAlgorithmImplementation();
	}
}