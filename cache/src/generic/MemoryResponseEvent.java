package generic;

public class MemoryResponseEvent extends Event {

	int value;
	int addr;
	
	public MemoryResponseEvent(long eventTime, Element requestingElement, Element processingElement, int value,int addr) {
		super(eventTime, EventType.MemoryResponse, requestingElement, processingElement);
		this.value = value;
		this.addr = addr;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
 
	public int getaddr() {
		return addr;
	}

	public void setaddr(int addr) {
		this.addr = addr;
	}

}
