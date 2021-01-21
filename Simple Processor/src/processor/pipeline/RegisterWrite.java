package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	int alu,load,op2;
	boolean isload;

	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{	
			
			op2=MA_RW_Latch.getop2();
			alu=MA_RW_Latch.getalu();
			isload=MA_RW_Latch.getisld();
	
			if(isload != true) containingProcessor.getRegisterFile().setValue(op2,alu);
			else {
				load = MA_RW_Latch.getld();
				containingProcessor.getRegisterFile().setValue(op2,load);
			}

			if(MA_RW_Latch.getisend()==true) {
				
				containingProcessor.getRegisterFile().setValue(0, 0);
				containingProcessor.getRegisterFile().setValue(1, 65535);
				containingProcessor.getRegisterFile().setValue(2, 65535);
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter());
				Simulator.setSimulationComplete(true);
			}
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
		//System.out.println("Write Donee");
	}

}
