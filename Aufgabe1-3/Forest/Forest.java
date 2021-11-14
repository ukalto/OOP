package Forest;

import Tree.Tree;

import java.util.HashMap;
import java.util.Map;

public class Forest {

    // area of the forest in ha
    private final int area = 1;

    // growing trees measured in solid meters (fm) of wood
    private double treePopulation;

    // share of trees (measured in cubic meters of wood) of each tree age
    // (in whole years) on the entire tree population
    // Key = age
    // Value = share
    private final HashMap<Integer, Double> ageStructure;

    // Indicator for diversity and resistance to diseases,
    //pests, environmental influences (smaller is better)
    //(invariant): health> = 0.25 && health <= 1,
    private double health;

    // expected ideal tree population in fm of wood, depending
    // of stage of development, tree species, location and care
    private double targetStock;
    // describes the maximum target stock
    private double maxTargetStock;

    // total amount of wood removed from the forest in cubic meters
    private double harvest;

    // Sum of the forest reserves including forest soil (in
    // Tons of CO2, whereby one ton of CO2 corresponds to one fm of wood)
    private double co2Stock;

    private final HashMap<Tree, Double> speciesStructure;

    private double soilQuality;

    private double stability;

    private double waterStock;

    private double hoursOfSunshine;

    private int wolves;

    private int deer;

    private int visitors;

    private ForestType forestType;

    private final HashMap<Integer, Forest> history = new HashMap<>();

    /* Precondition: tree population> = 0
                     health> = 0.25 && health <= 1
                     target stock> 0
                     max target stock> 0 && max target stock> = target stock
                     harvest> = 0
                     co2 supply> = 0


    */
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

    // assigns a forest type
    public void calcForestType() {
        double beech = 0;
        double blackPine = 0;
        double oakTree = 0;
        double scotsPine = 0;
        double spruceTree = 0;
        for (Map.Entry<Tree, Double> t : speciesStructure.entrySet()) {
            switch (t.getKey().getTreeType()) {
                case Beech:
                    beech = t.getValue();
                    break;
                case BlackPine:
                    blackPine = t.getValue();
                    break;
                case OakTree:
                    oakTree = t.getValue();
                    break;
                case ScotsPine:
                    scotsPine = t.getValue();
                    break;
                case SpruceTree:
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

    public boolean calcMonoculture() {
        return speciesStructure.size() == 1;
    }

    //simulates the animals wolf and deer
    public void simulateAnimalActivity() {
        //System.out.println("Im here: wolves:" + wolves + ", deer:" + deer);
        if (wolves >= deer) { //if there are more wolves than deer, 40% of the wolves starve to death because they cant find any prey
            wolves = (int) Math.floor(wolves * 0.6);
        } else {
            deer = deer - wolves; //wolves eat the wolves
            wolves *= 2 + 1; //wolves replroduce
            if (deer >= 1000) { //if more than 1 000, then there is a overpopulation of deer and they cant reproduce and 20% and another 100 starve to death
                deer = (int) Math.floor(deer * 0.8 - 100);
            } else {
                deer *= deer + 1; // deer exponentialy reproduce
            }
        }
    }

    //updates the tree state of the forest
    public void treeGrowth(double value) {
        // The flag is used to decide whether the value should be adjusted
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
        //Adjust proportions evenly
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
                    //avoid rounding errors
                    if (entry.getKey() != 0 && entry.getValue() < 0)
                        entry.setValue(0.0);
                }
            }
        }
    }

