package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.constants.Constants;

public class Printable {
    public final String green = Constants.ANSI_GREEN;
    private final String rst = Constants.ANSI_RESET;
    private final String blue = Constants.ANSI_BLUE;
    public String lineBlock = "█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████";
    public String rowWave = blue + "≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈" + rst;
    public String coupleRowWave = blue + "≈≈" + rst;
    public String[] levels;
    public String[] levelsC;

    public Printable() {
        levels = new String[5];
        levelsC = new String[3];
        String purple = Constants.ANSI_PURPLE;
        String simpleLineBlock = "███████████████████████";
        String lvl1LineBlock = "║█████████████████████║";
        String upperLvl1 = "╔═════════════════════╗";
        String bottomLvl1 = "╚═════════════════════╝";
        String insideLineBlock = "███████████████████";
        String upperInsideLvl2 = "┌───────────────────┐";
        String lowerInsideLvl2 = "└───────────────────┘";
        String threeLineBlock = "███";
        String nineLineBlock = "█████████";
        String verticalDoubleLine = "║";
        String verticalDoubleLineRight = "║\n";
        String verticalLine = "│";
        String fiveLineBlock = "█████";
        String sevenLineBlock = "███████";
        for (int i = 0; i <= 4; i++) {
            if (i == 0) {
                String lvl0 = green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n";
                levels[i] = lvl0;
            }
            if (i == 1) {
                String lvl1 = upperLvl1 + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        bottomLvl1;
                levels[i] = lvl1;
            }
            if (i == 2) {
                String lvl2 = upperLvl1 + "\n" +
                        verticalDoubleLine + purple + upperInsideLvl2 + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + lowerInsideLvl2 + rst + verticalDoubleLineRight +
                        bottomLvl1;
                levels[i] = lvl2;
            }
            String cyan = Constants.ANSI_CYAN;
            String singleLineBlock = "█";
            if (i == 3) {
                String thirteenLineBlock = "█████████████";
                String sixteenLineBlock = "████████████████";
                String seventeenLineBlock = "█████████████████";
                String lvl3 = upperLvl1 + "\n" +
                        verticalDoubleLine + purple + upperInsideLvl2 + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + fiveLineBlock + cyan + nineLineBlock + rst + fiveLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + threeLineBlock + cyan + thirteenLineBlock + rst + threeLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + singleLineBlock + cyan + sixteenLineBlock + rst + singleLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + cyan + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + singleLineBlock + cyan + seventeenLineBlock + rst + singleLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + threeLineBlock + cyan + thirteenLineBlock + rst + threeLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + fiveLineBlock + cyan + nineLineBlock + rst + fiveLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + lowerInsideLvl2 + rst + verticalDoubleLineRight +
                        bottomLvl1;
                levels[i] = lvl3;
            } else if (i == 4) {
                String lvl4 = upperLvl1 + "\n" +
                        verticalDoubleLine + purple + upperInsideLvl2 + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + fiveLineBlock + cyan + nineLineBlock + rst + fiveLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + threeLineBlock + cyan + fiveLineBlock + rst + blue + threeLineBlock + cyan + fiveLineBlock + rst + threeLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + singleLineBlock + cyan + fiveLineBlock + rst + blue + sevenLineBlock + cyan + fiveLineBlock + rst + singleLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + cyan + fiveLineBlock + rst + blue + nineLineBlock + cyan + fiveLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + singleLineBlock + cyan + fiveLineBlock + rst + blue + sevenLineBlock + cyan + fiveLineBlock + rst + singleLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + threeLineBlock + cyan + fiveLineBlock + rst + blue + threeLineBlock + cyan + fiveLineBlock + rst + threeLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + fiveLineBlock + cyan + nineLineBlock + rst + fiveLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + lowerInsideLvl2 + rst + verticalDoubleLineRight +
                        bottomLvl1;
                levels[i] = lvl4;
            }
        }
        for (int i = 0; i <= 2; i++) {
            String eightLineBlock = "████████";
            if (i == 0) {
                String tenLineBlock = "██████████";
                String lvl0c = green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + tenLineBlock + blue + threeLineBlock + green + tenLineBlock + rst + "\n" +
                        green + eightLineBlock + blue + sevenLineBlock + green + eightLineBlock + rst + "\n" +
                        green + sevenLineBlock + blue + nineLineBlock + green + sevenLineBlock + rst + "\n" +
                        green + eightLineBlock + blue + sevenLineBlock + green + eightLineBlock + rst + "\n" +
                        green + tenLineBlock + blue + threeLineBlock + green + tenLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n" +
                        green + simpleLineBlock + rst + "\n";
                levelsC[i] = lvl0c;
            }
            String sixLineBlock = "██████";
            if (i == 1) {
                String lvl1c = upperLvl1 + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        verticalDoubleLine + nineLineBlock + blue + threeLineBlock + rst + nineLineBlock + verticalDoubleLine + "\n" +
                        verticalDoubleLine + sevenLineBlock + blue + sevenLineBlock + rst + sevenLineBlock + verticalDoubleLine + "\n" +
                        verticalDoubleLine + sixLineBlock + blue + nineLineBlock + rst + sixLineBlock + verticalDoubleLine + "\n" +
                        verticalDoubleLine + sevenLineBlock + blue + sevenLineBlock + rst + sevenLineBlock + verticalDoubleLine + "\n" +
                        verticalDoubleLine + nineLineBlock + blue + threeLineBlock + rst + nineLineBlock + verticalDoubleLine + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        bottomLvl1;
                levelsC[i] = lvl1c;
            }
            if (i == 2) {
                String lvl2c = upperLvl1 + "\n" +
                        verticalDoubleLine + purple + upperInsideLvl2 + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + eightLineBlock + blue + threeLineBlock + rst + eightLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + sixLineBlock + blue + sevenLineBlock + rst + sixLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + fiveLineBlock + blue + nineLineBlock + rst + fiveLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + sixLineBlock + blue + sevenLineBlock + rst + sixLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + eightLineBlock + blue + threeLineBlock + rst + eightLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + rst + insideLineBlock + purple + verticalLine + rst + verticalDoubleLineRight +
                        verticalDoubleLine + purple + lowerInsideLvl2 + rst + verticalDoubleLineRight +
                        bottomLvl1;
                levelsC[i] = lvl2c;
            }
        }
    }

}
