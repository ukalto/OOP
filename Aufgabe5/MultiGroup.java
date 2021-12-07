import java.util.Iterator;

public class MultiGroup<X, Y> implements Group<X, Y> {

    // (I): list != null
    private final LinkedList<X> list;

    // (I): a != null
    private final Group<? extends Y, ?> a;
    // (I): r != null
    private final Relation<? super X, ? super Y> r;

    // (Inv): relatedCount >= 0
    // (Hist-c): can only be increased one by one
    private int relatedCount;

    // (Pre): a != null; r != null
    // (Post): sets this.a to a; this. r to r; relatedCount to 0; this.list is initialized
    public MultiGroup(Group<? extends Y, ?> a, Relation<? super X, ? super Y> r) {
        this.a = a;
        this.r = r;
        relatedCount = 0;
        this.list = new LinkedList<X>();
    }

    // (Pre): x != null
    // (Post): adds x to this.list if this.list doesn't contain x already
    @Override
    public void add(X x) {
        if (!list.contains(x))
            list.add(x);
    }

    // (Pre): x != null; y != null
    // (Post): increases this.relatedCount by one; returns true if this.r.related evaluates to true with x and y
    //         and false otherwise
    @Override
    public boolean related(X x, Y y) {
        this.relatedCount++;
        return this.r.related(x, y);
    }

    // (Post): returns the number of related calls made by this object
    @Override
    public int invoked() {
        return this.relatedCount;
    }

    // (Post): returns all elements of x in container which are related to at least one y
    //         using related(x,y)
    @Override
    public Iterator<X> iterator() {
        return new Iterator<X>() {

            private Node<X> head = list.getHead();
            private Node<X> nextCheck = list.getHead();
            private Node<X> previous = null;
            private boolean nextCalled = false;
            private boolean alreadyCalled = false;

            @Override
            public boolean hasNext() {
                if (nextCheck == null) {
                    nextCheck = head;
                    return false;
                }

                Iterator<? extends Y> it = a.iterator();
                while (it.hasNext()) {
                    Y y = it.next();
                    if (related(nextCheck.getValue(), y)) {
                        nextCheck = head;
                        return true;
                    }

                }
                nextCheck = nextCheck.getNext();
                return hasNext();
            }

            @Override
            public X next() {
                if (!hasNext())
                    throw new IllegalStateException();

                nextCalled = true;
                alreadyCalled = false;

                Iterator<? extends Y> it = a.iterator();
                while (it.hasNext()) {
                    Y y = it.next();
                    if (r.related(head.getValue(), y)) {
                        previous = head;
                        head = head.getNext();
                        nextCheck = head;
                        return previous.getValue();
                    }

                }
                head = head.getNext();
                return next();
            }

            @Override
            public void remove() {
                if (!nextCalled || alreadyCalled) {
                    throw new java.lang.IllegalStateException();
                }
                alreadyCalled = true;
                nextCalled = false;
                list.remove(previous.getValue());
                previous = null;
            }
        };
    }
}
