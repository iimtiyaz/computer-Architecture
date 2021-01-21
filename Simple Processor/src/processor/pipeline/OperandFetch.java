package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	String ins,opcode,imm1,btg,rs1,rs2,rd1,rd2,rd3,ra,sign;
	int o1,o2,imm,bt,f=1;
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			int in = IF_OF_Latch.getInstruction();
			ins = Integer.toBinaryString(in);
			while(true){
				if(ins.length()==32) break;
				else ins = "0" + ins;
			}

			opcode = ins.substring(0,5);
			rs1=ins.substring(5,10);
			rs2=ins.substring(10,15);
			//System.out.print("opcode");//ref
			//System.out.println(opcode);//ref
			//System.out.println(rs1);//ref
			//System.out.println(rs2);//ref
			rd1=ins.substring(15,20);
			imm1=ins.substring(15,32);

			rd2=rs2;
			o1=containingProcessor.getRegisterFile().getValue(Integer.parseInt(rs1,2));

			btg=ins.substring(10,32);
			sign=btg.substring(0,1);
			for(int i=0;i<10;i++) btg=sign+btg;
			//System.out.print("btg");//ref
			//System.out.println(btg);//ref
			//System.out.println(sign);//ref
			if(sign.equals("1")){
				String s="";
				for(int i=0;i<btg.length();i++){
					if(btg.charAt(i)=='1') s=s+'0';
					else s=s+'1';
				}
				bt = (-1*Integer.parseInt(s,2))-1;
			}
			else {
				//System.out.println("binary to integer imm value");//ref
				bt=Integer.parseInt(btg,2);
			}
			
			if(imm1.charAt(0) == '1'){
				String s="";
				for(int i=0;i<imm1.length();i++){
					if(imm1.charAt(i)=='1') s=s+'0';
					else s=s+'1';
				}
				imm = -1*Integer.parseInt(s,2)-1;
			}
			else{
				imm1 = "000000000000000" + imm1;
				imm=Integer.parseInt(imm1,2);
			}

			if(opcode.equals("00001")||opcode.equals("00011")||opcode.equals("00101")||opcode.equals("00111")||opcode.equals("01001")||opcode.equals("010011")||opcode.equals("01101")||opcode.equals("01111")||opcode.equals("10001")||opcode.equals("10011")||opcode.equals("10101")) {
				f=0;
			}
		
		    	if(opcode.equals("10111")||opcode.equals("10110")||opcode.equals("11001")||opcode.equals("11010")||opcode.equals("11011")||opcode.equals("11100")||f==0){
		    		o2=Integer.parseInt(rd2,2);
				OF_EX_Latch.setistype(false);
                         //System.out.println("first o2"+o2);//ref
		    		if(opcode.equals("10111")||opcode.equals("11001")||opcode.equals("11010")||opcode.equals("11011")||opcode.equals("11100")) {
		    			o2=containingProcessor.getRegisterFile().getValue(Integer.parseInt(rd2,2));
					//System.out.print("type_2_rs2_value ");//ref
					//System.out.println(o2);//ref
		    		}
		    	}
		    	if(opcode.equals("00010")||opcode.equals("00000")||opcode.equals("00100")||opcode.equals("00110")||opcode.equals("01000")||opcode.equals("01010")||opcode.equals("01100")||opcode.equals("01110")||opcode.equals("10000")||opcode.equals("10010")||opcode.equals("10100")){
		    		o2=containingProcessor.getRegisterFile().getValue(Integer.parseInt(rs2,2));
				//System.out.println("type_3_rs2");//ref
				//System.out.println(o2);//ref
		    		OF_EX_Latch.setrd(Integer.parseInt(rd1,2));
		    		OF_EX_Latch.setistype(true);
		    	}

			//System.out.println("Operand done");//ref
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
			OF_EX_Latch.setopcode(opcode);
			//System.out.println("operand1");//ref
			//System.out.println(o1);//ref
			//System.out.println("operand2");//ref
			//System.out.println(o2);//ref
			OF_EX_Latch.setop1(o1);
			OF_EX_Latch.setop2(o2);
			OF_EX_Latch.setimm(imm);
			OF_EX_Latch.setbt(bt);
			
		}
	}

}
