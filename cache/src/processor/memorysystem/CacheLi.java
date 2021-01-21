package processor.memorysystem;
import generic.Event.EventType;
import generic.*;
import processor.Clock;
import configuration.Configuration;
import processor.memorysystem.Cacheline;
import java.util.*;

public class CacheLi implements Element{
	Cacheline line[];
	int no = Configuration.L1i_numberOfLines;
	
	public CacheLi()
	{
		System.out.println("no of lines = "+ no);
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
		System.out.println("Instruction Cache");
		for(int i=0;i<no;i++){
			System.out.println(line[i].tag+"    "+line[i].data);
		}
	}

	@Override
	public void handleEvent(Event e) {
		System.out.println("Event type = "+e.getEventType());
		if(e.getEventType() == EventType.CacheRead)
		{	System.out.println("Handle CacheLI event");
			CacheReadEvent event = (CacheReadEvent) e;
			Simulator.getEventQueue().addEvent(
				new CacheResponseEvent(
					
							Clock.getCurrentTime(),
							this,
							event.getRequestingElement(),
							getWord(event.getAddressToReadFrom()),
							event.getAddressToReadFrom()
						)
				);
		}
	}

}
