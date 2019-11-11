package com.hw3;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Main {
    final static int MAX_HEROS_CAPACITY = 100;
    final static int NUM_ITEMS = 24;
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
        int totalGold = 5000;
        int heroPower = 0;


        LinkedHashMap<Integer, Integer> costValueMap = new LinkedHashMap<>(24);
        for (Integer arr[] : costValueArr) {
            costValueMap.put(arr[0], arr[1]);
        }
        ArrayList<Integer> heroes = generateHeros(costValueMap);
        //TODO: while loop with selection, crossing, mutation

    }
    private static ArrayList<Integer> generateHeros(LinkedHashMap<Integer, Integer> map){
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < MAX_HEROS_CAPACITY; i++) {
            result.add(generateSingleHero());
        }
        return result;
    }

    private static Integer generateSingleHero() {
        //TODO: generate bitmask (randomly) for a buying strategy
        return 0;
    }
}
