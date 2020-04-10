package it.polimi.ingsw.constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    //match constants
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 3;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    //server constants
    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 2500;

    //datetime
    public static String getInfo() {
        return(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " INFO: ");
    }

    public static String getErr() {
        return(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ERR: ");
    }


}