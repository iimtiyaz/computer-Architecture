package processor.pipeline;
import configuration.Configuration;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;
import generic.*;
import generic.Event.EventType;
import processor.memorysystem.CacheLi;

public class InstructionFetch implements Element {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		
		if(IF_EnableLatch.isIF_enable() && (IF_OF_Latch.iscomplete()!=1))
		{
			System.out.println("-----I am in IF Stage-----");
			if(IF_EnableLatch.isIF_busy())
			{
				System.out.println("IF busy");
				return;
			}
			 
			int PC;
			System.out.println("IF Stage is not busy");
			if(EX_IF_Latch.isbranch_taken()) {
				System.out.println("branchtaken");
				PC = EX_IF_Latch.getbranchpc();
				containingProcessor.getRegisterFile().setProgramCounter(PC);
				EX_IF_Latch.setbranch_taken(false);
			}
			System.out.println("Branch is not taken in IF Stage");
			Simulator.getEventQueue().addEvent(
				new  CacheReadEvent(
						Clock.getCurrentTime() + Configuration.L1i_latency,
						this, 
						containingProcessor.getcacheLi(),
						containingProcessor.getRegisterFile().getProgramCounter()
						)
				);
			System.out.println("Adding event in IF Stage done");
			IF_EnableLatch.setIF_busy(true);
			//IF_EnableLatch.setIF_enable(false);
		}
		
	}	
	
	@Override
	public void handleEvent(Event e)
	{
		if(IF_OF_Latch.isOF_busy())
		{
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			System.out.println("Event type = "+e.getEventType());
			if(e.getEventType() == EventType.CacheResponse){
				Simulator.incrementInstruction();
				System.out.println("Hello I am doing cache response");
				CacheResponseEvent event = (CacheResponseEvent) e;
				int value = event.getValue();
				if(value == -1){
					int pc = event.getpc();
					Simulator.getEventQueue().addEvent(
						new  MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this, 
							containingProcessor.getMainMemory(),
							pc
						)
					);
				}
				else{
					//Simulator.incrementInstruction();
					IF_EnableLatch.setIF_busy(false);
					System.out.println("Instruction cache " +value);
					IF_OF_Latch.setInstruction(value);
					int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
					containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
					IF_OF_Latch.setOF_enable(true);
					IF_EnableLatch.setIF_enable(false);
					EX_IF_Latch.setEX_enable(false);
				}
			}
			else if(e.getEventType() == EventType.MemoryResponse){
				System.out.println("I m memory response event");
				//Simulator.incrementInstruction();
				IF_EnableLatch.setIF_busy(false);
				MemoryResponseEvent event = (MemoryResponseEvent) e;
				int ins = event.getValue();
				int addr = event.getaddr();
				CacheLi c = containingProcessor.getcacheLi();
				c.setWord(addr,ins);
				System.out.println("Instruction value "+ ins);
				if(ins == -402653184){
					IF_OF_Latch.set_iscomplete(1);
					IF_OF_Latch.setOF_enable(false);
				}	
		
				System.out.println("Instruction = "+ins);
				IF_OF_Latch.setInstruction(ins);
				int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
				IF_OF_Latch.setOF_enable(true);
				IF_EnableLatch.setIF_enable(false);
				EX_IF_Latch.setEX_enable(false);
			}
		}
			
	}

}

