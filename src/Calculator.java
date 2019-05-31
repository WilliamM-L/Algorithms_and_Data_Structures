import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Calculator {
	public static void main(String[] args) {
		PrintWriter pw = null;
		Scanner sc = null;
		
		try {
			pw = new PrintWriter(new FileOutputStream("CalculatorResult.txt"));
			sc = new Scanner(new FileInputStream("Expressions.txt"));
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception! Check your files!");
			System.exit(0);
		} catch (Exception e2) {
			System.exit(0);
		}
		
		pw.close();
	}
	
	

}
