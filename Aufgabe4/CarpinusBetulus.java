public class CarpinusBetulus implements ContinentalClimate, Domestic {
    // (Invariant): size >= 0
    private double size;
    // (Invariant): longitude >= -180 && longitude <= 180
    private final double longitude;
    // (Invariant): latitude >= -90 && latitude <= 90
    private final double latitude;

    // (Pre): size >= 0 && longitude >= -180 && longitude <= 180 && latitude >= -90 && latitude <= 90
    // (Post): sets the value of this.size to size, this.longitude to longitude and this.latitude to latitude
    public CarpinusBetulus(double size, double longitude, double latitude) {
        this.size = size;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    // (Post): Returns the stronger incidence of the family under continental climate compared to oceanic climate
    //         where >= 1 is stronger and <= 1 is weaker; the returned value == 1.6
    public double incidence() {
        return 1.6;
    }

    @Override
    // (Post): Returns the scientific name of the tree species
    public String species() {
        return "Carpinus Betulus";
    }

    @Override
    // (Post): returns the estimated height of the tree in metres
    public double size() {
        return size;
    }

    @Override
    // (Pre): (size() + changeSize) >= 0
    // (Post): The estimated height of the tree gets updated by the value changeSize
    public void changeSize(double changeSize) {
        this.size += changeSize;
    }

    @Override
    // (Post): Always returns true
    public boolean testIsContintentalClimate() {
        return true;
    }

    @Override
    // (Post): Always returns true
    public boolean testIsDomestic() {
        return true;
    }

    // (Post): Returns the longitude stored in this object
    @Override
    public double longitude() {
        return longitude;
    }

    // (Post): Returns the latitude stored in this object
    @Override
    public double latitude() {
        return latitude;
    }

    // (Post): Always returns true
    @Override
    public boolean testIsTree() {
        return true;
    }
}
