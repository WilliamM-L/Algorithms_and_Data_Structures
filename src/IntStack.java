
public class IntStack {
	private int pointer;
	private int[] stack;
	
	public IntStack(int size) {
		super();
		pointer = 0;
		stack = new int[size];
	}
	
	public IntStack() {
		super();
		pointer = 0;
		stack = new int[10];
	}
	
	public int top() {
		return stack[pointer];
	}
	
	public void push(int p) {
		pointer++;
		stack[pointer] = p;
	}
	

}
