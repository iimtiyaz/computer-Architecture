package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean isld,isend,RW_enable;
	int ld,alu,op2;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setisend(boolean end) {
		this.isend=end;
	}

	public void setop2(Integer o2) {
		this.op2=o2;
	}

	public void setisld(boolean load) {
		this.isld=load;
	}
	
	public void setld(Integer load) {
		this.ld=load;
	}

	public void setalu(Integer alu) {
		this.alu=alu;
	}

	public int getalu() {
		return this.alu;
	}
	
	public int getld() {
		return this.ld;
	}
	
	public int getop2() {
		return this.op2;
	}
	
	public boolean getisld() {
		return this.isld;
	}
	
	public boolean getisend() {
		return this.isend;
	}
	
}
