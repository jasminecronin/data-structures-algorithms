import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Parses an input infix expression into a valid expression tree that can then
 * be evaluated. Prints the tree in prefix or postfix expressions as well.
 * 
 * @author Jasmine Roebuck 30037334
 */
public class ExpressionTree {

	// Test code
	 public static void main(String[] args) throws ParseException {
	 ExpressionTree tree = new ExpressionTree();
	 tree.parse("3 + 3 a");
	 printTree(tree.getRoot());
	 }

	private ExpTreeNode root = null;
	private int count; // Keep track of token location for error reporting

	/**
	 * Constructor to build an empty parse tree.
	 */
	public ExpressionTree() {

	}

	/**
	 * Getter method for the root of the expression tree.
	 * @return - root node of this tree
	 */
	public ExpTreeNode getRoot() {
		return root;
	}

	/**
	 * Build a parse tree from an expression.
	 * @param line - String containing the expression
	 * @throws ParseException - If an error occurs during parsing. The location of 
	 * 			the error is included in the thrown exception. Location is the token
	 * 			number, not the index.
	 */
	public void parse(String line) throws ParseException {
		Scanner sc = new Scanner(line);
		count = 1;
		ExpTreeNode expTree = expression(sc);
		if (sc.hasNext())
			throw new ParseException("Error: unexpected data after expression.", count);
		this.root = expTree;
	}

	/**
	 * Constructs an expression in the expression tree.
	 * @param sc - Scanner object
	 * @return - the subtree containing the expression
	 * @throws ParseException - If an error occurs during parsing. The location of 
	 * 			the error is included in the thrown exception. Location is the token
	 * 			number, not the index.
	 */
	private ExpTreeNode expression(Scanner sc) throws ParseException {
		ExpTreeNode term = term(sc);
		while (sc.hasNext("\\+") || sc.hasNext("-")) {
			String op = sc.next();
			count++;
			ExpTreeNode nextTerm = term(sc);
			term = new ExpTreeNode(op, term, nextTerm);
		}
		return term;
	}

	/**
	 * Constructs a term in the expression tree.
	 * @param sc - Scanner object
	 * @return - the subtree containing the term
	 * @throws ParseException - If an error occurs during parsing. The location of 
	 * 			the error is included in the thrown exception. Location is the token
	 * 			number, not the index.
	 */
	private ExpTreeNode term(Scanner sc) throws ParseException {
		ExpTreeNode factor = factor(sc);
		while (sc.hasNext("\\*") || sc.hasNext("/")) {
			String op = sc.next();
			count++;
			ExpTreeNode nextFactor = factor(sc);
			factor = new ExpTreeNode(op, factor, nextFactor);
		}
		return factor;
	}

	/**
	 * Constructs a factor in the expression tree.
	 * @param sc - Scanner object
	 * @return - the subtree containing the factor
	 * @throws ParseException - If an error occurs during parsing. The location of 
	 * 			the error is included in the thrown exception. Location is the token
	 * 			number, not the index.
	 */
	private ExpTreeNode factor(Scanner sc) throws ParseException {
		String number;
		try {
			number = sc.next();
			count++;
		} catch (NoSuchElementException e) {
			number = "";
		}
		try {
			int num = Integer.parseInt(number); // Do nothing with this int
			return new ExpTreeNode(number);
		} catch (NumberFormatException e) {
		}
		// We should have another expression enclosed in brackets
		if (number.equals("(")) {
			ExpTreeNode expression = expression(sc);
			if (!sc.hasNext("\\)"))
				throw new ParseException("Error: mismatched parentheses.", count);
			sc.next("\\)"); // Read the right bracket
			count++;
			return expression;
		} else { // Some invalid character is encountered.
			throw new ParseException("Error: unexpected character: " + number, count);
		}
	}

	/**
	 * Evaluate the expression tree and return the integer result. An empty tree
	 * returns 0.
	 * @return - The final integer result
	 */
	public int evaluate() {
		return evalNode(getRoot());
	}

	/**
	 * Recursive function to evaluate a node.
	 * @param node - The current node to evaluate
	 * @return - The final integer result
	 */
	private int evalNode(ExpTreeNode node) {
		if (node == null) {
			return 0;
		} else {
			try {
				int number = Integer.parseInt(node.el);
				return number;
			} catch (NumberFormatException e) {}

			assert (node.left != null && node.right != null);
			int a = evalNode(node.left);
			int b = evalNode(node.right);

			switch (node.el) {
			case "+":
				return a + b;
			case "-":
				return a - b;
			case "*":
				return a * b;
			case "/":
				assert (b != 0); // No division by 0
				return a / b;
			default:
				assert false; // Unsupported operator
				return 0;
			}
		}
	}

	/**
	 * Returns a postfix representation of the expression. Tokens are separated by
	 * whitespace. An empty tree returns a zero length string.
	 * @return - A postfix expression String
	 */
	public String postfix() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		buildPostfix(sb, getRoot());
		String res = sb.toString();
		return res;
	}

	/**
	 * Recursive function to build the postfix notation String
	 * @param sb - StringBuilder object
	 * @param node - Node we are parsing into the String
	 */
	private void buildPostfix(StringBuilder sb, ExpTreeNode node) {
		if (node == null) {
			return;
		} else {
			buildPostfix(sb, node.left);
			buildPostfix(sb, node.right);
			sb.append(node.el + " ");
		}
	}

	/**
	 * Returns a prefix representation of the expression. Tokens are separated by
	 * whitespace. An empty tree returns a zero length string.
	 * @return - A prefix expression String
	 */
	public String prefix() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		buildPrefix(sb, getRoot());
		String res = sb.toString();
		return res;
	}

	/**
	 * Recursive function to build the prefix notation String
	 * @param sb - StringBuilder object
	 * @param node - Node we are parsing into the String
	 */
	private void buildPrefix(StringBuilder sb, ExpTreeNode node) {
		if (node == null) {
			return;
		} else {
			sb.append(node.el + " ");
			buildPrefix(sb, node.left);
			buildPrefix(sb, node.right);
		}
	}

	/**
	 * Prints the entire expression tree in infix notation.
	 * @param node - The root of the tree or subtree to print
	 */
	static void printTree(ExpTreeNode node) {
		if (node == null) {
			return;
		}
		printTree(node.left);
		System.out.print(node.el + " ");
		printTree(node.right);
	}

}
