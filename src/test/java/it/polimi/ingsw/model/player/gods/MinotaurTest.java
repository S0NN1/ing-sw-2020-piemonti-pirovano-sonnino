package it.polimi.ingsw.model.player.gods;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.player.PlayerColors;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerForTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MinotaurTest {

    Worker minotaur;
    GameBoard gameBoard;
    ArrayList<Worker> workers;

    @BeforeEach
    void init(){
        minotaur = new Minotaur(PlayerColors.GREEN);
        gameBoard = new GameBoard();
        workers = new ArrayList<>();
        for(int i=0; i<8; i++){
            workers.add(new WorkerForTest(PlayerColors.RED));
        }
    }

    @Test
    @DisplayName("normal move")
    void normalMove(){
        minotaur.setPosition(gameBoard.getSpace(1,1));
        assertEquals(8,minotaur.selectMoves(gameBoard).size(),"1");
    }

    @Nested
    @DisplayName("isSelectable() test")
    class isSelectable{

        @Test
        @DisplayName("border test")
        void testBorder(){
            minotaur.setPosition(gameBoard.getSpace(0,1));
            int k = 0;
            for(int i=0; i<2; i++){
                for(int j=0; j<3; j++){
                    if(!(i==0 && j==1)){        //initialize
                        workers.get(k).setPosition(gameBoard.getSpace(i,j));
                        k++;
                    }
                }
            }

           assertEquals(3,minotaur.selectMoves(gameBoard).size(),"2");
        }

        @Test
        @DisplayName("corner test")
        void testCorner(){
            minotaur.setPosition(gameBoard.getSpace(4,4));
            workers.get(0).setPosition(gameBoard.getSpace(3,3));
            workers.get(1).setPosition(gameBoard.getSpace(3,4));
            workers.get(2).setPosition(gameBoard.getSpace(4,3));

            assertEquals(3,minotaur.selectMoves(gameBoard).size(),"3");
        }
        
    }



}