package expressivo;

public class Number implements Expression {
    private final double value;
    
    // Abstraction function:
    //   represents the value of this number
    // Rep invariant:
    //   value >= 0
    // Safety from rep exposure:
    //   value is final and immutable
    
    /**
     * Create a Number instance.
     * 
     * @param value nonnegative value
     */
    public Number(double value) {
        this.value = value;
        checkRep();
    }

    /**
     * Returns the value of this number.
     * 
     * @return the number's value
     */
    public double getValue() {
        return value;
    }

    /**
     * Checks the rep invariant.
     */
    private void checkRep() {
        assert value >= 0;
    }
    
    @Override 
    public String toString() {
        return String.valueOf(value);
    }

    @Override 
    public boolean equals(Object obj) {
        if (!(obj instanceof Number)) return false;
        Number other = (Number) obj;
        return this.value == other.value;
    }

    @Override 
    public int hashCode() {
        return Double.hashCode(value);
    }
    
    @Override public Expression differentiate(String variable) {
        return new Number(0);
    }
}
