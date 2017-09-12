package de.dhbw.horb.programmieren.projekt.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import de.dhbw.horb.programmieren.projekt.algorithms.SortAlgorithm;
import de.dhbw.horb.programmieren.projekt.controller.Algorithm;
import de.dhbw.horb.programmieren.projekt.events.EventType;
import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import de.dhbw.horb.programmieren.projekt.events.SortingListener;

/**
 * Der SortingService bewerkstelligt das eigentliche Starten der Algorithmen.
 * @author Alexander Lepper
 *
 */
public class SortingService {

	int[] array;
	int threads;
	int delay;
	int runs;
	Algorithm algo;
	SortAlgorithm algorithm;
	
	/**
	 * Startet die Sortierung.
	 * @param listeners Zuhörer, die am SOrtingServcie angemeldet werden sollen.
	 */
	public void startNewSort (ArrayList<SortingListener> listeners) {
		
		int[] sorterArray = array;
		
		//Array wurde bereits vom SortingThread generiert, deswegen erzeuge Event
		SortingEvent event = new SortingEvent("Array generiert", EventType.ARRAYGENERATED, 0);
		event.setCurrentArray(sorterArray);
		listeners.forEach((l) -> {l.handle(event);});
		
		
		long avgTime = 0;
		for (int i = 0; i < runs; i++) {
			
			//Erstelle Arbeitskopie des Arrays
			sorterArray = Arrays.copyOf(array, array.length);
			
			//Event für Anfang eines Durchlaufs
			SortingEvent runEvent = new SortingEvent("", EventType.SORTINGRUN, 0);
			runEvent.setCurrentArray(sorterArray);
			listeners.forEach((l) -> {l.handle(runEvent);});
			
			//Nimm Zeit uns starte Algorithmus
			long startTime = System.nanoTime();
			try {
				algorithm = algo.getAlgorithmImplementation();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (threads == 1) {
				algorithm.startSingleThreaded(sorterArray, delay);
			} else {
				algorithm.startMultiThreaded(sorterArray, threads, delay);
				try {
					algorithm.waitForEnd();
				} catch (InterruptedException | ExecutionException | CancellationException e) {
				}
			}
			avgTime += System.nanoTime() - startTime;
		}
		
		//Berechne Durschnittszeit
		avgTime /= runs;
		
		//Event für Ende der Sortierung
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
		this.algo = algorithm;
	}

	public void cancel() {
		algorithm.cancel();
	}
}