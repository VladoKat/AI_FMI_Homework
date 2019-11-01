package com.personal2;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private static final int[][] finalMatrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    private int[][] matrix;
    private int xPos0;
    private int yPos0;

    public Matrix(int[][] matrix) {
        super();
        this.matrix = matrix;
        setZeroPosition(matrix);
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        setZeroPosition(matrix);
    }

    private void setZeroPosition(int[][] matrix) {
        boolean foundZero = false;
        for (int i = 0; i < matrix.length && !foundZero; i++) {
            for (int j = 0; j < matrix[0].length && !foundZero; j++) {
                if (matrix[i][j] == 0) {
                    yPos0 = i;
                    xPos0 = j;
                    foundZero = true;
                }
            }
        }
    }

    public ArrayList<Matrix> generateNeighbors() {
        ArrayList<Matrix> result = new ArrayList<>();
        if (xPos0 + 1 < matrix.length) {
            int[][] tempArr = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
            tempArr[yPos0][xPos0] = matrix[yPos0][xPos0 + 1];
            tempArr[yPos0][xPos0 + 1] = 0;
            result.add(new Matrix(tempArr));
        }
        if (xPos0 - 1 >= 0) {
            int[][] tempArr = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
            tempArr[yPos0][xPos0] = matrix[yPos0][xPos0 - 1];
            tempArr[yPos0][xPos0 - 1] = 0;
            result.add(new Matrix(tempArr));
        }
        if (yPos0 + 1 < matrix[0].length) {
            int[][] tempArr = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
            tempArr[yPos0][xPos0] = matrix[yPos0 + 1][xPos0];
            tempArr[yPos0 + 1][xPos0] = 0;
            result.add(new Matrix(tempArr));
        }
        if (yPos0 - 1 >= 0) {
            int[][] tempArr = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
            tempArr[yPos0][xPos0] = matrix[yPos0 - 1][xPos0];
            tempArr[yPos0 - 1][xPos0] = 0;
            result.add(new Matrix(tempArr));
        }
        return result;
    }

    public Integer calculateDistance() {
        int length = 0;
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                if (this.matrix[i][j] != finalMatrix[i][j]) {
                    length += distance(matrix[i][j], i, j);
                }
            }
        }
        return length;
    }

    private int distance(int num, int xNum, int yNum) {
        for (int i = 0; i < finalMatrix.length; i++) {
            for (int j = 0; j < finalMatrix.length; j++) {
                if (finalMatrix[i][j] == num) {
                    int result = 0;
                    result += Math.abs(i - xNum);
                    result += Math.abs(j - yNum);
                    return result;
                }
            }
        }
        return Integer.MAX_VALUE; // never occurs
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Matrix)) {
            return false;
        }
        Matrix other = (Matrix) o;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != other.matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
