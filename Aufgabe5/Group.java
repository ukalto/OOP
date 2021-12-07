import java.util.Iterator;

public interface Group<X, Y> extends java.lang.Iterable<X>, Relation<X, Y> {

    // (Pre): x != null
    // (Post): adds x to this.list if this.list doesn't contain x already
    void add(X x);

    // (Pre): x != null; y != null
    // (Post): increases this.relatedCount by one; returns true if this.r.related evaluates to true with x and y
    //         and false otherwise
    @Override
    boolean related(X x, Y y);

    // (Post): returns all elements of x in container which are related to at least one y
    //         using related(x,y)
    @Override
    Iterator<X> iterator();

    // (Post): returns the number of related calls made by this object
    @Override
    int invoked();
}
