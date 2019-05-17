import java.util.InputMismatchException;
import java.util.Scanner;

public class Tetranacci {
	
	public static void main(String[] args) {
		long input=0;
		Scanner kb = new Scanner(System.in);
		boolean invalid;
		
		System.out.print("Enter what will be the input to the Tetranacci function: ");
		
		do {
			invalid=false;
			try {
			input = kb.nextInt();
			if (input<0)
				invalid=true;
			} catch(InputMismatchException e) {
				invalid = true;
			}
		} while (invalid);
		
		System.out.println("Testing Exp then TR:"  + "\t" + TRTetranacci(0, 0, 0, 1, input));
		
		System.currentTimeMillis();
		
		
	}
	
	public static long ExpTetranacci(long in) {
		//base case when 0<=n<=3
		if (in==0 || in==1 || in==2) 
			return 0;
		if (in==3) 
			return 1;
		//otherwise, exponential recursive calls are made [exponential because many of the calls are redundant]
		return ExpTetranacci(in-4)+ExpTetranacci(in-3)+ExpTetranacci(in-2)+ExpTetranacci(in-1);
		
	}
	
	public static long TRTetranacci(long a, long b, long c, long d, long n) {
		//base cases when 0<=n<=3, which is the final result, only one stack space is needed since the total sum 
		// will either be a, b, c or d.
		if (n==0) 
			return a;
		if (n==1) 
			return b;
		if (n==2) 
			return c;
		if (n==3) 
			return d;
		//Recursive call, simply shift the inputs, and calculate the fourth one by adding all of them[essence of Tetranacci]
		//n is used as a counter, so we know which base case to return
		return TRTetranacci(b, c, d, a+b+c+d, n-1);
	}

}
