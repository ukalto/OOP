public class QuercusRobur implements Quercus, Domestic, LightDemanding {

    // (Invariant): longitude >= -180 && longitude <= 180
    private final double longitude;

    // (Invariant): latitude >= -90 && latitude <= 90
    private final double latitude;

    // (Invariant): size >= 0
    private double size;

    // (Pre): size >= 0 && longitude >= -180 && longitude <= 180 && latitude >= -90 && latitude <= 90
    // (Post): sets the value of this.size to size, this.longitude to longitude and this.latitude to latitude
    public QuercusRobur(double size, double longitude, double latitude) {

        this.size = size;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // (Post): Returns the stronger incidence of the family under continental climate compared to oceanic climate
    //         where >= 1 is stronger and <= 1 is weaker; the returned value == 1.5
    @Override
    public double incidence() {
        return 1.5;
    }

    // (Post): returns the scientific name of the species
    @Override
    public String species() {
        return "QuercusRobur";
    }

    // (Post): returns the estimated height of the tree in metres
    @Override
    public double size() {
        return this.size;
    }

    // (Pre): (size() + changeSize) >= 0
    // (Post): The estimated height of the tree gets updated by the value changeSize
    @Override
    public void changeSize(double changeSize) {
        this.size += changeSize;
    }

    // (Post): Always returns true
    @Override
    public boolean testIsLightDemanding() {
        return true;
    }

    // (Post): Always returns true
    @Override
    public boolean testIsQuercus() {
        return true;
    }

    // (Post): Always returns true
    @Override
    public boolean testIsDomestic() {
        return true;
    }

    // (Post): Always returns true
    @Override
    public boolean testIsContintentalClimate() {
        return true;
    }

    // (Post): Always returns true
    @Override
    public boolean testIsFagaceae() {
        return true;
    }

    // (Post): Returns the longitude stored in this object
    @Override
    public double longitude() {
        return this.longitude;
    }

    // (Post): Returns the latitude stored in this object
    @Override
    public double latitude() {
        return this.latitude;
    }

    // (Post): Always returns true
    @Override
    public boolean testIsTree() {
        return true;
    }
}
