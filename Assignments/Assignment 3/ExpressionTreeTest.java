import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.text.ParseException;

public class ExpressionTreeTest {
    private static boolean equalToIgnoringWhiteSpace(String expected, String observed) {
        String []exp = expected.trim().split("\\s+");
        String []obs = observed.trim().split("\\s+");
        
        if(exp.length != obs.length) return false;
	
	    for(int i = 0; i < exp.length; i++) {
            if(exp[i].compareTo(obs[i]) != 0) return false;
        }
        return true;
    }
    // test cases...
    @Test
    public void basicTests() throws ParseException {
        ExpressionTree tree = new ExpressionTree();
        
        String in = "331";
        int outVal = 331;
        tree.parse( in );
        assertEquals( message(in, "evaluation", ""+outVal), outVal, tree.evaluate() );
        
        in = "331";
        String out = "331";
        tree.parse( in );
        assertTrue( message(in, "prefix()", out), equalToIgnoringWhiteSpace(out.trim(), tree.prefix().trim() ) );
                   
        in = "331";
        out = "331";
        tree.parse( in );
        assertTrue( message(in, "postfix()", out), equalToIgnoringWhiteSpace(out.trim(), tree.postfix().trim() ) );
        
        in = "( 331 )";
        outVal = 331;
        tree.parse( in );
        assertEquals( message(in, "evaluation", ""+outVal), outVal, tree.evaluate() );
    }
    
    @Test
    public void precedenceTests() throws ParseException {
        ExpressionTree tree = new ExpressionTree();
        
        String in = "9 - 5 * 2";
        int outVal = -1;
        tree.parse( in );
        assertEquals( message(in, "evaluation", ""+outVal), outVal, tree.evaluate() );
        
        String out = "- 9 * 5 2";
        tree.parse( in );
        assertTrue( message(in, "prefix()", out), equalToIgnoringWhiteSpace(out.trim(), tree.prefix().trim() ) );
        
        out = "9 5 2 * -";
        tree.parse( in );
        assertTrue( message(in, "postfix()", out), equalToIgnoringWhiteSpace(out.trim(), tree.postfix().trim() ) );
        
        in = "( 2 + 2 * ( 7 + 2 * ( 11 * 2 - 4 ) ) ) / ( 1 + ( 2 - 3 ) * 2 )";
        outVal = -88;
        tree.parse( in );
        assertEquals( message(in, "evaluation", ""+outVal), outVal, tree.evaluate() );
        
        out = "/ + 2 * 2 + 7 * 2 - * 11 2 4 + 1 * - 2 3 2";
        tree.parse( in );
        assertTrue( message(in, "prefix()", out), equalToIgnoringWhiteSpace(out.trim(), tree.prefix().trim() ) );
        
        out = "2 2 7 2 11 2 * 4 - * + * + 1 2 3 - 2 * + /";
        tree.parse( in );
        assertTrue( message(in, "postfix()", out), equalToIgnoringWhiteSpace(out.trim(), tree.postfix().trim() ) );
    }
    
    
    @Test(expected = ParseException.class)
    public void exceptionTestDelimiterMismatch() throws ParseException {
        ExpressionTree tree = new ExpressionTree();
        String in = "9 +  ( ( 5 * 2 ) 3 ";
        tree.parse( in );
    }
    
    @Test(expected = ParseException.class)
    public void exceptionTestIncompleteExpression() throws ParseException {
        ExpressionTree tree = new ExpressionTree();
        String in = "( 2 + 3 ) * ( 2 - 3 ) + ";
        tree.parse( in );
    }
    
    @Test(expected = ParseException.class)
    public void exceptionTestUnexpectedCharacter() throws ParseException {
        ExpressionTree tree = new ExpressionTree();
        String in = "( a + b ) * ( a - b ) ";
        tree.parse( in );
    }
    
    private String message( String input, String test, String output ) {
        return "\n" +
        "Input expression: " + input + "\n" +
        "Test: " + test + "\n" +
        "Expected output: " + output;
    }
    
    
    
 
    public static void main( String[] args ) {
        Result result = JUnitCore.runClasses( ExpressionTreeTest.class );
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
	
    }
}
