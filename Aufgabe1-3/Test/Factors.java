
// GOOD: By using an own class for the growthFactor and the lossFactor we increase
//           the object coupling and the cohesion, since less errors are prone to happen.
//           Since we first used only an array for the arrays, which could have been misused
//           in the future we added this class representing the factors.

public final class Factors{

    // (I): growthFactor >= 0 && growthFactor <= 1
    private final double growthFactor;

    // (I): lossFactor >= 0 && lossFactor <= 1
    private final double lossFactor;

    // (Pre): growthFactor >= 0 && growthFactor <= 1; lossFactor >= 0 && lossFactor <= 1
    // (Post): initializes growthFactor and lossFactor with the given values
    public Factors(double growthFactor, double lossFactor){
        this.growthFactor = growthFactor;
        this.lossFactor = lossFactor;
    }

    // (Post): returns the growth factor stored in this instance
    public double getGrowthFactor(){return growthFactor;}

    // (Post): returns the loss factor stored in this instance
    public double getLossFactor(){return lossFactor;}
}