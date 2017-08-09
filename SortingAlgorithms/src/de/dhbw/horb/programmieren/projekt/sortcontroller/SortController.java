package de.dhbw.horb.programmieren.projekt.sortcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import de.dhbw.horb.programmieren.projekt.sortcontroller.Options.InputType;

public class SortController {

	Options options;
	int[] currentArr;
	private ArrayList<SortObserver> observers = new ArrayList<>();

	public SortController(Options options) {

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

			int[] inputArray = StringToArrayConverter.stringToArray(options.getManualInput());
			notifyArrayIsGenerated(inputArray);
			startAlgorithm(inputArray);
		}

		if (options.getInputType()== InputType.RANDOM) {

			int[] randomArray = RandomArrayGenerator.generate(options.getUpperLimit(), options.getLowerLimit(), options.getCountNumbers());
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
				}
				timesSum += System.nanoTime()-startTime;
			}
		}
		notifySortIsDone(toSort, timesSum/options.getRepNumber());
	}

	public int[] getCurrentArr() {
		return currentArr;
	}
}