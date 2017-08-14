package de.dhbw.horb.programmieren.projekt.sortcontroller;

/**
 *
 * @author lamparn
 */
public class StringToArrayConverter {
    public static int[]stringToArray(String manualInput){
		String[] array = manualInput.split(",");
			
			int[] manualArrayInput = new int[array.length];
			for (int i = 0; i < array.length; i++) {
			    try {
			         manualArrayInput[i] = Integer.parseInt(array[i]);
			    } catch (NumberFormatException nfe) {};
			}
			//System.out.println(Arrays.toString(result));
			return manualArrayInput;
			
	}
}
