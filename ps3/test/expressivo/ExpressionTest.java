package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression ADT.
 */
public class ExpressionTest {

    // Testing strategy:
    // Partition tests for toString():
    // - Single Number, single Variable
    // - Composite expressions: '+' or '*'
    // - Nested operations like ((a + b) * c)

    // Partition tests for equals():
    // - Reflexive: e.equals(e) is true
    // - Symmetric: e1.equals(e2) iff e2.equals(e1)
    // - Transitive: if e1.equals(e2) and e2.equals(e3), then e1.equals(e3)
    // - Structure/content: identical structures/content are equal, others are not

    // Partition tests for hashCode():
    // - Equal expressions must have the same hashCode
    // - Ideally, different expressions should have different hashCodes

    @Test
    public void testToString() {
        // Single Number
        Expression number = new Number(5);
        assertEquals("5.0", number.toString());

        // Single Variable
        Expression variable = new Variable("x");
        assertEquals("x", variable.toString());

        // Composite Operation: Addition
        Expression addition = new Operation('+', new Variable("x"), new Number(3));
        assertEquals("(x + 3.0)", addition.toString());

        // Composite Operation: Multiplication
        Expression multiplication = new Operation('*', new Number(2), new Variable("y"));
        assertEquals("(2.0 * y)", multiplication.toString());

        // Nested Operations
        Expression nested = new Operation(
            '*',
            new Operation('+', new Variable("x"), new Variable("y")),
            new Variable("z")
        );
        assertEquals("((x + y) * z)", nested.toString());
    }

    @Test
    public void testEquals() {
        // Reflexive
        Expression expr1 = new Number(3);
        assertTrue(expr1.equals(expr1));

        // Symmetry
        Expression expr2 = new Variable("y");
        Expression expr3 = new Variable("y");
        assertTrue(expr2.equals(expr3));
        assertTrue(expr3.equals(expr2));

        // Transitivity
        Expression expr4 = new Operation('+', new Number(2), new Variable("x"));
        Expression expr5 = new Operation('+', new Number(2), new Variable("x"));
        Expression expr6 = new Operation('+', new Number(2), new Variable("x"));
        assertTrue(expr4.equals(expr5));
        assertTrue(expr5.equals(expr6));
        assertTrue(expr4.equals(expr6));

        // Different structure
        Expression expr7 = new Operation('*', new Number(2), new Variable("x"));
        assertFalse(expr4.equals(expr7));
    }

    @Test
    public void testHashCode() {
        // Equal numbers
        Expression num1 = new Number(5);
        Expression num2 = new Number(5);
        assertEquals(num1.hashCode(), num2.hashCode());

        // Structurally identical operations
        Expression op1 = new Operation('+', new Variable("x"), new Number(3));
        Expression op2 = new Operation('+', new Variable("x"), new Number(3));
        assertEquals(op1.hashCode(), op2.hashCode());

        // Different operations
        Expression op3 = new Operation('*', new Variable("x"), new Number(3));
        assertNotEquals(op1.hashCode(), op3.hashCode());

        // Nested structures
        Expression nested1 = new Operation(
            '*',
            new Operation('+', new Variable("a"), new Variable("b")),
            new Variable("c")
        );
        Expression nested2 = new Operation(
            '*',
            new Operation('+', new Variable("a"), new Variable("b")),
            new Variable("c")
        );
        assertEquals(nested1.hashCode(), nested2.hashCode());
    }
    
 // Variables representing expressions used in tests

    private final Expression oneExpr = new Number(1);  
    private final Expression zeroExpr = new Number(0); 
    private final Expression xVar = new Variable("x"); 
 

    // Expressions representing parsed operations
    private final Expression addExpr = new Operation('+', oneExpr, xVar);         // 1 + x
    private final Expression multiplyWithOneExpr = new Operation('*', xVar, oneExpr); // x * 1
    private final Expression combinedExpr = new Operation('*', addExpr, multiplyWithOneExpr); // (1 + x) * (x * 1)
    private final Expression yVar = new Variable("y"); 

