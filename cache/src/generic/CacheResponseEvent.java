package generic;

public class CacheResponseEvent extends Event {

	int value;
	int pc; 
	
	public CacheResponseEvent(long eventTime, Element requestingElement, Element processingElement, int value,int pc) {
		super(eventTime, EventType.CacheResponse, requestingElement, processingElement);
		this.value = value;
		this.pc = pc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getpc() {
		return pc;
	}

	public void setpc(int pc) {
		this.pc = pc;
	}

}
