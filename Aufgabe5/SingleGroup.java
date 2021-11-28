import java.util.Iterator;

public class SingleGroup<X> implements Group<X,X> {

    //TODO: all


    @Override
    public void add(X x) {

    }

    @Override
    public boolean related(X x, X x2) {
        return false;
    }

    @Override
    public int invoked() {
        return 0;
    }

    @Override
    public Iterator<X> iterator() {
        return null;
    }
}
