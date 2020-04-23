package it.polimi.ingsw.client;

import it.polimi.ingsw.client.messages.Message;
import it.polimi.ingsw.client.messages.actions.workerActions.AtlasBuildAction;
import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.constants.Move;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.*;

public class ActionHandler {

    ModelView modelView;

    //TODO ADD CALLS TO CLI/GUI'S METHOD
    public void readMessage(Answer answer){
        if(answer instanceof SelectSpacesMessage){
            //print list on cli
        }
        else if(answer instanceof MoveMessage){
            Move message = (Move) answer.getMessage();
            modelView.move(message.getOldPosition().getX(), message.getOldPosition().getY(),
                            message.getNewPosition().getX(), message.getNewPosition().getY());
        }
        //TODO ELIMINARE E INCORPORARE CON BUILDMESSAGE
        else if(answer instanceof AtlasBuildMessage){
            Couple message = (Couple) answer.getMessage();
            modelView.build(message.getX(),message.getY(),true);
        }
        else if(answer instanceof BuildMessage){
            Couple message = ((BuildMessage) answer).getMessage();
            modelView.build(message.getX(),message.getY(),false);
        }
        else if(answer instanceof ApolloMoveMessage){
            Move myMove = ((ApolloMoveMessage) answer).getMessage();
            Move otherMove = ((ApolloMoveMessage) answer).getOtherMove();
            modelView.apolloDoubleMove(myMove.getOldPosition().getX(),myMove.getOldPosition().getY(),
                    otherMove.getOldPosition().getX(), otherMove.getOldPosition().getY());
        }
        else if(answer instanceof MinotaurMoveMessage){
            Move myMove = ((MinotaurMoveMessage) answer).getMessage();
            Move otherMove = ((MinotaurMoveMessage) answer).getOtherMove();
            modelView.minotaurDoubleMove(myMove.getOldPosition().getX(),myMove.getOldPosition().getY(),
                    otherMove.getOldPosition().getX(),otherMove.getOldPosition().getY(),
                    otherMove.getNewPosition().getX(),otherMove.getNewPosition().getY());
        }


    }
}
