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
		
		
		try {
			pw = new PrintWriter(new FileOutputStream("CalculatorResult.txt"));
			sc = new Scanner(new FileInputStream("Expressions.txt"));
			
			while (sc.hasNextLine()) {
				result = compute(sc.nextLine());
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
		
		//String test = "( 2 ^ 5 - 6 ^ 2 ) + 4 / 4";
//		StringTokenizer tokenizer = new StringTokenizer(test, " ");
//		while (tokenizer.hasMoreTokens()) {
//			System.out.print(tokenizer.nextToken());
//		}
		
	
		
//		result = compute(test);
//		
		
		
		
		
	}
	

	private static double compute(String exp) {
		StringTokenizer tokenizer = new StringTokenizer(exp, " ");
		String next;
		comparison = false;
		
		while (tokenizer.hasMoreTokens()) {
			next = tokenizer.nextToken();
			System.out.print(next);
			if (next.equals("(")) {
				opStack.push("(");
			} else if (next.equals(")")) {
				inner();
			} else if (isNumber(next)) {
				valStack.push(Double.parseDouble(next));
			} else {
				repeatOps(next);
				opStack.push(next);
			}
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
		if (comparison) {
			return 999;
		} else {
			return valStack.top();
		}
		
	}
	
	private static void repeatOps(String refOp) {
		while(valStack.size()>1&&opStack.top()!="("&&opStack.size()>=1&&precedence(refOp)<=precedence(opStack.top())){
			doOp();
		}
		
	}
	
	private static void inner() {
		//while we are inside the parenthesizes, we compute what is there
		while (!opStack.top().equals("(")) {
			doOp();
		}
		opStack.pop();
	}
	
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
		double b = valStack.pop();
		double a = valStack.pop();
		String operation = opStack.pop();
		
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
