package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.Server;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Class Santorini is the main class of whole project.
 *
 * @author Alice Piemonti, Luca Pirovano, Nicolò Sonnino
 */
public class Santorini {

    /**
     * Method main selects CLI, GUI or Server based on the arguments provided.
     *
     * @param args of type String[]
     */
    public static void main(String[] args){
        System.out.println("Hi! Welcome to Santorini!\nWhat do you want to launch?");
        System.out.println("0. SERVER\n1. CLIENT (CLI INTERFACE)\n2. CLIENT (GUI INTERFACE)");
        System.out.println("\n>Type the number of the desired option!");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }
        switch (input) {
            case 0 -> Server.main(null);
            case 1 -> CLI.main(null);
            case 2 -> {
                System.out.println("You selected the GUI interface, have fun!\nStarting...");
                GUI.main(null);
            }
            default -> System.err.println("Invalid argument, please run the executable again with one of these options:\n1.server\n2.client");
        }
    }
}
