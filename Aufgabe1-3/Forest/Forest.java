import java.util.HashMap;
import java.util.Map;
// GOOD: Minimal object coupling because the calculations are mostly independent of other classes.The thought behind
//this was that the attribute needed for the calculations should be stored in the same class where they are mostly used.
public class Forest {
    //(I): area=1
    private final int area = 1;
    //(I): treePopulation >=0
    private double treePopulation;
    //(I): ageStructure != NULL
    private final HashMap<Integer, Double> ageStructure;
    //(I): 1 >= health >=0
    private double health;
    //(I): targetStock >=0
    private double targetStock;
    //(I): maxTargetStock >=0
    private double maxTargetStock;
    //(I): harvest >=0
    private double harvest;
    //(I): co2Stock >=0
    private double co2Stock;
    //(I): speciesStructure != NULL
    private final HashMap<Tree, Double> speciesStructure;
    //(I): 1 >= soilQuality >=0
    private double soilQuality;
    //(I): 1 >= stability >=0
    private double stability;
    //(I): 1 >= waterStock >=0
    private double waterStock;
    //(I): hoursOfSunshine >=0
    private double hoursOfSunshine;

    //(I): wolves >=0
    private int wolves;

    //(I): deer >=0
    private int deer;

    //(I): visitors >=0
    private int visitors;

    //(I): forestType != NULL
    private ForestType forestType;
    //(I): history != NULL
    private final HashMap<Integer, Forest> history = new HashMap<>();

    // (Pre): treePopulation> = 0; ageStructure != NULL; health> = 0.25 && health <= 1; targetStock >= 0
    //        maxTargetStock >= targetStock; harvest >= 0; co2Stock >= 0; soilQuality >= 0 && soilQuality <= 1;
    //        stability >= 0 && stability <= 1; waterStock >= 0 && waterStock <= 1; hoursOfSunshine >= 0;
    //        speciesStructure != NULL; wolves >= 0; deer >= 0; visitors >= 0
    // (Post): stores all the values given to the constructor in this instance of forest
    public Forest(double treePopulation, HashMap<Integer, Double> ageStructure, double health, double targetStock,
                  double maxTargetStock, double harvest, double co2Stock, double soilQuality, double stability,
                  double waterStock, double hoursOfSunshine, HashMap<Tree, Double> speciesStructure, int wolves, int deer,
                  int visitors) {
        this.treePopulation = treePopulation;
        this.ageStructure = ageStructure;
        this.health = health;
        this.targetStock = targetStock;
        this.maxTargetStock = maxTargetStock;
        this.harvest = harvest;
        this.co2Stock = co2Stock;
        this.soilQuality = soilQuality;
        this.stability = stability;
        this.waterStock = waterStock;
        this.hoursOfSunshine = hoursOfSunshine;
        this.speciesStructure = speciesStructure;
        this.wolves = wolves;
        this.deer = deer;
        this.visitors = visitors;
    }

    // BAD: using static checks increases the object coupling, although it would not be necessary.
    //      It would be more precise to calculate the type of forest dynamically. These calculations
    //      ended up being this way because it would be more simple.

    // (Post): assigned a forestType to the forest based on the speciesStructure, based on the portion of the trees.
    //         If no forest cannot be assigned to a special kind of forest, it is set to "other"
    public void calcForestType() {
        double beech = 0;
        double blackPine = 0;
        double oakTree = 0;
        double scotsPine = 0;
        double spruceTree = 0;
        for (Map.Entry<Tree, Double> t : speciesStructure.entrySet()) {
            switch (t.getKey().getTreeType()) {
                case "Beech":
                    beech = t.getValue();
                    break;
                case "BlackPine":
                    blackPine = t.getValue();
                    break;
                case "OakTree":
                    oakTree = t.getValue();
                    break;
                case "ScotsPine":
                    scotsPine = t.getValue();
                    break;
                case "SpruceTree":
                    spruceTree = t.getValue();
                    break;

            }
        }
        if (oakTree >= 0.4 && beech >= 0.4) {
            forestType = ForestType.BeechOak;
        }
        if (oakTree >= 0.6 && blackPine >= 0.2) {
            forestType = ForestType.BlackPineOak;
        }
        if (oakTree >= 0.4 && spruceTree >= 0.4) {
            forestType = ForestType.SpruceOak;
        }
        if (beech >= 0.6 && scotsPine >= 0.2) {
            forestType = ForestType.BreechScotspine;
        }
        if (forestType == null) forestType = ForestType.Other;
    }

