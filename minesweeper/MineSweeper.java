package minesweeper;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MineSweeper {

    private final String[][] field = new String[9][9];

    public void generateField() {

        initializeField();

        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int bombs = Integer.parseInt(scanner.nextLine());
        generateBombs(bombs);
        fillTheNumbers();
        printField();
    }

    private void fillTheNumbers() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (isTheMiddleCell(i, j)) {
                    checkMiddleCell(i, j);
                } else if (isTheCornerCell(i, j)) {
                    checkCornerCell(i, j);
                }
            }
        }
    }

    private void checkCornerCell(int originRow, int originColumn) {

        if (field[originRow][originColumn].equals("X")) {
            return;
        }

        int startRow = originRow;
        int startColumn = originColumn;
        if (originRow == 0 && originColumn == 8) {
            startColumn--;
        } else if (originRow == 8 && originColumn == 0) {
            startRow--;
        } else if (originRow == 8 && originColumn == 8) {
            startRow--;
            startColumn--;
        }

        checkField(originRow, originColumn, startRow, startColumn, 1);
    }

    private void checkMiddleCell(int originRow, int originColumn) {

        if (field[originRow][originColumn].equals("X")) {
            return;
        }

        int startRow = originRow - 1;
        int startColumn = originColumn - 1;
        checkField(originRow, originColumn, startRow, startColumn, 2);
    }

    private void checkField(int originRow, int originColumn, int startRow, int startColumn, int offset) {
        int count = 0;
        for (int k = startRow; k <= startRow + offset; k++) {
            for (int l = startColumn; l < startColumn + offset; l++) {
                if (k == originRow && l == originColumn) {
                    continue;
                }
                if (field[k][l].equals("X")) {
                    count++;
                }
            }
        }

        if (count > 0) {
            field[originRow][originColumn] = String.valueOf(count);
        }
    }

    private boolean isTheCornerCell(int i, int j) {
        return (i == 0 && j == 0) || (i == 8 && j == 0)
                || (i == 0 && j == 8)
                || (i == 8 && j == 8);
    }

    private boolean isTheMiddleCell(int i, int j) {
        return (i > 0 && i < 8 && j > 0 && j < 8);
    }

    private void printField() {
        for (String[] strings : field) {
            for (int j = 0; j < field.length; j++) {
                System.out.print(strings[j]);
            }
            System.out.println();
        }
    }

    private void initializeField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = ".";
            }
        }
    }

    private void generateBombs(int bombs) {
        Set<Integer> bombIndexes = new HashSet<>();
        while (bombIndexes.size() != bombs) {
            bombIndexes.add(Math.abs(ThreadLocalRandom.current().nextInt() % 81));
        }
        bombIndexes.forEach(integer -> field[integer / 9][integer % 9] = "X");
    }
}
