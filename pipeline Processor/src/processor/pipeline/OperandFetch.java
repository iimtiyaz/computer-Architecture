package processor.pipeline;
import generic.Simulator;

import java.util.*;
import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public void performOF()
	{
		
		if(IF_OF_Latch.isOF_enable())
		{
			
			int rs1=-1,rs2=-1,rd=-1,rd1=0,rd2=0;
			int rs1_=-1,rs2_=-1,rd_=-1;
			int in = IF_OF_Latch.getInstruction();
			String ins = Integer.toBinaryString(in);
			while(true){
				if(ins.length()==32) break;
				else ins = "0" + ins;
			}

			String opcode = ins.substring(0,5);
			OF_EX_Latch.setopcode(opcode);
			
			

			if(opcode.equals("00000")||opcode.equals("00010")||opcode.equals("00100")||opcode.equals("00110")||opcode.equals("01000")||opcode.equals("01010")||opcode.equals("01100")||opcode.equals("01110")||opcode.equals("10000")||opcode.equals("10010")||opcode.equals("10100")){
				rs1_= Integer.parseInt(ins.substring(5,10),2);
				rs2_= Integer.parseInt(ins.substring(10,15),2);
				rs1 = containingProcessor.getRegisterFile().getValue(rs1_);
				rs2 = containingProcessor.getRegisterFile().getValue(rs2_);
				rd = Integer.parseInt(ins.substring(15,20),2);
				rd2=1;
				rd1=rd;
			}

			else if(opcode.equals("00001")||opcode.equals("00011")||opcode.equals("00101")||opcode.equals("00111")||opcode.equals("01001")||opcode.equals("010011")||opcode.equals("01101")||opcode.equals("01111")||opcode.equals("10001")||opcode.equals("10011")||opcode.equals("10101")){
				//System.out.println("hey");
				rs1_= Integer.parseInt(ins.substring(5,10),2);
				rs1 = containingProcessor.getRegisterFile().getValue(rs1_);
				rd = Integer.parseInt(ins.substring(10,15),2);
				String imm = ins.substring(15,32);
				if(ins.charAt(15)=='1'){
					imm = imm.replace('0','2');
					imm = imm.replace('1','0');
					imm = imm.replace('2','1');
					rs2 = (Integer.parseInt(imm,2) + 1)*(-1);
				}
				else rs2 = Integer.parseInt(ins.substring(15,32),2);
				rd2=1;
				rd1=rd;
			}
			else if(opcode.equals("10110")) {
				rs1_= Integer.parseInt(ins.substring(5,10),2);
				rs1 = containingProcessor.getRegisterFile().getValue(rs1_);
				rd = Integer.parseInt(ins.substring(10,15),2);
				String imm = ins.substring(15,32);
				if(ins.charAt(15)=='1'){
					imm = imm.replace('0','2');
					imm = imm.replace('1','0');
					imm = imm.replace('2','1');
					rs2 = (Integer.parseInt(imm,2) + 1)*(-1);
				}
				else rs2 = Integer.parseInt(ins.substring(15,32),2);
				rd2=1;
				rd1=rd;
			}
			else if(opcode.equals("10111")){
				rs1_= Integer.parseInt(ins.substring(5,10),2);
				rd_= Integer.parseInt(ins.substring(10,15),2);
				rs1 = containingProcessor.getRegisterFile().getValue(rs1_);
				rd = containingProcessor.getRegisterFile().getValue(rd_);
				String imm = ins.substring(15,32);
				if(ins.charAt(15)=='1'){
					imm = imm.replace('0','2');
					imm = imm.replace('1','0');
					imm = imm.replace('2','1');
					rs2 = (Integer.parseInt(imm,2) + 1)*(-1);
				}
				else 
				     rs2 = Integer.parseInt(ins.substring(15,32),2);
				rd2=1;
				rd1=rd+rs2;
			}

			else if(opcode.equals("11001")||opcode.equals("11010")||opcode.equals("11011")||opcode.equals("11100")){
				rs1_= Integer.parseInt(ins.substring(5,10),2);
				rs2_= Integer.parseInt(ins.substring(10,15),2);
				rs1 = containingProcessor.getRegisterFile().getValue(rs1_);
				rs2 = containingProcessor.getRegisterFile().getValue(rs2_);
				int offset;
				String imm = ins.substring(15,32);
				if(ins.charAt(15)=='1'){
					imm = imm.replace('0','2');
					imm = imm.replace('1','0');
					imm = imm.replace('2','1');
					offset = (Integer.parseInt(imm,2) + 1)*(-1);
				}
				else offset = Integer.parseInt(ins.substring(15,32),2);
				rd = containingProcessor.getRegisterFile().getProgramCounter() + offset-1;
			}
			
			else if(opcode.equals("11000")){
				int offset;
				String imm = ins.substring(10,32);
				if(ins.charAt(10)=='1'){
					imm = imm.replace('0','2');
					imm = imm.replace('1','0');
					imm = imm.replace('2','1');
					offset = (Integer.parseInt(imm,2) + 1)*(-1);
				}
				else offset = Integer.parseInt(ins.substring(10,32),2);
				rd = containingProcessor.getRegisterFile().getProgramCounter() + offset-1;
			}

			


			Queue<Integer> rd_values = OF_EX_Latch.get_rd_values();
			if(rd_values.contains(rs1_) || rd_values.contains(rs2_) || rd_values.contains(rd_)){
                               System.out.println(opcode);
				System.out.println("data hazard");
				OF_EX_Latch.setEX_enable(false);
				IF_EnableLatch.setIF_enable(false);
				Simulator.incrementnoOFstall();
			}
			else{
                               System.out.println(opcode);
				//System.out.println("No hazard.");
				OF_EX_Latch.setEX_enable(true);
				OF_EX_Latch.setop1(rs1);
				OF_EX_Latch.setop2(rs2);
				OF_EX_Latch.setrd(rd);
				if(rd2==1){
					System.out.println("rd1 " +rd1);
					OF_EX_Latch.add_dest_reg(rd1);
					System.out.println("queue "+OF_EX_Latch.get_rd_values());
					rd=0;
					rd_values.peek();
				}
				IF_OF_Latch.setOF_enable(false);
			}
			
		}
	}
}
