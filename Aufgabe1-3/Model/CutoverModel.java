import java.util.Map;

public class CutoverModel extends FarmedModel {

    // (Pre): forest != NULL; loss >= 0 && loss <= 1; growth >= 0 && growth <= 1
    // (Post): sets the values: forest, lossFactor, growthFactor, loss and growth of this factor and
    //         also sets the max target stock to 350
    public CutoverModel(Forest forest, double loss, double growth) {
        super(forest, loss, growth);
    }


    // (Post): calculates and updates the tree population (and age structure) of the forest stored in this instance
    //      and might also invoke a harvest because of the loss or the tree population of the corresponding year
    //      which has additional impact on the population of the forest. Harvesting
    //      will cut over all trees older than 0.
    // (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //              provide correct calculations: calcAgeStructure(), calcHealth(), calcTargetStock(),
    //              calcCo2Stock()
    @Override
    public void calcStock(){
        if(getForest().getTreePopulation() > 0)
            stock = this.getForest().getTreePopulation();

        if (getLoss() <= 0.3) {
            if (getLoss() >= 0.1) {
                farmOlderThan(1, 0.9);
                harvestTaken += (getLoss());
                this.getForest().setHarvest(this.getForest().getHarvest() + harvestTaken);
            }
        }

        else if (forest.getTreePopulation() > forest.getMaxTargetStock()/3) {
            farmOlderThan(1, 0.9);
        }
        this.getForest().treeGrowth(getGrowth());


        setLoss(getLoss()+((harvestTaken /this.stock)));
    }

    //  (Post): calculates and sets additional influence factors (some by calling other functions)
    //  (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //               provide correct calculations: calcStock(), calcAgeStructure(), calcHealth(),
    //               calcTargetStock(), calcCo2Stock()
    @Override
    public void calcSpecificFactors() {

        forest.calcSoil();
        if(forest.getSoilQuality()<0.5){
            setLossFactor(lossFactor+lossFactor*((1-forest.getSoilQuality())));
            setGrowthFactor(growthFactor);
        }
    }

    // (Post): calculates the impact of following catastrophes on the forest and updates affected values:
    //      Fire, Freeze, Infestation, Moor, Storm - for Fire it calls the method of the super() class
    @Override
    protected void catastropheHandler(Catastrophe t) {
        double resist = 0;
        if(t.getCatastropheType() == CatastropheType.Freeze){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getFreezeResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            setLossFactor(lossFactor+(lossFactor/2*t.getDamageFactor()*(1-resist)));
            setGrowthFactor(growthFactor);

            co2Impact(((1 - getLoss() / 4)*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Infestation){

            infestationFarm(1,(double) 1/3,3);

            setLoss(getLoss()+((harvestTaken /this.stock)));

            if (getLoss() >= 0.2) {
                co2Impact((1 - (getLoss()-(harvestTaken/this.stock))));
            }
            else if (getLoss() < 0.2) {
                this.co2InsignificantLoss(getLoss() - (harvestTaken /this.stock));
            }
        }
        if(t.getCatastropheType() == CatastropheType.Moor){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getInfestationResistance();
            }
            resist /=forest.getSpeciesStructure().size();

            forest.calcSoil();
            setLossFactor(lossFactor+lossFactor*((forest.getSoilQuality()/13)*forest.getTreePopulation()/forest.getTargetStock())*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(lossFactor+lossFactor*((forest.getSoilQuality()/13)*((forest.getTreePopulation()/forest.getTargetStock()))*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

        }
        if(t.getCatastropheType() == CatastropheType.Storm){
            for (Map.Entry<Tree, Double> entry : forest.getSpeciesStructure().entrySet()){
                resist += entry.getKey().getStormResistance();
            }
            resist /=forest.getSpeciesStructure().size();


            setLossFactor(getLoss()*(yearsWithoutHarvest/16.0)*t.getDamageFactor()*(1-resist));
            setGrowthFactor(growthFactor);

            co2Impact(((getLoss() /13)*t.getDamageFactor())*(1-resist));

            forest.treeGrowth(getGrowth());

            if(Math.random()<0.1+(yearsWithoutHarvest/16.0))
                catastropheHandler(new InfestationCatastrophe(1.4));
        }
        else
            super.catastropheHandler(t);
    }

    // (Post): returns a string with the name of the Model
    //      and the details of the forest stored in this instance
    @Override
    public String toString() {
        return "-- Cutover Model: --\n" + getForest();
    }
}
