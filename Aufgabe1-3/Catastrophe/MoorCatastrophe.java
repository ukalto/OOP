package Catastrophe;

public class MoorCatastrophe extends Catastrophe {
    // (Pre): damageFactor >= 0 && damageFactor <= 5
    // (Post): A MoorCatastrophe is created with the catastropheType which is CatastropheType.Moor, and a damageFactor 0-5
    public MoorCatastrophe(double damageFactor) {
        super(CatastropheType.Moor, damageFactor);
    }
}