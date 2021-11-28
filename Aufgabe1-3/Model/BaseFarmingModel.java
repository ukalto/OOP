import java.util.Map;

// GOOD: By using subtyping for the models we guarantee a solid base of cohesion.
//            Every time someone wants to add a new model to the simulation he doesn't need
//            to write the whole model from scratch but rather can extend one of the existing models
//            or from this base model which is abstract. The base model is abstract, because it wouldn't be
//            correct that all models are subtypes from one specific model. By adding to every model "Model"
//            at the end of the class name the cohesion is even more clear.
public abstract class BaseFarmingModel {

    // (I): forest != NULL
    protected Forest forest;

    // (I): lossFactor >= 0 && lossFactor <= 1
    protected double lossFactor;

    // (I): growthFactor >= 0 && growthFactor <= 1
    protected double growthFactor;

    // (I): loss >= 0 && loss <= 1
    private double loss;

    // (I): growth >= 0 && growth <= 1
    private double growth;


    // (Pre): forest != NULL; lossFactor >= 0 && lossFactor <= 1; growthFactor >= 0 && growthFactor <= 1
    // (Post): sets the values: forest, lossFactor, growthFactor, loss and growth of this factor.
    public BaseFarmingModel(Forest forest, double lossFactor, double growthFactor) {
        this.forest = forest;
        this.lossFactor = lossFactor;
        this.growthFactor = growthFactor;
        this.loss = lossFactor * forest.getHealth();
        this.growth = (forest.getTargetStock() * growthFactor) - (forest.getTreePopulation() * loss);
    }


    // (Post): returns the forest stored in the instance
    public Forest getForest() {
        return forest;
    }

    // (Pre): year > 0 && year <= bygone years in simulation
    // (Post): returns string representing the average values of the forest stored in this instance
    public String history(int year){
        return forest.historyAverage(year);
    }

    // BAD: The client should only be able to call step() guaranteeing that all the
    //          necessary methods for the correct calculations of the current year are called in the
    //          correct order - therefore calcAgeStructure(), calcTargetStock(), calcHealth(), getGrowth() and
    //          getLoss() should be private instead of public. This probably happened
    //          because we added the step() method relatively late to our BaseModel and therefore forgot
    //          to change the keywords.

    // (Post): returns the growth of the current year stored in the instance
    public double getGrowth() { return this.growth; }

    // (Post): returns the loss of the current year stored in the instance
    public double getLoss() {
        return this.loss;
    }

    // (Post): (calculates and) updates the values lossFactor and loss of this instance
    public void setLossFactor(double lossFactor) {
        if (lossFactor < 0)
            this.lossFactor = 0;
        else if (lossFactor > 1)
            this.lossFactor = 1;
        else
            this.lossFactor = lossFactor;
        this.loss = lossFactor * forest.getHealth();
        if(!(this.loss>0))
            this.loss = 0;
        if(this.loss>1)
            this.loss = 1;
    }

    // (Post): (calculates and) updates the values growthFactor and growth of this instance
    public void setGrowthFactor(double growthFactor) {
        if (growthFactor < 0)
            this.growthFactor = 0;
        else if (growthFactor > 1)
            this.growthFactor = 1;
        else
            this.growthFactor = growthFactor;
        // update the growth
        this.growth = (forest.getTargetStock() * growthFactor) - (forest.getTreePopulation() * loss);
        if(!(this.growth>0))
            this.growth = 0;
        if(this.growth>1)
            this.growth = 1;
    }

    // GOOD: Although this method calculates and sets a lot of attributes from the forest stored in this instance
    //           there are no parameters needed to succeed. Everything is handled with the stored values and updated
    //           with methods from the forest. This way the object coupling is kept as minimal as possible.

