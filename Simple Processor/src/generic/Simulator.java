package generic;

import processor.Clock;
import processor.Processor;
import java.io.*;
import processor.pipeline.IF_EnableLatchType;

public class Simulator {
	static IF_EnableLatchType IF_EnableLatch;
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws Exception
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
	}
	
	@SuppressWarnings("resource")
	static void loadProgram(String assemblyProgramFile) throws IOException
	{
		try
		{
			InputStream i = null;
			DataInputStream d = null;
			i = new FileInputStream(assemblyProgramFile);
			d = new DataInputStream(i);
			int c=0;
			while(d.available()>0){
				int read = d.readInt();
				if(c==0) {
					processor.getRegisterFile().setProgramCounter(read);
				}
				else{
					processor.getMainMemory().setWord(c-1,read);
				}
				c = c + 1;
			}
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			processor.getRegisterFile().setValue(2, 65535);
		}
		catch(IOException e)
		{	
			e.printStackTrace();
		}
		
		
	}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
		}
		
		
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
