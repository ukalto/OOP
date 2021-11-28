public class BlackPineTree extends Tree{
    // (Pre): waterStock >= 0 && waterStock <= 100
    // (Post): A BlackPineTree is created with the max height 60, tree type Blackpine, min temp 20, max temp 40
    // max age 250, fire resistance 0-1, freeze 0-1, infestation resistance 0.2, moor resistance 0-1,
    // storm resistance 0.2, water stock 0-100
    public BlackPineTree(double waterStock) {
        this.maxHeight = 60;
        this.treeType = "BlackPine";
        this.minTemperature = 20;
        this.maxTemperature = 40;
        this.maxAge = 250;
        this.fireResistance = calcFireResistance();
        this.freezeResistance = calcFreezeResistance();
        this.infestationResistance = 0.2;
        this.moorResistance = calcMoorResistance();
        this.stormResistance = 0.2;
        this.waterStock = waterStock;
    }

    // (Pre): moorResistance >= 0 && moorResistance <= 1
    // (Post): Returns the calculated MoorResistance 0-1
    private double calcMoorResistance() {
        if (this.maxHeight < 15) {
            if (this.minTemperature < 20) return 0.3 * 0.56;
            else if (this.minTemperature < 30) return 0.3 * 0.74;
            else return 0.3;
        } else if (this.maxHeight < 25) {
            if (this.minTemperature < 20) return 0.3 * 0.33;
            else if (this.minTemperature < 30) return 0.3 * 0.55;
            else return 0.3 * 0.77;
        } else if (this.maxHeight < 35) {
            if (this.minTemperature < 20) return 0.3 * 0.4;
            else if (this.minTemperature < 30) return 0.3 * 0.54;
            else return 0.3 * 0.62;
        } else {
            if (this.minTemperature < 20) return 0.3 * 0.43;
            else if (this.minTemperature < 30) return 0.3 * 0.56;
            else return 0.3 * 0.79;
        }
    }

    // (Pre): fireResistance >= 0 && fireResistance <= 1
    // (Post): Returns the calculated FireResistance 0-1
    private double calcFireResistance() {
        return 1;
    }

    // (Pre): freezeResistance >= 0 && freezeResistance <= 1
    // (Post): Returns the calculated FreezeResistance 0-1
    private double calcFreezeResistance() {
        return 0;
    }
}