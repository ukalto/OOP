package Model;

import java.util.Map;

import Forest.*;
import Tree.*;
import Catastrophe.*;

public class FarmedModel extends BaseFarmingModel {

    // (I): yearsWithoutHarvest >= 0 && yearsWithoutHarvest <= 11
    // (Hist-C): can only be increased one by one or set to 0
    protected int yearsWithoutHarvest = 0;

    // (I): harvestTaken >= 0
    protected double harvestTaken = 0.0;

    // (I): stock >= 0
    protected double stock = 1.0;

    // BAD: The constructor violates the rules of cohesion and subtyping, because growth and loss should be named
    //           lossFactor and growthFactor, since in our model all of the four values exist and this
    //           could cause confusion and wrong calculations (therefore the rule is also violated in
    //           most of the subtypes of this model). This probably happened because all of us worked
    //           on the different models in task 1 causing the naming error and not noticing it in further inspections.

    // (Pre): forest != NULL; loss >= 0 && loss <= 1; growth >= 0 && growth <= 1
    // (Post): sets the values: forest (!=null), lossFactor (between 0 and 1), growthFactor (between 0 and 1),
    //         loss (between 0 and 1) and growth (between 0 and 1) of this factor and
    //         also sets the max target stock to 350
    public FarmedModel(Forest forest, double loss, double growth) {
        super(forest, loss, growth);
        forest.setMaxTargetStock(350);
    }

    // (Post): calculates and updates the tree population (and age structure) of the forest stored in this instance
    //      and might also invoke a harvest due to the loss of the corresponding year or the years without harvest
    //      which has additional impact on the population of the forest --> decreases stock
    //      - stock for sure between 0 and targetStock; and targetStock >= 0 and targetStock <= maxTargetStock
    //      yearsWithoutHarvest >= 0 && yearsWithoutHarvest <= 11
    // (History-C):for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //              provide correct calculations: calcAgeStructure(), calcHealth(), calcTargetStock(),
    //              calcCo2Stock()
    @Override
    public void calcStock() {
        if (getForest().getTreePopulation() > 0)
            stock = this.getForest().getTreePopulation();
        if (getLoss() <= 0.3) {
            if (getLoss() >= 0.1) {
                farmOlderThan(45, (double) 1 / 2);
                harvestTaken += (getLoss() / 2);
                this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);
            }
            if (getLoss() < 0.1) {
                yearsWithoutHarvest++;
                if (yearsWithoutHarvest == 11) {
                    farmOlderThan(75, (double) 2 / 3);
                }
            }
        }
        this.getForest().treeGrowth(getGrowth());

