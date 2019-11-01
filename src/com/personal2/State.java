package com.personal2;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class State implements Comparable<State> {
    private Matrix matrix;
    private Integer distance;
    private Integer level;

    public State(Matrix matrix, int level) {
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
        if (!(o instanceof State)) {
            return false;
        }
        State other = (State) o;
        if (other != null) {
            return matrix.equals(other.getMatrix());
        }
        return false;
    }

    @Override
    public int compareTo(State other) {
        if (this.getDistance() + level > other.getDistance() + other.level) {
            return 1;
        } else if (this.getDistance() < other.getDistance() + other.level) {
            return -1;
        } else {
            return 0;
        }
    }

    public ArrayList<State> generateNeighbors(int level) {
        return matrix.generateNeighbors().stream().map(m -> new State(m, level)).collect(
                Collectors.toCollection(ArrayList::new));
    }

    public int getLevel() {
        return level;
    }
}
