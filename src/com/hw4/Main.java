package com.hw4;

import java.util.Scanner;

public class Main {
    private static final char EMPTY = '_';
    private static final char X = 'x';
    private static final char O = 'o';
    private static int MIN = -1000;
    private static int MAX = 1000;
    // Java program to find the
    // next optimal move for a X

    static class Move {
        int row, col;
    }


    // This function returns true if there are moves
    // remaining on the board. It returns false if
    // there are no moves left to play.
    static Boolean isMovesLeft(char board[][]) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == EMPTY)
                    return true;
        return false;
    }

    // This is the evaluation function as discussed
    // in the previous article ( http://goo.gl/sJgv68 )
    static int evaluate(char board[][]) {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] &&
                    board[row][1] == board[row][2]) {
                if (board[row][0] == X)
                    return +10;
                else if (board[row][0] == O)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] &&
                    board[1][col] == board[2][col]) {
                if (board[0][col] == X)
                    return +10;

                else if (board[0][col] == O)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == X)
                return +10;
            else if (board[0][0] == O)
                return -10;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == X)
                return +10;
            else if (board[0][2] == O)
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
    // the possible ways the game can go and returns
    // the value of the board
    static int minimax(char board[][],
                       int depth, Boolean isMax, int alpha, int beta) {
        int score = evaluate(board);

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (!isMovesLeft(board))
            return 0;

        // If this maximizer's move
        int best = isMax ? -1000 : 1000;

        // Traverse all cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board[i][j] == EMPTY) {
                    // Make the move
                    board[i][j] = isMax ? X : O;

                    // Call minimax recursively and choose
                    // value depending on whose move it is
                    best = isMax ? Math.max(best, minimax(board, depth + 1, false, alpha, beta))
                            : Math.min(best, minimax(board, depth + 1, true, alpha, beta));
                    if(isMax){
                        alpha = Math.max(alpha, best);
                    } else {
                        beta = Math.min(beta, best);
                    }
                    if(beta <= alpha){
                        //Undo the move
                        board[i][j] = EMPTY;
                        return best;
                    }
                    // Undo the move
                    board[i][j] = EMPTY;
                }
            }
        }
        return best;
    }

    // This will return the best possible move
    static Move findBestMove(char board[][], boolean isMax) {
        int bestVal = isMax ? -1000 : 1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board[i][j] == EMPTY) {
                    // Make the move
                    board[i][j] = isMax ? X : O;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, !isMax, MIN, MAX);

                    // Undo the move
                    board[i][j] = EMPTY;

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if ((isMax && moveVal > bestVal) || (!isMax && moveVal < bestVal)) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }


    // Driver code
    public static void main(String[] args) {
        char board[][] = {
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY}};
        System.out.println("Choose \'x\' or \'o\'");
        Scanner sc = new Scanner(System.in);

        String character = sc.nextLine();
        switch (character) {
            case "o": {
                System.out.println("You chose to be second!");
                break;
            }
            case "x": {
                System.out.println("You chose to be first!");
                break;
            }
            default:
                System.out.println("Wrong input!");
        }

        if (!(character.equals("o") || character.equals("x"))) {
            System.out.println("in this if");
            return;
        }

        boolean isComputerMax;
        boolean isPlayerTurn;
        char playerSign;
        char computerSign;

        if (character.equals("x")) {

            playerSign = X;
            computerSign = O;
            isPlayerTurn = true;
            isComputerMax = false;
        } else {
            playerSign = O;
            computerSign = X;
            isPlayerTurn = false;
            isComputerMax = true;
        }

        while (isMovesLeft(board) && evaluate(board) == 0) {
            if (isPlayerTurn) {
                System.out.println("Please, enter row and column of desired spot for your mark, separated by a new line: ");
                Integer row = sc.nextInt();
                Integer col = sc.nextInt();
                if (board[row][col] == EMPTY) {
                    board[row][col] = playerSign;
                    isPlayerTurn = false;
                } else {
                    System.out.println("Invalid field! Restart Game!");
                    return;
                }

            } else {
                Move bestMove = findBestMove(board, isComputerMax);
                board[bestMove.row][bestMove.col] = computerSign;
                isPlayerTurn = true;
            }
            printBoard(board);
        }

    }

    private static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print("|" + board[i][j]);
                if (j == board[0].length - 1) {
                    System.out.println("|");
                }
            }
            System.out.println();
        }
    }

}

