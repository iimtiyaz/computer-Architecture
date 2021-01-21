package processor.pipeline;
import configuration.Configuration;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;
import generic.*;
import generic.Event.EventType;

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
				System.out.println("IF is busy");
				return;
			}
			
			int PC;
			if(EX_IF_Latch.isbranch_taken()) {
				System.out.println("branchtaken");
				PC = EX_IF_Latch.getbranchpc();
				containingProcessor.getRegisterFile().setProgramCounter(PC);
				EX_IF_Latch.setbranch_taken(false);
			}
			Simulator.getEventQueue().addEvent(
				new MemoryReadEvent(
						Clock.getCurrentTime() + Configuration.mainMemoryLatency,
						this, 
						containingProcessor.getMainMemory(),
						containingProcessor.getRegisterFile().getProgramCounter()
						)
				);
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
			Simulator.incrementInstruction();
			IF_EnableLatch.setIF_busy(false);
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			int ins = event.getValue();
			System.out.println("Instruction value "+ ins);
			if(ins == -402653184){
				IF_OF_Latch.set_iscomplete(1);
				IF_OF_Latch.setOF_enable(false);
			}
	
			//System.out.println("Instruction = "+ins);
			IF_OF_Latch.setInstruction(ins);
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_enable(false);
			EX_IF_Latch.setEX_enable(false);
		}
			
	}

}

