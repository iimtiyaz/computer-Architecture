package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable,is;
	String opcode;
	int o1,o2,imm,bt,rd;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public void setopcode(String opcode) {
		this.opcode=opcode;
	}

	public void setimm(int imm_value) {
		this.imm=imm_value;
	}

	public void setop2(int operand2) {
		this.o2=operand2;
	}

	public void setrd(int reg_dest) {
		this.rd=reg_dest;
	}

	public void setistype(boolean type) {
		this.is=type;
	}

	public void setop1(int operand1) {
		this.o1=operand1;
	}

	public void setbt(int branchtarget) {
		this.bt=branchtarget;
	}

	public String getopcode() {
		return this.opcode;
	}
	
	public boolean getistype1() {
		return this.is;
	}

	public int getimm() {
		return this.imm;
	}

	public int getrd() {
		return this.rd;
	}

	public int getop1() {
		return this.o1;
	}

	public int getop2() {
		return this.o2;
	}

	public int getbt() {
		return this.bt;
	}
	
}
