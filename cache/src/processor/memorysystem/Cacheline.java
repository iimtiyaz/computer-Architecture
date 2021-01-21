package processor.memorysystem;
import generic.Event.EventType;
import generic.*;
import processor.Clock;
import configuration.Configuration;

public class Cacheline{
	int tag;
	int data;

	public Cacheline(int tag, int data){
		this.tag = tag;
		this.data = data;
	}
}

 