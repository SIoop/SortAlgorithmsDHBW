package de.dhbw.horb.programmieren.projekt.sortcontroller;

public interface SortObserver {

	public void sortIsDone(int[] arr, long time);
	public void arrayIsGenerated(int[] arr);
}
