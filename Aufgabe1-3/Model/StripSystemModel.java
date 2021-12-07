package Model;

import java.util.Map;

import Forest.*;
import Tree.*;
import Catastrophe.*;

public class StripSystemModel extends FarmedModel {

    // (I): yearsWithoutHarvest >= 0 && yearsWithoutHarvest <= 7
    // yearsWithoutHarvest

    // (Pre): forest != NULL; loss >= 0 && loss <= 1; growth >= 0 && growth <= 1
    // (Post): sets the values: forest (!= null), lossFactor (between 0 and 1), growthFactor (between 0 and 1),
    //         loss (between 0 and 1) and growth (between 0 and 1) of this factor and
    //         also sets the max target stock to 350
    public StripSystemModel(Forest forest, double growthFactor, double lossFactor) {
        super(forest, growthFactor, lossFactor);
    }

    // (I): averageMaxAge >= 0 && averageMaxAge <= max age in forest.ageStructure
    private double averageMaxAge = 0;

    // (Post): calculates and updates the tree population (and age structure) of the forest stored in this instance
    //      and might also invoke a harvest due to the loss of the corresponding year or the years without harvest considering
    //      the average maximum age of the trees in the forest of this instance.
    //      This has additional impact on the population of the forest.
    //      - stock for sure between 0 and targetStock; and targetStock >= 0 and targetStock <= maxTargetStock;
    //      yearsWithoutHarvest >= 0 && yearsWithoutHarvest <= 7
    // (History-C):for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //              provide correct calculations: calcAgeStructure(), calcHealth(), calcTargetStock(),
    //              calcCo2Stock()
    @Override
    public void calcStock() {
        for (Map.Entry<Tree, Double> i : forest.getSpeciesStructure().entrySet()) {
            averageMaxAge += i.getKey().getMaxAge();
        }
        averageMaxAge /= forest.getSpeciesStructure().size();

        if (getForest().getTreePopulation() > 0)
            stock = this.getForest().getTreePopulation();
        if (getLoss() <= 0.3) {
            if (getLoss() >= 0.1) {
                farmOlderThan((int) (averageMaxAge / 3), (double) 1 / 2);
                harvestTaken += (getLoss() / 2);
                this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);
            }
            if (getLoss() < 0.1) {
                yearsWithoutHarvest++;
                if (yearsWithoutHarvest == 7) {
                    farmOlderThan((int) (averageMaxAge / 2), (double) 2 / 3);
                }
            }
        }
        this.getForest().treeGrowth(getGrowth());


        setLoss(getLoss() + ((harvestTaken / this.stock)));
    }


    //  (Post): calculates and sets additional influence factors (some by calling other functions)
    //  (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //               provide correct calculations: calcStock(), calcAgeStructure(), calcHealth(),
    //               calcTargetStock(), calcCo2Stock()
    @Override
    public void calcSpecificFactors() {

        forest.calcSoil();
        if (forest.getSoilQuality() < 0.5) {
            setLossFactor(lossFactor + (lossFactor * ((1 - forest.getSoilQuality()))) / 2);
            setGrowthFactor(growthFactor);
        }
    }

    // (Post): calculates the impact of following catastrophes on the forest and updates affected values:
    //      Fire, Freeze, Infestation, Moor, Storm - for Fire it calls the method of the super() class
    //      All of them decreases co2-stock - still between 0 and 1; increases lossFactor - still between 0 and 1
    //                  and increases growthFactor - still between 0 and 1
    @Override
    protected void catastropheHandler(Catastrophe t) {
        double resist = 0;
        if (t.getCatastropheType() == CatastropheType.Freeze) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getFreezeResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(lossFactor + (lossFactor / 3 * t.getDamageFactor() * (1 - resist)));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 5) * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());

        }
        if (t.getCatastropheType() == CatastropheType.Infestation) {

            infestationFarm((int) (averageMaxAge / 3), (double) 1 / 3, 2);

            setLoss(getLoss() + ((harvestTaken / this.stock)));

            if (getLoss() >= 0.25) {
                co2Impact((1 - (getLoss() - (harvestTaken / this.stock))));
            } else if (getLoss() < 0.25) {
                this.co2InsignificantLoss(getLoss() - (harvestTaken / this.stock));
            }
        }
        if (t.getCatastropheType() == CatastropheType.Moor) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getInfestationResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            forest.calcSoil();
            setLossFactor(lossFactor + lossFactor * ((forest.getSoilQuality() / 15) * forest.getTreePopulation() / forest.getTargetStock()) * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(lossFactor + lossFactor * ((forest.getSoilQuality() / 14) * ((forest.getTreePopulation() / forest.getTargetStock())) * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());

        }
        if (t.getCatastropheType() == CatastropheType.Storm) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getStormResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(getLoss() * (yearsWithoutHarvest / 18.0) * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss() / 14) * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());

            if (Math.random() < 0.1 + (yearsWithoutHarvest / 18.0))
                catastropheHandler(new InfestationCatastrophe(1.4));
        } else
            super.catastropheHandler(t);
    }

    // (Post): returns a string with the name of the Model
    //      and the details of the forest stored in this instance - never empty
    @Override
    public String toString() {
        return "-- Stripsystem Model: --\n" + getForest();
    }
}
