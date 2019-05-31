import java.io.PrintWriter;//class for outputting
import java.io.FileOutputStream;//class for outputting
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayStack<Double> valStack = new ArrayStack<Double>();
		System.out.println("The value at the top of the stack is: "+valStack.top());
		valStack.push(new Double(3));
		valStack.push(new Double(3));
		
		
		ArrayStack<String> opStack = new ArrayStack<String>();
		opStack.push("^");
		System.out.println(valStack);
		doOp(valStack,opStack);
		System.out.println(valStack);
		/*Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new FileInputStream("testcase.txt"));
			pw = new PrintWriter(new FileOutputStream("results.txt"));
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				char[] eq = line.toCharArray();
				
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e2) {
			System.out.println(e2.getMessage());
		}*/
		
		
	}
	/*doOp():
	 * 	valStack.pop()->x
	 * 	valStack.pop()->y
	 * 	opStack.pop()->op
	 * 	valStack.push(x, op, y).
	 */
	public static void doOp(ArrayStack<Double> valStack, ArrayStack<String> opStack) {
		double x = valStack.pop();
		double y = valStack.pop();
		String op = opStack.pop();
		push(valStack, x, op, y);
	}
	
	//Push method to store the result, not numbers or operands.
		public static boolean push(ArrayStack<Double> valStack, Double x, String op, Double y) {
			if (op.equals("(")) {
				//????
			}
			
			if (op.equals(")")){
				//????
			}
			if (op.equals("^")){
				valStack.push(Math.pow(x, y));
			} else if (op.equals("*")) {
				valStack.push(x*y);
			} else if (op.equals("/")) {
				valStack.push(x/y);
			}
			else if (op.equals("+")) {
				valStack.push(x+y);
			}
			else if (op.equals("-")) {
				valStack.push(x-y);
			} else if (op.equals(">")) {
				return x>y;
			} else if (op.equals(">=")) {
				return x>=y;
			} else if (op.equals("<=")) {
				return x<=y;
			} else if (op.equals("<")) {
				return x<y;
			} else if (op.equals("==")) {
				return x==y;
			} else if (op.equals("!=")) {
				return x!=y;
			}
			return true;
		}
	
	/*
	 * repeatOps(refOp):
	 * 	while (valStack.size()>1)&&(prec(refOps)<=prec(OpStack.pop()))
	 * 		doOp();
	 */
	public static void repeatOps(ArrayStack<Double> valStack, ArrayStack<String> opStack,String refOps) {
		while((valStack.size()>1)&&(precedence(refOps)<=precedence(opStack.top()))){
			doOp(valStack,opStack);
		}
		
	}
	
	public static int precedence(String op) {
		if (op.equals("(")||(op.equals(")"))){
			return 1;
		}
		if (op.equals("^")){
			return 2;
		} else if ((op.equals("*"))||(op.equals("/"))) {
			return 3;
		}
		else if ((op.equals("+"))||(op.equals("-"))) {
			return 4;
		} else if ((op.equals(">"))||(op.equals(">="))||(op.equals("<="))||(op.equals("<"))) {
			return 5;
		} else if ((op.equals("=="))||(op.equals("!="))) {
			return 6;
		}
		return 7;
	}
		
		/*
		 * evalExp():
		 * 	while there's another token z
		 * 	if isNumber(z) then valStack.push(z)
		 * 	else
		 * 		repeatOps(z)
		 * 		opStack.push(z)
		 *	repeatOps($)
		 *	return valStack.pop()
		 */
		public static Double evalExp(String s, ArrayStack<Double> valStack, ArrayStack<String> opStack) {
			StringTokenizer st = new StringTokenizer(s);
			while(st.hasMoreTokens()) {
				String z = st.nextToken();
				//Call the isNumber method
				if (isNumber(z)) {
					Double i = Double.parseDouble(z);
					valStack.push(i);
				} else {
					//repeat operation
					return valStack.top();
				}
			}
			return valStack.top();
		}
		
		public static boolean isNumber(String z) {
			try {
				Double d = Double.parseDouble(z);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
	
}