    //(Post): Returned true if there is only one species of trees in the forest. Returned false otherwise.
    public boolean calcMonoculture() {
        return speciesStructure.size() == 1;
    }

    //(Post): count of wolves and deer is increased or decreased based on mathematical calculations
    public void simulateAnimalActivity() {

        if (wolves >= deer) {
            wolves = (int) Math.floor(wolves * 0.6);
        } else {
            deer = deer - wolves;
            wolves *= 2 + 1;
            if (deer >= 1000) {
                deer = (int) Math.floor(deer * 0.8 - 100);
            } else {
                deer *= deer + 1;
            }
        }
    }

    // (Post): updated the tree population of the forest by the given value but only by the full value
    //      if possible. Otherwise, it will be increased/decreased by the fraction of the value possible.
    public void treeGrowth(double value) {

        boolean flag = false;
        if (value > 0) {
            if (treePopulation < targetStock) {
                if (treePopulation + value > targetStock)
                    value = targetStock - treePopulation;
                flag = true;
            }
        } else if (value < 0) {
            if (treePopulation - value < 0) {
                value = treePopulation;
            }
            flag = true;
        }

        if (flag) {
            treePopulation += value;

            if (value > 0) {
                if ((ageStructure.getOrDefault(0, 0.0) + value / treePopulation) > 1)
                    ageStructure.put(0, 1.0);
                else
                    ageStructure.put(0, ageStructure.getOrDefault(0, 0.0) + value / treePopulation);
                for (Map.Entry<Integer, Double> entry : ageStructure.entrySet()) {
                    if (entry.getKey() != 0 && entry.getValue() != 0)
                        if ((ageStructure.getOrDefault(0, 0.0)) == 1)
                            entry.setValue(0.0);
                        else
                            entry.setValue(entry.getValue() - value / treePopulation * entry.getValue());

                    if (entry.getKey() != 0 && entry.getValue() < 0)
                        entry.setValue(0.0);
                }
            }
        }
    }

    // BAD: Since all the Trees in the forest have different lifespans (since task 2) it isn't correct
    //           to just assume that every tree dies at the age of 250. This decreases the cohesion. The trees
    //           dying of old age should rather be determined by the proportions of the corresponding trees
    //           and the instance of this forest and their maximum age. This probably happened due to
    //           different views of the forest and the trees of the persons implementing them.
    // (Pre): loss >= 0 && loss <= 1
    // (Post): increases the age structure of all tress by one and updates the value
    //         considering the loss and adding it to the new trees (age 0) and
    //          removing the difference from loss from the other age groups.
    //          Also removes all trees older than 250.
    public void increaseAge(double loss) {
        Map<Integer, Double> temp = new HashMap<>();
        double sum = 0;
        for (Map.Entry<Integer, Double> entry : ageStructure.entrySet()) {
            if (entry.getKey() == 0) {
                if (entry.getValue() != 0) {
                    if ((entry.getValue() * (1 - loss)) > 0) {
                        sum += entry.getValue() * (1 - loss);
                        temp.put(1, sum);
                    }

                }
                temp.put(0, loss);
                sum += loss;
            } else if (entry.getKey() == 250) {
                temp.put(0, temp.getOrDefault(0, 0.0) + entry.getValue());
            } else {
                int alter = entry.getKey();
                double share = entry.getValue();
                if (share > 0)
                    if (sum < 1) {
                        if ((share * (1 - loss)) < 0)
                            temp.put(alter + 1, 0.0);
                        else if (((share * (1 - loss)) + sum) > 1) {
                            temp.put(alter + 1, 1 - sum);
                            sum = 1;
                        } else {
                            temp.put(alter + 1, (share * (1 - loss)));
                            sum += (share * (1 - loss));
                        }
                    }
            }
        }
        sum = 0;
        for (Map.Entry<Integer, Double> entry : temp.entrySet()) {
            sum += entry.getValue();
        }
        if (sum < 1)
            temp.put(0, temp.get(0) + (1 - sum));
        if (temp.get(0) > 1) {
            for (Map.Entry<Integer, Double> entry : temp.entrySet()) {
                entry.setValue(0.0);
            }
            temp.put(0, 1.0);
        }

        ageStructure.putAll(temp);
    }


