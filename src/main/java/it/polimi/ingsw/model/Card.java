package it.polimi.ingsw.model;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public enum Card {
    APOLLO, ARTEMIS, ATHENA, ATLAS, DEMETER, HEPHAESTUS, MINOTAUR, PAN, PROMETHEUS;

    public static Card parseInput(String input) {
        return Enum.valueOf(Card.class, input.toUpperCase());
    }

    public String godsDescription() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/json/gods.json")) {
            Object obj = parser.parse(reader);
            JSONArray godList = (JSONArray) obj;
            switch (this) {
                case APOLLO:
                    return godList.get(0).toString();
                case ARTEMIS:
                    return godList.get(1).toString();
                case ATHENA:
                    return godList.get(2).toString();
                case ATLAS:
                    return godList.get(3).toString();
                case DEMETER:
                    return godList.get(4).toString();
                case HEPHAESTUS:
                    return godList.get(5).toString();
                case MINOTAUR:
                    return godList.get(6).toString();
                case PAN:
                    return godList.get(7).toString();
                case PROMETHEUS:
                    return godList.get(8).toString();
                default:
                    throw new IllegalArgumentException("Unexpected god");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Unexpected god");
    }
}
