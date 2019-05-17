import java.util.InputMismatchException;
import java.util.Scanner;

public class Tetranacci {
	
	public static void main(String[] args) {
		int input=0;
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
		
		
		
		
	}
	
	public int ExpTetranacci(int in) {
		//base case when 0<=n<=3
		if (in==0 || in==1 || in==2) 
			return 0;
		if (in==3) 
			return 1;
		//otherwise, exponential recursive calls are made [exponential because many of the calls are redundant]
		return ExpTetranacci(in-4)+ExpTetranacci(in-3)+ExpTetranacci(in-2)+ExpTetranacci(in-1);
		
	}
	
	public int TRTetranacci(int in) {
		//base case when 0<=n<=3
		if (in==0 || in==1 || in==2) 
			return 0;
		if (in==3) 
				return 1;
		//otherwise, exponential recursive calls are made [exponential because many of the calls are redundant]
		return ExpTetranacci(in-4)+ExpTetranacci(in-3)+ExpTetranacci(in-2)+ExpTetranacci(in-1);
	}

}
