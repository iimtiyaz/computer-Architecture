package processor.pipeline;


import processor.Processor;

public class InstructionFetch {
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	OF_EX_LatchType OF_EX_Latch;

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
			
			if(EX_IF_Latch.isbranch_taken()) {
				System.out.println("branchtaken");
				
				containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.getbranchpc());
				EX_IF_Latch.setbranch_taken(false);
			}
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
		
			if(newInstruction == -402653184){
				System.out.println("end instruction");
				IF_OF_Latch.set_iscomplete(1);
				IF_OF_Latch.setOF_enable(false);
			}

			IF_OF_Latch.setInstruction(newInstruction);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			IF_OF_Latch.setOF_enable(true);
			//System.out.println("enable "+IF_OF_Latch.isOF_enable());
			EX_IF_Latch.setEX_enable(false);
		}
	}

}
