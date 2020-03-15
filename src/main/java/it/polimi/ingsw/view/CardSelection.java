package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.board.CardSelectionBoard;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class CardSelection extends Observable implements Observer, Runnable {
    private Scanner input;
    private PrintStream output;

    public CardSelection() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
    }

    public void showDescription(CardSelectionBoard model) {
        output.println(model.getDescription());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!(arg instanceof CardSelectionBoard)) {
            throw new IllegalArgumentException("Expected CardSelectionBoard type");
        }
        showDescription((CardSelectionBoard)o);
    }

    @Override
    public void run() {
        String selection;
        output.println("Select a god from a one below and get a description of him.");
        Card cards[] = Card.values();
        for(int i=0; i<cards.length; i++) {
            if(i==8) {
                output.println(cards[i].toString() + ".");
                break;
            }
            output.print(cards[i].toString() + ", ");
        }
        while(true) {
            output.print("Your selection: ");
            selection = input.next();
            try {
                Card god = Card.parseInput(selection);
                setChanged();
                notifyObservers(god);
            } catch (IllegalArgumentException e) {
                output.println("Unexpected input, it must be an element of the list above.");
            }
        }
    }
}
