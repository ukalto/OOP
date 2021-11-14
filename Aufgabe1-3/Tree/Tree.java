package Tree;

public abstract class Tree {
    private final double maxHeight; // maximum height of the tree
    private final TreeType treeType; // the type of the tree
    private final double minTemperature; // min temperature the tree is able to resist
    private final double maxTemperature; // max temperature the tree is able to resist
    private final int maxAge; // maximum age a tree can reach
    private final double fireResistance; // the resistance against fire
    private final double freezeResistance; // the resistance against freeze
    private final double infestationResistance; // the resistance against infestation
    private final double moorResistance; // the resistance against moore
    private final double stormResistance; // the resistance against storm
    private final double waterStock; // how much water is in the tree 0-100

    public Tree(double maxHeight, TreeType treeType, double minTemperature, double maxTemperature, int maxAge, double fireResistance, double freezeResistance, double infestationResistance, double moorResistance, double stormResistance, double waterStock) {
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

    private double calcFireResistance(double fireResistance) {
        if (this.waterStock < 20) return fireResistance * 0.3;
        else if (this.waterStock < 40) return fireResistance * 0.45;
        else if (this.waterStock < 60) return fireResistance * 0.6;
        else if (this.waterStock < 80) return fireResistance * 0.75;
        else return fireResistance * 0.9;
    }

    private double calcFreezeResistance(double freezeResistance) {
        if (this.minTemperature < 20 && this.maxTemperature < 25) return freezeResistance * 0.8;
        else if (this.minTemperature < 30 && this.maxTemperature < 35) return freezeResistance * 0.6;
        else if (this.minTemperature < 40 && this.maxTemperature < 45) return freezeResistance * 0.45;
        else return freezeResistance * 0.3;
    }

    private double calcInfestationResistance(double infestationResistance) {
        if (this.maxAge < 25) return infestationResistance * 0.3;
        else if (this.maxAge < 50) return infestationResistance * 0.45;
        else if (this.maxAge < 100) return infestationResistance * 0.6;
        else if (this.maxAge < 150) return infestationResistance * 0.75;
        else if (this.maxAge < 200) return infestationResistance * 0.9;
        else return infestationResistance;
    }

    private double calcStormResistance(double stormResistance) {
        if (this.maxHeight < 5) return stormResistance * 0.9;
        else if (this.maxHeight < 15) return stormResistance * 0.8;
        else if (this.maxHeight < 25) return stormResistance * 0.7;
        else if (this.maxHeight < 35) return stormResistance * 0.6;
        else if (this.maxHeight < 45) return stormResistance * 0.45;
        else return stormResistance * 0.3;
    }

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

    public TreeType getTreeType() {
        return treeType;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public double getFireResistance() {
        return fireResistance;
    }

    public double getFreezeResistance() {
        return freezeResistance;
    }

    public double getInfestationResistance() {
        return infestationResistance;
    }

    public double getMoorResistance() {
        return moorResistance;
    }

    public double getStormResistance() {
        return stormResistance;
    }
}