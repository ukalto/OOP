package Tree;//GOOD: By using subtyping we were able to reduce the needed code by a lot. it is also easier to add
//      new Subtypes of trees by creating new subclasses. It was necessary to make this class abstract to
//      represent the real life. We implemented the trees this way, because we weren't sure how many trees
//      we wanted to add exactly and this way it was easier to add new ones.

public abstract class Tree {
    // (I) maxHeight >= 0 && maxHeight <= 60
    protected double maxHeight;
    // (I) treeType != null
    protected String treeType;
    // (I) minTemperature >= 0 && minTemperature <= 30
    protected double minTemperature;
    // (I) maxTemperature >= 30 && maxTemperature <= 50
    protected double maxTemperature;
    // (I) maxAge >= 0 && maxAge <= 250
    protected int maxAge;
    // (I) fireResistance >= 0 && fireResistance <= 1
    protected double fireResistance;
    // (I) freezeResistance >= 0 && freezeResistance <= 1
    protected double freezeResistance;
    // (I) infestationResistance >= 0 && infestationResistance <= 1
    protected double infestationResistance;
    // (I) moorResistance >= 0 && moorResistance <= 1
    protected double moorResistance;
    // (I) stormResistance >= 0 && stormResistance <= 1
    protected double stormResistance;
    // (I) waterStock >= 0 && waterStock <= 100
    protected double waterStock;

    // (Pre): maxHeight >= 0 && maxHeight <= 60 && treeType != null && minTemperature >= 0 && minTemperature <= 30 && maxTemperature >= 30 && maxTemperature <= 50 && maxAge >= 0 && maxAge <= 250 && fireResistance >= 0 && fireResistance <= 1 && freezeResistance >= 0
    //                && freezeResistance <= 1 && infestationResistance >= 0 && infestationResistance <= 1 && moorResistance >= 0 && moorResistance <= 1 && stormResistance >= 0 && stormResistance <= 1 && waterStock >= 0 && waterStock <= 100
    // (Post): A tree is created with the max height 0-60, tree type a given String, min temp 0-30, max temp 30-50
    // max age 0-250, fire resistance 0-1, freeze resistance 0-1, infestation resistance 0-1, moor resistance 0-1,
    // storm resistance 0-1, water stock 0-100
    public Tree(double maxHeight, String treeType, double minTemperature, double maxTemperature, int maxAge, double fireResistance, double freezeResistance, double infestationResistance, double moorResistance, double stormResistance, double waterStock) {
        assert maxHeight >= 0 && maxHeight <= 60 && treeType != null && minTemperature >= 0 && minTemperature <= 30 && maxTemperature >= 30 && maxTemperature <= 50 && maxAge >= 0 && maxAge <= 250 && fireResistance >= 0 && fireResistance <= 1 && freezeResistance >= 0
                && freezeResistance <= 1 && infestationResistance >= 0 && infestationResistance <= 1 && moorResistance >= 0 && moorResistance <= 1 && stormResistance >= 0 && stormResistance <= 1 && waterStock >= 0 && waterStock <= 100;
        this.maxHeight = maxHeight;
        this.treeType = treeType;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.maxAge = maxAge;
        this.fireResistance = calcFireResistance(fireResistance);
        this.freezeResistance = calcFreezeResistance(freezeResistance);
        this.infestationResistance = calcInfestationResistance(infestationResistance);
        this.moorResistance = calcMoorResistance(moorResistance);
        this.stormResistance = calcStormResistance(stormResistance);
        this.waterStock = waterStock;
    }

    protected Tree() {
    }

    // (Pre): fireResistance >= 0 && fireResistance <= 1
    // (Post): Returns the calculated FireResistance 0-1
    private double calcFireResistance(double fireResistance) {
        if (this.waterStock < 20) return fireResistance * 0.3;
        else if (this.waterStock < 40) return fireResistance * 0.45;
        else if (this.waterStock < 60) return fireResistance * 0.6;
        else if (this.waterStock < 80) return fireResistance * 0.75;
        else return fireResistance * 0.9;
    }

