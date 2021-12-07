package Tree;

public class ScotsPineTree extends Tree {
    // (Pre): waterStock >= 0 && waterStock <= 100
    // (Post): A ScotsPineTree is created with the max height 25, tree type ScotsPine, min temp 30, max temp 40
    // max age 90, fire resistance 0.05, freeze 0-1, infestation resistance 0.05, moor resistance 0-1,
    // storm resistance 0.05, water stock 0-100
    public ScotsPineTree(double waterStock) {
        this.maxHeight = 25;
        this.treeType = "ScotsPine";
        this.minTemperature = 30;
        this.maxTemperature = 40;
        this.maxAge = 90;
        this.fireResistance = 0.05;
        this.freezeResistance = calcFreezeResistance();
        this.infestationResistance = 0.05;
        this.moorResistance = calcMoorResistance();
        this.stormResistance = 0.05;
        this.waterStock = waterStock;
    }

    // (Pre): freezeResistance >= 0 && freezeResistance <= 1
    // (Post): Returns the calculated FreezeResistance 0-1
    private double calcFreezeResistance() {
        if (this.minTemperature < 20 && this.maxTemperature < 25) return 0.35 * 0.76;
        else if (this.minTemperature < 30 && this.maxTemperature < 35) return 0.35 * 0.63;
        else if (this.minTemperature < 40 && this.maxTemperature < 45) return 0.35 * 0.52;
        else return 0.35 * 0.31;
    }

    // (Pre): moorResistance >= 0 && moorResistance <= 1
    // (Post): Returns the calculated MoorResistance 0-1
    private double calcMoorResistance() {
        return 1;
    }
}