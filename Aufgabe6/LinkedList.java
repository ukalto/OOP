class LinkedList {
    private Node head;

    // (Post): setzt head auf null
    public LinkedList() {
        head = null;
    }

    // (Pre): x != null
    // (Post): fügt x dieser Liste hinzu; Liste ändet sich
    public void add(Object x) {
        if (head == null)
            head = new Node(x, null);
        else {
            Node last = head;

            while (last.getNext() != null) {
                last = last.getNext();
            }

            last.setNext(new Node(x, null));
        }
    }

    // (Pre): x != null
    // (Post): Gibt true zurück falls x in dieser Liste, ansonsten falase; Liste ändert sich nicht
    public boolean contains(Object x) {
        for (Node i = head; i != null; i = i.getNext()) {
            if (x == i)
                return true;
        }
        return false;
    }

    // (Pre): x!= null
    // (Post): entfernt x von dieser Liste fall vorhanden, ansonsten ändert sich die Liste nicht
    public void remove(Object x) {
        if (head.getValue().equals(x)) {
            if (head.getNext() != null)
                head = head.getNext();
            else
                head = null;
        } else {
            Node last = head;
            Node prev;

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

    //TODO REMOVE
    // (Pre): x!= null; n != null
    // (Post): gibt true zurück falls nach Definition gleich ansonsten false
//    private boolean checkEqual(Object x, Node n){
//        return x instanceof Forstbetrieb && n.getValue() instanceof Forstbetrieb &&
//                ((Forstbetrieb) n.getValue()).getName().equals(((Forstbetrieb) x).getName()) ||
//                x instanceof Holzvollernter && n.getValue() instanceof Holzvollernter &&
//                        ((Holzvollernter) n.getValue()).getId() == ((Holzvollernter) x).getId();
//    }

    // (Post): Gibt this.head zurück - kann null sein
    public Node getHead() {
        return head;
    }
}