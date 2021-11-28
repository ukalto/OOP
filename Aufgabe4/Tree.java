public interface Tree {
    // (Post): Returns the scientific name of the tree species
    String species();

    // (Post): returns the estimated height of the tree in metres
    double size();

    // (Pre): (size() + changeSize) >= 0
    // (Post): The estimated height of the tree gets updated by the value changeSize
    void changeSize(double changeSize);

    // (Post): Always returns true
    boolean testIsTree();
}