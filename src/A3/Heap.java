package A3;

import java.util.Arrays;
//only used to compare arrays

//only used to compare two arrays for equality for better readability

public class Heap {
	public boolean isMax;
	private int size;
	private int[][] heap;
	//heap is of the form [[key,value,index],[key,value,index],[key,value,index],...]

	//Be sure to review the comparator parameters!
	//This should work since the compare method works differently depending on the type of the heap
	public int reconstruct(int start) {
		//looking at the parents for a violation of heap structure
		//won't go in if we're at the root
		if (heap[start]!=null&&(start!=0 && compare(heap[start],heap[(start-1)/2])==-1)) {
			swapNodes((start-1)/2, start);
			reconstruct((start-1)/2);
		} else {
			//(Checking the left child)
			if(2*start+1<size  && compare(heap[2*start+1],heap[start])==-1) {
				swapNodes(2*start+1, start);
				reconstruct(2*start+1);
			}
			//now checking the right child in case there was no match
			if (2*start+2<size && compare(heap[2*start+2],heap[start])==-1) {
				swapNodes(2*start+2, start);
				reconstruct(2*start+2);
			}
		}
		//once here, there is nothing wrong with the heap anymore
		//the index it's now at is returned in case it is used
		return start;
	}

	private int swapNodes(int n1, int n2) {
		//the indices have to be switched before the nodes are swapped for the indices to stay valid
		int indexBucket = heap[n1][2];
		heap[n1][2] = heap[n2][2];
		heap[n2][2] = indexBucket;
		//The clone must be created after the indices were swapped
		int[] bucket = heap[n1].clone();
		heap[n1] = heap[n2];
		heap[n2] = bucket;
		return indexBucket;
	}

	public int[] insert(int k, int v) {
		int[] toAdd = {k,v,size};
		//might need to extend the array representation of the heap
		if(size == heap.length)
			extendHeap();
		heap[size]= toAdd;
		int newIndex = reconstruct(size);
		size++;
		int[] result = {k,v,newIndex};

		return result;
	}

	public Heap(boolean isMax) {
		this.isMax = isMax;
		//the entries are not initialized
		heap = new int[3][];
		size = 0;
	}

	public void extendHeap() {
		//the new heap will be twice the size of the old heap
		int[][] replacement = new int[heap.length*2][];
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
		return remove(heap[0]);
	}

	public int[] remove(int[] e) {
		int[] result = null;
		//proceed only if the node is valid
		if (e!=null && e[2]<size && Arrays.equals(e, heap[e[2]])) {
			if (e[2] != size-1) {
				//procedure if we're removing a node that's not the last one
				result = heap[e[2]].clone();
				//the target node becomes the last node, and the last node is removed
				int toReconstructAt = swapNodes(e[2], size-1);
				heap[size-1] = null;
				size--;

				reconstruct(toReconstructAt);


			} else {
				//if the last node is wanted, no reconstruction of the heap is required
				result = heap[e[2]].clone();
				heap[e[2]] = null;
				size--;
			}
			//the heap is smaller
		}


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
		if (Arrays.equals(e, heap[e[2]])) {
			int oldKey = e[0];
		//Changing the key of the passed node in the heap
		heap[e[2]][0] = k;
		reconstruct(e[2]);
		//the key will be stored in that var
		return oldKey;
		}
		return -1;
	}

	public int replaceValue(int[] e, int v) {
		//checking if the nodes match
		if (Arrays.equals(e, heap[e[2]])) {
			//no need to reconstruct the tree, no keys were changed
			int oldValue = e[1];
			//Changing the key of the passed node in the heap
			heap[e[2]][1] = v;
			//the value will be stored in that var
			return oldValue;
		}
		return -1;
		
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
		//int[] toReplace = removeTop();
		//insert(toReplace[0],toReplace[1]);
		for (int i = 0; i < size; i++) {
			reconstruct(i);
		}
	}

	//printing only the keys of the tree
	public String toString() {
		String result = "[";
		for (int i = 0; i < size; i++) {
			result+="[";
			result+=heap[i][0]+", " + heap[i][1] +  ", " +heap[i][2];
			//", " + heap[i][1] +
			result+="]";
		}
		return result +="]";
	}


}
