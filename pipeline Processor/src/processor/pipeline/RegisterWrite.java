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
	
	public void performRW()
	{
		
		if(MA_RW_Latch.isRW_enable())
		{	
			int res=0;
			Simulator.incrementInstruction();
			
	
			if(MA_RW_Latch.getisrw()){
				if(MA_RW_Latch.getisld()){
					res = MA_RW_Latch.getld();
					//System.out.println("load_res"+load_res);
					//containingProcessor.getRegisterFile().setValue(rd,load_res);
					MA_RW_Latch.setisld(false);
				}
				else {
					res = MA_RW_Latch.getalu();
					//containingProcessor.getRegisterFile().setValue(rd,alu);
				}
				containingProcessor.getRegisterFile().setValue(MA_RW_Latch.getrd(),res);
				MA_RW_Latch.rem_queue();
			}

			//System.out.println("queue reg "+MA_RW_Latch.get_rd_values());
			//System.out.println("load_res"+MA_RW_Latch.getld());
			if(MA_RW_Latch.getld() == -1) MA_RW_Latch.rem_queue();
			System.out.println("queue at registerwrite"+MA_RW_Latch.get_rd_values());
			if(MA_RW_Latch.getisend()==true) {
				Simulator.setSimulationComplete(true);
			}
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
		//System.out.println("Write Donee");
	}

}
