package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.player.gods.advancedgods.*;
import it.polimi.ingsw.model.player.gods.simplegods.*;

/**
 * Class WorkerCreator is the factory provider class for Worker instances. This class provides an implementation of Worker depending on the Card and the PlayerColor supplied.
 *
 * @author Alice Piemonti
 * Created on 01/06/2020
 */
public class WorkerCreator {

    /**
     * Constructor WorkerCreator creates a new WorkerCreator instance.
     */
    public WorkerCreator() {
    }

    /**
     * Method getWorker creates an implementation of Worker, according to the Factory Pattern. The implementation of Worker is created with the supplied of the Card and the PlayerColor.
     *
     * @param godCard of type Card - The card chose during the game, which specifies the instance of Worker to implement.
     * @param color of type PlayerColors - The color of the player who chose the card.
     * @return Worker - The instance of Worker associated to the card.
     */
    public Worker getWorker( Card godCard, PlayerColors color) {
        switch (godCard) {
            case APOLLO -> {
                return new Apollo(color);
            }
            case ARES -> {
                return new Ares(color);
            }
            case ARTEMIS -> {
                return new Artemis(color);
            }
            case ATLAS -> {
                return new Atlas(color);
            }
            case CHARON -> {
                return new Charon(color);
            }
            case DEMETER -> {
                return new Demeter(color);
            }
            case HEPHAESTUS -> {
                return new Hephaestus(color);
            }
            case HESTIA -> {
                return new Hestia(color);
            }
            case MINOTAUR -> {
                return new Minotaur(color);
            }
            case PAN -> {
                return new Pan(color);
            }
            case PROMETHEUS -> {
                return new Prometheus(color);
            }
            case TRITON -> {
                return new Triton(color);
            }
            case ZEUS -> {
                return new Zeus(color);
            }
            default -> {
                return null;
            }
        }

    }
}
