package Catastrophe;

public abstract class Catastrophe {
    private CatastropheType catastropheType; // the type of the catastrophe
    private final double damageFactor; // how big the catastrophe is

    public Catastrophe(CatastropheType catastropheType, double damageFactor) {
        this.catastropheType = catastropheType;
        if (damageFactor > 5)
            damageFactor = 5;
        if (damageFactor < 0)
            damageFactor = 0;
        this.damageFactor = damageFactor;
    }

    public CatastropheType getCatastropheType() {
        return this.catastropheType;
    }

    public double getDamageFactor() {
        return damageFactor / 10;
    }

    public void setCatastropheType(CatastropheType catastropheType) {
        this.catastropheType = catastropheType;
    }

}