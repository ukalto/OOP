package Tree;

public class SpruceTree extends Tree {
    // (Pre): waterStock >= 0 && waterStock <= 100
    // (Post): A SpruceTree is created with the max height 15, tree type SpruceTree, min temp 0, max temp 30
    // max age 185, fire resistance 0.02, freeze 0-1, infestation resistance 0-1, moor resistance 0.2,
    // storm resistance 0.1, water stock 0-100
    public SpruceTree(double waterStock) {
        this.maxHeight = 15;
        this.treeType = "SpruceTree";
        this.minTemperature = 0;
        this.maxTemperature = 30;
        this.maxAge = 185;
        this.fireResistance = 0.02;
        this.freezeResistance = calcFreezeResistance();
        this.infestationResistance = calcInfestationResistance();
        this.moorResistance = 0.2;
        this.stormResistance = 0.1;
        this.waterStock = waterStock;
    }

    // (Pre): infestationResistance >= 0 && infestationResistance <= 1
    // (Post): Returns the calculated InfestationResistance 0-1
    private double calcInfestationResistance() {
        if (this.maxAge < 25) return 0.6 * 0.3;
        else if (this.maxAge < 50) return 0.6 * 0.45;
        else if (this.maxAge < 100) return 0.6 * 0.6;
        else if (this.maxAge < 150) return 0.6 * 0.75;
        else if (this.maxAge < 200) return 0.6 * 0.9;
        else return 0.6;
    }

    // (Pre): freezeResistance >= 0 && freezeResistance <= 1
    // (Post): Returns the calculated FreezeResistance 0-1
    protected double calcFreezeResistance() {
        return 1;
    }
}