package processor.pipeline;
import java.util.LinkedList;
import java.util.Queue;
public class OF_EX_LatchType {
	
	boolean EX_enable;
	String opcode;
	int o1,o2,rd;
	Queue<Integer> rd_values = new LinkedList<Integer>();
	
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

	public void setopcode(String opcod) {
		opcode=opcod;
	}

	public void setop1(int operand1) {
		o1=operand1;
	}

	public void setop2(int operand2) {
		o2=operand2;
	}

	public void setrd(int reg_dest) {
		rd=reg_dest;
	}

	public String getopcode() {
		return opcode;
	}

	public int getrd() {
		return rd;
	}

	public int getop1() {
		return o1;
	}

	public int getop2() {
		return o2;
	}

	public void add_dest_reg(int reg_dest){
		rd_values.add(reg_dest);
	}
	
	public Queue<Integer> get_rd_values(){
		return rd_values;
	}
}
