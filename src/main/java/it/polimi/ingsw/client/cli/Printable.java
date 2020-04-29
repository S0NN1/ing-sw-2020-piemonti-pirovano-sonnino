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
    public String lineBlock = "█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████";
    public String rowWave = blue + "≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈" + rst;
    public String coupleRowWave = blue + "≈≈" + rst;
    public String lvl0 =
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
                    green + "███████████████████████" + rst + "\n" +
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
                    "║█████████████████████║" + "\n" +
                    "║█████████████████████║" + "\n" +
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
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
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
                    "║" + purple + "│" + rst + "█" + cyan + "████████████████" + rst + "█" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + cyan + "███████████████████" + purple + "│" + rst + "║\n" +
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
                    "║" + purple + "│" + rst + "█" + cyan + "█████" + rst + blue + "███████" + cyan + "█████" + rst + "█" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + cyan + "█████" + rst + blue + "█████████" + cyan + "█████" + purple + "│" + rst + "║\n" +
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
                    green + "████████" + blue + "███████" + green + "████████" + rst + "\n" +
                    green + "███████" + blue + "█████████" + green + "███████" + rst + "\n" +
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
                    "║███████" + blue + "███████" + rst + "███████║" + "\n" +
                    "║██████" + blue + "█████████" + rst + "██████║" + "\n" +
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
                    "║" + purple + "│" + rst + "██████" + blue + "███████" + rst + "██████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "█████" + blue + "█████████" + rst + "█████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "██████" + blue + "███████" + rst + "██████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "████████" + blue + "███" + rst + "████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "│" + rst + "███████████████████" + purple + "│" + rst + "║\n" +
                    "║" + purple + "└───────────────────┘" + rst + "║\n" +
                    "╚═════════════════════╝";

}