    // (Post): calculates and sets the influence factors (some by calling other functions) such as:
    //         soilQuality (forest), lossFactor, growthFactor, stability (forest), hoursOfSunshine (forest),
    //         waterStock
    //
    // (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //              provide correct calculations: calcSpecificFactors(), calcStock(), calcAgeStructure(), calcHealth(),
    //              calcTargetStock(), calcCo2Stock()
    private void calcInfluenceFactors(){
        forest.calcSoil();
        setLossFactor(lossFactor+lossFactor*((1-forest.getSoilQuality())));
        if(forest.getSoilQuality() > .95)
            setGrowthFactor(growthFactor+(growthFactor*0.24));
        else
            setGrowthFactor(growthFactor);

        forest.calcStability();
        setLossFactor(lossFactor+lossFactor*(forest.getStability()));
        setGrowthFactor(growthFactor);

        setGrowthFactor(growthFactor+0.1 * forest.getWaterStock());

        if(forest.getHoursOfSunshine()<1000){
            setLossFactor(lossFactor+(lossFactor*0.13));
            setGrowthFactor(growthFactor-(growthFactor*0.57));
        }
        else if(forest.getHoursOfSunshine()<1800){
            setLossFactor(lossFactor+(lossFactor*0.08));
            setGrowthFactor(growthFactor-(growthFactor*0.3));
        }
        else{
            setGrowthFactor(growthFactor+(growthFactor*0.2));
        }

        if(forest.calcMonoculture()){
            setLossFactor(lossFactor+(lossFactor*0.2));
            setGrowthFactor(growthFactor+(growthFactor*0.1));
        }

        if(forest.getVisitors()>500){
            setLossFactor(lossFactor+(lossFactor*0.01));
            setGrowthFactor(growthFactor);
        }
        else if(forest.getVisitors()>1000){
            setLossFactor(lossFactor+(lossFactor*0.05));
            setGrowthFactor(growthFactor);
        }
        else if(forest.getVisitors()>5000){
            setLossFactor(lossFactor+(lossFactor*0.06));
            setGrowthFactor(growthFactor);
        }
        else if(forest.getVisitors()>10000){
            setLossFactor(lossFactor+(lossFactor*0.3));
            setGrowthFactor(growthFactor);
        }

        if(forest.getDeer()>forest.getWolfs()*1.5){
            setLossFactor(lossFactor+(lossFactor*0.03));
        }
        else if(forest.getDeer()>forest.getWolfs()*2){
            setLossFactor(lossFactor+(lossFactor*0.06));
        }
        else if(forest.getDeer()>forest.getWolfs()*3){
            setLossFactor(lossFactor+(lossFactor*0.1));
        }

        setGrowthFactor(growthFactor);

    }

    // (Post): updates the age structure of the forest stored in this instance by one year
    //  (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //               provide correct calculations: calcHealth(), calcTargetStock(), calcCo2Stock()
    public void calcAgeStructure() {
        this.getForest().increaseAge(getLoss());
    }

    // (Post): updates the targetStock considering the loss of the current year
    // (History-C): for each invocation of calcInfluenceFactors(), calcCo2Stock() has to be called to
    //              provide correction calculations.
    public void calcTargetStock() {
        if (getLoss() >= 0.3) {
            targetStockLoss(getLoss());
        }
        if (getForest().getTargetStock() <= getForest().getMaxTargetStock()) {
            getForest().increaseTargetStock(5);
            if (getForest().getTargetStock() > getForest().getMaxTargetStock())
                getForest().setTargetStock(getForest().getMaxTargetStock());
        }
    }

    // (Post): calculates the health of the forest stored in this instance using the age structure and updates
    //         the value.
    // (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //              provide correct calculations: calcTargetStock(), calcCo2Stock()
    public void calcHealth() {
        double health = 100;

        int size = 0;
        for (Map.Entry<Integer, Double> i : this.getForest().getAgeStructure().entrySet()) {
            if (i.getValue() != 0)
                size++;
        }

        health -= (250 - size) / (250.0 / 50);

        double i = 0;
        double difference = 0;
        for (Double value : forest.getAgeStructure().values()) {
            difference += Math.abs(value - i);
            i = value;
        }
        health -= (((100) * (difference - 0.0)) / (200.0 - 0.0)) + 0;

        forest.setHealth((((1 - 0.25) * (health - 0.25)) / (100.0 - 0.0)) + 0.25);
    }

    // GOOD: Using subtyping allows the client to just call step() guaranteeing correct calculations
    //           for the current year of the simulation with whichever model. We used subtyping here
    //           because every subtype u can be used where a base model m is wanted.

