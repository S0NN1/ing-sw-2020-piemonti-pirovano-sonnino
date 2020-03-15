package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Card {
    APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS;

    public static Card parseInput(String input) {
        return Enum.valueOf(Card.class, input.toUpperCase());
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
