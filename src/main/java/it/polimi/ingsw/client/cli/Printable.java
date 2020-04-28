package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.constants.Constants;

public class Printable {
    public final String green = Constants.ANSI_GREEN;
    private final String red = Constants.ANSI_RED;
    private final String rst = Constants.ANSI_RESET;
    private final String blue = Constants.ANSI_BLUE;
    private final String cyan = Constants.ANSI_CYAN;
    private final String purple = Constants.ANSI_PURPLE;
    private final String yellow = Constants.ANSI_YELLOW;
    public String upperBodyWorker = "u";
    public String lowerBodyWorker = "u";
    public String RowWave = blue + "≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈" + rst;
    public String coupleRowWave = blue + "≈≈" + rst;
    public String lvl0 =
            green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████" + upperBodyWorker + green + "███████████" + rst + "\n" +
                    green + "███████████" + lowerBodyWorker + green + "███████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n";
    public String lvl1 =
            "╔═════════════════════╗" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║██████████" + upperBodyWorker + "██████████║" + "\n" +
                    "║██████████" + lowerBodyWorker + "██████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "╚═════════════════════╝";
    public String lvl2 =
            "╔═════════════════════╗\n" +
                    "║" + purple + "┌───────────────────┐" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█████████" + upperBodyWorker + "█████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█████████" + lowerBodyWorker + "█████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "└───────────────────┘" + rst + "║\n" +
                    "╚═════════════════════╝";
    public String lvl3 =
            "╔═════════════════════╗\n" +
                    "║" + purple + "┌───────────────────┐" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█████" + cyan + "█████████" + rst + "█████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███" + cyan + "█████████████" + rst + "███" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█" + cyan + "████████" + upperBodyWorker + cyan + "████████" + rst + "█" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + cyan + "█████████" + lowerBodyWorker + cyan + "█████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█" + cyan + "█████████████████" + rst + "█" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███" + cyan + "█████████████" + rst + "███" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█████" + cyan + "█████████" + rst + "█████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "└───────────────────┘" + rst + "║\n" +
                    "╚═════════════════════╝ ";
    public String lvl4 =
            "╔═════════════════════╗\n" +
                    "║" + purple + "┌───────────────────┐" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█████" + cyan + "█████████" + rst + "█████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███" + cyan + "█████" + rst + blue + "███" + cyan + "█████" + rst + "███" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█" + cyan + "█████" + rst + blue + "███" + upperBodyWorker + blue + "███" + cyan + "█████" + rst + "█" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + cyan + "█████" + rst + blue + "████" + lowerBodyWorker + blue + "████" + cyan + "█████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█" + cyan + "█████" + rst + blue + "███████" + cyan + "█████" + rst + "█" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███" + cyan + "█████" + rst + blue + "███" + cyan + "█████" + rst + "███" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█████" + cyan + "█████████" + rst + "█████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "└───────────────────┘" + rst + "║\n" +
                    "╚═════════════════════╝ ";
    public String lvl0c =
            green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "██████████" + blue + "███" + green + "██████████" + rst + "\n" +
                    green + "████████" + blue + "███" + upperBodyWorker + blue + "███" + green + "████████" + rst + "\n" +
                    green + "███████" + blue + "████" + lowerBodyWorker + blue + "████" + green + "███████" + rst + "\n" +
                    green + "████████" + blue + "███████" + green + "████████" + rst + "\n" +
                    green + "██████████" + blue + "███" + green + "██████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n";
    public String lvl1c =
            "╔═════════════════════╗" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║█████████" + blue + "███" + rst + "█████████║" + "\n" +
                    "║███████" + blue + "███" + upperBodyWorker + blue + "███" + rst + "███████║" + "\n" +
                    "║██████" + blue + "████" + lowerBodyWorker + blue + "████" + rst + "██████║" + "\n" +
                    "║███████" + blue + "███████" + rst + "███████║" + "\n" +
                    "║█████████" + blue + "███" + rst + "█████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
                    "╚═════════════════════╝";
    public String lvl2c =
            "╔═════════════════════╗\n" +
                    "║" + purple + "┌───────────────────┐" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "████████" + blue + "███" + rst + "████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "██████" + blue + "███" + upperBodyWorker + blue + "███" + rst + "██████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█████" + blue + "████" + lowerBodyWorker + blue + "████" + rst + "█████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "██████" + blue + "███████" + rst + "██████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "████████" + blue + "███" + rst + "████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "└───────────────────┘" + rst + "║\n" +
                    "╚═════════════════════╝";

}
