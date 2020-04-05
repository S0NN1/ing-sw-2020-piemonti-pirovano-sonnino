package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.exceptions.DuplicateGodException;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.CardSelectionModel;
import it.polimi.ingsw.model.player.PlayersNumber;
import it.polimi.ingsw.observer.CardObservable;
import it.polimi.ingsw.observer.CardObserver;

import java.io.PrintStream;
import java.util.Scanner;


/**
 * @author Luca Pirovano
 */

public class CardSelection extends CardObservable<GodSelectionController> implements Runnable {
    private Scanner input;
    private PrintStream output;

    public CardSelection() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
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

    @Override
    public void run() {
        output.println("You have to choose gods power. Type LIST to get a list of available gods, DESC <god name> to get a god's description and ADD <god name> to add a God power to deck.");
        int selectionCounter = 0;
        while (selectionCounter < PlayersNumber.playerNumber) {
            output.println("Remaining gods to choose: " + (PlayersNumber.playerNumber - selectionCounter));
            String command;
            output.print("Command: ");
            command = input.next();
            try {
                switch (command.toUpperCase()) {
                    case "LIST":
                        godList();
                        break;
                    case "DESC":
                        notify("DESC", Card.parseInput(input.next()));
                        break;
                    case "ADD":
                        try {
                            Card selection = Card.parseInput(input.next());
                            Card.alreadyAdded(selection);
                            notify("ADD", selection);
                            selectionCounter++;
                        }
                        catch (DuplicateGodException e) {
                            System.err.println("God has already been added!");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong argument; the available ones are LIST, DESCRIPTION and SELECT");
                }
            }
            catch (IllegalArgumentException e) {
                System.err.println("Unexpected god!");
            }
        }
    }
}
