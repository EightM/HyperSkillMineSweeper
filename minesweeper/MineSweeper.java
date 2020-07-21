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
                }
            }
        }
    }

    private void checkMiddleCell(int i, int j) {

        if (field[i][j].equals("X")) {
            return;
        }

        int count = 0;
        int startRow = i - 1;
        int startColumn = j - 1;
        for (int k = startRow; k <= startRow + 2; k++) {
            for (int l = startColumn; l <= startColumn + 2; l++) {
                if (k == i && l == j) {
                    continue;
                }
                if (field[k][l].equals("X")) {
                    count++;
                }
            }
        }

        if (count > 0) {
            field[i][j] = String.valueOf(count);
        }
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
