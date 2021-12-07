package Catastrophe;

public class FreezeCatastrophe extends Catastrophe {
    // (Pre): damageFactor >= 0 && damageFactor <= 5
    // (Post): A FreezeCatastrophe is created with the catastropheType which is CatastropheType.Freeze, and a damageFactor 0-5
    public FreezeCatastrophe(double damageFactor) {
        super(CatastropheType.Freeze, damageFactor);
    }
}