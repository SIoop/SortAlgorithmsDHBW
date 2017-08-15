package de.dhbw.horb.programmieren.projekt.controller;

import de.dhbw.horb.programmieren.projekt.algorithms.SortAlgorithm;
import de.dhbw.horb.programmieren.projekt.algorithms.Quicksort;
import de.dhbw.horb.programmieren.projekt.algorithms.Mergesort;

public enum Algorithm {
	

	QUICKSORT (Quicksort.class), MERGESORT (Mergesort.class), UNDEFINED (Quicksort.class);
	
	Class<? extends SortAlgorithm> algo;
	
	private Algorithm (Class<? extends SortAlgorithm> algo) {
		
		this.algo = algo;
	}

	public SortAlgorithm getAlgorithmImplementation() throws InstantiationException, IllegalAccessException {
		SortAlgorithm algorithmImplementation = algo.newInstance();
		return algorithmImplementation;
	}
}
