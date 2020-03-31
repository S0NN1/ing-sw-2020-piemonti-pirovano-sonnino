package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    @BeforeEach
    void init(){
        Worker worker = new Worker(PlayerColors.RED);
    }

    @Test
    void colorTest(){
        Worker workerBlue = new Worker(PlayerColors.BLUE);
        Worker workerRed = new Worker(PlayerColors.RED);
        Worker workerGreen = new Worker(PlayerColors.GREEN);
        System.out.println(workerRed.getWorkerColor() + "red");
        System.out.println(workerGreen.getWorkerColor() + "green");
        System.out.println(workerBlue.getWorkerColor() + "blue");
    }

    @Test
    void setPosition() {
    }

    @Test
    void getPosition() {
    }

    @Test
    void isBlocked() {
    }

    @Test
    void hasWon() {
    }

    @Test
    void move() {
    }

    @Test
    void isSelectable() {
    }

    @Test
    void getMoves() {
    }

    @Test
    void build() {
    }

    @Test
    void isBuildable() {
    }

    @Test
    void getBuildableSpaces() {
    }
}