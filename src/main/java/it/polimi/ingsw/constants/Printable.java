package it.polimi.ingsw.constants;

public class Printable {
    public static final String GREEN = Constants.ANSI_GREEN;
    private static final String RESET = Constants.ANSI_RESET;
    private static final String BLUE = Constants.ANSI_BLUE;
    private static final String YELLOW = Constants.ANSI_YELLOW;
    static String purple = Constants.ANSI_PURPLE;
    public static final String LINE_BLOCK = "█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████";
    static String twelveLineBlock = "████████████";
    static String elevenLineBlock = "███████████";
    static String lvl1LineBlock = "║█████████████████████║";
    static String upperLvl1 = "╔═════════════════════╗";
    static String bottomLvl1 = "╚═════════════════════╝";
    static String tenLineBlock = "██████████";
    static String upperInsideLvl2 = "┌───────────────────┐";
    static String lowerInsideLvl2 = "└───────────────────┘";
    static String threeLineBlock = "███";
    static String nineLineBlock = "█████████";
    static String verticalDoubleLine = "║";
    static String verticalDoubleLineRight = "║\n";
    static String fiveLineBlock = "█████";
    static String sevenLineBlock = "███████";
    static String nineteenLineBlock = "███████████████████";
    static String cyan = Constants.ANSI_CYAN;
    static String singleLineBlock = "█";
    static String sixLineBlock = "██████";
    static String eightLineBlock = "████████";
    public static final String VERTICAL_LINE = "|";
    public static final String PLUS = "+";
    public static final String ROW_WAVE = BLUE + "≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈" + RESET;
    public static final String COUPLE_ROW_WAVE = BLUE + "≈≈" + RESET;

    public static final String SIDE_MENU =
            PLUS + "-" + PLUS + "\n" +
                    VERTICAL_LINE + YELLOW + "-" + "STATUS" + "-" + RESET + VERTICAL_LINE + "\n" +
                    PLUS + "-" + PLUS + "\n" +
                    VERTICAL_LINE + YELLOW + "-" + "PLAYER" + "-" + RESET + VERTICAL_LINE + "\n" +
                    VERTICAL_LINE + "-" + VERTICAL_LINE + "\n" +
                    PLUS + "-" + PLUS + "\n" +
                    VERTICAL_LINE + YELLOW + "-" + "GOD" + "-" + RESET + VERTICAL_LINE + "\n" +
                    VERTICAL_LINE + "-" + VERTICAL_LINE + "\n" +
                    PLUS + "-" + PLUS + "\n" +
                    VERTICAL_LINE + YELLOW + "-" + "COLOR" + "-" + RESET + VERTICAL_LINE + "\n" +
                    VERTICAL_LINE + "-" + VERTICAL_LINE + "\n" +
                    PLUS + "-" + PLUS + "\n";


    private static final String[] LEVELS = new String[]{
            GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + GREEN + elevenLineBlock + RESET + "\n",
            upperLvl1 + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    bottomLvl1,
            upperLvl1 + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    bottomLvl1,
            upperLvl1 + "\n" +
                    verticalDoubleLine + purple + upperInsideLvl2 + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + fiveLineBlock + cyan + nineLineBlock + RESET + fiveLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + threeLineBlock + cyan + sevenLineBlock + sixLineBlock + RESET + threeLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + singleLineBlock + cyan + nineLineBlock + cyan + eightLineBlock + RESET + singleLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + cyan + tenLineBlock + cyan + nineLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + singleLineBlock + cyan + nineLineBlock + cyan + eightLineBlock + RESET + singleLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + threeLineBlock + cyan + sevenLineBlock + sixLineBlock + RESET + threeLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + fiveLineBlock + cyan + nineLineBlock + RESET + fiveLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + lowerInsideLvl2 + RESET + verticalDoubleLineRight +
                    bottomLvl1,
            upperLvl1 + "\n" +
                    verticalDoubleLine + purple + upperInsideLvl2 + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + fiveLineBlock + cyan + nineLineBlock + RESET + fiveLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + threeLineBlock + cyan + fiveLineBlock + RESET + BLUE + threeLineBlock + cyan + fiveLineBlock + RESET + threeLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + singleLineBlock + cyan + fiveLineBlock + RESET + BLUE + sevenLineBlock + cyan + fiveLineBlock + RESET + singleLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + cyan + fiveLineBlock + RESET + BLUE + nineLineBlock + cyan + fiveLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + singleLineBlock + cyan + fiveLineBlock + RESET + BLUE + sevenLineBlock + cyan + fiveLineBlock + RESET + singleLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + threeLineBlock + cyan + fiveLineBlock + RESET + BLUE + threeLineBlock + cyan + fiveLineBlock + RESET + threeLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + fiveLineBlock + cyan + nineLineBlock + RESET + fiveLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + lowerInsideLvl2 + RESET + verticalDoubleLineRight +
                    bottomLvl1
    };
    private static final String[] LEVELS_WITH_DOME = new String[]{
            GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                    GREEN + tenLineBlock + BLUE + threeLineBlock + GREEN + tenLineBlock + RESET + "\n" +
                    GREEN + eightLineBlock + BLUE + sevenLineBlock + GREEN + eightLineBlock + RESET + "\n" +
                    GREEN + sevenLineBlock + BLUE + nineLineBlock + GREEN + sevenLineBlock + RESET + "\n" +
                    GREEN + eightLineBlock + BLUE + sevenLineBlock + GREEN + eightLineBlock + RESET + "\n" +
                    GREEN + tenLineBlock + BLUE + threeLineBlock + GREEN + tenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n" +
                    GREEN + twelveLineBlock + elevenLineBlock + RESET + "\n",
            upperLvl1 + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    verticalDoubleLine + nineLineBlock + BLUE + threeLineBlock + RESET + nineLineBlock + verticalDoubleLine + "\n" +
                    verticalDoubleLine + sevenLineBlock + BLUE + sevenLineBlock + RESET + sevenLineBlock + verticalDoubleLine + "\n" +
                    verticalDoubleLine + sixLineBlock + BLUE + nineLineBlock + RESET + sixLineBlock + verticalDoubleLine + "\n" +
                    verticalDoubleLine + sevenLineBlock + BLUE + sevenLineBlock + RESET + sevenLineBlock + verticalDoubleLine + "\n" +
                    verticalDoubleLine + nineLineBlock + BLUE + threeLineBlock + RESET + nineLineBlock + verticalDoubleLine + "\n" +
                    lvl1LineBlock + "\n" +
                    lvl1LineBlock + "\n" +
                    bottomLvl1,
            upperLvl1 + "\n" +
                    verticalDoubleLine + purple + upperInsideLvl2 + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + nineLineBlock + tenLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + eightLineBlock + BLUE + threeLineBlock + RESET + eightLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + sixLineBlock + BLUE + sevenLineBlock + RESET + sixLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + fiveLineBlock + BLUE + nineLineBlock + RESET + fiveLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + sixLineBlock + BLUE + sevenLineBlock + RESET + sixLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + eightLineBlock + BLUE + threeLineBlock + RESET + eightLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + VERTICAL_LINE + RESET + nineLineBlock + tenLineBlock + purple + VERTICAL_LINE + RESET + verticalDoubleLineRight +
                    verticalDoubleLine + purple + lowerInsideLvl2 + RESET + verticalDoubleLineRight +
                    bottomLvl1


    };

    public static String[] getLEVELS() {
        return LEVELS;
    }

    public static String[] getLEVELSWITHDOME() {
        return LEVELS_WITH_DOME;
    }


}
