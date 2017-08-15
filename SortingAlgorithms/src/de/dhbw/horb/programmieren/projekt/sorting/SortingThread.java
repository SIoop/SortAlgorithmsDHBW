package de.dhbw.horb.programmieren.projekt.sorting;

import java.util.ArrayList;

import de.dhbw.horb.programmieren.projekt.controller.Algorithm;
import de.dhbw.horb.programmieren.projekt.controller.InputMode;
import de.dhbw.horb.programmieren.projekt.events.EventType;
import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import de.dhbw.horb.programmieren.projekt.events.SortingListener;

public class SortingThread implements Runnable {

	String lowerLimit;
	String upperLimit;
	String amount;
	
	String manualInput;
	String fileInput;
	
	Algorithm algorithm;
	int threads;
	int delay;
	int runs;
	InputMode mode;
	
	SortingService service;
	
	ArrayList<SortingListener> listeners = new ArrayList<>();
	
	
	public SortingThread(String lowerLimit, String upperLimit, String amount, String manualInput, String fileInput,
			Algorithm algorithm, int threads, int delay, int runs, InputMode mode) {
		super();
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.amount = amount;
		this.manualInput = manualInput;
		this.fileInput = fileInput;
		this.algorithm = algorithm;
		this.threads = threads;
		this.delay = delay;
		this.runs = runs;
		this.mode = mode;
	}
	
	public void addSortListener (SortingListener lis) {
		listeners.add(lis);
	}
	

	@Override
	public void run() {
		int[] array;
		listeners.forEach((l) -> {l.handle(new SortingEvent("Sortierung wird gestartet...\nArray wird generiert...\n", EventType.SORTINGSTARTED, 0));});
		Runtime.getRuntime().gc();
		if(mode==InputMode.RANDOM) {
			array = ArrayGenerator.randomArray(Integer.parseInt(lowerLimit), Integer.parseInt(upperLimit), Integer.parseInt(amount));
		} else if(mode==InputMode.FILE) {
			array = ArrayGenerator.fileArray(fileInput);
		} else {
			array = ArrayGenerator.manualArray(manualInput);
		}
		service = new SortingService(array,threads,delay,runs, algorithm);
		service.startNewSort(listeners);
	}
	
	public void cancel() {
		service.cancel();
	}
}
