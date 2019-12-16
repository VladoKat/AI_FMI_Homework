package com.hw3;

import javafx.util.Pair;

import java.util.*;

public class Main {
    final static int MAX_HEROES_CAPACITY = 100;
    private static final int SELECTION_FACTOR = 30;
    private static final int MUTATION_FACTOR = 5;
    final static int NUM_ITEMS = 24;
    final static int TOTAL_GOLD = 5000;
    final static int MAX_ITER = 100;
    final static Integer[][] costValueArr = {
            {100, 150},
            {120, 40},
            {1600, 200},
            {700, 160},
            {150, 60},
            {680, 45},
            {270, 60},
            {385, 48},
            {230, 30},
            {520, 10},
            {1700, 400},
            {500, 300},
            {240, 15},
            {480, 10},
            {730, 40},
            {420, 70},
            {430, 75},
            {220, 80},
            {70, 20},
            {180, 12},
            {40, 50},
            {300, 10},
            {900, 20},
            {2000, 150}
    };

    public static void main(String[] args) {
        LinkedHashMap<Integer, Integer> costValueMap = new LinkedHashMap<>(24);
        for (Integer arr[] : costValueArr) {
            costValueMap.put(arr[0], arr[1]);
        }
        ArrayList<Pair<Integer, Integer>> heroPower = generateHeroPower(costValueMap);
//        System.out.println(heroPower.size());
        for (int i = 0; i < MAX_ITER; i++) {
            heroPower.sort((o1, o2) -> {
                if (o1.getKey() > o2.getKey()) {
                    return -1;
                }
                if (o1.getKey() == o2.getKey()) {
                    return 0;
                }
                return 1;
            });
            cross(heroPower, costValueMap);
//            mutate(heroPower, costValueMap);
        }
        heroPower.sort(Comparator.comparingInt(Pair::getKey));
        System.out.println(heroPower.get(0).getKey() + " " + heroPower.get(0).getValue());
    }

    private static void mutate(ArrayList<Pair<Integer, Integer>> heroes, LinkedHashMap<Integer, Integer> costValueMap) {
        Random r = new Random();
        for (int i = 0; i < MUTATION_FACTOR; i++) {
            Integer index = r.nextInt(MAX_HEROES_CAPACITY);
            heroes.set(index, mutateSingle(heroes.get(index), costValueMap));
        }
    }

    private static Pair<Integer, Integer> mutateSingle(Pair<Integer, Integer> singleHeroPower, LinkedHashMap<Integer, Integer> costValueMap) {
        return singleHeroPower;
        //TODO: see how mutation work
    }


    private static Integer generateSingleHero(LinkedHashMap<Integer, Integer> map) {
        int bitmask = 0;
        int sum = 0;
        Set<Integer> keySet = map.keySet();
        Random r = new Random();
        for (Integer i = 0; i < NUM_ITEMS && keySet.iterator().hasNext(); i++) {
            Integer key = keySet.iterator().next();
            if (r.nextDouble() >= 0.5 && sum + key <= TOTAL_GOLD) {
                bitmask += 1 << i;
                sum += key;
            }
        }
        return bitmask;
    }

    private static void cross(ArrayList<Pair<Integer, Integer>> heroesPowers, LinkedHashMap<Integer, Integer> costValueMap) {
        for (int i = 0; i < SELECTION_FACTOR * 2; i += 2) {
            Pair<Integer, Integer> singleHeroPower = crossTwo(heroesPowers.get(i), heroesPowers.get(i + 1), costValueMap);
            heroesPowers.set(heroesPowers.size() - SELECTION_FACTOR + i / 2, singleHeroPower);
        }
        System.out.println(heroesPowers.size());
        //TODO: see why Comparison method violates its general contract! (hint: print the array. after n-th iteration it fails)
        heroesPowers.sort(Comparator.comparingInt(Pair::getKey));
    }


    private static Pair<Integer, Integer> crossTwo(Pair<Integer, Integer> hero1, Pair<Integer, Integer> hero2, LinkedHashMap<Integer, Integer> costValueMap) {
        Integer heroGene1 = hero1.getValue();
        Integer heroGene2 = hero2.getValue();
        Integer heroGeneResult = 0;
        Random r = new Random();
        for (int i = 0; i < NUM_ITEMS; i++) {
            if (r.nextDouble() >= 0.5) {
                heroGeneResult += heroGene1 % 2;
            } else {
                heroGeneResult += heroGene2 % 2;
            }
            heroGene1 /= 2;
            heroGene2 /= 2;
        }
        Integer power = getPower(heroGeneResult, costValueMap);
        return new Pair<>(power, heroGeneResult);
    }

    private static Integer getPower(Integer heroGene, LinkedHashMap<Integer, Integer> costValueMap) {
        Integer cost = 0;
        Integer power = 0;
        while (heroGene != 0) {
            Map.Entry<Integer, Integer> singleCostValue = costValueMap.entrySet().iterator().next();
            Integer currItemCost = singleCostValue.getKey();
            Integer currItemPower = singleCostValue.getValue();
            if (heroGene % 2 == 1) {
                cost += currItemCost;
                power += currItemPower;
            }
            heroGene /= 2;
        }
        if (cost > TOTAL_GOLD) {
            power = 0;
        }
        return power;
    }

    private static ArrayList<Pair<Integer, Integer>> generateHeroPower(LinkedHashMap<Integer, Integer> costValueMap) {
        ArrayList<Pair<Integer, Integer>> heroPower = new ArrayList<>();
        for (int i = 0; i < MAX_HEROES_CAPACITY; i++) {
            Integer hero = generateSingleHero(costValueMap);
            Integer fitness = fitness(hero, costValueMap);
//            System.out.println(fitness);
            heroPower.add(new Pair<>(fitness, hero));
        }
        return heroPower;
    }

    private static Integer fitness(Integer hero, LinkedHashMap<Integer, Integer> costValueMap) {
        Integer result = 0;
        Set<Integer> keySet = costValueMap.keySet();
        while (hero != 0) {
            int bit = hero % 2;
            Integer key = keySet.iterator().next();
            if (bit == 1) {
                result += costValueMap.get(key);
            }
            hero /= 2;
        }
        return result;
    }
}
