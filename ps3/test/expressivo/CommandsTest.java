/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
 
    
    
    // TODO tests for Commands.differentiate() and Commands.simplify()
    @Test
    public void testDifferentiateNumber() {
        assertEquals("Expected differentiated expression for a constant number",
            "0.0", Commands.differentiate("1", "x"));
    }

    @Test
    public void testDifferentiateVariable() {
        assertEquals("Expected differentiated expression for the same variable",
            "1.0", Commands.differentiate("x", "x"));
    }

    @Test
    public void testDifferentiatePlus() {
        assertEquals("Expected differentiated expression for sum",
            "(0.0 + 1.0)", Commands.differentiate("1 + x", "x"));
    }

    @Test
    public void testDifferentiateMultiply() {
        assertEquals("Expected differentiated expression for product",
            "((1.0 * 1.0) + (x * 0.0))", Commands.differentiate("x * 1", "x"));
    }

    @Test
    public void testDifferentiateSingleSameVariable() {
        assertEquals("Expected differentiated expression for product of terms with the same variable",
            "(((0.0 + 1.0) * (x * 1.0)) + ((1.0 + x) * ((1.0 * 1.0) + (x * 0.0))))", 
            Commands.differentiate("(1.0 + x) * (x * 1.0)", "x"));
    }

    @Test
    public void testDifferentiateSingleDifferentVariable() {
        assertEquals("Expected differentiated expression for product of terms with a different variable",
            "(((0.0 + 0.0) * (x * 1.0)) + ((1.0 + x) * ((0.0 * 1.0) + (x * 0.0))))", 
            Commands.differentiate("(1.0 + x) * (x * 1.0)", "y"));
    }

    @Test
    public void testDifferentiateMultipleVariables() {
        assertEquals("Expected differentiated expression for product of two variables",
            "((1.0 * y) + (x * 0.0))", Commands.differentiate("x * y", "x"));
    }

 

    
}
