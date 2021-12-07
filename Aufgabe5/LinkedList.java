class LinkedList<X> {
    private Node<X> head;

    // (Post): sets head to null
    public LinkedList() {
        head = null;
    }

    // (Pre): x != null
    // (Post): adds x to this list; list does change
    public void add(X x) {
        if (head == null)
            head = new Node<X>(x, null);
        else {
            Node<X> last = head;

            while (last.getNext() != null) {
                last = last.getNext();
            }

            last.setNext(new Node<X>(x, null));
        }
    }

    // (Pre): x != null
    // (Post): returns true if x is contained in this list and false otherwise; list doesn't change
    public boolean contains(X x) {
        for (Node<X> i = head; i != null; i = i.getNext()) {
            if (x == i)
                return true;
        }
        return false;
    }

    // (Pre): x!= null
    // (Post): removes x from this list if it contained otherwise list doesn't change
    public void remove(X x) {
        if (head.getValue().equals(x)) {
            if (head.getNext() != null)
                head = head.getNext();
            else
                head = null;
        } else {
            Node<X> last = head;
            Node<X> prev;

            while (head.getNext() != null) {
                prev = last;
                last = last.getNext();
                if (last.getValue().equals(x)) {
                    prev.setNext(last.getNext());
                    break;
                }
            }
        }
    }

    // (Post): returns the head of this list. Can be null
    public Node<X> getHead() {
        return head;
    }
}