public interface Relation<X, Y> {

    // (Pre): x != null; y != null
    // (Post): increases this.relatedCount by one; returns true if this.r.related evaluates to true with x and y
    //         and false otherwise
    boolean related(X x, Y y);

    // (Post): returns the number of related calls made by this object
    int invoked();
}
