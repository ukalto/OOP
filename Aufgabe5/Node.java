public class Node<X> {

    // (Pre): x!= null
    // (Post): removes x from the list if it contained otherwise list doesn't change.
    private final X value;
    private Node<X> next;

    // (Pre): value != null
    // (Post): sets this.value to value; sets this.next to next; next might be null
    public Node(X value, Node<X> next){
        this.value = value;
        this.next = next;
    }

    // (Post): returns the value stored in this Node
    public X getValue(){return value;}

    // (Post): returns the next Node to which this Node references.
    public Node<X> getNext(){return next;}


    // (Post): sets this.next to next; next might be null
    public void setNext(Node<X> next){this.next = next;}
}
