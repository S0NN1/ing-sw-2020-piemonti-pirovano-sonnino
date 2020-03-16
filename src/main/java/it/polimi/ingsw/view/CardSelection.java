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

    public void godList() {
        output.println("Here's a list of selectable gods.");
        Card cards[] = Card.values();
        for(int i=0; i<cards.length; i++) {
            if(i==8) {
                output.println(cards[i].toString() + ".");
                break;
            }
            output.print(cards[i].toString() + ", ");
        }
    }

    public void godDescription() {
        output.println("Select a god and get a description of him. Type exit to stop.");
        String selection;
        while(true) {
            output.print("Your selection: ");
            selection = input.next();
            if (selection.equalsIgnoreCase("exit")) break;
            try {
                Card god = Card.parseInput(selection);
                setChanged();
                notifyObservers(god);
            } catch (IllegalArgumentException e) {
                output.println("Unexpected input, it must be an element of the list above.");
            }
        }
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
        int selectionCounter = 0;
        while (selectionCounter < 3) {
            String command;
            output.print("Command: ");
            command = input.next();
            switch (command.toUpperCase()) {
                case "LIST":
                    godList();
                    break;
                case "DESCRIPTION":
                    godDescription();
                    break;
                case "SELECT":
                    selectionCounter++;
                    break;
                default:
                    throw new IllegalArgumentException("Wrong argument; the avaible ones are LIST, DESCRIPTION and SELECT");
            }
        }
    }
}
