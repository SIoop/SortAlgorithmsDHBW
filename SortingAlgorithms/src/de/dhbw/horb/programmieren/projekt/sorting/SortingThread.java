package de.dhbw.horb.programmieren.projekt.sorting;

import java.util.ArrayList;

import de.dhbw.horb.programmieren.projekt.controller.Algorithm;
import de.dhbw.horb.programmieren.projekt.controller.InputMode;
import de.dhbw.horb.programmieren.projekt.events.EventType;
import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import de.dhbw.horb.programmieren.projekt.events.SortingListener;

/**
 * Sorting Thread ist ein Runnable, das ein Array generiert und die Sortierung mit diesem startet.
 * Um die Oberfläche nicht zu blockieren, geschieht dies vom Controller aus in einem neuen Thread.
 * @author Nick Lamparter
 *
 */
public class SortingThread implements Runnable {

	/**
	 * Der ausgelesene String aus dem Eingabefeld für die untere Grenze des Sortierarrays
	 */
	private String lowerLimit;
	/**
	 * Der ausgelesene String aus dem Eingabefeld für die obere Grenze des Sortierarrays
	 */
	private String upperLimit;
	/**
	 * Der ausgelesene String aus dem Eingabefeld für die Anzahl der zu sortierenden Zahlen
	 */
	private String amount;
	/**
	 * Der ausgelesene String aus dem Eingabefeld für die manuelle Eingabe der Zahlen
	 */
	private String manualInput;
	/**
	 * Der ausgelesene String aus dem Eingabefeld für den Pfad der zu sortierenden Datei
	 */
	private String fileInput;
	
	/**
	 * Der vom Benutzer ausgewählte Algorithmus
	 */
	private Algorithm algorithm;
	/**
	 * Die Anzahl der zur Sortierung verwendeten Threads
	 */
	private int threads;
	/**
	 * Die Verzögerung in Millisekunden
	 */
	private int delay;
	/**
	 * Die Anzahl der Sortierdurchläufe
	 */
	private int runs;
	/**
	 * Der Eingabemodus für die zu sortierenden Zahlen
	 */
	private InputMode mode;
	
	private SortingService service;
	
	/**
	 * Die Listener für diese Klasse
	 */
	private ArrayList<SortingListener> listeners = new ArrayList<>();
	
	
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
		
		//Wirf Event für Beginn der Sortierung
		listeners.forEach((l) -> {l.handle(new SortingEvent("Sortierung wird gestartet...\nArray wird generiert...\n", EventType.SORTINGSTARTED, 0));});
		Runtime.getRuntime().gc();
		
		//Generiere Array
		if(mode==InputMode.RANDOM) {
			array = ArrayGenerator.randomArray(Integer.parseInt(lowerLimit), Integer.parseInt(upperLimit), Integer.parseInt(amount));
		} else if(mode==InputMode.FILE) {
			try {
				array = ArrayGenerator.fileArray(fileInput);
			} catch (NumberFormatException e) {
				listeners.forEach((l) -> {l.handle(new SortingEvent("Fehler im Format der Eingabedatei! Nur Zahlen getrennt mit Kommas!\n", EventType.FILEERROR, 0));});
				return;
			}
		} else {
			array = ArrayGenerator.manualArray(manualInput);
		}
		
		//Starte Service mit Zuhörern
		service = new SortingService(array,threads,delay,runs, algorithm);
		service.startNewSort(listeners);
	}
	
	public void cancel() {
		service.cancel();
	}
}
