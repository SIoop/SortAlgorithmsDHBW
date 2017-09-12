package de.dhbw.horb.programmieren.projekt.controller;

import de.dhbw.horb.programmieren.projekt.algorithms.SortAlgorithm;
import de.dhbw.horb.programmieren.projekt.algorithms.Quicksort;
import de.dhbw.horb.programmieren.projekt.algorithms.Mergesort;

/**
 * Bildet einen Wrapper um die zu benutzende Algorithmusklasse.
 * Hier werden die spezifischen Implementierungen hinterlegt.
 * @author Alexander Lepper
 *
 */
public enum Algorithm {
	

	QUICKSORT (Quicksort.class), MERGESORT (Mergesort.class), UNDEFINED (Quicksort.class);
	
	Class<? extends SortAlgorithm> algo;
	
	private Algorithm (Class<? extends SortAlgorithm> algo) {
		
		this.algo = algo;
	}

	/**
	 * 
	 * @return Neue Instanz des dargestellten Algorithmus
	 * @throws InstantiationException Fehler bei der Instanzierung
	 * @throws IllegalAccessException Kein Zugriff auf Klasse
	 */
	public SortAlgorithm getAlgorithmImplementation() throws InstantiationException, IllegalAccessException {
		SortAlgorithm algorithmImplementation = algo.newInstance();
		return algorithmImplementation;
	}
}