        setLoss(getLoss() + ((harvestTaken / this.stock)));
    }


    // (Post): calculates the CO2 stock of the forest considering the loss, growth and harvest stored in this instance
    //      and updates the value (it's value is now between 0 and 1).
    @Override
    public void calcCo2Stock() {
        this.getForest().co2Adjust(getGrowth());

        if (getLoss() >= 0.3) {

            co2Impact((1 - (getLoss() - (harvestTaken / this.stock))));
        } else if (getLoss() < 0.3) {
            this.co2InsignificantLoss(getLoss() - (harvestTaken / this.stock));
        }
    }

    //  (Post): calculates and sets additional influence factors (some by calling other functions)
    //          changes lossFactor and growthFactor - both now between 0 and 1
    //  (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //               provide correct calculations: calcStock(), calcAgeStructure(), calcHealth(),
    //               calcTargetStock(), calcCo2Stock()
    @Override
    public void calcSpecificFactors() {
        if (forest.calcMonoculture()) {
            setLossFactor(lossFactor + (lossFactor * 0.22));
            setGrowthFactor(growthFactor + (lossFactor * 0.1));
        }

        forest.calcStability();
        setLossFactor(lossFactor + lossFactor * (1 - (forest.getStability() / 3)));
        setGrowthFactor(growthFactor);
    }

    // (Post): calculates the impact of following catastrophes on the forest and updates affected values:
    //      Fire, Freeze, Infestation, Moor, Storm
    //      All of them decreases co2-stock - still between 0 and 1; increases lossFactor - still between 0 and 1
    //                  and increases growthFactor - still between 0 and 1
    @Override
    protected void catastropheHandler(Catastrophe t) {
        double resist = 0;
        if (t.getCatastropheType() == CatastropheType.Fire) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getFireResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(0.1 * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - (getLoss() - (harvestTaken / stock))) * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());
        }
        if (t.getCatastropheType() == CatastropheType.Freeze) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getFreezeResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(lossFactor + lossFactor * 0.1 * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 2) * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());

        }
        if (t.getCatastropheType() == CatastropheType.Infestation) {

            infestationFarm(35, (double) 1 / 2, 2);

            setLoss(getLoss() + ((harvestTaken / this.stock)));

            if (getLoss() >= 0.3) {
                co2Impact((1 - (getLoss() - (harvestTaken / this.stock))));
            } else if (getLoss() < 0.3) {
                this.co2InsignificantLoss(getLoss() - (harvestTaken / this.stock));
            }
        }
        if (t.getCatastropheType() == CatastropheType.Moor) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getInfestationResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(lossFactor + lossFactor * (forest.getTreePopulation() / forest.getTargetStock()) * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(getLoss() - (forest.getTreePopulation() / forest.getTargetStock() * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());

        }
        if (t.getCatastropheType() == CatastropheType.Storm) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getStormResistance();
            }
            resist /= forest.getSpeciesStructure().size();


            setLossFactor(lossFactor + lossFactor * (yearsWithoutHarvest / 11.0) * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(getLoss() - t.getDamageFactor() * (1 - resist));

            forest.treeGrowth(getGrowth());

            if (Math.random() < 0.1 + (yearsWithoutHarvest / 11.0))
                catastropheHandler(new InfestationCatastrophe(1.6));
        }
    }

    // (Pre): n >= 0; proportion >= 0 && proportion <= 1
    // (Post): updates the the harvestTaken and the tree (age) population/structure harvesting all trees older than
    //         n --> stock gets decreased but is still >= 0; also sets yearsWithoutHarvest to 0
    protected void farmOlderThan(int n, double proportion) {
        yearsWithoutHarvest = 0;
        harvestTaken = 0;
        calcProportionAge(n);

        for (Map.Entry<Integer, Double> i : this.getForest().getAgeStructure().entrySet()) {
            if (i.getKey() >= n) {
                farmOlderThanStep(i, proportion);
            }
        }
    }

    // (Pre): n >= 0; p >= 0 && p <= 1; amount >= 0
    // (Post): updates the value of the harvest and the tree (age) population/structure with the help
    //         of farmOlderThan(n, p); harvest gets increased and stock gets reduced but still >= 0
    protected void infestationFarm(int n, double p, int amount) {
        farmOlderThan(n, p);
        harvestTaken += (getLoss() / amount);
        this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);

        this.getForest().treeGrowth(getGrowth());
    }

    // (Pre): i != NULL; proportion >= 0 && proportion <= 1
    // (Post): updates the value of the harvest and the tree (age) population/structure; harvest gets increased and
    //         age structure of forest does change - all proportions still >= 0
    protected void farmOlderThanStep(Map.Entry<Integer, Double> i, double proportion) {
        double emergencyHarvest = this.getForest().getTreePopulation() * this.getForest().getAgeStructure().get(i.getKey());
        harvestTaken = emergencyHarvest * proportion;
        this.getForest().setTreePopulation(this.getForest().getTreePopulation() - (emergencyHarvest));
        this.getForest().setHarvest(this.getForest().getHarvest() + (emergencyHarvest * proportion));

        this.getForest().getAgeStructure().put(i.getKey(), 0.0);
    }

    // (Pre): alter >= 0
    // (Post): updates the tree (age) population/structure removing every tree >= alter from the forest
    //         and calculating the new proportions - changes the age structure of the forest; all values still >= 0
    protected void calcProportionAge(int alter) {
        double sum = 0;
        double value = 0;
        int amountProportion = 0;

        for (Map.Entry<Integer, Double> entry : getForest().getAgeStructure().entrySet()) {
            if (entry.getKey() >= alter) {
                value += entry.getValue();
            }
            if (entry.getKey() < alter && entry.getValue() != 0) {
                amountProportion++;
            }

        }

        if (value < 0) value = 0;
        if (value > 1) value = 1;

        for (Map.Entry<Integer, Double> entry : getForest().getAgeStructure().entrySet()) {
            if (entry.getKey() < alter && entry.getValue() != 0) {
                if (sum >= 1)
                    entry.setValue(0.0);
                else
                    entry.setValue(entry.getValue() + value / amountProportion);
            }
            if (entry.getKey() < alter && entry.getValue() > 1) {
                entry.setValue(1.0);
                sum += entry.getValue();
            }

        }
    }

    // (Post): returns a string with the name of the Model
    //      and the details of the forest stored in this instance - never empty
    @Override
    public String toString() {
        return "-- Farmed Model: --\n" + getForest();
    }
}
