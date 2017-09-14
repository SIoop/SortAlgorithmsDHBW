package de.dhbw.horb.programmieren.projekt.sorting;

import java.util.concurrent.ThreadLocalRandom;

import de.dhbw.horb.programmieren.projekt.io.FileReaderWriter;

/**
 * Der ArrayGenerator generiert entweder ein Zufallszahlen-Arary oder wandelt einen String mit Zahlen und Kommas in ein Array um.
 * Weiterhin benutzt er den {@link FileReaderWriter} um Files einzulesen.
 * @author Nick Lamparter
 *
 */
public class ArrayGenerator {

	public static int[] randomArray(int lowerLimit, int upperLimit, int amount) {

		int[] genArray = new int[amount];

		for (int o = 0; o < genArray.length; o++) {

			int Result = ThreadLocalRandom.current().nextInt(lowerLimit, upperLimit + 1);
			genArray[o] = Result;

		}

		return genArray;
	}

	public static int[] manualArray(String input) {

		String[] array = input.split(",");

		int[] genArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			genArray[i] = Integer.parseInt(array[i]);
		}
		return genArray;
	}
	
	public static int[] fileArray (String filepath) {
		
		return new FileReaderWriter().readFile(filepath);
	}
}
