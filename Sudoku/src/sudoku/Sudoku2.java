/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

/**
 *
 * @author ayush
 */
import java.util.Random;
import java.util.Scanner;

public class Sudoku2 {

    void displayGrid(String[][] arr) {
        int line, symbols, size = arr.length, row = 0, column = 0;
        String dash;
        if (size > 9) {
            dash = "----";
        } else {
            dash = "---";
        }
        for (line = 1; line <= 2 * size + 1; line++) {
            if (line % 2 == 1) {
                System.out.print("+");
                for (symbols = 2 * size - 1; symbols > 0; symbols--) {
                    if (symbols % 2 == 1) {
                        System.out.print(dash);
                    } else {
                        System.out.print("+");
                    }
                }
                System.out.print("+");
            } else {
                for (symbols = 2 * size + 1; symbols > 0; symbols--) {
                    if (symbols % 2 == 1) {
                        System.out.print("|");
                    } else {
                        String value = String.valueOf(arr[row][column++]);
                        System.out.print(" " + value + " ");
                        if (column >= size) {
                            row++;
                            column = 0;
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    String[][] getArray(int size) {
        String[][] arr = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = "";
            }
        }
        fillArray(arr, 0, 0, size);
        return arr;
    }

    boolean fillArray(String[][] arr, int row, int col, int size) {
        if (row == size) {
            return true;
        }
        int nextRow = (col == size - 1) ? row + 1 : row;
        int nextCol = (col + 1) % size;
        int[] nums = getRandomNumbers(size);
        for (int i = 0; i < size; i++) {
            String digit = String.valueOf(nums[i]);
            if (isValid(arr, row, col, digit, size)) {
                arr[row][col] = digit;
                if (fillArray(arr, nextRow, nextCol, size)) {
                    return true;
                }
                arr[row][col] = "";
            }
        }
        return false;
    }

    boolean isValid(String[][] arr, int row, int col, String digit, int size) {
        for (int j = 0; j < col; j++) {
            if (digit.equals(arr[row][j])) {
                return false;
            }
        }
        for (int i = 0; i < row; i++) {
            if (digit.equals(arr[i][col])) {
                return false;
            }
        }
        return true;
    }

    int[] getRandomNumbers(int size) {
        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
            nums[i] = i + 1;
        }
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            int j = rand.nextInt(size);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        return nums;
    }

    void setEmptyBlocks(String[][] layout) {
        int size = layout.length;
        int emptyBlocks = (size * size) / 3;
        Random rand = new Random();
        for (int i = 0; i < emptyBlocks; i++) {
            int row = rand.nextInt(size);
            int column = rand.nextInt(size);
            layout[row][column] = " ";
        }
    }

    String[][] copyArray(String[][] arr) {
        int size = arr.length;
        String[][] newArr = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    void playGame(String[][] puzzle, String[][] solution) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your moves in the format: row X column Y value Z");
        System.out.println("Type 'exit' when you are finished.");
        while (true) {
            displayGrid(puzzle);
            System.out.println("Enter move or 'exit':");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            String[] parts = input.split(" ");
            int row = Integer.parseInt(parts[1]) - 1;
            int col = Integer.parseInt(parts[3]) - 1;
            String value = parts[5];
            puzzle[row][col] = value;
        }
        System.out.println("Final board:");
        displayGrid(puzzle);
        checkSolution(puzzle, solution);
    }

    void checkSolution(String[][] puzzle, String[][] solution) {
        int size = puzzle.length;
        boolean correct = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!puzzle[i][j].equals(solution[i][j])) {
                    correct = false;
                    System.out.println("Row " + (i + 1) + ", Column " + (j + 1) +
                                       ": expected " + solution[i][j] + ", got " + (puzzle[i][j].equals(" ") ? "empty" : puzzle[i][j]));
                }
            }
        }
        if (correct)
            System.out.println("\uD83C\uDF89 Congratulations! You solved the puzzle correctly!");
        else
            System.out.println("Your solution is incorrect.");
    }

    public static void main(String[] args) {
        Sudoku2 s = new Sudoku2();
        int size = Integer.parseInt(args[0]);
        String[][] solution = s.getArray(size);
        String[][] puzzle = s.copyArray(solution);
        s.setEmptyBlocks(puzzle);
        s.displayGrid(puzzle);
        s.playGame(puzzle, solution);
    }
}