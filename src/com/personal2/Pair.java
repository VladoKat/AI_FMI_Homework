package com.personal2;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Pair implements Comparable<Pair> {
    private Matrix matrix;
    private Integer distance;
    private Integer level;

    public Pair(Matrix matrix, int level) {
        super();
        this.matrix = matrix;
        this.level = level;
        this.calculateDistance();
    }

    private void calculateDistance() {
        this.distance = matrix.calculateDistance();
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public Integer getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair other = (Pair) o;
        if (other != null) {
            return matrix.equals(other.getMatrix());
        }
        return false;
    }

    @Override
    public int compareTo(Pair other) {
        if (this.getDistance() + level > other.getDistance() + other.level) {
            return 1;
        } else if (this.getDistance() < other.getDistance() + other.level) {
            return -1;
        } else {
            return 0;
        }
    }

    public ArrayList<Pair> generateNeighbors(int level) {
        return matrix.generateNeighbors().stream().map(m -> new Pair(m, level)).collect(
                Collectors.toCollection(ArrayList::new));
    }

    public int getLevel() {
        return level;
    }
}
