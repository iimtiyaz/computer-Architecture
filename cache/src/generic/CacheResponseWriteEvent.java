package generic;

public class CacheResponseWriteEvent extends Event {

	int addressToWriteTo;
	int value;
	 
	public CacheResponseWriteEvent(long eventTime, Element requestingElement, Element processingElement, int address, int value) {
		super(eventTime, EventType.CacheResponseWrite, requestingElement, processingElement);
		this.addressToWriteTo = address;
		this.value = value;
	}

	public int getAddressToWriteTo() {
		return addressToWriteTo;
	}

	public void setAddressToWriteTo(int addressToWriteTo) {
		this.addressToWriteTo = addressToWriteTo;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
