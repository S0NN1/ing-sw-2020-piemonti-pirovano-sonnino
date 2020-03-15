package it.polimi.ingsw;

import it.polimi.ingsw.controller.GodSelectionController;
import it.polimi.ingsw.model.board.CardSelectionBoard;
import it.polimi.ingsw.view.CardSelection;


public class App 
{
    public static void main( String[] args ) {
        CardSelectionBoard model = new CardSelectionBoard();
        CardSelection view = new CardSelection();
        GodSelectionController controller = new GodSelectionController(model, view);

        model.addObserver(view);
        view.addObserver(controller);

        view.run();
    }
}
