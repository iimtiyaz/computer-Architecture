package processor.pipeline;

public class IF_EnableLatchType {
	
	boolean IF_enable,data_hazard;
	int block;
	
	public IF_EnableLatchType()
	{
		IF_enable = true;
		data_hazard = false;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}

	public void setblock(int bl){
		block = bl;
	}

	public void set_haz(boolean haz){
		data_hazard = haz;
	}

	public int block(){
		return block;
	}

	public boolean isdata_haz(){
		return data_hazard;
	}

}