    // (Pre): value >= 0
    // (Post): updates the target stock by the value value if possible otherwise sets it to the max target stock
    //         of this instance.
    public void increaseTargetStock(double value) {
        if (targetStock + value < maxTargetStock)
            targetStock += value;
        else
            targetStock = maxTargetStock;
    }


    // (Post): updates the co2-stock by the value stock. If the stock drops below 0 it will be set to 0.
    public void co2Adjust(double stock) {
        if (co2Stock + stock < 0)
            co2Stock = 0;
        else
            co2Stock += stock;

        if (!(co2Stock >= 0))
            co2Stock = 0;
    }

    // (Post): returns the tree population stored in this instance.
    public double getTreePopulation() {
        return treePopulation;
    }

    // (Post): sets the tree population in this instance to the value treePopulation. In case it drops below
    //         0 it will be set to 0.
    public void setTreePopulation(double treePopulation) {
        if (this.treePopulation + treePopulation < 0 || !(this.treePopulation + treePopulation >= 0))
            this.treePopulation = 0;
        else
            this.treePopulation = treePopulation;
    }

    // (Post): calculates the soil and returns it with a value between 0 and 1.
    public void calcSoil() {
        switch (getForestType()) {
            case BeechOak:
                setSoilQuality(1);
                break;
            case BlackPineOak:
                setSoilQuality(0.87);
                break;
            case SpruceOak:
                setSoilQuality(0.92);
                break;
            case BreechScotspine:
                setSoilQuality(0.63);
                break;
            case Other:
                setSoilQuality(0.1);
                break;
            default:
                setSoilQuality(0);
        }
    }

    // (Post): calculates the water stock and updates the stored value with a value between 0 and 1.
    public void calcWaterstock(){
        calcSoil();
        calcStability();

        double scaledSunHours = hoursOfSunshine/(12*365);

        setWaterStock((stability+soilQuality+scaledSunHours)/3);
    }


    // (Post): calculates the stability and updates the stored value with a value between 0 and 1 considering
    //         the current age structure.
    public void calcStability() {
        double youngForestFactor = 0;
        double averageAge = 0;
        double proportionOfOld = 0;
        for (Map.Entry<Tree, Double> i : speciesStructure.entrySet()) {
            averageAge += i.getKey().getMaxAge();
        }
        averageAge /= speciesStructure.size();
        for (int i = (int) averageAge; i < ageStructure.size(); i++) {
            proportionOfOld += ageStructure.get(i);
        }

        if (proportionOfOld < .5)
            youngForestFactor = 1;
        else if (proportionOfOld < .7)
            youngForestFactor = 0.8;
        else if (proportionOfOld < .8)
            youngForestFactor = 0.7;
        else if (proportionOfOld < .9)
            youngForestFactor = 0.6;

        calcSoil();
        setStability(getSoilQuality() * youngForestFactor);
    }
    //(Pre): year > 0
    //(Post): saved a copy of this Forest to the History HashMap for statistics
    public void makeHistoryEntry(int year) {
        history.put(year, new Forest(this.treePopulation, null, this.health, this.targetStock, this.maxTargetStock,
                this.harvest, this.co2Stock, this.soilQuality, this.stability, this.waterStock, this.hoursOfSunshine,
                null, this.wolves, this.deer, this.visitors));
    }