    @Test
    public void testParseSingleNumber() {
        // Test parsing of a single number
        Expression parsedExpr = Expression.parse("1");
        assertEquals("Expected parsed expression to match 1", oneExpr, parsedExpr);
    }

    @Test
    public void testParseSingleVariable() {
        // Test parsing of a single variable
        Expression parsedExpr = Expression.parse("x");
        assertEquals("Expected parsed expression to match variable x", xVar, parsedExpr);
    }

    @Test
    public void testParseAddition() {
        // Test parsing of an addition expression
        Expression parsedExpr = Expression.parse("1 + x");
        assertEquals("Expected parsed expression to match 1 + x", addExpr, parsedExpr);
    }

    @Test
    public void testParseMultiplication() {
        // Test parsing of a multiplication expression
        Expression parsedExpr = Expression.parse("x * 1");
        assertEquals("Expected parsed expression to match x * 1", multiplyWithOneExpr, parsedExpr);
    }

    @Test
    public void testParseComplexExpression() {
        // Test parsing of a complex expression
        Expression parsedExpr = Expression.parse("(1 + x) * (x * 1)");
        assertEquals("Expected parsed expression to match (1 + x) * (x * 1)", combinedExpr, parsedExpr);
    }

    @Test
    public void testParseIllegalExpression() {
        // Test parsing of an invalid expression
        try {
            Expression.parse("3 x");
            fail("Expected IllegalArgumentException for invalid expression");
        } catch (IllegalArgumentException e) {
            // Exception expected, test passes
        }
    }
    private final Expression exp1 = new Operation('+', oneExpr, xVar);
    private final Expression exp2 = new Operation('*', xVar, oneExpr);
    private final Expression exp3 = new Operation('*', exp1, exp2);
    private final Expression exp4 = new Operation('*', xVar, yVar);
    @Test
    public void testDifferentiateNumber() {
        assertEquals("expected differentiated expression", oneExpr.differentiate("x"), zeroExpr);
    }

    @Test
    public void testDifferentiateVariable() {
        assertEquals("expected differentiated expression", xVar.differentiate("x"), oneExpr);
    }

    @Test
    public void testDifferentiatePlus() {
        Expression exp = new Operation('+', zeroExpr, oneExpr);
        assertEquals("expected differentiated expression", exp1.differentiate("x"), exp);
    }

    @Test
    public void testDifferentiateMultiply() {
        Expression exp = new Operation('+', new Operation('*', oneExpr, oneExpr),
            new Operation('*', xVar, zeroExpr));
        assertEquals("expected differentiated expression", exp2.differentiate("x"), exp);
    }

    @Test
    public void testDifferentiateSingleSameVariable() {
        Expression left = new Operation('*', new Operation('+', zeroExpr, oneExpr),
            new Operation('*', xVar, oneExpr));
        Expression right = new Operation('*', new Operation('+', oneExpr, xVar),
            new Operation('+', new Operation('*', oneExpr, oneExpr), new Operation('*', xVar, zeroExpr)));
        Expression exp = new Operation('+', left, right);
        assertEquals("expected differentiated expression", exp3.differentiate("x"), exp);
    }

    @Test
    public void testDifferentiateSingleDifferentVariable() {
        Expression left = new Operation('*', new Operation('+', zeroExpr, zeroExpr),
            new Operation('*', xVar, oneExpr));
        Expression right = new Operation('*', new Operation('+', oneExpr, xVar),
            new Operation('+', new Operation('*', zeroExpr, oneExpr), new Operation('*', xVar, zeroExpr)));
        Expression exp = new Operation('+', left, right);
        assertEquals("expected differentiated expression", exp3.differentiate("y"), exp);
    }

    @Test
    public void testDifferentiateMultipleVariables() {
        Expression exp = new Operation('+', new Operation('*', oneExpr, yVar),
            new Operation('*', xVar, zeroExpr));
        assertEquals("expected differentiated expression", exp4.differentiate("x"), exp);
    }


}
