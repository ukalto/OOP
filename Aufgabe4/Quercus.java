public interface Quercus extends ContinentalClimate, Fagaceae {

    // (Post): Returns the stronger incidence of the family under continental climate compared to oceanic climate
    //         where >= 1 is stronger and <= 1 is weaker; the returned value >= 0
    @Override
    double incidence();

    // (Post): Returns the scientific name of the species
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
    boolean testIsQuercus();

    // (Post): Always returns true
    @Override
    boolean testIsContintentalClimate();

    // (Post): Always returns true
    @Override
    boolean testIsFagaceae();

    // (Post): Always returns true
    @Override
    boolean testIsTree();
}
