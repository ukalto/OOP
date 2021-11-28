import java.util.Iterator;

public class MultiGroup<X,Y> implements Group<X,Y> {

    private LinkedList<X> list;
    private final Group<? extends Y, ?> a;
    private final Relation<? super X,? super Y> r;

    // (Inv): relatedCount >= 0
    // (Hist-c): can only be increased one by one
    private int relatedCount;

    //TODO: important: X and Y are maybe not correct yet (missing extends/super/wildcards)

    // (Pre): a != null; r != null
    // (Post): sets this.a to a and this. r to r
    public MultiGroup(Group<? extends Y, ?> a, Relation<? super X,? super Y> r){
        this.a = a;
        this.r = r;
        relatedCount = 0;
    }

    // (Pre): x != null
    // (Post): adds x to this.list if this.list doesn't contain x already
    @Override
    public void add(X x) {
        if(!list.contains(x))
            list.add(x);
    }

    // (Pre): x != null; y != null
    // (Post): increases this.relatedCount by one; returns true if this.r.related evaluates to true with x and y
    //         and false otherwise
    @Override
    public boolean related(X x, Y y) {
        this.relatedCount++;
        return this.r.related(x,y);
    }


    // (Post): returns the number of related calls made by this object
    @Override
    public int invoked() {
        return this.relatedCount;
    }

    // (Post): returns an Iterator<X> that returns all x's that are related(x,y) to at least one y in a
    @Override
    public Iterator<X> iterator() {
        return new Iterator<X>() {

            Node<X> head = list.getHead();
            Node<X> previous = null;

            @Override
            public boolean hasNext() {
                Iterator<? extends Y> it = a.iterator();
                while (it.hasNext()){
                    Y y = it.next();
                    if(r.related(head.getValue(),y))
                        return true;
                }

                return false;
            }

            @Override
            public X next() {
                if(!hasNext())
                    return null;

                    Iterator<? extends Y> it = a.iterator();
                    while (it.hasNext()){
                        Y y = it.next();
                        if(r.related(head.getValue(),y)){
                            previous = head;
                            head = head.getNext();
                            return head.getValue();
                        }
                    }

                return null;
            }

            @Override
            public void remove() {
                if(!(previous == null)){
                    list.remove(previous.getValue());
                    previous = null;
                }
            }
        };
    }
}
