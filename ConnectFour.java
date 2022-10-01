package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import four.Cell;

public class ConnectFour extends JFrame {

    private int playerNumber = 1;
    private String symbol = "X";
    private Cell[][] boardArr = new Cell[6][7];
    private boolean winnerFound = false;

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Cell pressed = (Cell) e.getSource();
            if (pressed.getText().equals("Reset")) {
                resetBoard();
            } else {
                if (!pressed.getText().equals("X") && !pressed.getText().equals("O") && !isWinnerFound()) {
                    getCorrectCell(pressed).setText(symbol);
                    checkWinner();
                    changePlayer();
                }
            }
        }
    }

    public ConnectFour() {

        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setTitle("Connect Four");

        setBoard();

        setLayout(new GridLayout(7, 7));
        setVisible(true);

    }

    private void setBoard() {

        for (int i = boardArr.length-1; i >= 0; i--) {
            for (int j = boardArr[i].length-1; j >= 0 ; j--) {
                String let = null;
                switch (j) {
                    case 6 -> let = "A";
                    case 5 -> let = "B";
                    case 4 -> let = "C";
                    case 3 -> let = "D";
                    case 2 -> let = "E";
                    case 1 -> let = "F";
                    case 0 -> let = "G";
                }
                boardArr[i][j] = new Cell(" ");
                boardArr[i][j].setName("Button" + let + (i+1));
                boardArr[i][j].addActionListener(new ButtonListener());
                boardArr[i][j].setBackground(Color.lightGray);
                boardArr[i][j].setMargin(new Insets(0, 0, 0, 0));
                add(boardArr[i][j]);
            }
        }
        Cell reset = new Cell("Reset");
        reset.setName("ButtonReset");
        reset.setMargin(new Insets(0, 0, 0, 0));
        reset.addActionListener(new ButtonListener());
        add(reset);

    }
    private Cell getCorrectCell(Cell pressed){


        int column = 0;
        Cell freeCell = new Cell();
        for (int i = boardArr.length-1; i >= 0; i--) {
            for (int j = boardArr[i].length-1; j >= 0 ; j--) {
                if (boardArr[i][j].equals(pressed)) {
                    column = j;
                }
            }
        }
        for (int i = 5; i >= 0; i--) {
            if (boardArr[i][column].getText().equals(" ")) {
                freeCell = boardArr[i][column];
                boardArr[i][column] = freeCell;
            }
        }
        return freeCell;
    }

    private void resetBoard() {

        for (int i = boardArr.length - 1; i >= 0; i--) {
            for (int j = boardArr[i].length - 1; j >= 0; j--) {
                boardArr[i][j].setText(" ");
                boardArr[i][j].setBackground(Color.lightGray);
                boardArr[i][j].setMargin(new Insets(0, 0, 0, 0));
            }
        }
        setPlayer(1);
        setSymbol("X");
        setWinnerFound(false);
    }

    private void changePlayer() {

        if (playerNumber == 1) {
            setPlayer(2);
            setSymbol("O");
        } else {
            setPlayer(1);
            setSymbol("X");
        }
    }

    private void checkWinner() {

        Cell temp = new Cell();
        int count = 0;
        for (int i = 0; i < boardArr.length; i++) {
            for (int j = 0; j < boardArr[i].length; j++) {
                temp = boardArr[i][j];
                if (temp.getText().equals(" ")) {
                    continue;
                } else if (j <= boardArr[i].length - 4 && temp.getText().equals(boardArr[i][j + 1].getText()) && temp.getText().equals(boardArr[i][j + 2].getText()) && temp.getText().equals(boardArr[i][j + 3].getText())) {
                    gameWon(temp, boardArr[i][j + 1], boardArr[i][j + 2], boardArr[i][j + 3]);
                } else if (j >= boardArr[i].length + 4 && temp.getText().equals(boardArr[i][j - 1].getText()) && temp.getText().equals(boardArr[i][j - 2].getText()) && temp.getText().equals(boardArr[i][j - 3].getText())) {
                    gameWon(temp, boardArr[i][j - 1], boardArr[i][j - 2], boardArr[i][j - 3]);
                } else if (i <= boardArr.length - 4 && temp.getText().equals(boardArr[i + 1][j].getText()) && temp.getText().equals(boardArr[i + 1][j].getText()) && temp.getText().equals(boardArr[i + 3][j].getText())) {
                    gameWon(temp, boardArr[i + 1][j], boardArr[i + 2][j], boardArr[i + 3][j]);
                } else if (i <= boardArr.length - 4 && j <= boardArr[i].length -4) {
                    if (temp.getText().equals(boardArr[i + 1][j + 1].getText()) && temp.getText().equals(boardArr[i + 2][j + 2].getText()) && temp.getText().equals(boardArr[i + 3][j + 3].getText())) {
                        gameWon(temp, boardArr[i+1][j + 1], boardArr[i+2][j + 2], boardArr[i+3][j + 3]);
                    }
                } else if (i <= boardArr.length - 4 && j >= boardArr[i].length - 4) {
                    if (temp.getText().equals(boardArr[i + 1][j - 1].getText()) && temp.getText().equals(boardArr[i + 2][j - 2].getText()) && temp.getText().equals(boardArr[i + 3][j - 3].getText())) {
                        gameWon(temp, boardArr[i + 1][j - 1], boardArr[i + 2][j - 2], boardArr[i + 3][j - 3]);
                    }
                }
            }
        }
    }

    private void gameWon(Cell cell1, Cell cell2, Cell cell3, Cell cell4) {

        Cell[] cellArr = new Cell[]{cell1, cell2, cell3, cell4};

        for (Cell cell : cellArr) {
            cell.setBackground(Color.cyan);
        }

        setWinnerFound(true);

    }

    public int getPlayer() {
        return playerNumber;
    }

    public void setPlayer(int player) {
        this.playerNumber = player;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isWinnerFound() {
        return winnerFound;
    }

    public void setWinnerFound(boolean winnerFound) {
        this.winnerFound = winnerFound;
    }
}