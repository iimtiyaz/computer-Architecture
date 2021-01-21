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
	
	int aluresult,op2,load_res;
	boolean isreadwrite=true,isload=false,isend=false;

	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable()){
			//System.out.println("Enable");//ref
			op2=EX_MA_Latch.getop2();
			aluresult=EX_MA_Latch.getalures();
			isload=EX_MA_Latch.getisld();
			if(EX_MA_Latch.getisld()==true) {
				load_res=containingProcessor.getMainMemory().getWord(aluresult);
			}
			if(EX_MA_Latch.getisst()==true) {
				containingProcessor.getMainMemory().setWord(aluresult,op2);
				IF_EnableLatch.setIF_enable(true);
			}
			isreadwrite=EX_MA_Latch.getisrw();
			isend=EX_MA_Latch.getisend();
			//System.out.println(isend);//ref
		
			EX_MA_Latch.setMA_enable(false);
			if(isreadwrite==true) {
				MA_RW_Latch.setld(load_res);
				MA_RW_Latch.setalu(aluresult);
				MA_RW_Latch.setRW_enable(true);
				MA_RW_Latch.setisld(isload);
				MA_RW_Latch.setop2(op2);
				MA_RW_Latch.setisend(isend);	
			}
		}
		//System.out.println("Memory Done");//ref
	}

}
