package Model;// this represents the classic natural model

import Forest.*;
import Tree.*;
import Catastrophe.*;

import java.util.Map;

public class NaturalModel extends BaseFarmingModel {
    public NaturalModel(Forest forest, double lossFactor, double growthFactor) {
        super(forest, lossFactor, growthFactor);
    }

    // calculate new tree stock
    @Override
    public void calcStock() {
        this.getForest().treeGrowth(getGrowth());
    }

    // calculate Co2-loss with loss
    @Override
    public void calcCo2Stock() {
        // Since the Co2-levels of the natural model are only affected by
        // the growth and loss there are no new calculations needed.
        this.getForest().co2Adjust(getGrowth());
        if (getLoss() < 0.3) {
            this.co2InsignificantLoss(getLoss());
        } else if (getLoss() >= 0.3) {
            co2Impact(1 - getLoss() / 2);
        }
    }

    @Override
    public void calcSpecificFactors() {
        // The only difference in the Natural Model is that
        // Deers have an higher impact on the loss and growth

        //UpdateDeersAndWolvesFactor (add to the factors already calculated in calcInfluenceFactors)
        if(forest.getDeer()>forest.getWolfs()*1.2){
            setLossFactor(lossFactor+(lossFactor*0.03));
        }
        else if(forest.getDeer()>forest.getWolfs()*1.5){
            setLossFactor(lossFactor+(lossFactor*0.04));
        }
        else if(forest.getDeer()>forest.getWolfs()*1.7){
            setLossFactor(lossFactor+(lossFactor*0.2));
        }
        else if(forest.getDeer()>forest.getWolfs()*2){
            setLossFactor(lossFactor+(lossFactor*0.3));
        }
        else if(forest.getDeer()>forest.getWolfs()*2.5){
            setLossFactor(lossFactor+(lossFactor*0.4));
        }
        else if(forest.getDeer()>forest.getWolfs()*3){
            setLossFactor(lossFactor+(lossFactor*0.5));
        }

        setGrowthFactor(growthFactor);
    }

    @Override
    protected void catastropheHandler(Catastrophe t) {
        // the impact is always pending of the loss or growth

        double resist = 0;
        if(t.getCatastropheType() == CatastropheType.Fire){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getFireResistance();
            }
            resist /=forest.getSpeciesStructure().size();


            setLossFactor(getLoss()/3*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 2)*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());
        }
        if(t.getCatastropheType() == CatastropheType.Freeze){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getFreezeResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // freeze-Deaths do not have too big of an impact
            // since most of thaws later on in the natural model
            setLossFactor(getLoss()/10*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact((getLoss()*0.03*t.getDamageFactor()*(1-resist)));

            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Infestation){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getInfestationResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // Infestation can be very bad for the forest
            // if the forest also has a bad resistance it can
            // decimate most of it and the co2-impact is therefore
            // very impactful
            setLossFactor((1-getLoss()/3)*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 2)*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Moor){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getMoorResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // Since the forest is untouched it should be pretty resistant against
            // moores.
            // but if not then the impact can be very great on the lossFactor
            // but it doesn't have too big of an impact on the co2-levels.
            setLossFactor(getLoss()*0.5*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss()*0.07)*t.getDamageFactor())*(1-resist));


            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Storm){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getStormResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            // storms shouldn't have to big of an impact on the loss
            // of the forest and very low on co2.
            setLossFactor(getLoss()*0.07*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss()/4)*t.getDamageFactor())*(1-resist));


            forest.treeGrowth(getGrowth());

            // after a storm there is a chance for an Infestation
            // because there is laying a lot of dead wood and leaves
            // around. Therefore the damage factor is 2 (higher)
            if(Math.random()<0.05)
                catastropheHandler(new InfestationCatastrophe(2));
        }
    }


    @Override
    public String toString() {
        return "-- Natural Model: --\n" + getForest();
    }
}