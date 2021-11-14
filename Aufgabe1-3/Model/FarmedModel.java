package Model;// this represents the classic farmed model

import Forest.*;
import Tree.*;
import Catastrophe.*;

import java.util.Map;

public class FarmedModel extends BaseFarmingModel {
    protected int yearsWithoutHarvest = 0;
    // the amount harvested from the forest
    protected double harvestTaken = 0.0;
    // the stock before any growth. this is needed for future
    // calculations
    protected double stock = 1.0;

    public FarmedModel(Forest forest, double loss, double growth) {
        super(forest, loss, growth);
        forest.setMaxTargetStock(350);
    }

    @Override
    public void calcStock() {
        // Share of stock for scaling the harvest for the loss
        // Exception for first year; Therefore the if is needed
        if(getForest().getTreePopulation() > 0)
            stock = this.getForest().getTreePopulation();
        if (getLoss() <= 0.3) {
            if (getLoss() >= 0.1) { // if true there is a small catastrophe
                farmOlderThan(45, (double) 1 / 2);
                harvestTaken += (getLoss() / 2);// half of natural loss will be harvested
                this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);
            }
            if (getLoss() < 0.1) {
                yearsWithoutHarvest++;
                if (yearsWithoutHarvest == 11) { //intended harvest
                    farmOlderThan(75, (double) 2/3);
                }
            }
        }
        this.getForest().treeGrowth(getGrowth()); // the natural loss-growth factor

        // add taken wood to loss
        setLoss(getLoss()+((harvestTaken /this.stock)));
    }



    @Override
    public void calcCo2Stock() {
        // the natural co2-stock adjustment
        this.getForest().co2Adjust(getGrowth());
        // the co2-stuck must be adjusted considering the loss
        if (getLoss() >= 0.3) {
            // taken wood doesn't get added to the co2-stock
            co2Impact((1 - (getLoss()-(harvestTaken/this.stock))));
        }
         else if (getLoss() < 0.3) {
            this.co2InsignificantLoss(getLoss() - (harvestTaken/this.stock));
        }
    }

    @Override
    public void calcSpecificFactors() {
        // monoculture has a bigger impact here since the trees get less time to grow
        // keep in mind: all these factors get added to the already calculated factors in the BaseFarmingModel

        if(forest.calcMonoculture()){
            setLossFactor(lossFactor+(lossFactor*0.22));
            setGrowthFactor(growthFactor+(lossFactor*0.1));
        }

        // stability has a bigger impact on this model
        // because there are fewer trees to help each other

        forest.calcStability();
        setLossFactor(lossFactor+lossFactor*(1-(forest.getStability()/3)));
        setGrowthFactor(growthFactor);
    }

    @Override
    protected void catastropheHandler(Catastrophe t) {
        double resist = 0;
        if(t.getCatastropheType() == CatastropheType.Fire){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getFireResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            setLossFactor(0.1*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - (getLoss()-(harvestTaken/stock)))*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());
        }
        if(t.getCatastropheType() == CatastropheType.Freeze){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getFreezeResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // the impact of freeze is very dependent of
            // the last harvest since less tress are affected if the last harvest
            // is closer

            setLossFactor(lossFactor+lossFactor*0.1*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 2)*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Infestation){

            //Infestation causes the harvest of 1/2 of all tress older than 35, since they might die
            infestationFarm(35, (double)1/2,2);

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
        if(t.getCatastropheType() == CatastropheType.Moor){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getInfestationResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // the impact of a moore is depending very much on the
            // current tree population
            setLossFactor(lossFactor+lossFactor*(forest.getTreePopulation()/forest.getTargetStock())*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(getLoss()-(forest.getTreePopulation()/forest.getTargetStock()*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Storm){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getStormResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // storms also depend on the date of the last harvest
            // since there are fewer trees close to one another after
            // a harvest the "domino effect" is very unlikely but possible
            // the impact on the co2-stock is not very high

            setLossFactor(lossFactor+lossFactor*(yearsWithoutHarvest/11.0)*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(getLoss()-t.getDamageFactor()*(1-resist));

            forest.treeGrowth(getGrowth());

            // after a storm there is a chance for an Infestation
            // because there is laying a lot of dead wood and leaves
            // around. The shorter the distance to the last
            // harvest the higher the chance (since there are more
            // free spaces for dead wood)
            if(Math.random()<0.1+(yearsWithoutHarvest/11.0))
                catastropheHandler(new InfestationCatastrophe(1.6));
        }
    }

    protected void farmOlderThan(int n, double proportion) { //proportion: the value that gets harvested
        yearsWithoutHarvest = 0;
        harvestTaken = 0;
        calcProportionAge(n);
        // update all affected values
        for (Map.Entry<Integer, Double> i : this.getForest().getAgeStructure().entrySet()) {
            if (i.getKey() >= n) {
                //calculation: population - population of >=n old trees
                farmOlderThanStep(i, proportion);
            }
        }
    }

    protected void infestationFarm(int n, double p, int amount){
        farmOlderThan(n, p);
        harvestTaken += (getLoss() / amount);// half of natural loss will be harvested
        this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);

        this.getForest().treeGrowth(getGrowth()); // the natural loss-growth factor
    }

    protected void farmOlderThanStep(Map.Entry<Integer, Double> i, double proportion){
        double emergencyHarvest = this.getForest().getTreePopulation() * this.getForest().getAgeStructure().get(i.getKey());
        harvestTaken = emergencyHarvest * proportion;
        this.getForest().setTreePopulation(this.getForest().getTreePopulation() - (emergencyHarvest));
        this.getForest().setHarvest(this.getForest().getHarvest() + (emergencyHarvest * proportion));
        // times the proportion since only the proportion gets harvested
        this.getForest().getAgeStructure().put(i.getKey(), 0.0);
    }

    // adjust the share of the trees
    protected void calcProportionAge(int alter){
        double sum = 0;
        double value = 0;
        int amountProportion=0;
        // sum up the lost share and count the amount of entries that need to be adjusted
        for (Map.Entry<Integer, Double> entry : getForest().getAgeStructure().entrySet()){
            if(entry.getKey()>=alter){
                value+=entry.getValue();
            }
            if(entry.getKey() <alter && entry.getValue() != 0){
                amountProportion++;
            }

        }
        //avoid rounding errors
        if(value < 0) value = 0;
        if(value > 1) value = 1;
        // split up the share of to the remaining ages
        for (Map.Entry<Integer, Double> entry : getForest().getAgeStructure().entrySet()) {
            if (entry.getKey() <alter && entry.getValue() != 0){
                if(sum>=1)
                    entry.setValue(0.0);
                else
                    entry.setValue(entry.getValue() + value /amountProportion);
            }
            if (entry.getKey() <alter && entry.getValue() >1){
                entry.setValue(1.0);
                sum += entry.getValue();
            }

        }
    }

    @Override
    public String toString() {
        return "-- Farmed Model: --\n" + getForest();
    }
}
