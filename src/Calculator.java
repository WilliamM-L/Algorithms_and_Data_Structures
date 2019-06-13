import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Calculator {
	static Stack<Double> valStack = new Stack<Double>();
	static Stack<String> opStack = new Stack<String>();
	static boolean finalBooleanResult=false;
	static boolean comparison = false;
	public static void main(String[] args) {
		PrintWriter pw = null;
		Scanner sc = null;
		double result;
		String toCompute;
		
		//Setting up the file IO
		try {
			pw = new PrintWriter(new FileOutputStream("CalculatorResult.txt"));
			sc = new Scanner(new FileInputStream("Expressions.txt"));
			//Reading each line 
			while (sc.hasNextLine()) {
				toCompute = sc.nextLine();
				pw.print(toCompute+ " : ");
				result = compute(toCompute);
				//if the doOp method has determined that the result of the expression is a boolean, we display it 
				if (comparison) {
					pw.println(finalBooleanResult);
				} else {
					pw.println(result);
				}
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception! Check your files!");
			System.exit(0);
		} catch (Exception e2) {
			System.out.println("Something bad happened!");
			System.exit(0);
		}
		sc.close();
		pw.close();
		
	}
	

	private static double compute(String exp) {
		//Separating the line into tokens separated by " "
		StringTokenizer tokenizer = new StringTokenizer(exp, " ");
		String next;
		comparison = false;
		
		while (tokenizer.hasMoreTokens()) {
			next = tokenizer.nextToken();
			System.out.print(next);
			if (next.equals("(")) {
				opStack.push("(");
			} else if (next.equals(")")) {
				//when the closing bracket is encountered
				inner();
			//numbers are directly pushed onto the value stack
			} else if (isNumber(next)) {
				valStack.push(Double.parseDouble(next));
			} else {
			//if nothing has happened so far, we check if the encountered operator has more precedence
			//than the one on top of the stack
				repeatOps(next);
				opStack.push(next);
			}
			//used for debugging
			System.out.println("~~~~~~~~~~~~");
			System.out.println("values: " + valStack);
			System.out.println("operators: " + opStack);
			System.out.println("~~~~~~~~~~~~");
		}
		//clear the remaining ops that might be there
		while(opStack.top()!=null) {
			doOp();
			System.out.println("~~~~~~~~~~~~");
			System.out.println("values: " + valStack);
			System.out.println("operators: " + opStack);
			System.out.println("~~~~~~~~~~~~");
		}
		//if the result is in fact a boolean, return a bogus value
		if (comparison) {
			return 999;
		} else {
			//popping the last value, so it can be reused for the next expression
			return valStack.pop();
		}
		
	}
	
	private static void repeatOps(String refOp) {
		//if there are at least two value on the value stack, and an actual operator is in the operator stack,
		//and the received operator has a lower precedence than the one on the stack
		//then operations will done until that is no longer the case
		while(valStack.size()>1&&opStack.top()!="("&&opStack.size()>=1&&precedence(refOp)<=precedence(opStack.top())){
			doOp();
		}
		
	}
	
	private static void inner() {
		//while we are inside the parenthesizes, we compute what is there
		while (!opStack.top().equals("(")) {
			doOp();
		}
		//removing that parentheses
		opStack.pop();
	}
	
	//simply represent the precedence scheme for the operators
	private static int precedence(String op) {
		if (op.equals("(")||op.equals(")"))
			return 6;
		if (op.equals("^"))
			return 5;
		if ((op.equals("*"))||(op.equals("/")))
			return 4;
		if ((op.equals("+"))||(op.equals("-")))
			return 3;
		if ((op.equals(">"))||(op.equals(">="))||(op.equals("<="))||(op.equals("<")))
			return 2;
		if ((op.equals("=="))||(op.equals("!=")))
			return 1;
		
		System.err.println("No match was found! Returning -1! --> " + op);
		return -1;
	}
	
	private static void doOp() {
		//getting the values and operand
		double b = valStack.pop();
		double a = valStack.pop();
		String operation = opStack.pop();
		//computing the result
		if (operation.equals("^")){
			valStack.push(Math.pow(a, b));
		} else if (operation.equals("*")) {
			valStack.push(a*b);
		} else if (operation.equals("/")) {
			valStack.push(a/b);
		}
		else if (operation.equals("+")) {
			valStack.push(a+b);
		}
		else if (operation.equals("-")) {
			valStack.push(a-b);
		//if the final answer is in fact a boolean, we indicate it be setting the static var comparison to true
		} else if (operation.equals(">")) {
			finalBooleanResult = a>b; comparison = true;
		} else if (operation.equals(">=")) {
			finalBooleanResult = a>=b; comparison = true;
		} else if (operation.equals("<=")) {
			finalBooleanResult = a<=b; comparison = true;
		} else if (operation.equals("<")) {
			finalBooleanResult = a<b; comparison = true;
		} else if (operation.equals("==")) {
			finalBooleanResult = a==b; comparison = true;
		} else if (operation.equals("!=")) {
			finalBooleanResult = a!=b; comparison = true;
		}
		
	}
	
	//checks if the received string is a number
	private static boolean isNumber(String s) {
		try {
			double result = Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			System.out.println("String is empty!");
			return false;
		}
		return true;
	}

}
