package Model;

import Forest.*;
import Catastrophe.*;

public class NaturalHumanModel extends NaturalModel {

    // (Pre): forest != NULL; lossFactor >= 0 && lossFactor <= 1; growthFactor >= 0 && growthFactor <= 1
    // (Post): sets the values: forest (!=null), lossFactor (between 0 and 1), growthFactor (between 0 and 1),
    //         loss (between 0 and 1) and growth (between 0 and 1)
    public NaturalHumanModel(Forest forest, double lossFactor, double growthFactor) {
        super(forest, lossFactor, growthFactor);
    }

    // (Post): calculates the tree population of the forest stored in this instance considering the growth of the
    //      corresponding year and updates the value. target stock does change but is still between 0 and
    //      maxTargetStock
    // (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //              provide correct calculations: calcAgeStructure(), calcHealth(), calcTargetStock(),
    //              calcCo2Stock()
    @Override
    public void calcStock() {
        if (Math.random() < 0.05)
            forest.setTargetStock(forest.getTargetStock() - (forest.getTargetStock() * 0.2));
        if (Math.random() < 0.05)
            forest.setTargetStock(forest.getTargetStock() + (forest.getTargetStock() * 0.2));
        this.getForest().treeGrowth(getGrowth() - (getGrowth() * 0.07));
    }

    //  (Post): calculates and sets additional influence factors (some by calling other functions) and some
    //       by calling the method of the super class
    //  (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //               provide correct calculations: calcStock(), calcAgeStructure(), calcHealth(),
    //               calcTargetStock(), calcCo2Stock()
    @Override
    public void calcSpecificFactors() {
        super.calcSpecificFactors();

        if (forest.getVisitors() > 1000) {
            setLossFactor(lossFactor + (lossFactor * 0.04));
            setGrowthFactor(growthFactor);
        } else if (forest.getVisitors() > 2000) {
            setLossFactor(lossFactor + (lossFactor * 0.1));
            setGrowthFactor(growthFactor);
        } else if (forest.getVisitors() > 10000) {
            setLossFactor(lossFactor + (lossFactor * 0.2));
            setGrowthFactor(growthFactor);
        } else if (forest.getVisitors() > 50000) {
            setLossFactor(lossFactor + (lossFactor * 0.4));
            setGrowthFactor(growthFactor);
        }


    }

    // (Post): calculates the impact of following catastrophes on the forest and updates affected values:
    //      Fire, Freeze, Infestation, Moor, Storm - the impact of this compared to the super() class
    //      only differs with a higher chance for causing an InfestationCatastrophe
    //      All of them decreases co2-stock - still between 0 and 1; increases lossFactor - still between 0 and 1
    //                  and increases growthFactor - still between 0 and 1
    @Override
    protected void catastropheHandler(Catastrophe t) {
        super.catastropheHandler(t);

        if (Math.random() < 0.1)
            super.catastropheHandler(new InfestationCatastrophe(2.5));


    }

    // (Post): returns a string with the name of the Model
    //      and the details of the forest stored in this instance - never empty
    @Override
    public String toString() {
        return "-- Natural with humans Model: --\n" + getForest();
    }
}
