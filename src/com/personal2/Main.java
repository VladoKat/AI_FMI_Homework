package com.personal2;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {

        int[][] startMatrixArr = {{6, 5, 3}, {2, 4, 8}, {7, 0, 1}};

        Matrix startMatrix = new Matrix(startMatrixArr);
        PriorityQueue<Pair> states = new PriorityQueue<>();
        ArrayList<Pair> visited = new ArrayList<>();
        states.add(new Pair(startMatrix, 0));

        int level = 0;
        boolean foundEnd = false;
        while (!states.isEmpty() && !foundEnd) {
            Pair current = states.remove();
            if (current.getDistance() == 0) {
                level = current.getLevel();
                foundEnd = true;
            } else {
                level = current.getLevel() + 1;
                visited.add(current);
                addNeighbors(current, level, states, visited);
            }
        }
        System.out.println(level);
    }

    private static void addNeighbors(Pair current, int level, PriorityQueue<Pair> states, ArrayList<Pair> visited) {
        generateNeighbors(current, level).stream().filter(n -> !visited.contains(n)).forEach(n -> states.add(n));
    }

    private static ArrayList<Pair> generateNeighbors(Pair current, int level) {
        return current.generateNeighbors(level);
    }
}
