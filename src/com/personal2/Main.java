package com.personal2;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {

        int[][] startMatrixArr = {{6, 5, 3}, {2, 4, 8}, {7, 0, 1}};

        Matrix startMatrix = new Matrix(startMatrixArr);
        PriorityQueue<State> states = new PriorityQueue<>();
        ArrayList<State> visited = new ArrayList<>();
        states.add(new State(startMatrix, 0));

        int level = 0;
        boolean foundEnd = false;
        while (!states.isEmpty() && !foundEnd) {
            State current = states.remove();
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

    private static void addNeighbors(State current, int level, PriorityQueue<State> states, ArrayList<State> visited) {
        generateNeighbors(current, level).stream().filter(n -> !visited.contains(n)).forEach(n -> states.add(n));
    }

    private static ArrayList<State> generateNeighbors(State current, int level) {
        return current.generateNeighbors(level);
    }
}
