package com.personal;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        int[][] maze = {
                {1, 1, 0, 1, 1, 1},
                {1, 2, 0, 0, 1, 1},
                {1, 1, 1, 1, 2, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1}
        };

        Pair<Integer, Integer> start = new Pair<>(0, 0);
        Pair<Integer, Integer> end = new Pair<>(4, 4);
        Pair<Integer, Integer> teleport1 = new Pair(1, 1);
        Pair<Integer, Integer> teleport2 = new Pair(2, 4);

        Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> states = new LinkedBlockingQueue<>();
        states.add(new Pair(start, start));
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> current;
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> resultSeq = new HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>();
        boolean foundEnd = false;
        while (!states.isEmpty() && !foundEnd) {
            current = states.remove();
            resultSeq.put(current.getValue(), current.getKey());
            if (current.getValue().getKey() == end.getKey()
                    && current.getValue().getValue() == end.getValue()) {
                foundEnd = true;
            } else {
                if (maze[current.getValue().getKey()][current.getValue().getValue()] == 2
                        && current.getValue().getKey() == teleport1.getKey()
                        && current.getValue().getValue() == teleport1.getValue()) {
                    maze[teleport1.getKey()][teleport1.getValue()] = 0;
                    maze[teleport2.getKey()][teleport2.getValue()] = 0;
                    current = new Pair(current.getKey(), teleport2);
                }
                if (maze[current.getValue().getKey()][current.getValue().getValue()] == 2
                        && current.getValue().getKey() == teleport2.getKey()
                        && current.getValue().getValue() == teleport2.getValue()) {
                    maze[teleport1.getKey()][teleport1.getValue()] = 0;
                    maze[teleport2.getKey()][teleport2.getValue()] = 0;
                    current = new Pair(current.getKey(), teleport1);
                }
                maze[current.getValue().getKey()][current.getValue().getValue()] = 0;
                pushNeighbours(current.getValue(), maze, states);
            }
        }
        findWay(start, end, teleport1, teleport2, resultSeq);
    }

    private static void pushNeighbours(Pair<Integer, Integer> current, int[][] maze, Queue<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> states) {
        int x = current.getKey();
        int y = current.getValue();
        if (x + 1 < maze.length && maze[x + 1][y] != 0) {
            states.add(new Pair(current, new Pair(x + 1, y)));
        }
        if (x - 1 >= 0 && maze[x - 1][y] != 0) {
            states.add(new Pair(current, new Pair(x - 1, y)));
        }
        if (y + 1 < maze[0].length && maze[x][y + 1] != 0) {
            states.add(new Pair(current, new Pair(x, y + 1)));
        }
        if (y - 1 >= 0 && maze[x][y - 1] != 0) {
            states.add(new Pair(current, new Pair(x, y - 1)));
        }
    }

    private static void findWay(Pair<Integer, Integer> start, Pair<Integer, Integer> end,
                                Pair<Integer, Integer> teleport1, Pair<Integer, Integer> teleport2,
                                HashMap resultSeq) {
        boolean foundStart = false;
        int pathLength = 0;
        Pair<Integer, Integer> current = end;
        boolean wentThroughTele = false;
        while (!foundStart) {
            if (current.getKey() == start.getKey() && current.getValue() == start.getValue()) {
                foundStart = true;
            } else if (current.getKey() == teleport1.getKey()
                    && current.getValue() == teleport1.getValue() && !wentThroughTele) {
                current = teleport2;
                wentThroughTele = true;
            } else if (current.getKey() == teleport2.getKey()
                    && current.getValue() == teleport2.getValue() && !wentThroughTele) {
                current = teleport1;
                wentThroughTele = true;
            } else {
                pathLength++;
                System.out.println(resultSeq.get(current));
                current = (Pair<Integer, Integer>) resultSeq.get(current);
            }
        }
        System.out.println(pathLength);
    }
}