    // BAD: Classcoherence is reduced, because we used static values. However, we should have used values from the tree.
    // In first place I thought of giving trees a bigger range for the minTemperature and maxTemperature, but ended in a more compromised
    // range which I forgot to include in the method.
    // (Pre): freezeResistance >= 0 && freezeResistance <= 1
    // (Post): Returns the calculated FreezeResistance 0-1
    private double calcFreezeResistance(double freezeResistance) {
        if (this.minTemperature < 20 && this.maxTemperature < 25) return freezeResistance * 0.8;
        else if (this.minTemperature < 30 && this.maxTemperature < 35) return freezeResistance * 0.6;
        else if (this.minTemperature < 40 && this.maxTemperature < 45) return freezeResistance * 0.45;
        else return freezeResistance * 0.3;
    }

    // (Pre): infestationResistance >= 0 && infestationResistance <= 1
    // (Post): Returns the calculated InfestationResistance 0-1
    private double calcInfestationResistance(double infestationResistance) {
        if (this.maxAge < 25) return infestationResistance * 0.3;
        else if (this.maxAge < 50) return infestationResistance * 0.45;
        else if (this.maxAge < 100) return infestationResistance * 0.6;
        else if (this.maxAge < 150) return infestationResistance * 0.75;
        else if (this.maxAge < 200) return infestationResistance * 0.9;
        else return infestationResistance;
    }

    // (Pre): stormResistance >= 0 && stormResistance <= 1
    // (Post): Returns the calculated StormResistance 0-1
    private double calcStormResistance(double stormResistance) {
        if (this.maxHeight < 5) return stormResistance * 0.9;
        else if (this.maxHeight < 15) return stormResistance * 0.8;
        else if (this.maxHeight < 25) return stormResistance * 0.7;
        else if (this.maxHeight < 35) return stormResistance * 0.6;
        else if (this.maxHeight < 45) return stormResistance * 0.45;
        else return stormResistance * 0.3;
    }

    // BAD: Classcoherence is reduced, because we used static values. However, we should have used values from the tree.
    // In first place I thought of giving trees a bigger range for the minTemperature, but ended in a more compromised
    // range which I forgot to include in the method.
    // (Pre): moorResistance >= 0 && moorResistance <= 1
    // (Post): Returns the calculated MoorResistance 0-1
    private double calcMoorResistance(double moorResistance) {
        if (this.maxHeight < 15) {
            if (this.minTemperature < 20) return moorResistance * 0.4;
            else if (this.minTemperature < 30) return moorResistance * 0.6;
            else if (this.minTemperature < 40) return moorResistance * 0.8;
            else return moorResistance;
        } else if (this.maxHeight < 25) {
            if (this.minTemperature < 20) return moorResistance * 0.3;
            else if (this.minTemperature < 30) return moorResistance * 0.45;
            else if (this.minTemperature < 40) return moorResistance * 0.6;
            else return moorResistance * 0.75;
        } else if (this.maxHeight < 35) {
            if (this.minTemperature < 20) return moorResistance * 0.2;
            else if (this.minTemperature < 30) return moorResistance * 0.25;
            else if (this.minTemperature < 40) return moorResistance * 0.35;
            else return moorResistance * 0.5;
        } else {
            if (this.minTemperature < 20) return moorResistance * 0.4;
            else if (this.minTemperature < 30) return moorResistance * 0.5;
            else if (this.minTemperature < 40) return moorResistance * 0.6;
            else return moorResistance * 0.7;
        }
    }

    // (Post): Returns the TreeType as a String
    public String getTreeType() {
        return treeType;
    }

    // (Post): Returns the MaxAge 0-250
    public int getMaxAge() {
        return maxAge;
    }

    // (Post): Returns the FireResistance 0-1
    public double getFireResistance() {
        return fireResistance;
    }

    // (Post): Returns the FreezeResistance 0-1
    public double getFreezeResistance() {
        return freezeResistance;
    }

    // (Post): Returns the InfestationResistance 0-1
    public double getInfestationResistance() {
        return infestationResistance;
    }

    // (Post): Returns the MoorResistance 0-1
    public double getMoorResistance() {
        return moorResistance;
    }

    // (Post): Returns the StormResistance 0-1
    public double getStormResistance() {
        return stormResistance;
    }
}