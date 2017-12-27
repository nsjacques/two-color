/*
 * Noah Jacques
 * 28701431
 * CS 311 - Program 2
 * 
 * This program decides whether or not the graph represented in the file (passed as an argument) is two-colorable.
 * It does this with a TCDecider object and this object's checkTC method.
 * 
 * To run from terminal in the directory:
 * 		>javac TwoColorDriver.java
 * 		>java TwoColorDriver "inputFilenameHere" > "outputFilenameHere"
 * 
 * Run Time:
 * 	On smallgraph:
 * 		real	0m0.139s
 *		user	0m0.110s
 *		sys		0m0.033s
 * 	On largegraph1:
 * 		real	0m0.127s
 *		user	0m0.134s
 *		sys		0m0.050s
 * 	On largegraph2:
 * 		real	0m0.201s
 *		user	0m0.134s
 *		sys		0m0.050s
 * 
 * Run on:
 * Macbook Pro late 2013
 * OS X El Capitan v 10.11.3
 * 2.4 GHz Intel Core i5
 * 8 GB 1600 MHz DDR3
 */

import java.io.*;

public class TwoColorDriver {
	public static void main(String[] args) throws FileNotFoundException{
	
		String filename = "";

		if (args[0].equals("-p") || args[0].equals("--print"))
			filename = args[1];
		else 
			filename = args[0];
		
		TCDecider color = new TCDecider();
		
		color.checkTC(filename);

		if (args[0].equals("-p") || args[0].equals("--print")){
			color.printResult();
		}
		
	}
}