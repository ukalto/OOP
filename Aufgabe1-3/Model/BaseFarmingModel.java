package Model;

import Forest.*;
import Catastrophe.*;

import java.util.Map;

public abstract class BaseFarmingModel {

    protected Forest forest;
    protected double lossFactor;
    protected double growthFactor;
    private double loss;
    private double growth;

    public BaseFarmingModel(Forest forest, double lossFactor, double growthFactor) {
        this.forest = forest;
        this.lossFactor = lossFactor;
        this.growthFactor = growthFactor;
        this.loss = lossFactor * forest.getHealth();
        this.growth = (forest.getTargetStock() * growthFactor) - (forest.getTreePopulation() * loss);
    }

    public Forest getForest() {
        return forest;
    }

    public String history(int year){
        return forest.historyAverage(year);
    }

    public double getGrowth() { return this.growth; }

    public double getLoss() {
        return this.loss;
    }

    public void setLossFactor(double lossFactor) {
        if (lossFactor < 0)
            this.lossFactor = 0;
        else if (lossFactor > 1)
            this.lossFactor = 1;
        else
            this.lossFactor = lossFactor;
        // update the loss
        this.loss = lossFactor * forest.getHealth();
        if(!(this.loss>0))
            this.loss = 0;
        if(this.loss>1)
            this.loss = 1;
    }

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

    //sets the base Influence Factors
    //The abstract method calcSpecificFactors adjusts these to the different models
    private void calcInfluenceFactors(){
        //UpdateSoilFactor
        forest.calcSoil();
        setLossFactor(lossFactor+lossFactor*((1-forest.getSoilQuality())));
        if(forest.getSoilQuality() > .95)
            setGrowthFactor(growthFactor+(growthFactor*0.24));
        else
            setGrowthFactor(growthFactor);

        //UpdateStabilityFactor
        forest.calcStability();
        setLossFactor(lossFactor+lossFactor*(forest.getStability()));
        setGrowthFactor(growthFactor);

        //UpdateWaterFactor
        //Scale down to interval 0 - 0.1 (this shouldn't have too big of an influence
        setGrowthFactor(growthFactor+0.1 * forest.getWaterStock());

        //UpdateSunshineFactor
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


        //UpdateMonocultureFactor
        if(forest.calcMonoculture()){
            setLossFactor(lossFactor+(lossFactor*0.2));
            setGrowthFactor(growthFactor+(growthFactor*0.1));
        }

        //Impact of visitors
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

        //Update deer And WolvesFactor
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

    // increase age of trees and calculate proportions
    public void calcAgeStructure() {
        this.getForest().increaseAge(getLoss());
    }

    // calculate the target stock considering the loss
    public void calcTargetStock() {
        // if loss >= 0.3 the target stock has to be adjusted
        if (getLoss() >= 0.3) {
            targetStockLoss(getLoss());
        }
        // if the target stock os below the maximum stock it has to be adjusted
        if (getForest().getTargetStock() <= getForest().getMaxTargetStock()) {
            getForest().increaseTargetStock(5);
            if (getForest().getTargetStock() > getForest().getMaxTargetStock())
                getForest().setTargetStock(getForest().getMaxTargetStock());
        }
    }

    // calculates the health of the forest considering the age structure
    // model: if the ate group is not existent - 1/(250/2) (max 50% impact)
    //                    calculate the difference in age groups (max 50% impact)
    //                    scale 0 to 100 down to 0.25 to 1
    public void calcHealth() {
        double health = 100;

        // calculate the missing age groups --> value between 0 and 50
        int size = 0;
        for (Map.Entry<Integer, Double> i : this.getForest().getAgeStructure().entrySet()) {
            if (i.getValue() != 0)
                size++;
        }

        health -= (250 - size) / (250.0 / 50);

        // calculate the difference in age groups;
        // scale the difference to 0 to 50
        double i = 0;
        double difference = 0;
        for (Double value : forest.getAgeStructure().values()) {
            difference += Math.abs(value - i);
            i = value;
        }
        // formula: ((rangeMax-rangeMin)*(difference-min)/(max-min))+rangeMin
        // extreme case = 200
        health -= (((100) * (difference - 0.0)) / (200.0 - 0.0)) + 0;
        // scale 0 to 100 to 0.25 bis 1
        forest.setHealth((((1 - 0.25) * (health - 0.25)) / (100.0 - 0.0)) + 0.25);
    }

    // One step of the simulation
    public void step(int visitors, double sunHours, Catastrophe c, int year) {
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

    public void co2Impact(double impact){
        if (impact < 0)
            impact = 0;
        if(forest.getco2Vorrat() * impact < 0)
            forest.setco2Vorrat(0);
        else
            forest.setco2Vorrat(forest.getco2Vorrat() * impact);
    }

    // adjusts the co2-stock if there is n insignificant loss
    public void co2InsignificantLoss(double loss) {
        if(forest.getco2Vorrat()+ forest.getTreePopulation() * loss / 3 < 0)
            forest.setco2Vorrat(0);
        else
            forest.setco2Vorrat(forest.getco2Vorrat() + forest.getTreePopulation() * loss / 3);
    }

    // calculates the new stock due to a loss
    public void targetStockLoss(double loss) {
        if (forest.getTargetStock() * ((1 - loss)) < 0)
            forest.setTargetStock(0);
        else
            forest.setTargetStock(forest.getTargetStock() * ((1 - loss)));
    }

    public void setLoss(double loss){ this.loss = loss;}

    // abstract methods
    protected abstract void calcStock();

    protected abstract void calcCo2Stock();

    protected abstract void calcSpecificFactors();

    protected abstract void catastropheHandler(Catastrophe t);

    public abstract String toString();
}