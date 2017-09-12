package de.dhbw.horb.programmieren.projekt.controller;

import java.io.File;

/**
 * Diese Klasse prüft die Benutzereingaben auf der Oberfläche auf Korrektheit.
 * @author Robert Metzinger
 *
 */
public class InputChecker {

	public class IncorrectInputException extends Exception {

		public IncorrectInputException(String string) {
			super(string);
		}

		private static final long serialVersionUID = -3454104695997470670L;

	}

	/**
	 * Der String aus dem manuellen Eingabefeld der GUI
	 */
	String manualInput;
	String fileInput;
	String upperLimit;
	String lowerLimit;
	String amount;
	
	InputMode mode;
	String threads;
	String delay;
	String runs;
	
	public InputChecker (InputMode mode, String lowerLimit, String upperLimit, String amount, String manualInput, String fileInput, String threads, String delay, String runs) {
		
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
		this.amount = amount;
		this.manualInput = manualInput;
		this.fileInput = fileInput;
		this.mode = mode;
		this.threads = threads;
		this.delay = delay;
		this.runs = runs;
	}
	
	public void check() throws IncorrectInputException {
		
		String exceptionMessage = "";
		
		//Check Threads: Correct Format + < 129
		try {
			int thr = Integer.parseInt(threads);
			if(thr > 128) {
				exceptionMessage = exceptionMessage.concat("Anzahl an Threads darf nicht über 128 sein!\n");
			}
		} catch (NumberFormatException e) {
			exceptionMessage = exceptionMessage.concat("Anzahl an Threads muss eine korrekte Zahl sein!\n");
		}
		
		//Check Delay: Correct Format + <= 100
		try {
			int del = Integer.parseInt(delay);
			if(del > 100) {
				exceptionMessage = exceptionMessage.concat("Verzögerung darf nicht über 100ms sein!\n");
			}
		} catch (NumberFormatException e) {
			exceptionMessage = exceptionMessage.concat("Verzögerung muss eine korrekte Zahl sein!\n");
		}
		
		//Check Runs: Correct Format + < 10000
		try {
			int run = Integer.parseInt(runs);
			if(run > 10000) {
				exceptionMessage = exceptionMessage.concat("Durchläufe dürfen nicht über 10000 sein!\n");
			}
		} catch (NumberFormatException e) {
			exceptionMessage = exceptionMessage.concat("Durchläufe müssen eine korrekte Zahl sein!\n");
		}
		
		//Check if input for modes is correct:
		if(mode == InputMode.FILE) {
			if(!new File(fileInput).exists()) {
				exceptionMessage = exceptionMessage.concat("Ungültiger Dateipfad!\n");
			}
			if(!fileInput.endsWith("txt")) {
				exceptionMessage = exceptionMessage.concat("Nur .txt-Dateien werden akzeptiert!\n");
			}
			
			if(fileInput.equals(""))exceptionMessage = exceptionMessage.concat("Relevante Felder dürfen nicht leer sein!\n");
		}
		else if (mode == InputMode.MANUAL) {
			for (int i = 0; i < manualInput.length(); i++) {
				
				if(!Character.isDigit(manualInput.charAt(i)) && manualInput.charAt(i) != ',') {
					exceptionMessage = exceptionMessage.concat("Die manuelle Eingabe darf nur aus Zahlen und Kommas bestehen!\n");
					break;
				}
			}
			if(manualInput.equals(""))exceptionMessage = exceptionMessage.concat("Relevante Felder dürfen nicht leer sein!\n");
		}
		else {
			
			//Check Lower Limit: Correct Format
			try {
				Integer.parseInt(lowerLimit);
			} catch (NumberFormatException e) {
				exceptionMessage = exceptionMessage.concat("Untere Grenze für Zufallszahlen muss eine korrekte Zahl sein!\n");
			}
			
			//Check Upper Limit: Correct Format
			try {
				Integer.parseInt(upperLimit);
			} catch (NumberFormatException e) {
				exceptionMessage = exceptionMessage.concat("Obere Grenze für Zufallszahlen muss eine korrekte Zahl sein!\n");
			}
			
			//Check Amount: Correct Format + < 10000
			try {
				int am = Integer.parseInt(amount);
				if(am*8 > Runtime.getRuntime().freeMemory()) {
					exceptionMessage = exceptionMessage.concat("Zu wenig Speicher (Verfügbar: " + Runtime.getRuntime().freeMemory() + ", benötigt: " + am*4 + ")!\n");
				}
			} catch (NumberFormatException e) {
				exceptionMessage = exceptionMessage.concat("Anzahl an Zufallszahlen muss eine korrekte Zahl sein!\n");
			}
			
		}
		
		if(threads.equals("") || delay.equals("") || runs.equals("")) exceptionMessage = exceptionMessage.concat("Relevante Felder dürfen nicht leer sein!\n");
		
		//If Exception msg is not empty, throw it
		if(!exceptionMessage.equals("")) {
			throw new IncorrectInputException(exceptionMessage);
		}
	}
}
