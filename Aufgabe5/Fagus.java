public class Fagus implements Tree{
    // >= 0
    private double height;
    // (I) 0-1
    private double shadowLeaves;

    // (Pre): height >= 0 && shadowLeaves >= 0 && shadowLeaves <= 1
    // (Post): sets the this.height = height and this.shadowLeaves = shadowLeaves
    public Fagus(double height, double shadowLeaves) {
        this.height = height;
        this.shadowLeaves = shadowLeaves;
    }

    // (Post): returns a new relation from Fagus and Fagus
    public static Relation<Fagus, Fagus> relation() {
        return new Relation<Fagus, Fagus>() {
            // (I) >= 0
            // (Hist-c): can only be increased one by one
            private int invokedCounter = 0;

            // (Pre): fagus1 != null && fagus2 != null
            // (Post): returns true if the height of fagus1 is bigger than the height of fagus2 and if the
            // shadowLeaves of fagus 1 are less than the shadowLeaves of fagus2
            @Override
            public boolean related(Fagus fagus1, Fagus fagus2) {
                invokedCounter++;
                return fagus1.height > fagus2.height && fagus1.shadowLeaves < fagus2.shadowLeaves;
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
        return this.height;
    }
}
