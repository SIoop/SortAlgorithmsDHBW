package de.dhbw.horb.programmieren.projekt.algorithms.test;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.dhbw.horb.programmieren.projekt.algorithms.Mergesort;
import de.dhbw.horb.programmieren.projekt.sorting.ArrayGenerator;

@RunWith(JUnit4.class)
public class MergesortTest {

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
		
		Mergesort sort = new Mergesort();
		sort.startSingleThreaded(testArray, 0);
		assertArrayEquals(sortedArray, testArray);
	}
}
