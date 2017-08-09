package de.dhbw.horb.programmieren.projekt.sortcontroller;

import de.dhbw.horb.programmieren.projekt.algorithms.SortAlgorithm;
import de.dhbw.horb.programmieren.projekt.algorithms.ThreadedMergesort;
import de.dhbw.horb.programmieren.projekt.algorithms.ThreadedQuicksort;

public class Options {

	public enum InputType {MANUALLY, FILE, RANDOM};
	
	private InputType inputType;
	private SortAlgorithm algorithm;
	private int numberOfThreads;
	private boolean animation;
	
	private String manualInput;
	private String filePath;
	private int lowerLimit, upperLimit, countNumbers, repNumber, delay;
	
	public boolean isAnimation() {
		return animation;
	}
	public void setAnimation(boolean animation) {
		this.animation = animation;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public InputType getInputType() {
		return inputType;
	}
	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}
	public SortAlgorithm getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(SortAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	public void setAlgorithm(String algorithm) {
		if(algorithm=="Quicksort") this.algorithm = new ThreadedQuicksort();
		else this.algorithm = new ThreadedMergesort();
	}
	public int getNumberOfThreads() {
		return numberOfThreads;
	}
	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}
	public String getManualInput() {
		return manualInput;
	}
	public void setManualInput(String manualInput) throws IncorrectInputException {
		if(manualInput.equals("")) throw new IncorrectInputException("Manuelle Eingabe ist leer!\n");
		else this.manualInput = manualInput;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) throws IncorrectInputException {
		if(filePath.equals(""))throw new IncorrectInputException("Datei-Pfad ist leer!\n");
		else this.filePath = filePath;
	}
	public int getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public int getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}
	public int getCountNumbers() {
		return countNumbers;
	}
	public void setCountNumbers(int countNumbers) {
		this.countNumbers = countNumbers;
	}
	public int getRepNumber() {
		return repNumber;
	}
	public void setRepNumber(int repNumber) {
		this.repNumber = repNumber;
	}
	
	public void setLowerLimit (String lowerLimit) throws IncorrectInputException {
		try {
			this.lowerLimit = Integer.parseInt(lowerLimit);
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Untere Grenze f¸r Zufallszahlen ist falsch!\n");
		}
	}
	
	public void setUpperLimit(String upperLimit) throws IncorrectInputException {
		try {
			this.upperLimit = Integer.parseInt(upperLimit);
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Obere Grenze f¸r Zufallszahlen ist falsch!\n");
		}
	}
	
	public void setCountNumbers(String countNumbers) throws IncorrectInputException {
		try {
			this.countNumbers = Integer.parseInt(countNumbers);
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Anzahl der Zufallszahlen ist falsch!\n");
		}
	}
	
	public void setDelay(String delay) throws IncorrectInputException {
		try {
			this.delay = Integer.parseInt(delay);
			if(this.delay > 1000) throw new IncorrectInputException ("Verzˆgerung ist zu groﬂ! (Maximum: 1000)\n");
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Verzˆgerung ist falsch!\n");
		}
	}
	
	public void setRuns(String runs) throws IncorrectInputException {
		try {
			this.repNumber = Integer.parseInt(runs);
			if(this.repNumber > 10000) throw new IncorrectInputException ("Anzahl der Durchf¸hrungen ist zu groﬂ! (Maximum: 10000)\n");
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Anzahl der Durchf¸hrungen ist falsch!\n");
		}
	}
	
	public void setThreadNumber(String threadNumber) throws IncorrectInputException {
		try {
			this.numberOfThreads = Integer.parseInt(threadNumber);
			if(this.numberOfThreads > 128) throw new IncorrectInputException ("Anzahl der Threads ist zu groﬂ! (Maximum: 128)\n");
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Anzahl der Threads ist falsch!\n");
		}
	}
	
	public class IncorrectInputException extends Exception {

		public IncorrectInputException (String s) {
			super (s);
		}
		private static final long serialVersionUID = 4892989218298864067L;
		
	}
}