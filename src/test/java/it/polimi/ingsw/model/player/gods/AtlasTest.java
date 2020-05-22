package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.constants.Couple;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Space;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClient;
import it.polimi.ingsw.server.answers.Answer;
import it.polimi.ingsw.server.answers.worker.BuildMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Piemonti
 */
class AtlasTest {

    Worker atlas;
    GameBoard gameBoard;
    Space build;

    @BeforeEach
    void init(){
        atlas = new Atlas(PlayerColors.RED);
        gameBoard = new GameBoard();
        atlas.setPosition(gameBoard.getSpace(1,1));
        build = gameBoard.getSpace(2,2);

    }

    /**
     * test the method build: build a normal block. Super class method is called.
     */
    @Test
    @DisplayName("build block")
    void buildBlock() {
        VirtualClientStub client = new VirtualClientStub();
        atlas.createListeners(client);
        assertEquals(8, atlas.getBuildableSpaces(gameBoard).size(),"1");

        assertTrue(atlas.canBuildOnto(build),"1b");
        assertTrue(atlas.build(build,false),"2"); //build a normal block
        assertEquals(1,build.getTower().getHeight(),"3");
        assertFalse(build.getTower().isCompleted(),"4");

        assertEquals(build.getRow(),client.getBuild().getRow(),"5");
        assertEquals(build.getColumn(),client.getBuild().getColumn(),"6");
        assertFalse(client.isDome(),"7");
    }

    /**
     * test the method build: build a dome at any level. Notify buildListener with the space and dome value = true
     */
    @Test
    @DisplayName("build dome")
    void buildDome(){
        VirtualClientStub client = new VirtualClientStub();
        atlas.createListeners(client);
        assertTrue(atlas.build(build, true),"1");

        assertTrue(build.getTower().isCompleted(),"2");
        assertEquals(0,build.getTower().getHeight(),"3");

        assertEquals(build.getRow(),client.getBuild().getRow(),"4");
        assertEquals(build.getColumn(),client.getBuild().getColumn(),"5");
        assertTrue(client.isDome(),"6");
    }

    private static class VirtualClientStub extends VirtualClient {

       private Couple build;
       private boolean dome;
        /**
         * Prepares the answer for sending it through the network, putting it in a serialized package, called SerializedMessage,
         * then sends the packaged answer to the transmission protocol, located in the socket-client handler.
         *
         * @param serverAnswer the answer to be sent to the user.
         * @see SocketClientConnection for more details.
         */
        @Override
        public void send(Answer serverAnswer) {
            if(serverAnswer instanceof BuildMessage){
                build = ((BuildMessage) serverAnswer).getMessage();
                dome = ((BuildMessage) serverAnswer).getDome();
            }
            else fail("not build message");
        }
        @Override
        public void sendAll(Answer serverAnswer) {
            if(serverAnswer instanceof BuildMessage){
                build = ((BuildMessage) serverAnswer).getMessage();
                dome = ((BuildMessage) serverAnswer).getDome();
            }
            else fail("not build message");
        }

        public Couple getBuild() {
            return build;
        }

        public boolean isDome() {
            return dome;
        }
    }
}