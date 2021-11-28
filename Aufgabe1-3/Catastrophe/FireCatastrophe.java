public class FireCatastrophe extends Catastrophe {
    // (Pre): damageFactor >= 0 && damageFactor <= 5
    // (Post): A FireCatastrophe is created with the catastropheType which is CatastropheType.Fire, and a damageFactor 0-5
    public FireCatastrophe(double damageFactor) {
        super(CatastropheType.Fire, damageFactor);
    }
}