package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int instruction;
	int complete;
	boolean OF_busy;
	
	public IF_OF_LatchType()
	{
		OF_enable = false;
		OF_busy = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public void setOF_busy(boolean busy){
		OF_busy = busy;
	}

	public boolean isOF_busy() {
		return OF_busy;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int ins) {
		instruction = ins;
	}

	public void set_iscomplete(int comp){
		complete = comp;
	}

	public int iscomplete(){
		return complete;
	}
}
