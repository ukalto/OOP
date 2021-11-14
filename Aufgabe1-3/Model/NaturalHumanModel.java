package Model;// This model is very close to the NaturalModel.
// Only difference is that there are more humans and they create footpaths for themselves.
// Since the forest is not meant for humans there is going to be no harvest and it
// doesn't matter if footpaths disappear due to growth

import Forest.*;
import Catastrophe.*;

public class NaturalHumanModel extends NaturalModel{
    public NaturalHumanModel(Forest forest, double lossFactor, double growthFactor) {
        super(forest, lossFactor, growthFactor);
    }

    // calculate new tree stock
    @Override
    public void calcStock() {
        // the footpaths reduce or increase (if the forest regrows over the footpath)
        // the targetStock. There are two separate calculations
        // because there could be created a new footpath and overgrown an other footpath at the
        // same time.
        if(Math.random()<0.05)
            forest.setTargetStock(forest.getTargetStock()-(forest.getTargetStock()*0.2));
        if(Math.random()<0.05)
            forest.setTargetStock(forest.getTargetStock()+(forest.getTargetStock()*0.2));
        // the sidewalks impact the growth by a very small factor
        this.getForest().treeGrowth(getGrowth()-(getGrowth()*0.07));
    }

    @Override
    public void calcSpecificFactors() {
        // since the wolves and deers have the same impact as in the Natural Model.
        super.calcSpecificFactors();

        // the visitors have an higher impact on the loss, since they may
        // leave trash behind
        if(forest.getVisitors()>1000){
            setLossFactor(lossFactor+(lossFactor*0.04));
            setGrowthFactor(growthFactor);
        }
        else if(forest.getVisitors()>2000){
            setLossFactor(lossFactor+(lossFactor*0.1));
            setGrowthFactor(growthFactor);
        }
        else if(forest.getVisitors()>10000){
            setLossFactor(lossFactor+(lossFactor*0.2));
            setGrowthFactor(growthFactor);
        }
        else if(forest.getVisitors()>50000){
            setLossFactor(lossFactor+(lossFactor*0.4));
            setGrowthFactor(growthFactor);
        }


    }

    @Override
    protected void catastropheHandler(Catastrophe t) {
        // the impact is always pending of the loss or growth
        // the impact is very similar to the one of the
        // Natural, only storms have a higher chance of
        // causing an infestation, since sticks could lay on the ground
        // therefore two different Infestations could occur if here and
        // in the super class occurs an infestation.
        // also the impact of the possible infestation is higher
        super.catastropheHandler(t);

        if(Math.random()<0.1)
            super.catastropheHandler(new InfestationCatastrophe(2.5));


    }


    @Override
    public String toString() {
        return "-- Natural with humans Model: --\n" + getForest();
    }
}
