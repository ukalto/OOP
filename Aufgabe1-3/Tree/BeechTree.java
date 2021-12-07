package Tree;

public class BeechTree extends Tree {
    // (Pre): waterStock >= 0 && waterStock <= 100
    // (Post): A BeechTree is created with the max height 45, tree type Beech, min temp 30, max temp 50
    // max age 230, fire resistance 0-1, freeze resistance 0.2, infestation resistance 0-1, moor resistance 0.2,
    // storm resistance 0.1, water stock 0-100
    public BeechTree(double waterStock) {
        this.maxHeight = 45;
        this.treeType = "Beech";
        this.minTemperature = 30;
        this.maxTemperature = 50;
        this.maxAge = 230;
        this.fireResistance = calcFireResistance();
        this.freezeResistance = 0.2;
        this.infestationResistance = calcInfestationResistance();
        this.moorResistance = 0.2;
        this.stormResistance = 0.1;
        this.waterStock = waterStock;
    }

    // (Pre): fireResistance >= 0 && fireResistance <= 1
    // (Post): Returns the calculated FireResistance 0-1
    private double calcFireResistance() {
        if (this.waterStock < 20) return 0.4 * 0.2;
        else if (this.waterStock < 40) return 0.4 * 0.5;
        else if (this.waterStock < 60) return 0.4 * 0.7;
        else if (this.waterStock < 80) return 0.4 * 0.8;
        else return 0.4 * 0.9;
    }

    // (Pre): infestationResistance >= 0 && infestationResistance <= 1
    // (Post): Returns the calculated InfestationResistance 0-1
    protected double calcInfestationResistance() {
        return 0;
    }
}