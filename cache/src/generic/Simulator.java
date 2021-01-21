package generic;
import processor.Clock;
import processor.Processor;
import java.io.*;
import processor.pipeline.IF_EnableLatchType;
import processor.memorysystem.MainMemory;
import generic.Statistics;
import processor.pipeline.RegisterFile;

public class Simulator {
	static IF_EnableLatchType IF_EnableLatch;
	public static Processor processor;
	static boolean simulationComplete;
	static MainMemory r = new MainMemory();
	static	int no_of_instruction=0;
	static int no_of_stall=0;
	static int no_of_wrongpath=0;
	static EventQueue eventQueue;

	public static void setupSimulation(String assemblyProgramFile, Processor p) throws Exception
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
		eventQueue = new EventQueue();
	}
	 
	//@SuppressWarnings("resource")
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
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
				//System.out.println("hello");
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
			///System.out.println("Start processing events");
			eventQueue.processEvents();	
			//System.out.println("Events processed");
			processor.getOFUnit().performOF();		
			processor.getIFUnit().performIF();
			Clock.incrementClock();
		}
		
		// TODO
		// set statistics
		Statistics.setNumberOfInstructions(no_of_instruction);
		Statistics.setNumberOfCycles((int)Clock.getCurrentTime());
		//System.out.println("No. of wrong paths = " + Statistics.no_of_wrongpath);
		//System.out.println("No. of stalls = " + Statistics.no_of_stall);
	}

	public static EventQueue getEventQueue() {
		return eventQueue;
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
