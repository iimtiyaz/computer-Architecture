package generic;

public class StoreEvent extends Event {
	
	public StoreEvent(long eventTime, Element requestingElement, Element processingElement) {
		super(eventTime, EventType.StoreEvent, requestingElement, processingElement);
	}

}
 