package minesweeper;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MineSweeper {

    private final String[][] field = new String[9][9];
    Set<Integer> bombIndexes = new HashSet<>();
    Set<Integer> userMarks = new HashSet<>();

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
                } else {
                    checkBorderCell(i, j);
                }
            }
        }
    }

    private void checkBorderCell(int originRow, int originColumn) {

        if (bombIndexes.contains(originRow * 9 + originColumn)) {
            return;
        }

        int startRow = originRow;
        int startColumn = originColumn;
        int rowOffset = 0;
        int columnOffset = 0;
        if (originRow == 0) {
            rowOffset = 1;
            columnOffset = 2;
            startColumn--;
        } else if (originColumn == 0) {
            rowOffset = 2;
            columnOffset = 1;
            startRow--;
        } else if (originRow == 8) {
            rowOffset = 1;
            columnOffset = 2;
            startRow--;
            startColumn--;
        } else if (originColumn == 8) {
            rowOffset = 2;
            columnOffset = 1;
            startRow--;
            startColumn--;
        }

        checkField(originRow, originColumn, startRow, startColumn, rowOffset, columnOffset);

    }

    private void checkCornerCell(int originRow, int originColumn) {

        if (bombIndexes.contains(originRow * 9 + originColumn)) {
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

        checkField(originRow, originColumn, startRow, startColumn, 1, 1);
    }

    private void checkMiddleCell(int originRow, int originColumn) {

        if (bombIndexes.contains(originRow * 9 + originColumn)) {
            return;
        }

        int startRow = originRow - 1;
        int startColumn = originColumn - 1;
        checkField(originRow, originColumn, startRow, startColumn, 2, 2);
    }

    private void checkField(int originRow, int originColumn, int startRow, int startColumn, int rowOffset, int columnOffset) {
        int count = 0;
        for (int k = startRow; k <= startRow + rowOffset; k++) {
            for (int l = startColumn; l <= startColumn + columnOffset; l++) {
                if (k == originRow && l == originColumn) {
                    continue;
                }
                if (bombIndexes.contains(k * 9 + l)) {
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
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < field.length; i++) {
            String[] strings = field[i];
            System.out.print(i + 1 + "|");
            for (int j = 0; j < field.length; j++) {
                System.out.print(strings[j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    private void initializeField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = ".";
            }
        }
    }

    private void generateBombs(int bombs) {
        while (bombIndexes.size() != bombs) {
            bombIndexes.add(Math.abs(ThreadLocalRandom.current().nextInt() % 81));
        }
        // bombIndexes.forEach(integer -> field[integer / 9][integer % 9] = "X");
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (!userMarks.equals(bombIndexes)) {
            System.out.println("Set/delete mines marks " +
                    "(x and y coordinates):");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            if (isFieldWithNumber(x, y)) {
                System.out.println("There is a number here!");
                continue;
            }

            setMark(x, y);
            printField();
        }
        System.out.println("Congratulations! You have found all mines!");
    }

    private void setMark(int x, int y) {
        if (field[y - 1][x - 1].equals("*")) {
            field[y - 1][x - 1] = ".";
            userMarks.remove((y - 1) * 9 + (x - 1));
        } else {
            field[y - 1][x - 1] = "*";
            userMarks.add((y - 1) * 9 + (x - 1));
        }
    }

    private boolean isFieldWithNumber(int x, int y) {
        return !field[y - 1][x - 1].equals(".");
    }
}
