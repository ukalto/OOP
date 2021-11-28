import java.util.Map;


public class NaturalModel extends BaseFarmingModel {

    // (Pre): forest != NULL; lossFactor >= 0 && lossFactor <= 1; growthFactor >= 0 && growthFactor <= 1
    // (Post): sets the values: forest, lossFactor, growthFactor, loss and growth of this factor.
    public NaturalModel(Forest forest, double lossFactor, double growthFactor) {
        super(forest, lossFactor, growthFactor);
    }

    // (Post): calculates the tree population of the forest stored in this instance considering the growth of the
    //      corresponding year and updates the value.
    // (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //              provide correct calculations: calcAgeStructure(), calcHealth(), calcTargetStock(),
    //              calcCo2Stock()
    @Override
    public void calcStock() {
        this.getForest().treeGrowth(getGrowth());
    }


    // (Post): calculates the CO2 stock of the forest considering the loss and growth stored in this instance
    //      and updates the value.
    @Override
    public void calcCo2Stock() {
        this.getForest().co2Adjust(getGrowth());
        if (getLoss() < 0.3) {
            this.co2InsignificantLoss(getLoss());
        } else if (getLoss() >= 0.3) {
            co2Impact(1 - getLoss() / 2);
        }
    }

    //  (Post): calculates and sets additional influence factors (some by calling other functions)
    //  (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //               provide correct calculations: calcStock(), calcAgeStructure(), calcHealth(),
    //               calcTargetStock(), calcCo2Stock()
    @Override
    public void calcSpecificFactors() {
        if (forest.getDeer() > forest.getWolfs() * 1.2) {
            setLossFactor(lossFactor + (lossFactor * 0.03));
        } else if (forest.getDeer() > forest.getWolfs() * 1.5) {
            setLossFactor(lossFactor + (lossFactor * 0.04));
        } else if (forest.getDeer() > forest.getWolfs() * 1.7) {
            setLossFactor(lossFactor + (lossFactor * 0.2));
        } else if (forest.getDeer() > forest.getWolfs() * 2) {
            setLossFactor(lossFactor + (lossFactor * 0.3));
        } else if (forest.getDeer() > forest.getWolfs() * 2.5) {
            setLossFactor(lossFactor + (lossFactor * 0.4));
        } else if (forest.getDeer() > forest.getWolfs() * 3) {
            setLossFactor(lossFactor + (lossFactor * 0.5));
        }

        setGrowthFactor(growthFactor);
    }

    // (Post): calculates the impact of following catastrophes on the forest and updates affected values:
    //      Fire, Freeze, Infestation, Moor, Storm
    @Override
    protected void catastropheHandler(Catastrophe t) {

        double resist = 0;
        if (t.getCatastropheType() == CatastropheType.Fire) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getFireResistance();
            }
            resist /= forest.getSpeciesStructure().size();


            setLossFactor(getLoss() / 3 * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 2) * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());
        }
        if (t.getCatastropheType() == CatastropheType.Freeze) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getFreezeResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(getLoss() / 10 * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact((getLoss() * 0.03 * t.getDamageFactor() * (1 - resist)));

            forest.treeGrowth(getGrowth());

        }
        if (t.getCatastropheType() == CatastropheType.Infestation) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getInfestationResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor((1 - getLoss() / 3) * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 2) * t.getDamageFactor()) * (1 - resist));

            forest.treeGrowth(getGrowth());

        }
        if (t.getCatastropheType() == CatastropheType.Moor) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getMoorResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(getLoss() * 0.5 * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss() * 0.07) * t.getDamageFactor()) * (1 - resist));


            forest.treeGrowth(getGrowth());

        }
        if (t.getCatastropheType() == CatastropheType.Storm) {
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()) {
                resist += entry.getKey().getStormResistance();
            }
            resist /= forest.getSpeciesStructure().size();

            setLossFactor(getLoss() * 0.07 * t.getDamageFactor() * (1 - resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss() / 4) * t.getDamageFactor()) * (1 - resist));


            forest.treeGrowth(getGrowth());

            if (Math.random() < 0.05)
                catastropheHandler(new InfestationCatastrophe(2));
        }
    }

    // (Post): returns a string with the name of the Model
    //      and the details of the forest stored in this instance
    @Override
    public String toString() {
        return "-- Natural Model: --\n" + getForest();
    }
}