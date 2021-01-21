package processor.memorysystem;
import generic.Event.EventType;
import generic.*;
import processor.Clock;
import configuration.Configuration;
import processor.memorysystem.Cacheline;
import java.util.*;

public class CacheLd implements Element{
	Cacheline line[];
	int no = Configuration.L1d_numberOfLines;
	static MainMemory m = new MainMemory();

	public CacheLd()
	{
		line = new Cacheline[no];
		for(int i=0;i<no;i++){
			line[i] = new Cacheline(-1,-1);
		}
	}
	
	public int getWord(int address)
	{
		for(int i=0;i<no;i++){
			if(line[i].tag == address) return line[i].data;
		} 
		return -1;
	}
	
	public void setWord(int address, int value)
	{
		int mod = address % no;
		line[mod].tag = address;
		line[mod].data = value;
	}

	public void printstate(){
		System.out.println("Data Cache");
		for(int i=0;i<no;i++){
			System.out.println(line[i].tag+"    "+line[i].data);
		}
	}


	@Override
	public void handleEvent(Event e) {
		
		if(e.getEventType() == EventType.CacheReadData)
		{	
			System.out.println("Read kar raha hai");
			CacheReadDataEvent event = (CacheReadDataEvent) e;
			System.out.println("getWord = "+getWord(event.getAddressToReadFrom()));
			System.out.println("addressto read from = "+event.getAddressToReadFrom());
			Simulator.getEventQueue().addEvent(
				new CacheResponseDataEvent(
							Clock.getCurrentTime(),
							this,
							event.getRequestingElement(),
							getWord(event.getAddressToReadFrom()),
							event.getAddressToReadFrom()
						)
				);
		}
		else if(e.getEventType() == EventType.CacheWrite){
			CacheWriteEvent event = (CacheWriteEvent) e;
			setWord(event.getAddressToWriteTo(),event.getValue());
			//m.setWord(event.getAddressToWriteTo(),event.getValue());
			Simulator.getEventQueue().addEvent(
				new CacheResponseWriteEvent (
					Clock.getCurrentTime(),
					this,
					event.getRequestingElement(),
					event.getAddressToWriteTo(),
					event.getValue()
				)					
			);
		}
	}
}
