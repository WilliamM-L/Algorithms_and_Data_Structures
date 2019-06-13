import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Tetranacci {
	
	public static void main(String[] args) {
		//variables used throughout the program
		long input=0;
		Scanner kb = new Scanner(System.in);
		boolean invalid;
		long start;
		double TRinterval,EXinterval, result;
		
		//The commented block below was used to test the program 
		
		//System.out.print("Enter what will be the input to the Tetranacci function: ");
		
//		do {
//			invalid=false;
//			try {
//			input = kb.nextInt();
//			if (input<0)
//				invalid=true;
//			} catch(InputMismatchException e) {
//				invalid = true;
//			}
//		} while (invalid);
		
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(new FileOutputStream("out.txt"));
			pw.println("~~~~~Testing the Tail Recursive version!~~~~~");
			for (int i = 5; i <= 1500; i+=5) {
				start = System.nanoTime();
				result = TRTetranacci(0, 0, 0, 1, i);
				TRinterval = (System.nanoTime()-start)/1000000.0; 
				pw.print("Input = "+i+":" + "\t" + result+ "\t");
				
				pw.println("Time elapsed: "+TRinterval);
			}
			pw.println("~~~~~Testing the Exponential version!~~~~~");
			for (int i = 5; i <= 35; i+=5) {
				start = System.nanoTime();
				result = ExpTetranacci(i);
				EXinterval = (System.nanoTime()-start)/1000000.0; 
				pw.print("Input = "+i+":" + "\t" + result + "\t");
				
				pw.println("Time elapsed: "+EXinterval);
			}
		} catch (FileNotFoundException e) {
			System.exit(0);
		} catch (Exception e2) {
			System.exit(0);
		}
		
		pw.close();
		
		
		
		
		
		
//		start = System.nanoTime();
//		
//		System.out.println("Testing TR:" + "\t" + TRTetranacci(0, 0, 0, 1, input));
//		
//		end = System.nanoTime();
//		TRinterval = end-start; 
//		
//		System.out.println(end);
//		System.out.println(start);
		
	}
	
	public static double ExpTetranacci(double in) {
		//base case when 0<=n<=3
		if (in==0 || in==1 || in==2) 
			return 0;
		if (in==3) 
			return 1;
		//otherwise, exponential recursive calls are made [exponential because many of the calls are redundant]
		return ExpTetranacci(in-4)+ExpTetranacci(in-3)+ExpTetranacci(in-2)+ExpTetranacci(in-1);
		
	}
	
	public static double TRTetranacci(double a, double b, double c, double d, double n) {
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
