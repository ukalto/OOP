import java.util.Iterator;

public interface Group<X,Y> extends java.lang.Iterable<X>, Relation<X,Y> {

    void add(X x);

    @Override
    boolean related(X x, Y y);

    @Override
    Iterator<X> iterator();
}
