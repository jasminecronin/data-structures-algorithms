
import java.util.Scanner;

/**
 * Evaluates a valid expression in prefix notation. Only operates on integers,
 * and only performs addition, subtraction, multiplication, and division.
 * 
 * @author Jasmine Roebuck 30037334
 *
 */
public class Prefix {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner("");
		System.out.println(evaluate(scan));
	}

	public static int evaluate(Scanner sc) {
		if (!sc.hasNext())
			return 0;
		String token = sc.next();
		try { // Check if the token is an integer
			int number = Integer.parseInt(token);
			return number;
		} catch (NumberFormatException e) {
			// Do nothing, token must be an operator
		}

		int a = evaluate(sc);
		int b = evaluate(sc);

		if (token.equals("*")) {
			return (a * b);
		} else if (token.equals("/")) {
			return (a / b);
		} else if (token.equals("+")) {
			return (a + b);
		} else { // token.equals("-")
			return (a - b);
		}
	}
}