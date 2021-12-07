import java.util.Iterator;

public class SingleGroup<X> implements Group<X, X> {

    // (I): list != null
    private final LinkedList<X> list;

    // (Inv): relatedCount >= 0
    // (Hist-c): can only be increased one by one
    private int relatedCount;

    // (Post): sets relatedCount to 0; this.list is initialized
    public SingleGroup() {
        this.list = new LinkedList<X>();
        relatedCount = 0;
    }

    // (Pre): x != null
    // (Post): adds x to this.list if this.list doesn't contain x already
    @Override
    public void add(X x) {
        if (!list.contains(x))
            list.add(x);
    }

    // (Pre): x != null; y != null
    // (Post): increases this.relatedCount by one; returns true if x equals y
    @Override
    public boolean related(X x, X y) {
        this.relatedCount++;
        return x == y;
    }

    // (Post): returns the number of related calls made by this object
    @Override
    public int invoked() {
        return this.relatedCount;
    }

    // (Post): returns an Iterator<X> that returns all x's from the container because related() is always true,
    //         therefore no check is needed
    @Override
    public Iterator<X> iterator() {
        return new Iterator<X>() {

            private Node<X> head = list.getHead();
            private Node<X> previous = null;
            private boolean nextCalled = false;
            private boolean alreadyCalled = false;

            @Override
            public boolean hasNext() {
                return head != null;
            }

            @Override
            public X next() {
                if (!hasNext()) throw new IllegalStateException("No elements left!");
                ;
                nextCalled = true;
                alreadyCalled = true;
                previous = head;
                head = head.getNext();
                return previous.getValue();
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
