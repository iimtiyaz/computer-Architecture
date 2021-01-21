package generic;

public class CacheResponseDataEvent extends Event {

	int value;
	int pc;
	 
	public CacheResponseDataEvent(long eventTime, Element requestingElement, Element processingElement, int value,int pc) {
		super(eventTime, EventType.CacheResponseData, requestingElement, processingElement);
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
