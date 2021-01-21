package processor.pipeline;
import java.util.LinkedList;
import java.util.Queue;
public class EX_MA_LatchType {
	
	boolean MA_enable,isld,isst,isrw,isend,MA_busy;
	Queue<Integer> rd_values = new LinkedList<Integer>();
  
	public EX_MA_LatchType()
	{
		MA_enable = false;
		MA_busy = false;
		isrw = true;
		isld = false;
		isend = false;
		isst = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	int alures=0,rd=0;

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
		
	}

	public void setisend(boolean op2) {
		isend=op2;
	}

	public void setisld(boolean isl) {
		isld=isl;
	}

	public void setisst(boolean iss) {
		isst=iss;
	}

	public void setisrw(boolean isr) {
		isrw=isr;
	}

	public void setalures(Integer result) {
		alures=result;
	}

	public void setrd(Integer r) {
		rd=r;
	}

	public boolean getisld() {
		return isld;
	}

	public boolean getisst() {
		return isst;
	}

	public int getalures() {
		return alures;
	}
	
	public boolean getisend() {
		return isend;
	}

	public boolean getisrw() {
		return isrw;
	}

	public int getrd() {
		return rd;
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

	public void setMA_busy(boolean a) {
		MA_busy = a;
	}
	
	public boolean isMA_busy() {
		return MA_busy;
}
}
