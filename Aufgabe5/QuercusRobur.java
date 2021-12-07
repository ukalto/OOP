public class QuercusRobur extends Quercus {
    // (I): != null
    private final String resistanceFeature;

    // (Pre): treeHeight >= 0 && stemHeight >= 0 && stemHeight <= treeHeight; resistanceFeature!= null
    // (Post): sets the this.treeHeight = treeHeight and this.stemHeight = stemHeight and this.resistanceFeature = resistanceFeature
    public QuercusRobur(double treeHeight, double stemHeight, String resistanceFeature) {
        super(treeHeight, stemHeight);
        this.resistanceFeature = resistanceFeature;
    }

    // (Post): returns the description of the resistance
    public String resistance() {
        return this.resistanceFeature;
    }
}
