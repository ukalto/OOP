public interface Domestic extends Tree {

    // (Post): returns currently stored longitude
    double longitude();

    // (Post): returns currently stored latitude
    double latitude();

    // (Post): Returns the scientific name of the tree species
    @Override
    String species();

    // (Post): returns the estimated height of the tree in metres
    @Override
    double size();

    // (Pre): (size() + changeSize) >= 0
    // (Post): The estimated height of the tree gets updated by the value changeSize
    @Override
    void changeSize(double changeSize);

    // (Post): Always returns true
    boolean testIsDomestic();

    // (Post): Always returns true
    @Override
    boolean testIsTree();
}
