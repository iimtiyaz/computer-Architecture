package generic;

public class ExecutionCompleteEvent extends Event {

	int rd,alures;
	
	public ExecutionCompleteEvent(long eventTime, Element requestingElement, Element processingElement, int rd, int alures)
	{
		super(eventTime, EventType.ExecutionComplete, requestingElement, processingElement);
		this.rd = rd;
		this.alures = alures;
	}
 
	public int get_rd(){
		return rd;
	}

	public int get_alures(){
		return alures;
	}
}
