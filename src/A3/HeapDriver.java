package A3;

public class HeapDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//true = Max Heap false = Min Heap
		Heap h = new Heap(true);
		h.insert(2, 5);
		System.out.println(h);
		h.insert(5, 2);
		System.out.println(h);
		h.insert(1, 6);
		System.out.println(h);
		h.toggle();
		System.out.println(h);
		h.toggle();
		System.out.println(h);
		System.out.println("The size is now " + h.size());
		System.out.println(h.state());
		h.insert(21, 8);
		h.insert(7, 4);
		h.insert(3, 85);
		h.insert(67, 6);
		System.out.println(h);
		int[] sample1 = {7,4,1};
		h.remove(sample1);
		System.out.println(h);
		h.insert(8, 1);
		System.out.println(h);
		int[] sample2 = {21,8,2};
		h.replaceKey(sample2, 7);
		System.out.println(h);
		h.toggle();
		System.out.println(h);
		h.insert(9, 4);
		System.out.println(h);
		h.toggle();
		System.out.println(h);
		h.insert(17, 8);
		h.insert(11, 4);
		h.insert(28, 85);
		h.insert(51, 6);
		System.out.println(h);
		h.toggle();
		System.out.println(h);
		h.removeTop();
		System.out.println(h);
		h.toggle();
		System.out.println(h);

	}

}
