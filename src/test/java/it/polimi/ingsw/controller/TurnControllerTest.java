package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.messages.actions.turnActions.StartTurnAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

class TurnControllerTest {
    @BeforeEach
    void setValues() {
        StartTurnAction action = new StartTurnAction() {
            @Override
            public Object getMessage() {
                return null;
            }
        };
        Assertions.assertEquals("start", action.option);
    }
}