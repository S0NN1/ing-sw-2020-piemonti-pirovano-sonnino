package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.exceptions.CardNotChosenException;
import it.polimi.ingsw.exceptions.DuplicateGodException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Card {
    APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS;

    private static ArrayList<Card> chosen = new ArrayList<>();

    public static Card parseInput(String input) {
        return Enum.valueOf(Card.class, input.toUpperCase());
    }

    public static Card parseChoice(String input) throws CardNotChosenException {
        Card card = Enum.valueOf(Card.class, input.toUpperCase());
        if (!chosen.remove(card)) {
            throw new CardNotChosenException();
        }
        return card;

    }

    public static void alreadyAdded(Card card) throws DuplicateGodException {
        if(chosen.contains(card)) {
            throw new DuplicateGodException();
        }
        chosen.add(card);
    }

    public String godsDescription() {

        Gson gson = new Gson();

        try {
            God[] god = gson.fromJson(new FileReader("src/main/resources/json/gods.json"), God[].class);
            for(God god1:god) {
                if (god1.getName().equalsIgnoreCase(this.toString())) {
                    return("Property: " + god1.getProperty() + "\nDescription: " + god1.getDesc());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Unexpected god");
    }
}
