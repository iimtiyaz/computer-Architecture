package processor.memorysystem;
import generic.Event.EventType;
import generic.*;
import processor.Clock;
import processor.memorysystem.CacheLi;
import generic.Simulator;

public class MainMemory implements Element{
	int[] memory;
	//Processor p = Simulator.processor;
	//static CacheLi c = new CacheLi();
	//static CacheLd d = new CacheLd();
	
	public MainMemory()
	{
		memory = new int[65536];
	}
	
	public int getWord(int address)
	{
		return memory[address];
	}
	
	public void setWord(int address, int value)
	{
		memory[address] = value;
	} 
	
	public String getContentsAsString(int startingAddress, int endingAddress)
	{
		if(startingAddress == endingAddress)
			return "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nMain Memory Contents:\n\n");
		for(int i = startingAddress; i <= endingAddress; i++)
		{
			sb.append(i + "\t\t: " + memory[i] + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public void handleEvent(Event e) {
		
		if(e.getEventType() == EventType.MemoryRead)
		{	
			System.out.println("memory sa Read kar raha hai");
			MemoryReadEvent event = (MemoryReadEvent) e;
			Simulator.getEventQueue().addEvent(
				new MemoryResponseEvent(
					
							Clock.getCurrentTime(),
							this,
							event.getRequestingElement(),
							getWord(event.getAddressToReadFrom()),
							event.getAddressToReadFrom()
						
						)
				);
		}
		else if(e.getEventType() == EventType.MemoryWrite)
		{
			System.out.println("memory ma Write kar raha hai");
			MemoryWriteEvent event = (MemoryWriteEvent) e;
			setWord(event.getAddressToWriteTo(), event.getValue());
			Simulator.getEventQueue().addEvent(
				new StoreEvent(
						Clock.getCurrentTime(),
						this,
						event.getRequestingElement()
					)
				);
		}
		
	}

}
