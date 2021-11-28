public abstract class Catastrophe {
    // (I) catastropheType != null
    private CatastropheType catastropheType;
    // (I) damageFactor >= 0 && damageFactor <= 5
    private final double damageFactor;

    // (Pre): catastropheType != null
    // (Post): A Catastrophe is created with the catastropheType which is an Enum, and a damageFactor 0-5
    public Catastrophe(CatastropheType catastropheType, double damageFactor) {
        this.catastropheType = catastropheType;
        if (damageFactor > 5)
            damageFactor = 5;
        if (damageFactor < 0)
            damageFactor = 0;
        this.damageFactor = damageFactor;
    }

    // (Post): Returns the CatastropheType as an Enum
    public CatastropheType getCatastropheType() {
        return this.catastropheType;
    }

    // (Post): Returns the damageFactor divided by 10 0-5
    public double getDamageFactor() {
        return damageFactor / 10;
    }

    // (Pre): catastropheType != null
    // (Post): The catastrophe type of this object is set
    public void setCatastropheType(CatastropheType catastropheType) {
        this.catastropheType = catastropheType;
    }
}