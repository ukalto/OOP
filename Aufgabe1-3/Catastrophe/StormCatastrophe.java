package Catastrophe;

public class StormCatastrophe extends Catastrophe {
    // (Pre): damageFactor >= 0 && damageFactor <= 5
    // (Post): A StormCatastrophe is created with the catastropheType which is CatastropheType.Storm, and a damageFactor 0-5
    public StormCatastrophe(double damageFactor) {
        super(CatastropheType.Storm, damageFactor);
    }
}