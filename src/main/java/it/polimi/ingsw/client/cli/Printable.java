package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.constants.Constants;

public class Printable {
    public static final String GREEN = Constants.ANSI_GREEN;
    private static final String RESET = Constants.ANSI_RESET;
    private static final String BLUE = Constants.ANSI_BLUE;
    public static final String LINE_BLOCK = "█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████";
    public static final String ROW_WAVE = BLUE + "≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈" + RESET;
    public static final String COUPLE_ROW_WAVE = BLUE + "≈≈" + RESET;
    public String[] levels;
    public String[] levelsC;

    public Printable() {
        levels = new String[5];
        levelsC = new String[3];
        String purple = Constants.ANSI_PURPLE;
        String twelveLineBlock ="████████████";
        String elevenLineBlock = "███████████";
        String lvl1LineBlock = "║█████████████████████║";
        String upperLvl1 = "╔═════════════════════╗";
        String bottomLvl1 = "╚═════════════════════╝";
        String tenLineBlock = "██████████";
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
                String lvl0 = GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n";
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
                        verticalDoubleLine + purple + upperInsideLvl2 + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + lowerInsideLvl2 + RESET + verticalDoubleLineRight +
                        bottomLvl1;
                levels[i] = lvl2;
            }
            String cyan = Constants.ANSI_CYAN;
            String singleLineBlock = "█";
            if (i == 3) {
                String sixLineBlock = "██████";
                String seventeenLineBlock = "█████████████████";
                String eightLineBlock = "████████";
                String lvl3 = upperLvl1 + "\n" +
                        verticalDoubleLine + purple + upperInsideLvl2 + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + fiveLineBlock + cyan + nineLineBlock + RESET + fiveLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + threeLineBlock + cyan + sevenLineBlock + sixLineBlock + RESET + threeLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + singleLineBlock + cyan + nineLineBlock + cyan + eightLineBlock + RESET + singleLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + cyan + nineLineBlock + cyan + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + singleLineBlock + cyan + seventeenLineBlock + RESET + singleLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + threeLineBlock + cyan + sevenLineBlock + sixLineBlock + RESET + threeLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + fiveLineBlock + cyan + nineLineBlock + RESET + fiveLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + lowerInsideLvl2 + RESET + verticalDoubleLineRight +
                        bottomLvl1;
                levels[i] = lvl3;
            } else if (i == 4) {
                String lvl4 = upperLvl1 + "\n" +
                        verticalDoubleLine + purple + upperInsideLvl2 + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + fiveLineBlock + cyan + nineLineBlock + RESET + fiveLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + threeLineBlock + cyan + fiveLineBlock + RESET + BLUE + threeLineBlock + cyan + fiveLineBlock + RESET + threeLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + singleLineBlock + cyan + fiveLineBlock + RESET + BLUE + sevenLineBlock + cyan + fiveLineBlock + RESET + singleLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + cyan + fiveLineBlock + RESET + BLUE + nineLineBlock + cyan + fiveLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + singleLineBlock + cyan + fiveLineBlock + RESET + BLUE + sevenLineBlock + cyan + fiveLineBlock + RESET + singleLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + threeLineBlock + cyan + fiveLineBlock + RESET + BLUE + threeLineBlock + cyan + fiveLineBlock + RESET + threeLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + fiveLineBlock + cyan + nineLineBlock + RESET + fiveLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + lowerInsideLvl2 + RESET + verticalDoubleLineRight +
                        bottomLvl1;
                levels[i] = lvl4;
            }
        }
        for (int i = 0; i <= 2; i++) {
            String eightLineBlock = "████████";
            if (i == 0) {
                String lvl0c = GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                        GREEN + tenLineBlock + BLUE + threeLineBlock + GREEN + tenLineBlock + RESET + "\n" +
                        GREEN + eightLineBlock + BLUE + sevenLineBlock + GREEN + eightLineBlock + RESET + "\n" +
                        GREEN + sevenLineBlock + BLUE + nineLineBlock + GREEN + sevenLineBlock + RESET + "\n" +
                        GREEN + eightLineBlock + BLUE + sevenLineBlock + GREEN + eightLineBlock + RESET + "\n" +
                        GREEN + tenLineBlock + BLUE + threeLineBlock + GREEN + tenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                        GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n";
                levelsC[i] = lvl0c;
            }
            String sixLineBlock = "██████";
            if (i == 1) {
                String lvl1c = upperLvl1 + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        verticalDoubleLine + nineLineBlock + BLUE + threeLineBlock + RESET + nineLineBlock + verticalDoubleLine + "\n" +
                        verticalDoubleLine + sevenLineBlock + BLUE + sevenLineBlock + RESET + sevenLineBlock + verticalDoubleLine + "\n" +
                        verticalDoubleLine + sixLineBlock + BLUE + nineLineBlock + RESET + sixLineBlock + verticalDoubleLine + "\n" +
                        verticalDoubleLine + sevenLineBlock + BLUE + sevenLineBlock + RESET + sevenLineBlock + verticalDoubleLine + "\n" +
                        verticalDoubleLine + nineLineBlock + BLUE + threeLineBlock + RESET + nineLineBlock + verticalDoubleLine + "\n" +
                        lvl1LineBlock + "\n" +
                        lvl1LineBlock + "\n" +
                        bottomLvl1;
                levelsC[i] = lvl1c;
            }
            if (i == 2) {
                String lvl2c = upperLvl1 + "\n" +
                        verticalDoubleLine + purple + upperInsideLvl2 + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + eightLineBlock + BLUE + threeLineBlock + RESET + eightLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + sixLineBlock + BLUE + sevenLineBlock + RESET + sixLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + fiveLineBlock + BLUE + nineLineBlock + RESET + fiveLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + sixLineBlock + BLUE + sevenLineBlock + RESET + sixLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + eightLineBlock + BLUE + threeLineBlock + RESET + eightLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + verticalLine + RESET + nineLineBlock + tenLineBlock + purple + verticalLine + RESET + verticalDoubleLineRight +
                        verticalDoubleLine + purple + lowerInsideLvl2 + RESET + verticalDoubleLineRight +
                        bottomLvl1;
                levelsC[i] = lvl2c;
            }
        }
    }

}
