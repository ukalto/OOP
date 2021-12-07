package Catastrophe;

public class InfestationCatastrophe extends Catastrophe {
    // (Pre): damageFactor >= 0 && damageFactor <= 5
    // (Post): A InfestationCatastrophe is created with the catastropheType which is CatastropheType.Infestation, and a
    //         damageFactor 0-5
    public InfestationCatastrophe(double damageFactor) {
        super(CatastropheType.Infestation, damageFactor);
    }
}