    // adjusts the age structure (n + 1)
    public void increaseAge(double loss) {
        //Create a copy of the map so that new pairs can be inserted
        Map<Integer, Double> temp = new HashMap<>();
        double sum = 0;
        for (Map.Entry<Integer, Double> entry : ageStructure.entrySet()) {
            //Set proportion of trees of age 0 to failure
            if (entry.getKey() == 0) {
                if (entry.getValue() != 0) {
                    //to avoid rounding errors
                    if ((entry.getValue() * (1 - loss)) > 0) {
                        sum += entry.getValue() * (1 - loss);
                        temp.put(1, sum);
                    }

                }
                temp.put(0, loss);
                sum += loss;
            } else if (entry.getKey() == 250) { //Trees can live a maximum of 250 years
                temp.put(0, temp.getOrDefault(0, 0.0) + entry.getValue());
            } else {
                //Adjust all other ages
                int alter = entry.getKey();
                double share = entry.getValue();
                if (share > 0)
                    //avoid rounding errors
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

        // to avoid any rounding errors
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


    // adjusts the target stock by the given value
    // precondition value> = 0
    public void increaseTargetStock(double value) {
        if (targetStock + value < maxTargetStock)
            targetStock += value;
        else
            targetStock = maxTargetStock;
    }

    //
    // adjusts the Co2 supply by the given value
    public void co2Adjust(double stock) {
        if (co2Stock + stock < 0)
            co2Stock = 0;
        else
            co2Stock += stock;

        if (!(co2Stock >= 0))
            co2Stock = 0;
    }

    public double getTreePopulation() {
        return treePopulation;
    }

    public void setTreePopulation(double treePopulation) {
        if (this.treePopulation + treePopulation < 0 || !(this.treePopulation + treePopulation >= 0))
            this.treePopulation = 0;
        else
            this.treePopulation = treePopulation;
    }

    // this depends on the forestType: 0 worst; 1 best
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

    // 1 = best; 0 = worst
    public void calcWaterstock(){
        calcSoil();
        calcStability();

        //scale amount of sun hours down to 0 to 1
        double scaledSunHours = hoursOfSunshine/(12*365);

        //the water stock is calculated with the average of the stability, soil and the sun hours
        setWaterStock((stability+soilQuality+scaledSunHours)/3);
    }


    // 1 = best; 0 = worst
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
        //if the forest contains more young wood the stability is higher
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

    public void makeHistoryEntry(int year) {
        history.put(year, new Forest(this.treePopulation, null, this.health, this.targetStock, this.maxTargetStock,
                this.harvest, this.co2Stock, this.soilQuality, this.stability, this.waterStock, this.hoursOfSunshine,
                null, this.wolves, this.deer, this.visitors));
    }

    public HashMap<Integer, Forest> getHistory() {
        return history;
    }

    public HashMap<Integer, Double> getAgeStructure() {
        return ageStructure;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getTargetStock() {
        return targetStock;
    }

    public void setTargetStock(double targetStock) {
        if (targetStock < 0)
            this.targetStock = 0;
        else this.targetStock = Math.min(targetStock, maxTargetStock);
    }

    public double getMaxTargetStock() {
        return maxTargetStock;
    }

    public double getHarvest() {
        return harvest;
    }

    public void setHarvest(double harvest) {
        this.harvest = harvest;
    }

    public void setMaxTargetStock(double zielbestand) {
        this.maxTargetStock = zielbestand;
    }

    public void setco2Vorrat(double co2Vorrat) {
        this.co2Stock = co2Vorrat;
    }

    public double getco2Vorrat() {
        return this.co2Stock;
    }

    public void setVisitors(int visitors) {
        this.visitors = Math.max(visitors, 0);
    }

    public int getVisitors() {
        return this.visitors;
    }

    public double getSoilQuality() {
        return soilQuality;
    }

    public void setSoilQuality(double soilQuality) {
        this.soilQuality = soilQuality;
    }


    public double getStability() {
        return stability;
    }

    public HashMap<Tree, Double> getSpeciesStructure() {
        return this.speciesStructure;
    }

    public void setStability(double stability) {
        if (stability < 0)
            this.stability = 0;
        else if (stability > 1)
            this.stability = 1;
        else
            this.stability = stability;
    }

    public double getWaterStock() {
        return waterStock;
    }

    public void setWaterStock(double waterStock) {
        if (waterStock < 0)
            this.waterStock = 0;
        else if (waterStock > 1)
            this.waterStock = 1;
        else
            this.waterStock = waterStock;
    }

    public void setHoursOfSunshine(double hoursOfSunshine) {
        this.hoursOfSunshine = Math.max(hoursOfSunshine, 0);
    }

    public double getHoursOfSunshine() {
        return hoursOfSunshine;
    }

    public long getWolfs() {
        return wolves;
    }

    public long getDeer() {
        return deer;
    }

    public ForestType getForestType() {
        return forestType;
    }

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