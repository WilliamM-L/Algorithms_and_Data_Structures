package A3;
import java.util.Arrays;
//only used to compare two arrays for equality for better readability

public class Heap {
	public boolean isMax;
	private int size;
	private int[][] heap;
	//heap is of the form [[key,value],[key,value],[key,value],...]
	private static int toRemember;
	private static int foundAt;
	
	//Be sure to review the comparator parameters!
	//This should work since the compare method works differently depending on the type of the heap
	public void reconstruct(int start) {
		int[] bucket = {-1,-1};
		//looking at the parents for a violation of heap structure
		//won't go in if we're at the root
		if (start!=0 && compare(heap[start],heap[(start-1)/2])==-1) {
			bucket = heap[(start-1)/2];
			heap[(start-1)/2] = heap[start];
			heap[start] = bucket;
			reconstruct((start-1)/2);
		} else {
			//looking at the children for a violation of the heap structure
			//won't go in if we're at the leaves
			//(Checking the left child)
			if(2*start+1<size && compare(heap[2*start+1],heap[start])==-1) {
				bucket = heap[2*start+1];
				heap[2*start+1] = heap[start];
				heap[start] = bucket;
				reconstruct(2*start+1);
			}
			//now checking the right child in case there was no match
			if (2*start+2<size && compare(heap[2*start+2],heap[start])==-1) {
				bucket = heap[2*start+2];
				heap[2*start+2] = heap[start];
				heap[start] = bucket;
				reconstruct(2*start+2);
			}
		}
		//once here, there is nothing wrong with the heap anymore
	}
	
	public int[] insert(int k, int v) {
		int[] toAdd = {k,v};
		//might need to extend the array representation of the heap
		if(size == heap.length)
			extendHeap();
		heap[size]= toAdd;
		size++;
		
		return toAdd.clone();
	}
	
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
		int[] result = null;
		if (index != size-1) {
			result = heap[index].clone();
			heap[index] = heap[size-1];
			heap[size-1] = null;
			reconstruct(index);
		} else {
			result = heap[index].clone();
			heap[index] = null;
		}
		
		size--;
		return result;
	}
	
	//here node2 will be taken from the heap
	public int compare(int[] node1, int[] node2) {
		//you might need some <= or >= in here since a node can have children with equal keys
		if (isMax) {
			if(node1[0]>node2[0])
				return -1;
			if(node1[0]<=node2[0])
				return 1;
		}
		//this applies if we're dealing with a min heap
		if(node1[0]<node2[0])
			return -1;
		if(node1[0]>=node2[0])
			return 1;
		System.err.println("A problem occured!");
		return -2;
	}
	
	//since this is a heap, and not a binary search tree, it takes O(n) to find the entry,
	//because we don't know which child of any node is the biggest child node.
	public int replaceKey(int[] e, int k) {
		replacerHelper(e,k,0, true);
		reconstruct(foundAt);
		//the key will be stored in that var
		return toRemember;
	}
	
	public int replaceValue(int[] e, int v) {
		replacerHelper(e,v,0, false);
		//no need to reconstruct the tree, no keys were changed
		
		//the value will be stored in that var
		return toRemember;
	}
	
	public void replacerHelper(int[] e, int kv, int current, boolean wantKey) {
		//it has gone too far
		if(current>size-1)
			return;
		//the desired node was found
		if(Arrays.equals(e, heap[current])) {
			if (wantKey) {
				toRemember = e[0];e[0] = kv;
				foundAt = current;
			} else {
				toRemember = e[1];e[1] = kv;
				foundAt = current;
			}
			return;
		}
		if(compare(e, heap[current])==-1)
			return;//it is useless to continue searching
		//visiting the children of the current node
		replacerHelper(e, kv, current*2+1, wantKey);
		replacerHelper(e, kv, current*2+2, wantKey);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public String state() {
		if (isMax)
			return "This heap is a Max-Heap!";
		return "This heap is a Min-Heap!";
	}
	
	public void toggle() {
		isMax = !isMax;
		reconstruct(0);
	}
	

}
