package processor.pipeline;

public class EX_IF_LatchType {
 
	boolean EX_enable,b_taken;
	int b_pc;

	public EX_IF_LatchType()
	{
		EX_enable = false;
		b_taken = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public void setbranch_taken(boolean branch){
		b_taken = branch;
	}

	public void setbranchpc(int branchpc){
		b_pc=branchpc;
	}

	public boolean isbranch_taken(){
		return b_taken;
	}

	public int getbranchpc(){
		return b_pc;
	}

}
