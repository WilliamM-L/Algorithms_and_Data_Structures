package A3;

public class Heap {
	public boolean isMax;
	private int size;
	private int[][] heap;
	//heap is of the form [[key,value],[key,value],[key,value],...]
	
	//when accessing heap nodes, it is assumed that the user is only interested in the value of the node, not its key
	
	public Heap(boolean isMax) {
		this.isMax = isMax;
		//the entries are not initialized
		heap = new int[3][];
		size = 0;
	}
	
	public void extendHeap() {
		//the new heap will be twice the size of the old heap
		int[][] replacement = new int[heap.length*2][2];
		for (int i = 0; i < heap.length; i++) {
			replacement[i] = heap[i];
		}
		heap = replacement;
	}
	
	//this does not change the heap structure, it returns the value
	public int[] top() {
		return heap[0];
	}
	
	//after removing the top, the tree must be reconstructed
	public int[] removeTop()	{
		return remove(0);
	}
	
	public int[] remove(int index) {
		int[] result = heap[index].clone();
		heap[index] = null;
		reconstruct();
		size--;
		return result;
	}
	
	public void reconstruct() {
		
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public String state() {
		if (isMax) {
			return "This heap is a Max-Heap!";
		}
		
		return "This heap is a Min-Heap!";
	}
	
	public void toggle() {
		isMax = !isMax;
		reconstruct();
	}
	
	
	
	
	
		

}
