package generic;

public class CacheReadDataEvent extends Event {

	int addressToReadFrom;
	 
	public CacheReadDataEvent(long eventTime, Element requestingElement, Element processingElement, int address) {
		super(eventTime, EventType.CacheReadData, requestingElement, processingElement);
		this.addressToReadFrom = address;
	}

	public int getAddressToReadFrom() {
		return addressToReadFrom;
	}

	public void setAddressToReadFrom(int addressToReadFrom) {
		this.addressToReadFrom = addressToReadFrom;
	}
}
