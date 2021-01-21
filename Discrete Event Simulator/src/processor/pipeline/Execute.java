package processor.pipeline;
import generic.Simulator;
import processor.Processor;
import generic.*;
import configuration.Configuration;
import processor.Clock; 

public class Execute implements Element{
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
			System.out.println("-----I am in EX Stage-----");
			if(OF_EX_Latch.isEX_busy()) {
             System.out.println("ex is busy");
				return;
			}
	
			int o1=OF_EX_Latch.getop1();
			int o2=OF_EX_Latch.getop2();
			int rd=OF_EX_Latch.getrd();
			int a=-1;
			String opcode=OF_EX_Latch.getopcode();
			EX_MA_Latch.setisrw(true);
			EX_MA_Latch.setrd(rd);
			int control_haz=0;
			System.out.println("opcode "+opcode);
			
			if(opcode.equals("00000") || opcode.equals("00001")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1+o2//alu_Result
								)					
				);
				OF_EX_Latch.setEX_busy(true); 
				
			}
			else if(opcode.equals("00010") || opcode.equals("00011")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1-o2//alu_Result
								)					
				);
				OF_EX_Latch.setEX_busy(true); 
			}
			else if(opcode.equals("00100") || opcode.equals("00101")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.multiplier_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1*o2//alu_Result
								)					
				);
				OF_EX_Latch.setEX_busy(true); 
			}
			else if(opcode.equals("00110") || opcode.equals("00111")){
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.divider_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1/o2
								)					
				);
				OF_EX_Latch.setEX_busy(true); 
				int rem=o1%o2;
				containingProcessor.getRegisterFile().setValue(31,rem);
			}
			else if(opcode.equals("01000") || opcode.equals("01001")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1&o2
								)					
				);	
				OF_EX_Latch.setEX_busy(true);		
			}
			else if(opcode.equals("01010") || opcode.equals("01011")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1|o2//alu_Result
								)					
				);
				OF_EX_Latch.setEX_busy(true);
			}
			else if(opcode.equals("01100") || opcode.equals("01101")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1^o2
								)					
				);
				OF_EX_Latch.setEX_busy(true);
			}
			else if(opcode.equals("01110") || opcode.equals("01111")){
				if(o1<o2) {
					Simulator.getEventQueue().addEvent(
							new ExecutionCompleteEvent (
										Clock.getCurrentTime()+Configuration.ALU_latency,
										this,
										containingProcessor.getEXUnit(),
										rd,
										1
									)					
					);
				}
				else {
					Simulator.getEventQueue().addEvent(
							new ExecutionCompleteEvent (
										Clock.getCurrentTime()+Configuration.ALU_latency,
										this,
										containingProcessor.getEXUnit(),
										rd,
										0
									)					
					);
				}
				OF_EX_Latch.setEX_busy(true);
			}
			else if(opcode.equals("10000") || opcode.equals("10001")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1 << o2
								)					
				);
				OF_EX_Latch.setEX_busy(true);
			}
			else if(opcode.equals("10010") || opcode.equals("10011")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1 >>> o2
								)					
				);
				OF_EX_Latch.setEX_busy(true);
			}
			else if(opcode.equals("10100") || opcode.equals("10101")) {
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1 >> o2
								)					
				);
				OF_EX_Latch.setEX_busy(true);
			}
			else if(opcode.equals("10110")) { //load
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rd,
									o1+o2
								)					
				);
				OF_EX_Latch.setEX_busy(true);
				EX_MA_Latch.setisld(true);
			}
			else if(opcode.equals("10111")){ //store
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									o1,
									rd+o2
								)					
				);
				OF_EX_Latch.setEX_busy(true);
				EX_MA_Latch.setisld(false);
				EX_MA_Latch.setisst(true);
				EX_MA_Latch.setisrw(false);
			}
			else if(opcode.equals("11000")) { //jmp
				EX_IF_Latch.setbranch_taken(true);
				EX_IF_Latch.setbranchpc(rd);
				control_haz=1;
				EX_MA_Latch.setisrw(false);
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(true);
			}
			else if(opcode.equals("11001")){ //beq
				if(o1==o2){
					EX_IF_Latch.setbranch_taken(true);
					control_haz=1;
					EX_IF_Latch.setbranchpc(rd);
				}
				EX_MA_Latch.setisrw(false);
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(true);
			}
			else if(opcode.equals("11010")){ //bne
				if(o1!=o2){
					EX_IF_Latch.setbranch_taken(true);
					EX_IF_Latch.setbranchpc(rd);
					control_haz=1;
				}
				EX_MA_Latch.setisrw(false);
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(true);
			}
			else if(opcode.equals("11011")){ //blt
				if(o1<o2){
					EX_IF_Latch.setbranch_taken(true);
					EX_IF_Latch.setbranchpc(rd);
					control_haz=1;
				}
				EX_MA_Latch.setisrw(false);
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(true);
			}
			else if(opcode.equals("11100")){ //bgt
				if(o1>o2){
					EX_IF_Latch.setbranch_taken(true);
					EX_IF_Latch.setbranchpc(rd);
					control_haz=1;
				}
				EX_MA_Latch.setisrw(false);
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(true);
			}
			else if(opcode.equals("11101")){ //end
				EX_MA_Latch.setisend(true);
				EX_MA_Latch.setisrw(false);
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(true);
			}
			else System.out.println("not an instruction");

			if(control_haz==1){
				Simulator.incrementwrongpaths();
				IF_OF_Latch.set_iscomplete(0);
				IF_OF_Latch.setOF_enable(false);
			}
			
		}
	}

	@Override
	public void handleEvent(Event e)
	{
		if(EX_MA_Latch.isMA_busy())
		{
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			OF_EX_Latch.setEX_busy(false);
			ExecutionCompleteEvent event = (ExecutionCompleteEvent) e;
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
			EX_MA_Latch.setrd(event.get_rd());
			//System.out.println("EX queue values "+OF_EX_Latch.get_rd_values());
			EX_MA_Latch.setqueue(OF_EX_Latch.get_rd_values());
			EX_MA_Latch.setalures(event.get_alures());
			
		}
	}

}
