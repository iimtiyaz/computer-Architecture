package processor.pipeline;
import java.util.LinkedList;
import java.util.Queue;
public class MA_RW_LatchType {
	
	boolean isld,isend,RW_enable,isrw;
	int ld,alu,rd;
	Queue<Integer> rd_values = new LinkedList<Integer>();
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
		isrw = true;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setisend(boolean end) {
		isend=end;
	}
  
	public void setrd(Integer r) {
		rd=r;
	}

	public void setisld(boolean load) {
		isld=load;
	}

	public void setisrw(boolean rw) {
		isrw=rw;
	}
	
	public void setld(Integer load) {
		ld=load;
	}

	public void setalu(Integer al) {
		alu=al;
	}

	public int getalu() {
		return alu;
	}
	
	public int getld() {
		return ld;
	}
	
	public int getrd() {
		return rd;
	}
	
	public boolean getisld() {
		return isld;
	}
	
	public boolean getisend() {
		return isend;
	}

	public boolean getisrw() {
		return isrw;
	}

	public void add_dest_reg(int reg_dest){
		rd_values.add(reg_dest);
	}

	public Queue<Integer> get_rd_values(){
		return rd_values;
	}

	public void setqueue(Queue<Integer> rd){
		rd_values=rd;
	}

	public void rem_queue(){
		rd_values.remove();
	}
	
}
