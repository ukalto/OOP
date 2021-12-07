package Model;

import java.util.Map;

import Forest.*;
import Tree.*;
import Catastrophe.*;

public class SelectionCuttingModel extends FarmedModel {

    // (Pre): forest != NULL; loss >= 0 && loss <= 1; growth >= 0 && growth <= 1
    // (Post): sets the values: forest (!= null), lossFactor (between 0 and 1), growthFactor (between 0 and 1),
    //         loss (between 0 and 1) and growth (between 0 and 1) of this factor and
    //         also sets the max target stock to 350
    public SelectionCuttingModel(Forest forest, double loss, double growth) {
        super(forest, loss, growth);
    }

    // (I): oldestTree >= 0
    private int oldestTree = 0;

    // (Post): calculates and updates the tree population (and age structure) of the forest stored in this instance
    //      and might also invoke a harvest due to the loss of the corresponding year or the years without harvest
    //      which has additional impact on the population of the forest. If true the forest gets harvested
    //      in three different age groups.
    //      - stock for sure between 0 and targetStock; and targetStock >= 0 and targetStock <= maxTargetStock;
    //      yearsWithoutHarvest >= 0 && yearsWithoutHarvest <= 11
    // (History-C):for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //              provide correct calculations: calcAgeStructure(), calcHealth(), calcTargetStock(),
    //              calcCo2Stock()
    @Override
    public void calcStock() {
        for (Map.Entry<Integer, Double> entry : forest.getAgeStructure().entrySet()) {
            if (entry.getValue() > 0)
                oldestTree = entry.getKey();
        }

        if (getForest().getTreePopulation() > 0)
            stock = this.getForest().getTreePopulation();
        if (getLoss() <= 0.3) {
            if (getLoss() >= 0.1) {
                harvestStep();
            }
            if (getLoss() < 0.1) {
                yearsWithoutHarvest++;
                if (yearsWithoutHarvest == 11) {
                    farmBetween(oldestTree / 10, oldestTree / 9, (double) 1 / 2);
                    farmBetween(oldestTree / 6, oldestTree / 5, (double) 1 / 2);
                    farmBetween((int) (oldestTree / 1.2), oldestTree, (double) 1 / 2);
                }
            }
        }
        this.getForest().treeGrowth(getGrowth());

        setLoss(getLoss() + ((harvestTaken / this.stock)));
    }

    // (Post): Harvests the trees in the age groups oldestTree/10,oldestTree/9; oldestTree/6,oldestTree/5; and
    //      oldestTree/1.2),oldestTree updating the values harvestTaken (>= 0) and the population of the forest
    //      and the age structure (all values still between 0 and 1).
    private void harvestStep() {
        farmBetween(oldestTree / 10, oldestTree / 9, (double) 1 / 2);
        farmBetween(oldestTree / 6, oldestTree / 5, (double) 1 / 2);
        farmBetween((int) (oldestTree / 1.2), oldestTree, (double) 1 / 2);
        harvestTaken += (getLoss() / 2);// half of natural loss will be harvested
        this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);

        this.getForest().treeGrowth(getGrowth()); // the natural loss-growth factor
    }


    //  (Post): calculates and sets additional influence factors (some by calling other functions)
    //      All of them decreases co2-stock - still between 0 and 1; increases lossFactor - still between 0 and 1
    //                  and increases growthFactor - still between 0 and 1
    @Override
    protected void catastropheHandler(Catastrophe t) {
        double resist = 0;
        if (t.getCatastropheType() == CatastropheType.Infestation) {
            harvestStep();

            setLoss(getLoss() + ((harvestTaken / this.stock)));

            if (getLoss() >= 0.3) {
                co2Impact((1 - (getLoss() - (harvestTaken / this.stock))));
            } else if (getLoss() < 0.3) {
                this.co2InsignificantLoss(getLoss() - (harvestTaken / this.stock));
            }
        } else if (t.getCatastropheType() == CatastropheType.Storm) {

            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getStormResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(getLoss() * (yearsWithoutHarvest / 15.0) * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss() / 14) * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());

            if (Math.random() < 0.1 + (yearsWithoutHarvest / 20.0))
                catastropheHandler(new InfestationCatastrophe(2.2));
        } else
            super.catastropheHandler(t);
    }

    // BAD: since the methods farmBetween() and calcProportionAge() use the same values for lower
    //          and upper the values could be stored somewhere or at least be compared. Otherwise
    //          this could cause wrong calculations - meaning the object coupling isn't perfect here.
    //          This probably happened due to the fact that the methods were combined at first and
    //          split up later in the programming process and the object coupling wasn't regarded too much.

    // (Pre): lower >= 0; upper >= lower; proportion >= 0 && proportion <= 1
    // (Post): updates the the harvestTaken (>= 0) and the tree (age) population/structure
    //         harvesting all trees between lower and upper (all values still between 0 and 1)
    //         also sets yearsWithoutHarvest to 0
    public void farmBetween(int lower, int upper, double proportion) {
        yearsWithoutHarvest = 0;
        harvestTaken = 0;
        calcProportionAge(lower, upper);

        for (Map.Entry<Integer, Double> i : this.getForest().getAgeStructure().entrySet()) {
            if (i.getKey() >= lower && i.getKey() <= upper) {
                farmOlderThanStep(i, proportion);
            }
        }
    }


    // (Pre): lower >= 0; upper >= lower
    // (Post): updates the tree (age) population/structure removing every between lower (included) and upper (included)
    //         from the forest and calculating the new proportions (all proportions still between 0 and 1)
    public void calcProportionAge(int lower, int upper) {
        double sum = 0;
        double value = 0;
        int amountProportion = 0;

        for (Map.Entry<Integer, Double> entry : getForest().getAgeStructure().entrySet()) {
            if (entry.getKey() >= lower && entry.getKey() <= upper) {
                value += entry.getValue();
            }
            if ((entry.getKey() < lower && entry.getValue() != 0) || (entry.getKey() > upper && entry.getValue() != 0)) {
                amountProportion++;
            }

        }

        if (value < 0) value = 0;
        if (value > 1) value = 1;

        for (Map.Entry<Integer, Double> entry : getForest().getAgeStructure().entrySet()) {
            if ((entry.getKey() < lower && entry.getValue() != 0) || (entry.getKey() > upper && entry.getValue() != 0)) {
                if (sum >= 1)
                    entry.setValue(0.0);
                else
                    entry.setValue(entry.getValue() + value / amountProportion);
            }
            if ((entry.getKey() < lower && entry.getValue() > 1) || (entry.getKey() < upper && entry.getValue() > 1)) {
                entry.setValue(1.0);
            }
            sum += entry.getValue();

        }
    }

    // (Post): returns a string with the name of the Model
    //      and the details of the forest stored in this instance - never empty
    @Override
    public String toString() {
        return "-- Selection cutting Model: --\n" + getForest();
    }
}
