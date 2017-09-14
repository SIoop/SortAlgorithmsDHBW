package de.dhbw.horb.programmieren.projekt.algorithms.test;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import de.dhbw.horb.programmieren.projekt.algorithms.Quicksort;
import de.dhbw.horb.programmieren.projekt.sorting.ArrayGenerator;

@RunWith(JUnit4.class)
public class QuicksortTest {

	private int[] testArray;
	private int[] sortedArray;
	
	@Before
	public void setUp() {
		
		testArray = ArrayGenerator.randomArray(Integer.MIN_VALUE, Integer.MAX_VALUE-1, 100000);
		sortedArray = Arrays.copyOf(testArray, testArray.length);
		Arrays.sort(sortedArray);
	}
	
	@Test
	public void test() throws Exception {
		
		Quicksort sort = new Quicksort();
		sort.startSingleThreaded(testArray, 0);
		assertArrayEquals(sortedArray, testArray);
	}
}
