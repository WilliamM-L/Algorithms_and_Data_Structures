import java.io.PrintWriter;//class for outputting
import java.io.FileOutputStream;//class for outputting
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
 * COMP 352 Assignment 2
 * @author Camil Bouzidi ID: 40099611
 * @date May 31 2019
 * @version 1.6
 * This is the driver, which contains the methods used to do calculations, for the calculator.
 */
public class Driver {
	static String exp = "";
	static String line="";
	static ArrayStack<String> valStack =  new ArrayStack<String>();
	static ArrayStack <String> opStack = new ArrayStack<String>();
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = null;
		PrintWriter pw = null;
		try {
			sc = new Scanner(new FileInputStream("Expressions.txt"));
			pw = new PrintWriter(new FileOutputStream("Expressionsresults.txt"));
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				System.out.println(line);
				String ans = evalExp(line);
				pw.println("Result of "+line+": "+ans);
				System.out.println("Result of "+line+": "+ans);
				System.out.println("valStack is: "+valStack);
				
			}
			sc.close();
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e2) {
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
	public static void doOp() {
		String y = valStack.pop();
		String x = valStack.pop();
		String op = opStack.pop();
		System.out.println("DOING "+x+op+y);
		System.out.println("THE STACK IS NOW "+valStack);
		push(x, op, y);
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
		public static boolean push(String x1, String op, String y1) {
			if (!(isNumber(x1)&&isNumber(y1))) {
				System.out.println("PROBLEM!");
				return false;
			}else {
				double x = Double.parseDouble(x1);
				double y = Double.parseDouble(y1);
				//if (op.equals("(")) {
					//Do nothing, it cannot be used as an operator
					//opStack.push(op);
				//}
				
				//if (op.equals(")")){
				//}
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
				} else {
					return false;
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
	public static void repeatOps(String refOps) {
		System.out.println("valStack has size "+valStack.size());
		System.out.println("precedence of refOps, which is "+refOps+", is "+precedence(refOps));
		System.out.println("opStack top is "+opStack.top());
		while((valStack.size()>1)&&(precedence(refOps)>=precedence(opStack.top()))&&(opStack.size()>=1)&&(opStack.top()!="(")){
			doOp();
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
		public static String evalExp(String s) {
			
			StringTokenizer st = new StringTokenizer(s);
			while(st.hasMoreTokens()) {
				String z = st.nextToken();
				//Call the isNumber method
				if (isNumber(z)) {
					Double i = Double.parseDouble(z);
					valStack.push(i+"");
					System.out.println(valStack);
				} else if (z.equals("(")){
					opStack.push("(");
				} else if (z.equals(")")){
					insideParentheses();
				} else {
					repeatOps(z);
					opStack.push(z);
				}
			}
			while(opStack.top()!=null) {
				doOp();
				System.out.println("~~~~~~~~~~~~");
				System.out.println("values: " + valStack);
				System.out.println("operators: " + opStack);
				System.out.println("~~~~~~~~~~~~");
			}
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
			} catch (NullPointerException e) {
				System.out.println("String is empty!");
				return false;
			}
			return true;
		}
		
		public static void insideParentheses() {
			while (!(opStack.top().equals("("))) {
				doOp();
			}
		}
	
}
