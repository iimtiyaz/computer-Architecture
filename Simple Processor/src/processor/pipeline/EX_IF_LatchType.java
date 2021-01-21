package processor.pipeline;

public class EX_IF_LatchType {
	int offset = 0;
	boolean IF_enable,isbt;
	
	public EX_IF_LatchType()
	{
		IF_enable=false;
	}

	public void setIF_enable(boolean if_enable) {
		IF_enable = if_enable;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setbt(boolean ibt,Integer bt) {
		isbt=ibt;
		if(ibt==true) offset=bt;
		else{	
			isbt=false;
			offset=0;
		}
	}
}
