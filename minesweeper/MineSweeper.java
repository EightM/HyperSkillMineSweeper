package minesweeper;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MineSweeper {

    private int bombs;
    public void generateField() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        bombs = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < 9; i++) {
            generateRow();
        }
    }

    private void generateRow() {
        StringBuilder row = new StringBuilder(".........");
        Set<Integer> bombIndexes = new HashSet<>();
        long numberOfBombs = Math.min(Math.abs(bombs * ThreadLocalRandom.current().nextInt() % 9), bombs);
        bombs -= numberOfBombs;
        while (bombIndexes.size() != numberOfBombs) {
            bombIndexes.add(Math.abs(ThreadLocalRandom.current().nextInt() % 9));
        }

        bombIndexes.forEach(integer -> row.setCharAt(integer, 'X'));
        System.out.println(row.toString());
    }
}
