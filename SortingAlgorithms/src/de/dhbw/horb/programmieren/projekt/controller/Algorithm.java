package de.dhbw.horb.programmieren.projekt.controller;

import de.dhbw.horb.programmieren.projekt.algorithms.SortAlgorithm;
import de.dhbw.horb.programmieren.projekt.algorithms.Quicksort;
import de.dhbw.horb.programmieren.projekt.algorithms.Mergesort;

public enum Algorithm {

	QUICKSORT (Quicksort.class), MERGESORT (Mergesort.class), UNDEFINED (Quicksort.class);
	
	SortAlgorithm algorithmImplementation;
	
	private Algorithm (Class<? extends SortAlgorithm> algo) {
		try {
			algorithmImplementation = algo.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public SortAlgorithm getAlgorithmImplementation() {
		return algorithmImplementation;
	}
}
