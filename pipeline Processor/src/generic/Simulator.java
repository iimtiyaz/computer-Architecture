package generic;

import processor.Clock;

import processor.Processor;
import java.io.*;
import processor.pipeline.IF_EnableLatchType;
import processor.memorysystem.MainMemory;
import generic.Statistics;


public class Simulator {
	static IF_EnableLatchType IF_EnableLatch;
	static Processor processor;
	static boolean simulationComplete;
	static MainMemory r = new MainMemory();
	static	int no_of_instruction=0;
	static int no_of_stall=0;
	static int no_of_wrongpath=0;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws Exception
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
	}
	
	//@SuppressWarnings("resource")
	static void loadProgram(String assemblyProgramFile)
	{
		
		try{
		InputStream i = null;
		DataInputStream d = null;
		i = new FileInputStream(assemblyProgramFile);
		d = new DataInputStream(i);
		int c=0;
		while(d.available()>0){
			int read = d.readInt();
			System.out.println(read);
			if(c==0) {
				processor.getRegisterFile().setProgramCounter(read);
				
				//System.out.println(processor.getRegisterFile().getProgramCounter());
			}
			else{
				processor.setMainMemory(r);
				r.setWord(c-1,read);
			}
			c = c + 1;
		}
		System.out.println("");
		processor.getRegisterFile().setValue(0, 0);
        	processor.getRegisterFile().setValue(1, 65535);
        	processor.getRegisterFile().setValue(2, 65535);
		}
		catch(Exception e){r.getContentsAsString(0,10);}
	}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();	
			processor.getOFUnit().performOF();		
			processor.getIFUnit().performIF();
			Clock.incrementClock();
		}
		
		// TODO
		// set statistics
		Statistics.setNumberOfInstructions(no_of_instruction);
		Statistics.setNumberOfCycles((int)Clock.getCurrentTime());
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}

	public static void incrementwrongpaths() {
		Statistics.no_of_wrongpath ++;
	}

	public static void incrementnoOFstall() {
		Statistics.no_of_stall ++;
	}
	
	public static void incrementInstruction(){
		no_of_instruction++;

	}


}
