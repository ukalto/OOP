public class Quercus implements Tree {
    // (I) >= 0
    private double treeHeight;
    // (I) >= 0 && <= treeHeight
    private double stemHeight;

    // (Pre): treeHeight >= 0 && stemHeight >= 0 && stemHeight <= treeHeight
    // (Post): sets the this.treeHeight = treeHeight and this.stemHeight = stemHeight
    public Quercus(double treeHeight, double stemHeight) {
        this.treeHeight = treeHeight;
        this.stemHeight = stemHeight;
    }

    // (Post): returns a new relation from Quercus and another Tree
    public static Relation<Quercus, Tree> relation() {
        return new Relation<Quercus, Tree>() {
            // (I) >= 0
            // (Hist-c): can only be increased one by one
            private int invokedCounter = 0;

            // (Pre): quercus != null && tree != null
            // (Post): returns true if the stemHeight of the quercus is bigger than the height of the given tree
            @Override
            public boolean related(Quercus quercus, Tree tree) {
                invokedCounter++;
                return quercus.stemHeight >= tree.height();
            }

            // (Post): returns the invokedCounter which is >= 0
            @Override
            public int invoked() {
                return invokedCounter;
            }
        };
    }

    // (Post): returns the treeHeight of this
    @Override
    public double height() {
        return this.treeHeight;
    }
}