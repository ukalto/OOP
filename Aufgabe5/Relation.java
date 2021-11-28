public interface Relation<X, Y> {
    boolean related(X x, Y y);

    int invoked();
}
