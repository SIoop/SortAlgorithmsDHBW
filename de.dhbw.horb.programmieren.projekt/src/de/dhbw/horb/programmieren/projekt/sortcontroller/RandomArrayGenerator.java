package de.dhbw.horb.programmieren.projekt.sortcontroller;

import java.util.Random;

/**
 *
 * @author lamparn
 */
public class RandomArrayGenerator {
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
}
