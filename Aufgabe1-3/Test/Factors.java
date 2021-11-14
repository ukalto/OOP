package Test;

public final class Factors{
    private final double growthFactor;
    private final double lossFactor;

    public Factors(double growthFactor, double lossFactor){
        this.growthFactor = growthFactor;
        this.lossFactor = lossFactor;
    }

    public double getGrowthFactor(){return growthFactor;}
    public double getLossFactor(){return lossFactor;}
}