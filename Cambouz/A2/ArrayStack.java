/*
 * COMP 352 A2
 * Camil Bouzidi ID: 40099611
 * The arraystack class using dynamic array.
 */
public class ArrayStack<T> {
	Object[] data;
	final int CAP = 10;
	int top=-1;

	public ArrayStack() {
		data = new Object[CAP];
	}
	
	public int size() {
		return top+1;
	}
	
	public boolean isEmpty(){
		if (top==-1)
			return true;
		return false;
	}
	
	public T top() {
		if (this.isEmpty())
			return null;
		return (T) data[top];
	}
	
	public T pop() {
		if (this.isEmpty())
			return null;
		return (T) data[top--];
	}
	
	public boolean push(T t) {
		if (size()==data.length) {
			Object[] newData = new Object[this.size()+1];
			for (int j=0; j<data.length; j++) {
				newData[j] = (T) data[j];
			}
			newData[++top] = (T) t;
			data = newData;
			return true;
		}else {
			data[++top] = (T) t;
			return true;
		}
	}
	
	public String toString() {
		if (this.isEmpty())
			return "Empty stack";
		String s ="[";
		for (int i=0; i<data.length; i++) {
			if (i==top) {
				s+=""+data[i]+"]";
				break;
			}
			s+=""+data[i]+", ";
		}
		return s;
	}
}
