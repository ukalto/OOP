public class FagusSylvatica implements Fagaceae, Domestic {
    // (Invariant): size >= 0
    private double size;

    // (Invariant): longitude >= -180 && longitude <= 180
    private final double longitude;
    // (Invariant): latitude >= -90 && latitude <= 90
    private final double latitude;

    // (Pre): size >= 0; longitude >= -180 && longitude <= 180; latitude >= -90 && latitude <= 90
    // (Post): sets the value of this.size to size, this.longitude to longitude and this.latitude to latitude
    public FagusSylvatica(double size, double longitude, double latitude) {
        this.size = size;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    // (Post): Returns the scientific name of the tree species
    public String species() {
        return "Fagus Sylvatica";
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
    public boolean testIsFagaceae() {
        return true;
    }

    @Override
    // (Post): Always returns true
    public boolean testIsDomestic() {
        return true;
    }

    @Override
    // (Post): Returns the longitude stored in this object
    public double longitude() {
        return longitude;
    }

    @Override
    // (Post): Returns the latitude stored in this object
    public double latitude() {
        return latitude;
    }

    // (Post): Always returns true
    @Override
    public boolean testIsTree() {
        return true;
    }
}
