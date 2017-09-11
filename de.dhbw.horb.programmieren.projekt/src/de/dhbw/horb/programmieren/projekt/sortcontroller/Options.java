package de.dhbw.horb.programmieren.projekt.sortcontroller;

import de.dhbw.horb.programmieren.projekt.algorithms.SortAlgorithm;
import de.dhbw.horb.programmieren.projekt.algorithms.ThreadedMergesort;
import de.dhbw.horb.programmieren.projekt.algorithms.ThreadedQuicksort;

/**
 * Diese Klasse verwaltet die Eingaben des Anwenders
 * @author itmetzr
 *
 */
public class Options {

	public enum InputType {MANUALLY, FILE, RANDOM};
	
	private InputType inputType;
	private SortAlgorithm algorithm;
	private int numberOfThreads;
	private boolean animation;
	
	private String manualInput;
	private String filePath;
	private int lowerLimit, upperLimit, countNumbers, repNumber, delay;
	
	/**
	 * Erzeugt ein Objekt des entsprechenden Algorithmus
	 * @param algorithm
	 */
	public void setAlgorithm(String algorithm) {
		if(algorithm=="Quicksort") this.algorithm = new ThreadedQuicksort();
		else this.algorithm = new ThreadedMergesort();
	}
	
	/**
	 * Schreibt die manuelle Eingabe in das Options Objekt. Wirft Exception bei leerem String.
	 * @param manualInput
	 * @throws IncorrectInputException
	 */
	public void setManualInput(String manualInput) throws IncorrectInputException {
		if(manualInput.equals("")) throw new IncorrectInputException("Manuelle Eingabe ist leer!\n");
		else this.manualInput = manualInput;
	}
	
	/**
	 * Schreibt den Dateipfad in das Options Objekt. Wirft Exception bei leerem String.
	 * @param filePath
	 * @throws IncorrectInputException
	 */
	public void setFilePath(String filePath) throws IncorrectInputException {
		if(filePath.equals(""))throw new IncorrectInputException("Datei-Pfad ist leer!\n");
		else this.filePath = filePath;
	}
	
	/**
	 * Wandelt die angegebene untere Grenze in einen Integer um und schreibt sie in das Options Objekt. Wirft Exception bei leerem String. 
	 * @param lowerLimit
	 * @throws IncorrectInputException
	 */
	public void setLowerLimit (String lowerLimit) throws IncorrectInputException {
		try {
			this.lowerLimit = Integer.parseInt(lowerLimit);
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Untere Grenze f¸r Zufallszahlen ist falsch!\n");
		}
	}
	
	/**
	 * Wandelt die angegebene obere Grenze in einen Integer um und schreibt sie in das Options Objekt. Wirft Exception bei leerem String. 
	 * @param upperLimit
	 * @throws IncorrectInputException
	 */
	public void setUpperLimit(String upperLimit) throws IncorrectInputException {
		try {
			this.upperLimit = Integer.parseInt(upperLimit);
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Obere Grenze f¸r Zufallszahlen ist falsch!\n");
		}
	}
	
	/**
	 * Wandelt die angegebene Anzahl in einen Integer um und schreibt sie in das Options Objekt. Wirft Exception bei leerem String. 
	 * @param countNumbers
	 * @throws IncorrectInputException
	 */
	public void setCountNumbers(String countNumbers) throws IncorrectInputException {
		try {
			this.countNumbers = Integer.parseInt(countNumbers);
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Anzahl der Zufallszahlen ist falsch!\n");
		}
	}
	
	/**
	 * Wandelt die angegebene Verzˆgerung in einen Integer um und schreibt sie in das Options Objekt. Wirft Exception bei einer Zahl von ¸ber 1000. 
	 * @param delay
	 * @throws IncorrectInputException
	 */
	public void setDelay(String delay) throws IncorrectInputException {
		try {
			this.delay = Integer.parseInt(delay);
			if(this.delay > 1000) throw new IncorrectInputException ("Verzˆgerung ist zu groﬂ! (Maximum: 1000)\n");
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Verzˆgerung ist falsch!\n");
		}
	}
	
	/**
	 * Wandelt die angegebene Anzahl an Durchl‰ufen in einen Integer um und schreibt sie in das Options Objekt. Wirft Exception bei einer Zahl von ¸ber 1000. 
	 * @param runs
	 * @throws IncorrectInputException
	 */
	public void setRuns(String runs) throws IncorrectInputException {
		try {
			this.repNumber = Integer.parseInt(runs);
			if(this.repNumber > 10000) throw new IncorrectInputException ("Anzahl der Durchf¸hrungen ist zu groﬂ! (Maximum: 10000)\n");
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Anzahl der Durchf¸hrungen ist falsch!\n");
		}
	}
	
	/**
	 * Wandelt die angegebene Anzahl an Threads in einen Integer um und schreibt sie in das Options Objekt. Wirft Exception bei einer Zahl von ¸ber 128. 
	 * @param threadNumber
	 * @throws IncorrectInputException
	 */
	public void setThreadNumber(String threadNumber) throws IncorrectInputException {
		try {
			this.numberOfThreads = Integer.parseInt(threadNumber);
			if(this.numberOfThreads > 128) throw new IncorrectInputException ("Anzahl der Threads ist zu groﬂ! (Maximum: 128)\n");
		} catch (NumberFormatException e) {
			throw new IncorrectInputException("Anzahl der Threads ist falsch!\n");
		}
	}
	
	/**
	 * Diese Klasse ist die Exception bei falschen Eingaben
	 * @author itmetzr
	 *
	 */
	public class IncorrectInputException extends Exception {

		public IncorrectInputException (String s) {
			super (s);
		}
		private static final long serialVersionUID = 4892989218298864067L;
		
	}
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
	public int getNumberOfThreads() {
		return numberOfThreads;
	}
	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}
	public String getManualInput() {
		return manualInput;
	}
	public String getFilePath() {
		return filePath;
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
}