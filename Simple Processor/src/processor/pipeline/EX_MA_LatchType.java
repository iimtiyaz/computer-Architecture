package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	boolean isld=false,isst=false,isrw=true,isend=false;
	int alures=0,op2=0;
	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;

	}

	public void setisend(boolean op2) {
		this.isend=op2;
	}

	public void setis(boolean isld,boolean isst,boolean isrw) {
		this.isst=isst;
		this.isld=isld;
		this.isrw=isrw;
	}

	public void setalures(Integer result) {
		this.alures=result;
	}

	public void setop2(Integer op2) {
		this.op2=op2;
	}

	public boolean getisld() {
		return this.isld;
	}

	public boolean getisst() {
		return this.isst;
	}

	public int getalures() {
		return this.alures;
	}
	
	public boolean getisend() {
		return this.isend;
	}

	public boolean getisrw() {
		return this.isrw;
	}

	public int getop2() {
		return this.op2;
	}
	
}
