package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.constants.Constants;

public class Printable {
    public final String green = Constants.ANSI_GREEN;
    private final String red = Constants.ANSI_RED;
    private final String rst = Constants.ANSI_RESET;
    private final String blue = Constants.ANSI_BLUE;
    private final String yellow = Constants.ANSI_YELLOW;
    public String lineBlock = "█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████";
    public String rowWave = blue + "≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈" + rst;
    public String coupleRowWave = blue + "≈≈" + rst;
    public String[] levels;
    public String[] levelsC;

    public Printable() {
        levels = new String[5];
        levelsC = new String[3];
        String purple = Constants.ANSI_PURPLE;
        for (int i = 0; i <= 4; i++) {
            if (i == 0) {
                String lvl0 = green + "███████████████████████" + rst + "\n" +
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
                levels[i] = lvl0;
            }
            if (i == 1) {
                String lvl1 = "╔═════════════════════╗" + "\n" +
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
                levels[i] = lvl1;
            }
            if (i == 2) {
                String lvl2 = "╔═════════════════════╗\n" +
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
                levels[i] = lvl2;
            }
            String cyan = Constants.ANSI_CYAN;
            if (i == 3) {
                String lvl3 = "╔═════════════════════╗\n" +
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
                levels[i] = lvl3;
            } else if(i==4) {
                String lvl4 = "╔═════════════════════╗\n" +
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
                levels[i] = lvl4;
            }
        }
        for (int i = 0; i <= 2; i++) {
            if (i == 0) {
                String lvl0c = green + "███████████████████████" + rst + "\n" +
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
                levelsC[i] = lvl0c;
            }
            if (i == 1) {
                String lvl1c = "╔═════════════════════╗" + "\n" +
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
                levelsC[i] = lvl1c;
            }
            if (i == 2) {
                String lvl2c = "╔═════════════════════╗\n" +
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
                levelsC[i] = lvl2c;
            }
        }
    }

}
