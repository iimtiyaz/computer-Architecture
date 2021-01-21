package processor.pipeline;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	int o1,o2,imm,bt,a;
	IF_EnableLatchType IF_EnableLatch;

	String opcode;
	boolean isld =  false;
	boolean isst =  false;
	boolean isbt =  false;
	boolean isend =  false;
	boolean isrw =  true;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch,IF_EnableLatchType IF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_EnableLatch = IF_EnableLatch;
	}

	public void performEX()
	{
		if(OF_EX_Latch.isEX_enable()){
			o1=OF_EX_Latch.getop1();
			o2=OF_EX_Latch.getop2();
			imm=OF_EX_Latch.getimm();
			bt=OF_EX_Latch.getbt();
			opcode=OF_EX_Latch.getopcode();
			//System.out.println("opcode ");//ref
			//System.out.println(opcode);//ref
			
			isbt=false;
			isld=false;isst=false;isrw=true;isend=false;
			switch(opcode)
			{
			case "00000":
				a=o1+o2;
				break;
			case "00001":
				a=o1+imm;
				break;
			case "00010":
				a=o1-o2;
				break;
			case "00011":
				a=o1-imm;
				break;
			case "00100":
				a=o1*o2;
				break;
			case "00101":
				a=o1*imm;
				break;
			case "00110":
				a=o1/o2;
				int rem=o1%o2;
				containingProcessor.getRegisterFile().setValue(31,rem);
				break;
			case "00111":
				a=o1/imm;
				 rem=o1%imm;
				containingProcessor.getRegisterFile().setValue(31,rem);
				break;
			case "01000":
				a=o1&o2;
				break;
			case"01001":
				a=o1&imm;
				break;
			case "01010":
				 a=o1|o2;
				break;
			case "01011":
				 a=o1|imm;
				break;
			case "01100":
				a=o1^o2;
				break;
			case "01101":
				a=o1^imm;
				break;
			case "01110":
				if(o1<o2) a=1;
				else a=0;
				break;
			case "01111":
				if(o1<imm) a=1;
				else a=0;
				break;
			case "10000":
				a=o1<<o2;
				break;
			case "10001":
				 a=o1<<imm;
				break;
			case "10010":
				 a=o1>>>o2;
				break;
			case "10011":
				 a=o1>>>imm;
				break;
			case "10100":
				a=o1>>o2;
				break;
			case "10101":
				a=o1>>imm;
				break;
			case "10110":
				isld=true;
				a=o1+imm;
				break;
			case "10111":
				isst=true;
				a=o2+imm;
				o2=o1;
				break;
			case "11000":
				isbt=true;
				break;
			case "11001":
				if(o1==o2){
					bt=imm;
					isbt=true;
				}
				break;
			case "11010":
				if(o1!=o2){
					bt=imm;
					isbt=true;
				}
				break;
			case"11011":
				if(o1<o2){
					bt=imm;
					isbt=true;
				}
				break;
			case "11100":
				if(o1>o2){
					bt=imm;
					isbt=true;
				}
				break;
			
			case "11101":
				isend=true;
				break;
				default:
				  System.out.println("something wrong with code");
			}

			//System.out.println("isbt"+isbt);//ref
			//System.out.println("bt"+bt);//ref
			EX_IF_Latch.setbt(isbt,bt);
			//System.out.println("result");//ref
			//System.out.println(a);//ref
			EX_MA_Latch.setalures(a);
			if(OF_EX_Latch.getistype1()) EX_MA_Latch.setop2(OF_EX_Latch.getrd());
			else  EX_MA_Latch.setop2(o2);	

			EX_MA_Latch.setisend(isend);
			//System.out.println("isend ");//ref
			//System.out.println(isend);//ref

			//System.out.println(EX_MA_Latch.getisend());//ref
			if(isbt==true||isst==true) isrw=false;
			EX_MA_Latch.setis(isld,isst,isrw);
			OF_EX_Latch.setEX_enable(false);

			if(isbt==true) {EX_IF_Latch.setIF_enable(true);}
			else if(opcode.equals("11001")||opcode.equals("11010")||opcode.equals("11011")||opcode.equals("11100")) {
				//System.out.println("case2");//ref
				IF_EnableLatch.setIF_enable(true);
			}
			else {EX_MA_Latch.setMA_enable(true);}
		}
		//System.out.println("Execute Done");//ref
	}
}
