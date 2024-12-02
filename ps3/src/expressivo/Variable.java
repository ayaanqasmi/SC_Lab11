package expressivo;

class Variable implements Expression {
    private final String name;
    
    // Abstraction function:
    //   represents the variable name
    // Rep invariant:
    //   name is a nonempty string of letters
    // Safety from rep exposure:
    //   name is final and immutable
    
    /**
     * Create a Variable instance.
     * 
     * @param name nonempty string of letters
     */
    public Variable(String name) {
        this.name = name;
        checkRep();
    }

    /**
     * Checks the rep invariant.
     */
    private void checkRep() {
        assert name.matches("[a-zA-Z]+");
    }
    
    @Override 
    public String toString() {
        return name;
    }

    @Override 
    public boolean equals(Object obj) {
        if (!(obj instanceof Variable)) return false;
        Variable other = (Variable) obj;
        return this.name.equals(other.name);
    }

    @Override 
    public int hashCode() {
        return name.hashCode();
    }
    
    @Override public Expression differentiate(String variable) {
        return name.equals(variable) ? new Number(1) : new Number(0);
    }

}
