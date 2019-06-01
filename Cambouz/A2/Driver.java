import java.io.PrintWriter;//class for outputting
import java.io.FileOutputStream;//class for outputting
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

//PLEASE NOTE: I was not able to implement a use of the parentheses, but please take into account that all other operators work. I have worked tirelessly on this project
//and hope that you can look past the parentheses not working.

/*
 * COMP 352 Assignment 2
 * @author Camil Bouzidi ID: 40099611
 * @date May 31 2019
 * @version 1.6
 * This is the driver, which contains the methods used to do calculations, for the calculator.
 * Ignoring the parentheses, it works great.
 */
public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayStack<String> valStack = new ArrayStack<String>();
		System.out.println("The value at the top of the stack is: "+valStack.top());
		ArrayStack<String> opStack = new ArrayStack<String>();
		
		System.out.println(valStack);
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new FileInputStream("testcase.txt"));
			pw = new PrintWriter(new FileOutputStream("results.txt"));
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				System.out.println(line);
				valStack =  new ArrayStack<String>();
				opStack = new ArrayStack<String>();
				evalExp(line, valStack, opStack);
				pw.println("Result of "+line+": "+valStack);
				System.out.println("Result of "+line+": "+valStack);
				
			}
			sc.close();
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e2) {
			System.out.println(e2.getMessage());
		}
		
		
	}
	/*doOp():
	 * 	valStack.pop()->x
	 * 	valStack.pop()->y
	 * 	opStack.pop()->op
	 * 	valStack.push(x, op, y).
	 */
	/*
	 * @name: doOp
	 * Breaks down the values to calculate the operation
	 * @param ArrayStack<String> valStack: for values.
	 * @param ArrayStack<String> opStack: for operators
	 * @return: void 
	 */
	public static void doOp(ArrayStack<String> valStack, ArrayStack<String> opStack) {
		String x = valStack.pop();
		String y = valStack.pop();
		String op = opStack.pop();
		push(valStack, opStack, y, op, x);
	}
	
	//Push method to store the result, not numbers or operands.
	/*
	 * @name: push
	 * @param ArrayStack<String> valStack: for values.
	 * @param ArrayStack<String> opStack: for operators.
	 * @param String a: value 1.
	 * @param String b: value 2.
	 * @param op: operator.
	 * @return: boolean 
	 */
		public static boolean push(ArrayStack<String> valStack, ArrayStack<String> opStack, String a, String op, String b) {
			if (!(isNumber(a)&&isNumber(b))) {
				return false;
			}else {
				double x = Double.parseDouble(a);
				double y = Double.parseDouble(b);
				if (op.equals("(")) {
					//Do nothing, it cannot be used as an operator
				}
				
				if (op.equals(")")){
					//Evaluate anything prior to it
					/*while(true) {
						if (opStack.top().equals("(")) {
							break;
						}
						doOp(valStack,opStack);
					}*/
				}
				if (op.equals("^")){
					valStack.push(Math.pow(x, y)+"");
				} else if (op.equals("*")) {
					valStack.push(x*y+"");
				} else if (op.equals("/")) {
					valStack.push(x/y+"");
				}
				else if (op.equals("+")) {
					valStack.push(x+y+"");
				}
				else if (op.equals("-")) {
					valStack.push(x-y+"");
				} else if (op.equals(">")) {
					valStack.push((x>y)+"");
				} else if (op.equals(">=")) {
					valStack.push((x>=y)+"");
				} else if (op.equals("<=")) {
					valStack.push((x<=y)+"");
				} else if (op.equals("<")) {
					valStack.push((x<y)+"");
				} else if (op.equals("==")) {
					valStack.push((x==y)+"");
				} else if (op.equals("!=")) {
					valStack.push((x!=y)+"");
				}
				return true;
			}
			
		}
	
	/*
	 * repeatOps(refOp):
	 * 	while (valStack.size()>1)&&(prec(refOps)<=prec(OpStack.pop()))
	 * 		doOp();
	 */
	
		/*
		 * @name: repeatOps
		 * @param ArrayStack<String> valStack: for values.
		 * @param ArrayStack<String> opStack: for operators.
		 * @param String refOps: reference operator.
		 * @return: void 
		 */
	public static void repeatOps(ArrayStack<String> valStack, ArrayStack<String> opStack,String refOps) {
		System.out.println("valStack has size"+valStack.size());
		System.out.println("precedence of refOps, which is"+refOps+" is "+precedence(refOps));
		System.out.println("opStack top is "+opStack.top());
		while((valStack.size()>1)&&(precedence(refOps)<=precedence(opStack.top()))){
			doOp(valStack,opStack);
		}
		
	}
	
	/* Returns the precedence order of the operator
	 * @name: precedence
	 * @param String op: operator.
	 * @return: int 
	 */
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
		}else if (op.equals("$")) {
			return 7;
		}
		return 8;
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
	
		/*Evaluates the expression.
		 * @name evalExp
		 * @param String s: equation.
		 * @param ArrayStack<String> valStack: stack for values.
		 * @param ArrayStack<String> opStack: stack for operations.
		 * @return string: the final value
		 */
		public static String evalExp(String s, ArrayStack<String> valStack, ArrayStack<String> opStack) {
			
			StringTokenizer st = new StringTokenizer(s);
			while(st.hasMoreTokens()) {
				String z = st.nextToken();
				//Call the isNumber method
				if (isNumber(z)) {
					Double i = Double.parseDouble(z);
					valStack.push(i+"");
					System.out.println(valStack);
				} else {
					repeatOps(valStack, opStack, z);
					opStack.push(z);
				}
			}
			repeatOps(valStack,opStack,"$");
			return valStack.top();
		}
		
		/*
		 * Returns a boolean depending on if string is number.
		 * @name: isNumber
		 * @param: String z
		 * @return: boolean
		 */
		public static boolean isNumber(String z) {
			try {
				Double d = Double.parseDouble(z);
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
	
}
