package de.dhbw.horb.programmieren.projekt.sortcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import de.dhbw.horb.programmieren.projekt.sortcontroller.UserInput.InputType;

public class SortController {

	UserInput options;
	int[] currentArr;
	private ArrayList<SortObserver> observers = new ArrayList<>();

	public SortController(UserInput options) {

		this.options = options;
	}

	public void addObserver(SortObserver obs) {
		observers.add(obs);
	}

	private void notifySortIsDone(int[] arr, long time) {
		for (SortObserver obs : observers) {
			obs.sortIsDone(arr, time);
		}
	}
	
	private void notifyArrayIsGenerated(int[] arr) {
		for (SortObserver obs : observers) {
			obs.arrayIsGenerated(arr);
		}
	}

	public void start() {

		if (options.getInputType() == InputType.MANUALLY) {

			int[] inputArray = stringToArray(options.getManualInput());
			notifyArrayIsGenerated(inputArray);
			startAlgorithm(inputArray);
		}

		if (options.getInputType()== InputType.RANDOM) {

			int[] randomArray = generate(options.getUpperLimit(), options.getLowerLimit(), options.getCountNumbers());
			currentArr = randomArray;
			notifyArrayIsGenerated(randomArray);
			startAlgorithm(randomArray);
		}
		if (options.getInputType()== InputType.FILE) {
			IOController FilePathReader = new IOController();
			int[] inputArray = FilePathReader.readFile(options.getFilePath());
			notifyArrayIsGenerated(inputArray);
			startAlgorithm(inputArray);
		}
	}
	
	public void cancelSorting () {
		
		options.getAlgorithm().cancel();
	}
	
	public void startAlgorithm(int[] inputArray) {
		
		long timesSum = 0;
		int[] toSort = null;
		for(int i = 0; i < options.getRepNumber(); i++) {
			toSort = Arrays.copyOf(inputArray, inputArray.length);
			currentArr = toSort;
			if(options.getNumberOfThreads()==1) {
				long startTime = System.nanoTime();
				options.getAlgorithm().startSingleThreaded(toSort, options.getDelay());
				timesSum += System.nanoTime()-startTime;
			} else {
				long startTime = System.nanoTime();
				options.getAlgorithm().startMultiThreaded(toSort, options.getNumberOfThreads(), options.getDelay());
				try {
					options.getAlgorithm().waitForEnd();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} catch (CancellationException e) {
					
				}
				timesSum += System.nanoTime()-startTime;
			}
		}
		notifySortIsDone(toSort, timesSum/options.getRepNumber());
	}
	
	public static int[]stringToArray(String manualInput){
		String[] array = manualInput.split(",");
			
			int[] manualArrayInput = new int[array.length];
			for (int i = 0; i < array.length; i++) {
			    try {
			         manualArrayInput[i] = Integer.parseInt(array[i]);
			    } catch (NumberFormatException nfe) {};
			}
			return manualArrayInput;
			
	}
	
	public static int[] generate(int upper, int lower, int count) {
		int[] inputArray = new int[count];
		for (int o = 0; o < inputArray.length; o++) {
			Random p = new Random();
			int Low = lower;
			int High = upper;
			int Result = p.nextInt((High + 1) - Low) + Low;
			inputArray[o] = Result;
		}
		return inputArray;
	}

	public int[] getCurrentArr() {
		return currentArr;
	}
}