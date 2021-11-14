package Model;

import Forest.*;
import Tree.*;
import Catastrophe.*;

import java.util.Map;

public class StripSystemModel extends FarmedModel {
    public StripSystemModel(Forest forest, double growthFactor, double lossFactor) {
        super(forest, growthFactor, lossFactor);
    }

    private double averageMaxAge = 0;

    @Override
    public void calcStock(){
        for (Map.Entry<Tree, Double> i : forest.getSpeciesStructure().entrySet()) {
            averageMaxAge += i.getKey().getMaxAge();
        }
        averageMaxAge /= forest.getSpeciesStructure().size();

        // Share of stock for scaling the harvest for the loss
        // Exception for first year; Therefore the if is needed
        if(getForest().getTreePopulation() > 0)
            stock = this.getForest().getTreePopulation();
        if (getLoss() <= 0.3) {
            if (getLoss() >= 0.1) { // if true there is a small catastrophe
                // harvest Everything older than 1/2 of max age - Because the rest of the forest that's
                // remaining is in danger of getting lost
                farmOlderThan((int)(averageMaxAge/3), (double) 1 / 2);
                harvestTaken += (getLoss() / 2);// half of natural loss will be harvested
                this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);
            }
            if (getLoss() < 0.1) {
                yearsWithoutHarvest++;
                if (yearsWithoutHarvest == 7) { //intended harvest
                    farmOlderThan((int)(averageMaxAge/2), (double) 2/3);
                }
            }
        }
        this.getForest().treeGrowth(getGrowth()); // the natural loss-growth factor

        // add taken wood to loss
        setLoss(getLoss()+((harvestTaken /this.stock)));
    }

    @Override
    public void calcSpecificFactors() {
        // bad soil has 1.5 times the impact on the loss on this model
        forest.calcSoil();
        if(forest.getSoilQuality()<0.5){
            setLossFactor(lossFactor+(lossFactor*((1-forest.getSoilQuality())))/2);
            setGrowthFactor(growthFactor);
        }
    }

    @Override
    protected void catastropheHandler(Catastrophe t) {
        // Since this model is similar to the
        // cut over model the impact is also similar
        // but less impactful
        double resist = 0;
        if(t.getCatastropheType() == CatastropheType.Freeze){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getFreezeResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // the impact of freeze is very dependent of
            // the last harvest since less tress are affected if the last harvest
            // is closer and also since there are likely to be much less
            // trees around the impact is less than in the farmed model.

            setLossFactor(lossFactor+(lossFactor/3*t.getDamageFactor()*(1-resist)));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 5)*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Infestation){

            //Infestation causes the a harvest of 1/3 of all tress older than averageMaxAge/3, since they might die
            infestationFarm((int)(averageMaxAge/3),(double) 1/3,2);

            // add taken wood to loss
            setLoss(getLoss()+((harvestTaken /this.stock)));

            // the co2-stock is impacted by the amount of
            // harvest
            if (getLoss() >= 0.25) {
                co2Impact((1 - (getLoss()-(harvestTaken/this.stock))));
            }
            else if (getLoss() < 0.25) {
                this.co2InsignificantLoss(getLoss() - (harvestTaken /this.stock));
            }
        }
        if(t.getCatastropheType() == CatastropheType.Moor){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getInfestationResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // the impact of a moore is depending very much on the
            // current tree population and here also on the soil quality
            forest.calcSoil();
            setLossFactor(lossFactor+lossFactor*((forest.getSoilQuality()/15)*forest.getTreePopulation()/forest.getTargetStock())*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(lossFactor+lossFactor*((forest.getSoilQuality()/14)*((forest.getTreePopulation()/forest.getTargetStock()))*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Storm){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getStormResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // storms also depend on the date of the last harvest
            // since there are less trees close to one another after
            // a harvest the "domino effect" is very unlikely but possible
            // the impact on the co2-stock is not very high

            setLossFactor(getLoss()*(yearsWithoutHarvest/18.0)*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss() /14)*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

            // after a storm there is a chance for an Infestation
            // because there is laying a lot of dead wood and leaves
            // around. The shorter the distance to the last
            // harvest the higher the chance (since there are more
            // free spaces for dead wood)
            if(Math.random()<0.1+(yearsWithoutHarvest/18.0))
                catastropheHandler(new InfestationCatastrophe(1.4));
        }
        else
            super.catastropheHandler(t);
    }

    @Override
    public String toString() {
        return "-- Stripsystem Model: --\n" + getForest();
    }
}