    // (Pre): visitors >= 0; sunHours >= 0
    // (Post): calls all methods needed to simulate a year of the forest stored in this model
    //         and updates all the values.
    // (Hist-C): year can only be increased one by one - meaning is needs to be increased by one
    //           compared to the previous entry.
    public void step(int visitors, double sunHours, Catastrophe c,int year) {
        forest.calcForestType();
        forest.simulateAnimalActivity();
        forest.setVisitors(visitors);
        forest.setHoursOfSunshine(sunHours);
        forest.calcWaterstock();
        if(c != null) catastropheHandler(c);
        calcInfluenceFactors();
        calcSpecificFactors();
        calcStock();
        calcAgeStructure();
        calcHealth();
        calcTargetStock();
        calcCo2Stock();
        forest.makeHistoryEntry(year);
    }

    //  BAD: Since the client should only be able to call step() guaranteeing that all the
    //          necessary methods for the correct calculations of the current year are called in the
    //          correct order co2Impact(), co2InsignificantLoss(), targetStockLoss() and
    //          setLoss() should be protected instead of public. This probably happened
    //          because we added the step() method relatively late to our BaseModel and therefore forgot
    //          to change the keywords.

    // (Post): calls all methods needed to simulate a year of the forest stored in this model
    //         and updates all the values.
    public void co2Impact(double impact){
        if (impact < 0)
            impact = 0;
        if(forest.getco2Vorrat() * impact < 0)
            forest.setco2Vorrat(0);
        else
            forest.setco2Vorrat(forest.getco2Vorrat() * impact);
    }

    // (Pre): loss >= 0; loss <= 1
    // (Post): updates the co2-stock with the given loss value
    public void co2InsignificantLoss(double loss) {
        if(forest.getco2Vorrat()+ forest.getTreePopulation() * loss / 3 < 0)
            forest.setco2Vorrat(0);
        else
            forest.setco2Vorrat(forest.getco2Vorrat() + forest.getTreePopulation() * loss / 3);
    }

    // (Pre): loss >= 0; loss <= 1
    // (Post): updates the co2-stock with the given loss value
    public void targetStockLoss(double loss) {
        if (forest.getTargetStock() * ((1 - loss)) < 0)
            forest.setTargetStock(0);
        else
            forest.setTargetStock(forest.getTargetStock() * ((1 - loss)));
    }

    // (Pre): loss >= 0; loss <= 1
    // (Post): sets loss stored in this instance to the given value loss
    public void setLoss(double loss){ this.loss = loss;}

    // BAD: Here we could have implemented some base methode for the underlying abstract methods
    //           instead of declaring them abstract which could have been used by very simple models
    //           that don't wish to change these calculations. This probably happened in an early stage
    //           of planning task 2 because our Models NaturalModel and FarmedModel are pretty different
    //           and we didn't think about other models that could have been added in the future and
    //           didn't mean to change these calculations.

    // GOOD: Although the cohesion isn't perfect here the object coupling is as minimal as possible.
    //           All of the values in the abstract methods are calculated without parameters (with exception of
    //           the catastrophe in (Catastrophe t)) and only by using the stored values in the forest and
    //           methods offered by the forest.

    // (Post): calculates the tree population of the forest stored in this instance
    //  (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //               provide correct calculations: calcAgeStructure(), calcHealth(), calcTargetStock(),
    //               calcCo2Stock()
    protected abstract void calcStock();

    // (Post): calculates the CO2 stock of the forest stored in this instance and updates the value.
    protected abstract void calcCo2Stock();

    // (Post): calculates and sets additional influence factors (some by calling other functions)
    //  (History-C): for each invocation of calcInfluenceFactors(), following methods have to be invoked as well to
    //               provide correct calculations: calcStock(), calcAgeStructure(), calcHealth(),
    //               calcTargetStock(), calcCo2Stock()
    protected abstract void calcSpecificFactors();

    // (Post): calculates the impact of a catastrophe on the forest and updates affected values
    protected abstract void catastropheHandler(Catastrophe t);

    // (Post): returns a string with the name of the Model
    //      and the details of the forest stored in this instance
    public abstract String toString();
}