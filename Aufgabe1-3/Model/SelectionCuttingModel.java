package Model;

import Forest.*;
import Tree.*;
import Catastrophe.*;

import java.util.Map;

public class SelectionCuttingModel extends FarmedModel {

    public SelectionCuttingModel(Forest forest, double loss, double growth) {
        super(forest, loss, growth);
    }

    private int oldestTree = 0;
    @Override
    public void calcStock() {
        for (Map.Entry<Integer, Double> entry : forest.getAgeStructure().entrySet()) {
            if(entry.getValue()>0)
                oldestTree = entry.getKey();
        }
        // Share of stock for scaling the harvest for the loss
        // Exception for first year; Therefore the if is needed
        if(getForest().getTreePopulation() > 0)
            stock = this.getForest().getTreePopulation();
        if (getLoss() <= 0.3) {
            if (getLoss() >= 0.1) { // if true there is a small catastrophe
                // Farm everything in 3 different age groups and not just the old ones (if possible otherwise
                // just in the groups that are possible)
                harvestStep();
            }
            if (getLoss() < 0.1) {
                yearsWithoutHarvest++;
                if (yearsWithoutHarvest == 11) { //intended harvest
                    farmBetween(oldestTree/10,oldestTree/9,(double)1/2);
                    farmBetween(oldestTree/6,oldestTree/5,(double)1/2);
                    farmBetween((int)(oldestTree/1.2),oldestTree,(double)1/2);
                }
            }
        }
        this.getForest().treeGrowth(getGrowth()); // the natural loss-growth factor

        // add taken wood to loss
        setLoss(getLoss()+((harvestTaken /this.stock)));
    }

    private void harvestStep(){
        farmBetween(oldestTree/10,oldestTree/9,(double)1/2);
        farmBetween(oldestTree/6,oldestTree/5,(double)1/2);
        farmBetween((int)(oldestTree/1.2),oldestTree,(double)1/2);
        harvestTaken += (getLoss() / 2);// half of natural loss will be harvested
        this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);

        this.getForest().treeGrowth(getGrowth()); // the natural loss-growth factor
    }

    @Override
    protected void catastropheHandler(Catastrophe t) {
        // due to the fact that this is very similar to the classic FarmedModel
        // many of the catastrophes have the same impact.
        // only difference is for infestation and storm
        double resist = 0;
        if(t.getCatastropheType() == CatastropheType.Infestation){
            //Infestation causes the a harvest of 1/2 of all tress in the three groups, since they might die
            harvestStep();

            // add taken wood to loss
            setLoss(getLoss()+((harvestTaken /this.stock)));

            // the co2-stock is impacted by the amount of
            // harvest
            if (getLoss() >= 0.3) {
                co2Impact((1 - (getLoss()-(harvestTaken/this.stock))));
            }
            else if (getLoss() < 0.3) {
                this.co2InsignificantLoss(getLoss() - (harvestTaken /this.stock));
            }
        }
        else if(t.getCatastropheType() == CatastropheType.Storm){
            // due to the fact that there is more variance to the heights of the trees the impact is less
            //
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getStormResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // storms also depend on the date of the last harvest
            // since there are less trees close to one another after
            // a harvest the "domino effect" is very unlikely but possible
            // the impact on the co2-stock is not very high

            setLossFactor(getLoss()*(yearsWithoutHarvest/15.0)*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss() /14)*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

            // after a storm there is a chance for an Infestation
            // because there is laying a lot of dead wood and leaves
            // around. The shorter the distance to the last
            // harvest the higher the chance (since there are more
            // free spaces for dead wood)
            if(Math.random()<0.1+(yearsWithoutHarvest/20.0))
                catastropheHandler(new InfestationCatastrophe(2.2));
        }
        else
            super.catastropheHandler(t);
    }

    public void farmBetween(int lower, int upper, double proportion) { //proportion: the value that gets harvested
        yearsWithoutHarvest = 0;
        harvestTaken = 0;
        calcProportionAge(lower, upper);
        // update all affected values
        for (Map.Entry<Integer, Double> i : this.getForest().getAgeStructure().entrySet()) {
            if (i.getKey() >= lower && i.getKey() <=upper) {
                //calculation: population - population of population of >=n old trees
                farmOlderThanStep(i, proportion);
            }
        }
    }

    // adjust the share of the trees
    public void calcProportionAge(int lower, int upper){
        double sum = 0;
        double value = 0;
        int amountProportion=0;
        // sum up the lost share and count the amount of entries that need to be adjusted
        for (Map.Entry<Integer, Double> entry : getForest().getAgeStructure().entrySet()){
            if(entry.getKey()>=lower && entry.getKey()<=upper){
                value+=entry.getValue();
            }
            if((entry.getKey() <lower && entry.getValue() != 0)||(entry.getKey() > upper && entry.getValue() != 0)){
                amountProportion++;
            }

        }
        //avoid rounding errors
        if(value < 0) value = 0;
        if(value > 1) value = 1;
        // split up the share of to the remaining ages
        for (Map.Entry<Integer, Double> entry : getForest().getAgeStructure().entrySet()) {
            if ((entry.getKey() <lower && entry.getValue() != 0)||(entry.getKey() > upper && entry.getValue() != 0)){
                if(sum>=1)
                    entry.setValue(0.0);
                else
                    entry.setValue(entry.getValue() + value /amountProportion);
            }
            if ((entry.getKey() <lower && entry.getValue() >1)||(entry.getKey() <upper && entry.getValue() >1)){
                entry.setValue(1.0);
            }
            sum += entry.getValue();

        }
    }

    @Override
    public String toString() {
        return "-- Selection cutting Model: --\n" + getForest();
    }
}
