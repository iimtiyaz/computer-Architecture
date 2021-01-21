package processor.pipeline;
import generic.Simulator;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_EnableLatchType IF_EnableLatch;

	 
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch,IF_OF_LatchType iF_OF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
	}

	public void performEX()
	{
		//System.out.println("op3 "+OF_EX_Latch.isEX_enable());
		if(OF_EX_Latch.isEX_enable()){
			int o1=OF_EX_Latch.getop1();
			int o2=OF_EX_Latch.getop2();
			int rd=OF_EX_Latch.getrd();
			int a=-1;
			String opcode=OF_EX_Latch.getopcode();
			EX_MA_Latch.setisrw(true);
			EX_MA_Latch.setrd(rd);
			int control_haz=0;
			System.out.println("opcode "+opcode);
			
			if(opcode.equals("00000") || opcode.equals("00001")) {a=o1+o2;}
			else if(opcode.equals("00010") || opcode.equals("00011")) {a=o1-o2;}
			else if(opcode.equals("00100") || opcode.equals("00101")) {a=o1*o2;}
			else if(opcode.equals("00110") || opcode.equals("00111")){
				a=o1/o2;
				int rem=o1%o2;
				containingProcessor.getRegisterFile().setValue(31,rem);
			}
			else if(opcode.equals("01000") || opcode.equals("01001")) {a=o1&o2;}
			else if(opcode.equals("01010") || opcode.equals("01011")) {a=o1|o2;}
			else if(opcode.equals("01100") || opcode.equals("01101")) {a=o1^o2;}
			else if(opcode.equals("01110") || opcode.equals("01111")){
				if(o1<o2) a=1;
				else a=0;
			}
			else if(opcode.equals("10000") || opcode.equals("10001")) {a=o1<<o2;}
			else if(opcode.equals("10010") || opcode.equals("10011")) {a=o1>>>o2;}
			else if(opcode.equals("10100") || opcode.equals("10101")) {a=o1>>o2;}
			else if(opcode.equals("10110")) {
				a=o1+o2;
				EX_MA_Latch.setisld(true);
			}
			else if(opcode.equals("10111")){
				a=rd+o2;
				EX_MA_Latch.setisst(true);
				EX_MA_Latch.setisrw(false);
				EX_MA_Latch.setrd(o1);
			}
			else if(opcode.equals("11000")) {
				EX_IF_Latch.setbranch_taken(true);
				EX_IF_Latch.setbranchpc(rd);
				control_haz=1;
				EX_MA_Latch.setisrw(false);
			}
			else if(opcode.equals("11001")){
				if(o1==o2){
					EX_IF_Latch.setbranch_taken(true);
					control_haz=1;
					EX_IF_Latch.setbranchpc(rd);
				}
				EX_MA_Latch.setisrw(false);
			}
			else if(opcode.equals("11010")){
				if(o1!=o2){
					EX_IF_Latch.setbranch_taken(true);
					EX_IF_Latch.setbranchpc(rd);
					control_haz=1;
				}
				EX_MA_Latch.setisrw(false);
			}
			else if(opcode.equals("11011")){
				if(o1<o2){
					EX_IF_Latch.setbranch_taken(true);
					EX_IF_Latch.setbranchpc(rd);
					control_haz=1;
				}
				EX_MA_Latch.setisrw(false);
			}
			else if(opcode.equals("11100")){
				if(o1>o2){
					EX_IF_Latch.setbranch_taken(true);
					EX_IF_Latch.setbranchpc(rd);
					control_haz=1;
				}
				EX_MA_Latch.setisrw(false);
			}
			else if(opcode.equals("11101")){
				EX_MA_Latch.setisend(true);
				EX_MA_Latch.setisrw(false);
			}
			else System.out.println("not an instruction");

			
			EX_MA_Latch.setalures(a);
			EX_MA_Latch.setqueue(OF_EX_Latch.get_rd_values());
			EX_MA_Latch.setMA_enable(true);
			OF_EX_Latch.setEX_enable(false);

			if(control_haz==1){
                                System.out.println(opcode);
				System.out.println("control hazard");
				Simulator.incrementwrongpaths();
				IF_OF_Latch.set_iscomplete(0);
				IF_OF_Latch.setOF_enable(false);
			}
			
		}
		//System.out.println("Execute Done");
	}
}