    // BAD: Here we used a lot of getters and setters which leads to a reduction of object coupling.
    //           Instead we should have used method like adjustCo2-stock which takes the current
    //           value into account the changes it appropriately. This probably happened
    //           because the original plan was that the models and the forest would be implemented by different
    //           persons and since the person who implemented the forest didn't know exactly how the
    //           person implementing the models wanted to use the forest he happened to implement a lot of
    //           getters and setters.
    //(Post):returned the history Map
    public HashMap<Integer, Forest> getHistory() {
        return history;
    }

    //(Post):returned the ageStructure Map
    public HashMap<Integer, Double> getAgeStructure() {
        return ageStructure;
    }

    //(Post):returned the health value
    public double getHealth() {
        return health;
    }

    //(Pre):1 >= health >= 0
    //(Post):sets the health value
    public void setHealth(double health) {
        this.health = health;
    }

    //(Post):returned value of targetStock
    public double getTargetStock() {
        return targetStock;
    }

    // (Post): sets the target stock to the value targetStock. In case it drops below 0 it will be set to 0
    //         and if it gets above the max target stock it will be set to maxTargetStock.
    public void setTargetStock(double targetStock) {
        if (targetStock < 0)
            this.targetStock = 0;
        else this.targetStock = Math.min(targetStock, maxTargetStock);
    }

    //(Post):returned value of maxTargetStock
    public double getMaxTargetStock() {
        return maxTargetStock;
    }

    //(Post):returned amount of harvest
    public double getHarvest() {
        return harvest;
    }

    //(Pre): harvest >=0
    //(Post): sets the value of the harvest in this forest
    public void setHarvest(double harvest) {
        this.harvest = harvest;
    }

    //(Pre): zielbestand >=0
    //(Post):sets the maxTargetStock
    public void setMaxTargetStock(double zielbestand) {
        this.maxTargetStock = zielbestand;
    }

    //(Pre):co2Vorrat >= 0
    //(Post):sets amount co2Stock
    public void setco2Vorrat(double co2Vorrat) {
        this.co2Stock = co2Vorrat;
    }

    //(Post):returned amount of co2Stock
    public double getco2Vorrat() {
        return this.co2Stock;
    }

    //(Post): sets number of visitors
    public void setVisitors(int visitors) {
        this.visitors = Math.max(visitors, 0);
    }

    //(Post):returned amount of visitors in this forest
    public int getVisitors() {
        return this.visitors;
    }

    //(Post):returned value of soilQuality
    public double getSoilQuality() {
        return soilQuality;
    }

    //(Pre):1 >= soilQulity >= 0
    //(Post):sets value of soilQuality
    public void setSoilQuality(double soilQuality) {
        this.soilQuality = soilQuality;
    }

    //(Post):returned the value of stability between 0 and 1
    public double getStability() {
        return stability;
    }
    //(Post):returned the HashMap with the species
    public HashMap<Tree, Double> getSpeciesStructure() {
        return this.speciesStructure;
    }

    // (Post): sets the stability to the value stability in an interval between 0 and 1.
    public void setStability(double stability) {
        if (stability < 0)
            this.stability = 0;
        else if (stability > 1)
            this.stability = 1;
        else
            this.stability = stability;
    }
    //(Post):returned amount of water
    public double getWaterStock() {
        return waterStock;
    }

    // (Post): sets the stability to the value stability in an interval between 0 and 1.
    public void setWaterStock(double waterStock) {
        if (waterStock < 0)
            this.waterStock = 0;
        else if (waterStock > 1)
            this.waterStock = 1;
        else
            this.waterStock = waterStock;
    }

    //(Post):sets amount of hours of sun, but sets 0 if it is negative
    public void setHoursOfSunshine(double hoursOfSunshine) {
        this.hoursOfSunshine = Math.max(hoursOfSunshine, 0);
    }

    //(Post):returned amount of hours of sun
    public double getHoursOfSunshine() {
        return hoursOfSunshine;
    }

    //(Post):returned amount of wolves
    public long getWolfs() {
        return wolves;
    }

    //(Post):returned amount of deer
    public long getDeer() {
        return deer;
    }

    //(Post):returned forestType
    public ForestType getForestType() {
        return forestType;
    }

