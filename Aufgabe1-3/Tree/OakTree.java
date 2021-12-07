package Tree;

public class OakTree extends Tree {
    // (Pre): waterStock >= 0 && waterStock <= 100
    // (Post): A OakTree is created with the max height 35, tree type OakTree, min temp 10, max temp 30
    //  max age 210, fire resistance 0-1, freeze 0-1, infestation resistance 0.25, moor resistance 0.25,
    //  storm resistance 0-1, water stock 0-100
    public OakTree(double waterStock) {
        this.maxHeight = 35;
        this.treeType = "OakTree";
        this.minTemperature = 10;
        this.maxTemperature = 30;
        this.maxAge = 210;
        this.fireResistance = calcFireResistance();
        this.freezeResistance = calcFreezeResistance();
        this.infestationResistance = 0.25;
        this.moorResistance = 0.25;
        this.stormResistance = calcStormResistance();
        this.waterStock = waterStock;
    }

    // (Pre): fireResistance >= 0 && fireResistance <= 1
    // (Post): Returns the FireResistance 0-1
    private double calcFireResistance() {
        return 1;
    }

    // (Pre): freezeResistance >= 0 && freezeResistance <= 1
    // (Post): Returns the FreezeResistance 0-1
    private double calcFreezeResistance() {
        return 0;
    }

    // (Pre): stormResistance >= 0 && stormResistance <= 1
    // (Post): Returns the calculated StormResistance 0-1
    private double calcStormResistance() {
        if (this.maxHeight < 5) return 0.25 * 0.9;
        else if (this.maxHeight < 15) return 0.25 * 0.8;
        else if (this.maxHeight < 25) return 0.25 * 0.7;
        else if (this.maxHeight < 35) return 0.25 * 0.6;
        else if (this.maxHeight < 45) return 0.25 * 0.45;
        else return 0.25 * 0.3;
    }
}