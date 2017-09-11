package de.dhbw.horb.programmieren.projekt.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.dhbw.horb.programmieren.projekt.sorting.ArrayGenerator;

public class FileReaderWriter {

	public int[] readFile (String filepath) {
		List<String> content;
		int[] result = null;
		try {
			content = Files.readAllLines(Paths.get(filepath));
			ArrayList<String> strings = new ArrayList<>();
			for(String s : content) {
				strings.addAll(Arrays.asList(s.split(",")));
			}
			result = new int[strings.size()];
			for(int i = 0; i < strings.size(); i++) {
				result[i] = Integer.parseInt(strings.get(i));
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	public void writeFile(String filepath, int[] arr) {
		LinkedList<String> toWrite = new LinkedList<>();
		String currentString = new String();
		for(int i=0; i < arr.length;i++) {
			if(i != 0 && i%20 == 0 ) {
				toWrite.add(currentString);
				currentString=new String();
				currentString += String.valueOf(arr[i]) + ",";
			} else {
				currentString += String.valueOf(arr[i]) + ",";
			}
		}
		if(currentString!=null) {
			toWrite.add(currentString);
		}
		if(!Files.exists(Paths.get(filepath))) {
			try {
				Files.createFile(Paths.get(filepath));
				Files.write(Paths.get(filepath),toWrite);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main (String[] args) {
//		int[] test = new int[65];
//		for(int i = 0; i < test.length; i++)  {
//			test[i] = i;
//		}
//		new IOController().writeFile("testfile.txt", test);
//		int[] arr = new FileReaderWriter().readFile("testfile.txt");
//		System.out.println(Arrays.toString(arr));
		ArrayGenerator generator = new ArrayGenerator();
		int[] arr = generator.randomArray(1, Integer.MAX_VALUE, 10000);
		FileReaderWriter writer = new FileReaderWriter();
		writer.writeFile("C:/users/itmetzr/desktop/test10k.txt", arr);
	}
}
