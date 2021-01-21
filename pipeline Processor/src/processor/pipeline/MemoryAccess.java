package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
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
			int load_res=0;
			if(EX_MA_Latch.getisld()) {
				load_res = containingProcessor.getMainMemory().getWord(EX_MA_Latch.getalures());
				MA_RW_Latch.setisld(true);
				EX_MA_Latch.setisld(false);
			}
			else if(EX_MA_Latch.getisst()) {
				containingProcessor.getMainMemory().setWord(EX_MA_Latch.getalures(),EX_MA_Latch.getrd());
				load_res=-1;
				EX_MA_Latch.setisst(false);
			}
			else load_res=0;
			
		
			MA_RW_Latch.setld(load_res);
			MA_RW_Latch.setalu(EX_MA_Latch.getalures());
			MA_RW_Latch.setisrw(EX_MA_Latch.getisrw());
			MA_RW_Latch.setrd(EX_MA_Latch.getrd());
			MA_RW_Latch.setisend(EX_MA_Latch.getisend());
			MA_RW_Latch.setqueue(EX_MA_Latch.get_rd_values());
			EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setRW_enable(true);	
			
		}
		//System.out.println("Memory Done");
	}

}