    //(Post):returned all statistics of the given tree and also displayed the HashMap of the age distribution between
    //the trees in the Forest
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Area of the forest: ").append(area).append("ha\n");
        builder.append("Treepopulation: ").append(treePopulation).append("fm\n");
        builder.append("Agestructure:\n");
        int count = 0;
        for (Map.Entry<Integer, Double> entry : ageStructure.entrySet()) {
            if (entry.getValue() != 0) {
                builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\t");
                count++;
                if (count % 4 == 0)
                    builder.append("\n");
            }

        }
        builder.append("\nHealth: ").append(health).append(" (>= 0.25 && health <= 1)\n");
        builder.append("Targetstock: ").append(targetStock).append("fm\n");
        builder.append("Harvest: ").append(harvest).append("fm\n");
        builder.append("Co2-Stock: ").append(co2Stock).append("\n");
        builder.append("Wolves: ").append(wolves).append("\n");
        builder.append("Deer: ").append(deer).append("\n");
        builder.append("Visitors: ").append(visitors).append("\n");
        return builder.toString();
    }

    // (Pre): years > 0 && years <= bygone years in simulation && history.size == years
    // (Post): A presentation for the average of the given years of the tree data
    public String historyAverage(int years) {
        double averageTreePopulation = 0;
        double averageHealth = 0;
        double averageTargetStock = 0;
        double averageMaxTargetStock = 0;
        double averageHarvest = 0;
        double averageCo2Stock = 0;
        double averageSoilQuality = 0;
        double averageStability = 0;
        double averageWaterStock = 0;
        double averageHoursOfSunshine = 0;
        double averageWolfs = 0;
        double averageDeer = 0;
        double averageVisitors = 0;
        for (int i = 1; i <= years; i++) {
            averageTreePopulation += history.get(i).getTreePopulation();
            averageHealth += history.get(i).getHealth();
            averageTargetStock += history.get(i).getTargetStock();
            averageMaxTargetStock += history.get(i).getMaxTargetStock();
            averageHarvest += history.get(i).getHarvest();
            averageCo2Stock += history.get(i).getco2Vorrat();
            averageSoilQuality += history.get(i).getSoilQuality();
            averageStability += history.get(i).getStability();
            averageWaterStock += history.get(i).getWaterStock();
            averageHoursOfSunshine += history.get(i).getHoursOfSunshine();
            averageWolfs += history.get(i).getWolfs();
            averageDeer += history.get(i).getDeer();
            averageVisitors += history.get(i).getVisitors();
        }
        averageTreePopulation /= years;
        averageHealth /= years;
        averageTargetStock /= years;
        averageMaxTargetStock /= years;
        averageHarvest /= years;
        averageCo2Stock /= years;
        averageSoilQuality /= years;
        averageStability /= years;
        averageWaterStock /= years;
        averageHoursOfSunshine /= years;
        averageWolfs /= years;
        averageDeer /= years;
        averageVisitors /= years;
        StringBuilder builder = new StringBuilder();
        builder.append("-- Average of the past: " + years + " years --").append("\n");
        builder.append("Average tree population: ").append(averageTreePopulation).append(" fm\n");
        builder.append("Average health: ").append(averageHealth).append("\n");
        builder.append("Average target stock: ").append(averageTargetStock).append(" fm\n");
        builder.append("Average max target stock: ").append(averageMaxTargetStock).append(" fm\n");
        builder.append("Average harvest: ").append(averageHarvest).append(" fm\n");
        builder.append("Average co2stock: ").append(averageCo2Stock).append("\n");
        builder.append("Average soil quality: ").append(averageSoilQuality).append("\n");
        builder.append("Average stability: ").append(averageStability).append("\n");
        builder.append("Average water stock: ").append(averageWaterStock).append("\n");
        builder.append("Average hours of sunshine: ").append(averageHoursOfSunshine).append("\n");
        builder.append("Average wolves: ").append(averageWolfs).append("\n");
        builder.append("Average deer: ").append(averageDeer).append("\n");
        builder.append("Average visitors: ").append(averageVisitors).append("\n");
        return builder.toString();
    }
}