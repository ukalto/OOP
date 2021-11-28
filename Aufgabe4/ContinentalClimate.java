public interface ContinentalClimate extends Tree {
    // (Post): Returns the stronger incidence of the family under continental climate compared to oceanic climate
    //         where >= 1 is stronger and <= 1 is weaker.
    double incidence();

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
    boolean testIsContintentalClimate();

    // (Post): Always returns true
    @Override
    boolean testIsTree();
}
