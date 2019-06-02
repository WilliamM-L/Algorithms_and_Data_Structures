
public class Stack<S> {
	private int pointer;
	private Object[] stack;
	
	
	public Stack() {
		super();
		pointer = 0;
		stack = new Object[10];
	}
	//this does not remove the element on top of the stack
	public S top() {
		return (S)stack[pointer];
	}
	
	public void push(S s) {
		//the array will be copied to a new one if need be, the size is incremented, not multiplied
		if(pointer == stack.length-1)
			increaseStackSpace();
		//if the pointer points to a null entry, the stack is empty, the pointer is not incremented
		if (stack[pointer] == null) {
			stack[pointer] = s;
		} else{
			//otherwise, the pointer should be incremented
			stack[++pointer] = s;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public S pop() {
		Object result;
		if(pointer>0) {
			result = stack[pointer];
			pointer--;
			return (S) result;
		}
		//if we get here the pointer is 0, must manually remove the item since the pointer shouldn't be negative
		result = (S)stack[pointer];
		stack[0] = null;
		return (S) result;
	}
	
	private void increaseStackSpace() {
		Object[] replacement = new Object[stack.length + 5];
		for (int i = 0; i < stack.length; i++) {
			replacement[i] = stack[i];
		}
		stack = replacement;
	}
	
	public int size() {
		if (pointer!=0) {
			return pointer+1;
		} else {
			//if the pointer is at 0, must check if the position contains something
			if (stack[pointer] ==null) {
				return 0;
			} else {
				return 1;
			}
		}
	}
	//printing the stack for debugging purposes
	public String toString() {
		if (stack[pointer] == null)
			return "Empty!";
		String s ="[";
		for (int i=0; i<stack.length; i++) {
			if (i==pointer) {
				s+=""+stack[i]+"]";
				break;
			}
			s+=""+stack[i]+", ";
		}
		return s;
	}
	

}
