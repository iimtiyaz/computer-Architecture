package processor.pipeline;
import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	EX_MA_LatchType EX_MA_Latch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch,EX_MA_LatchType eX_MA_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.EX_MA_Latch = eX_MA_Latch;
	}
	
	public void performRW()
	{
		//System.out.println("op1 "+MA_RW_Latch.isRW_enable());
		if(MA_RW_Latch.isRW_enable())
		{	
			//System.out.println("-----I am in RW Stage-----");
			if(EX_MA_Latch.isMA_busy()) {
				return;
			}

			int res=0;
			//Simulator.incrementInstruction();
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			//System.out.println("isrw  = "+MA_RW_Latch.getisrw());
			if(MA_RW_Latch.getisrw()){
				//System.out.println("isld  = "+MA_RW_Latch.getisld());
				if(MA_RW_Latch.getisld()){
					res = MA_RW_Latch.getld();
					//System.out.println("load_res "+res);
					///System.out.println("address "+MA_RW_Latch.getrd());
					containingProcessor.getRegisterFile().setValue(MA_RW_Latch.getrd(),res);
					MA_RW_Latch.setisld(false);
				} 
				else {
					res = MA_RW_Latch.getalu();
					containingProcessor.getRegisterFile().setValue(MA_RW_Latch.getrd(),res);
				}
				//containingProcessor.getRegisterFile().setValue(MA_RW_Latch.getrd(),res);
				//System.out.println("RW Queue values "+MA_RW_Latch.get_rd_values());
				MA_RW_Latch.rem_queue();
			}

			//System.out.println("queue reg "+MA_RW_Latch.get_rd_values());
			///System.out.println("load_res"+MA_RW_Latch.getld());
			if(MA_RW_Latch.getld() == -1) MA_RW_Latch.rem_queue();
			//System.out.println("queue reg 11 "+MA_RW_Latch.get_rd_values());
			if(MA_RW_Latch.getisend()==true) {
				Simulator.setSimulationComplete(true);
			}
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
		//System.out.println("Write Donee");
	}

}
