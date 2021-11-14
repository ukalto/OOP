package Test;

import Forest.*;
import Tree.*;
import Catastrophe.*;
import Model.*;

import java.util.*;

public class Test {

    public static void main(String[] args) {
        int treePopulation = 0;
        double health = 0.5;
        double targetStock = 250.0;
        double maxTargetStock = 250.0;
        double harvest = 0.0;
        double co2Stock = 10.0;
        Factors factors = calcFactors();
        int years = 1000;
        int visitors = 0;
        double sunhours = 0;
        HashMap<Tree, Double> treeMap = new HashMap<>(){{
            put(new OakTree(0.8), 0.6);
            put(new BlackPineTree(0.3), 0.4);
        }};

        HashMap<Tree, Double> treeMap2 = new HashMap<>(){{
            put(new OakTree(0.8), 1.0);
        }};

        HashMap<Tree, Double> treeMap3 = new HashMap<>(){{
            put(new OakTree(0.8), 0.3);
            put(new SpruceTree(0.3), 0.3);
            put(new ScotsPineTree(0.2), 0.4);
        }};

        HashMap<Tree, Double> treeMap4 = new HashMap<>(){{
            put(new BeechTree(0.7), 0.2);
            put(new SpruceTree(0.3), 0.4);
            put(new ScotsPineTree(0.2), 0.4);
        }};

        Forest forest = new Forest(treePopulation, new HashMap<>(), health, targetStock, maxTargetStock, harvest,
                co2Stock, 0.0, 0.0, 0.0, 0.0, treeMap, 6, 10, 0);
        Forest forest2 = new Forest(treePopulation, new HashMap<>(), health, targetStock, maxTargetStock, harvest,
                co2Stock, 0.0, 0.0, 0.0, 0.0, treeMap2, 4, 27, 0);
        Forest forest3 = new Forest(treePopulation, new HashMap<>(), health, targetStock, maxTargetStock, harvest,
                co2Stock, 0.0, 0.0, 0.0, 0.0, treeMap3, 2, 10, 0);
        Forest forest4 = new Forest(treePopulation, new HashMap<>(), health, targetStock, maxTargetStock, harvest,
                co2Stock, 0.0, 0.0, 0.0, 0.0, treeMap4, 0, 0, 0);
        Forest forest5 = new Forest(treePopulation, new HashMap<>(), health, targetStock, maxTargetStock, harvest,
                co2Stock, 0.0, 0.0, 0.0, 0.0, treeMap3, 0, 0, 0);
        Forest forest6 = new Forest(treePopulation, new HashMap<>(), health, targetStock, maxTargetStock, harvest,
                co2Stock, 0.0, 0.0, 0.0, 0.0, treeMap2, 1, 9, 0);

        BaseFarmingModel nature = new NaturalModel(forest, factors.getLossFactor(), factors.getGrowthFactor());
        BaseFarmingModel natureWhuman = new NaturalHumanModel(forest2, factors.getLossFactor(), factors.getGrowthFactor());
        BaseFarmingModel farmedModel = new FarmedModel(forest3, factors.getLossFactor(), factors.getGrowthFactor());
        BaseFarmingModel cut = new CutoverModel(forest4, factors.getLossFactor(), factors.getGrowthFactor());
        BaseFarmingModel strip = new StripSystemModel(forest5, factors.getLossFactor(), factors.getGrowthFactor());
        BaseFarmingModel selection = new SelectionCuttingModel(forest6, factors.getLossFactor(), factors.getGrowthFactor());

        List<BaseFarmingModel> models = new ArrayList<BaseFarmingModel>() {{
            add(nature);
            add(natureWhuman);
            add(farmedModel);
            add(cut);
            add(strip);
            add(selection);
        }};

        System.out.println("Start of simulation");
        for (int i = 1; i <= years; i++) {
            for (BaseFarmingModel m : models) {
                m.setLossFactor(factors.getLossFactor());
                m.setGrowthFactor(factors.getGrowthFactor());
            }
            Random r = new Random();
            Catastrophe c = null;
            if (r.nextBoolean() && r.nextBoolean()) {
                //catastrophe occurs
                switch (r.nextInt(5) + 1) {
                    case 1:
                        c = new FireCatastrophe(r.nextDouble());
                        break;
                    case 2:
                        c = new FreezeCatastrophe(r.nextDouble());
                        break;
                    case 3:
                        c = new InfestationCatastrophe(r.nextDouble());
                        break;
                    case 4:
                        c = new MoorCatastrophe(r.nextDouble());
                        break;
                    case 5:
                        c = new StormCatastrophe(r.nextDouble());
                        break;
                }
            }
            for (BaseFarmingModel m : models) {
                if (r.nextBoolean() || i == 1) {
                    visitors = r.nextInt(100 * years) + 1;

                } else {
                    visitors = m.getForest().getHistory().get(i - 1).getVisitors();
                }
                if (r.nextBoolean() || i == 1) {
                    sunhours = (r.nextInt(12) + 1) * 365;
                } else {
                    sunhours = m.getForest().getHistory().get(i - 1).getHoursOfSunshine();
                }
                m.step(visitors, sunhours, c, i);
            }
            if (i % 100 == 0) {
                System.out.println("Year " + i + ":\n");
                for (BaseFarmingModel m : models) {
                    System.out.println(m);
                    System.out.println(m.history(i));
                }

            }
            factors = calcFactors();
        }
        System.out.println("\nEnd of simulation");
    }

    public static Factors calcFactors() {
        if (Math.random() < 0.03) {
            return new Factors(Math.random() * (1), (Math.random() * (1)));
        } else {
            return new Factors(Math.random() * (0.08), (Math.random() * (0.08)));
        }
    }
}