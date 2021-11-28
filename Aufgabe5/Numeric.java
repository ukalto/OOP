public class Numeric implements Relation<Integer, Integer> {
    // (I) > = 0
    private int c;
    // (I) >= 0
    // (Hist-c): can only be increased one by one
    private int invokedCounter;

    // (Pre): c >= 0
    // (Post): sets this.c = c
    public Numeric(int c) {
        this.c = c;
    }

    // (Pre): valid Integers
    // (Post): counts up the invokedCounter by 1 and returns true if the range of the first and the second integer
    // is not higher than the value c of this
    @Override
    public boolean related(Integer i1, Integer i2) {
        this.invokedCounter++;
        if (i1 >= i2)
            return i2 + c >= i1;
        else
            return i1 + c >= i2;
    }

    // (Post): returns the invokedCounter which is >= 0
    @Override
    public int invoked() {
        return this.invokedCounter;
    }
}
