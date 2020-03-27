package it.polimi.ingsw.view;

import it.polimi.ingsw.model.board.GameBoard;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class View extends Observable implements Observer, Runnable {
    private Scanner input;
    private PrintStream output;

    public View() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
    }

    public void showBoard(GameBoard board) {
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++) {
                //output.print(board.getSpace(i, j).getWorker(). + " ");
            }
            output.println();
        }
    }

    @Override
    public void run() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
