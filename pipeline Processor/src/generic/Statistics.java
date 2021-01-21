package generic;

import java.io.PrintWriter;

public class Statistics {
	
	public
	static int numberOfInstructions;
	static int numberOfCycles;
	static int no_of_stall;
	static int no_of_wrongpath;
	
 
	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Number of times the instructions were stalled = "+ no_of_stall);
			writer.println("Number of times the instruction on wrong branch path entered = "+ no_of_wrongpath);
			
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	
	public int getNumberOfInstructions(){
		return Statistics.numberOfInstructions;
	}
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public static void setNumberOfstall(int numberOfStalls) {
		Statistics.no_of_stall = numberOfStalls;
	}

	public static void setNumberOfwrongpaths(int numberOfWrongPaths) {
		Statistics.no_of_wrongpath = numberOfWrongPaths;
	}
}
