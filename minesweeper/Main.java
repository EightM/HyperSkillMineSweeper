package minesweeper;

public class Main {

    public static void main(String[] args) {
        MineSweeper mineSweeper = new MineSweeper();
        mineSweeper.generateField();
        mineSweeper.startGame();
    }
}
