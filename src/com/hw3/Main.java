package com.hw3;

import java.util.*;

public class Main {
    final static int MAX_HEROS_CAPACITY = 100;
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
    public static void main(String[] args){
        int heroPower = 0;

        LinkedHashMap<Integer, Integer> costValueMap = new LinkedHashMap<>(24);
        for (Integer arr[] : costValueArr) {
            costValueMap.put(arr[0], arr[1]);
        }
        ArrayList<Integer> heroes = generateHeroes(costValueMap);
        System.out.println(heroes.size());
        select(heroes, costValueMap);

//        for(int i = 0; i < MAX_ITER; i++){
//            select(heroes, costValueMap);
//            cross(heroes);
//            mutate(heroes);
//        }

    }
    private static ArrayList<Integer> generateHeroes(LinkedHashMap<Integer, Integer> map){
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < MAX_HEROS_CAPACITY; i++) {
            result.add(generateSingleHero(map));
        }
        return result;
    }

    private static Integer generateSingleHero(LinkedHashMap<Integer, Integer> map) {
        int bitmask = 0;
        int sum = 0;
        Set<Integer> keySet = map.keySet();
        Random r = new Random();
        for (Integer i = 0; i < NUM_ITEMS && keySet.iterator().hasNext(); i++) {
            Integer key = keySet.iterator().next();
            if(r.nextDouble() >= 0.5 && sum + key <= TOTAL_GOLD){
                bitmask += 1 << i;
                sum += key;
            }
        }
        return bitmask;
    }

    private static void select(ArrayList<Integer> heroes, LinkedHashMap<Integer, Integer> costValueMap){
        SortedMap<Integer, Integer> heroPower = new TreeMap<>();
        System.out.println(heroes.size());
        for (Integer hero : heroes) {
            heroPower.put(fitness(hero, costValueMap), hero);
        }
        System.out.println(heroPower.size());
        for (SortedMap.Entry e:
             heroPower.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }

    private static Integer fitness(Integer hero, LinkedHashMap<Integer, Integer> costValueMap){
        Integer result = 0;
        Set<Integer> keySet = costValueMap.keySet();
        while(hero != 0 || !keySet.iterator().hasNext()){
            int bit = hero % 2;
            Integer key = keySet.iterator().next();
            if(bit == 1){
                result += costValueMap.get(key);
            }
            hero /= 2;
        }
        return result;
    }
}
