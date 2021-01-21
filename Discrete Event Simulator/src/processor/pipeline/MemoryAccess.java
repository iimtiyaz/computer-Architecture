package processor.pipeline;
import generic.Simulator;
import processor.Processor;
import generic.*;
import configuration.Configuration;
import processor.Clock;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.StoreEvent;
import generic.Event.EventType;
 
public class MemoryAccess implements Element{
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch,IF_EnableLatchType if_enableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = if_enableLatch;
	}
	
	

	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable()){
			System.out.println("-----I am in MA Stage-----");

			if(EX_MA_Latch.isMA_busy()){
             System.out.println("ma is busy");
				return;
			}

			int load_res=0;
			//System.out.println("MA queue values "+EX_MA_Latch.get_rd_values());
			if(EX_MA_Latch.getisld()) {
				Simulator.getEventQueue().addEvent(
					new MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this,
							containingProcessor.getMainMemory(),
							EX_MA_Latch.getalures()
						)
				);
				EX_MA_Latch.setMA_busy(true);
				EX_MA_Latch.setisld(false);
				MA_RW_Latch.setisld(true);
			}

			else if(EX_MA_Latch.getisst()) {
				
				System.out.println("EX_MA_Latch -alures " + EX_MA_Latch.getalures());
				System.out.println("EX_MA_Latch -rd " + EX_MA_Latch.getrd());
				Simulator.getEventQueue().addEvent(
						new MemoryWriteEvent (
									Clock.getCurrentTime()+Configuration.mainMemoryLatency,
									this,
									containingProcessor.getMainMemory(),
									EX_MA_Latch.getalures(),
									EX_MA_Latch.getrd()
									)					
				);
				EX_MA_Latch.setMA_busy(true);
				//EX_MA_Latch.setMA_enable(false);
				load_res=-1;
				EX_MA_Latch.setisst(false);
			}
			else {
				load_res=0;
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
				EX_MA_Latch.setMA_busy(false);
				MA_RW_Latch.setisrw(EX_MA_Latch.getisrw());
				MA_RW_Latch.setrd(EX_MA_Latch.getrd());
				MA_RW_Latch.setisend(EX_MA_Latch.getisend());
				MA_RW_Latch.setqueue(EX_MA_Latch.get_rd_values());
				MA_RW_Latch.setalu(EX_MA_Latch.getalures());
			}
		
			MA_RW_Latch.setld(load_res);	
			
		}
		//System.out.println("Memory Done");
	}
	
	@Override
	public void handleEvent(Event e)
	{
		if(MA_RW_Latch.isRW_busy()){
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else {	
			if(e.getEventType() == EventType.MemoryResponse){
				
				EX_MA_Latch.setMA_busy(false);
				MemoryResponseEvent event = (MemoryResponseEvent) e;
				MA_RW_Latch.setld(event.getValue());
				//MA_RW_Latch.setld(load_res);
				MA_RW_Latch.setisrw(EX_MA_Latch.getisrw());
				MA_RW_Latch.setrd(EX_MA_Latch.getrd());
				MA_RW_Latch.setisend(EX_MA_Latch.getisend());
				MA_RW_Latch.setqueue(EX_MA_Latch.get_rd_values());
				MA_RW_Latch.setRW_enable(true);
				EX_MA_Latch.setMA_enable(false);
			}
			else if(e.getEventType() == EventType.StoreEvent){
				
				EX_MA_Latch.setMA_busy(false);
				StoreEvent event = (StoreEvent) e;
				//MA_RW_Latch.setld(-1);
				//MA_RW_Latch.setalu(event.getValue());
				MA_RW_Latch.setisrw(EX_MA_Latch.getisrw());
				//MA_RW_Latch.setrd(event.getAddressToWriteTo());
				MA_RW_Latch.setisend(EX_MA_Latch.getisend());
				MA_RW_Latch.setqueue(EX_MA_Latch.get_rd_values());
				MA_RW_Latch.setRW_enable(true);
				EX_MA_Latch.setMA_enable(false);
			}
		}
	}
}
