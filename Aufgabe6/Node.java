public class Node {

    //(Inv): value != null
    private final Object value;
    private Node next;

    // (Pre): value != null
    // (Post): setzt this.value auf value; setzt this.next auf next; next kann null sein
    public Node(Object value, Node next) {
        this.value = value;
        this.next = next;
    }

    // (Post): gibt den Wert gespeichert in dieser Node zurück
    public Object getValue() {
        return value;
    }

    // (Post): Gibt die "next"-Node auf die diese Node referenziert zurück - kann null sein
    public Node getNext() {
        return next;
    }


    // (Post): setzt this.next auf next; next kann null sein
    public void setNext(Node next) {
        this.next = next;
    }
}
