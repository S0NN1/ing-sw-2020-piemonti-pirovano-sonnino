package it.polimi.ingsw.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Printable class stores all grid assets for the CLI.
 *
 * @author Nicolò Sonnino
 */
  public class Printable {
    /**
     * Constructor Printable creates a new Printable instance.
     */
    private Printable(){}
  private static final String GREEN = Constants.ANSI_GREEN;
  private static final String PURPLE = Constants.ANSI_PURPLE;
  public static final String LINE_BLOCK =
          "█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████";
  private static final String TWELVE_LINE_BLOCK = "████████████";
  private static final String ELEVEN_LINE_BLOCK = "███████████";
  private static final String LVL_1_LINE_BLOCK = "║█████████████████████║";
  private static final String UPPER_LVL_1 = "╔═════════════════════╗";
  private static final String BOTTOM_LVL_1 = "╚═════════════════════╝";
  private static final String TEN_LINE_BLOCK = "██████████";
  private static final String UPPER_INSIDE_LVL_2 = "┌───────────────────┐";
  private static final String LOWER_INSIDE_LVL_2 = "└───────────────────┘";
  private static final String THREE_LINE_BLOCK = "███";
  private static final String NINE_LINE_BLOCK = "█████████";
  private static final String VERTICAL_DOUBLE_LINE = "║";
  private static final String VERTICAL_DOUBLE_LINE_RIGHT = "║\n";
  private static final String FIVE_LINE_BLOCK = "█████";
  private static final String SEVEN_LINE_BLOCK = "███████";
  private static final String NINETEEN_LINE_BLOCK = "███████████████████";
  private static final String CYAN = Constants.ANSI_CYAN;
  public static final String SINGLE_LINE_BLOCK = "█";
  private static final String SIX_LINE_BLOCK = "██████";
  private static final String EIGHT_LINE_BLOCK = "████████";
  private static final String VERTICAL_LINE = "|";
  private static final String PLUS = "+";
  protected static final HashMap<String, String> GOD_MAP_SIDE_MENU = new HashMap<>();
  private static final String RESET = Constants.ANSI_RESET;
  private static final String BLUE = Constants.ANSI_BLUE;
  public static final String ROW_WAVE =
      BLUE
          + "≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈"
          + RESET;
  public static final String COUPLE_ROW_WAVE = BLUE + "≈≈" + RESET;
  private static final String YELLOW = Constants.ANSI_YELLOW;
  public static final String SIDE_MENU =
      PLUS
          + "-"
          + PLUS
          + "\n"
          + VERTICAL_LINE
          + YELLOW
          + "-"
          + "STATUS"
          + "-"
          + RESET
          + VERTICAL_LINE
          + "\n"
          + PLUS
          + "-"
          + PLUS
          + "\n"
          + VERTICAL_LINE
          + YELLOW
          + "-"
          + "PLAYER"
          + "-"
          + RESET
          + VERTICAL_LINE
          + "\n"
          + VERTICAL_LINE
          + "-"
          + VERTICAL_LINE
          + "\n"
          + PLUS
          + "-"
          + PLUS
          + "\n"
          + VERTICAL_LINE
          + YELLOW
          + "-"
          + "GOD"
          + "-"
          + RESET
          + VERTICAL_LINE
          + "\n"
          + VERTICAL_LINE
          + "-"
          + VERTICAL_LINE
          + "\n"
          + PLUS
          + "-"
          + PLUS
          + "\n"
          + VERTICAL_LINE
          + YELLOW
          + "-"
          + "COLOR"
          + "-"
          + RESET
          + VERTICAL_LINE
          + "\n"
          + VERTICAL_LINE
          + "-"
          + VERTICAL_LINE
          + "\n"
          + PLUS
          + "-"
          + PLUS
          + "\n";
  private static final String[] LEVELS =
      new String[] {
        GREEN
            + TWELVE_LINE_BLOCK
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + GREEN
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n",
        UPPER_LVL_1
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + BOTTOM_LVL_1,
        UPPER_LVL_1
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + UPPER_INSIDE_LVL_2
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINETEEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINETEEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINETEEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINETEEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINETEEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINETEEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINETEEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + LOWER_INSIDE_LVL_2
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + BOTTOM_LVL_1,
        UPPER_LVL_1
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + UPPER_INSIDE_LVL_2
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + FIVE_LINE_BLOCK
            + CYAN
            + NINE_LINE_BLOCK
            + RESET
            + FIVE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + THREE_LINE_BLOCK
            + CYAN
            + SEVEN_LINE_BLOCK
            + SIX_LINE_BLOCK
            + RESET
            + THREE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + SINGLE_LINE_BLOCK
            + CYAN
            + NINE_LINE_BLOCK
            + CYAN
            + EIGHT_LINE_BLOCK
            + RESET
            + SINGLE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + CYAN
            + TEN_LINE_BLOCK
            + CYAN
            + NINE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + SINGLE_LINE_BLOCK
            + CYAN
            + NINE_LINE_BLOCK
            + CYAN
            + EIGHT_LINE_BLOCK
            + RESET
            + SINGLE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + THREE_LINE_BLOCK
            + CYAN
            + SEVEN_LINE_BLOCK
            + SIX_LINE_BLOCK
            + RESET
            + THREE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + FIVE_LINE_BLOCK
            + CYAN
            + NINE_LINE_BLOCK
            + RESET
            + FIVE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + LOWER_INSIDE_LVL_2
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + BOTTOM_LVL_1,
        UPPER_LVL_1
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + UPPER_INSIDE_LVL_2
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + FIVE_LINE_BLOCK
            + CYAN
            + NINE_LINE_BLOCK
            + RESET
            + FIVE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + THREE_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + BLUE
            + THREE_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + THREE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + SINGLE_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + BLUE
            + SEVEN_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + SINGLE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + BLUE
            + NINE_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + SINGLE_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + BLUE
            + SEVEN_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + SINGLE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + THREE_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + BLUE
            + THREE_LINE_BLOCK
            + CYAN
            + FIVE_LINE_BLOCK
            + RESET
            + THREE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + FIVE_LINE_BLOCK
            + CYAN
            + NINE_LINE_BLOCK
            + RESET
            + FIVE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + LOWER_INSIDE_LVL_2
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + BOTTOM_LVL_1
      };
  private static final String[] LEVELS_WITH_DOME =
      new String[] {
        GREEN
            + TWELVE_LINE_BLOCK
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TEN_LINE_BLOCK
            + BLUE
            + THREE_LINE_BLOCK
            + GREEN
            + TEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + EIGHT_LINE_BLOCK
            + BLUE
            + SEVEN_LINE_BLOCK
            + GREEN
            + EIGHT_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + SEVEN_LINE_BLOCK
            + BLUE
            + NINE_LINE_BLOCK
            + GREEN
            + SEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + EIGHT_LINE_BLOCK
            + BLUE
            + SEVEN_LINE_BLOCK
            + GREEN
            + EIGHT_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TEN_LINE_BLOCK
            + BLUE
            + THREE_LINE_BLOCK
            + GREEN
            + TEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n"
            + GREEN
            + TWELVE_LINE_BLOCK
            + ELEVEN_LINE_BLOCK
            + RESET
            + "\n",
        UPPER_LVL_1
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + NINE_LINE_BLOCK
            + BLUE
            + THREE_LINE_BLOCK
            + RESET
            + NINE_LINE_BLOCK
            + VERTICAL_DOUBLE_LINE
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + SEVEN_LINE_BLOCK
            + BLUE
            + SEVEN_LINE_BLOCK
            + RESET
            + SEVEN_LINE_BLOCK
            + VERTICAL_DOUBLE_LINE
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + SIX_LINE_BLOCK
            + BLUE
            + NINE_LINE_BLOCK
            + RESET
            + SIX_LINE_BLOCK
            + VERTICAL_DOUBLE_LINE
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + SEVEN_LINE_BLOCK
            + BLUE
            + SEVEN_LINE_BLOCK
            + RESET
            + SEVEN_LINE_BLOCK
            + VERTICAL_DOUBLE_LINE
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + NINE_LINE_BLOCK
            + BLUE
            + THREE_LINE_BLOCK
            + RESET
            + NINE_LINE_BLOCK
            + VERTICAL_DOUBLE_LINE
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + LVL_1_LINE_BLOCK
            + "\n"
            + BOTTOM_LVL_1,
        UPPER_LVL_1
            + "\n"
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + UPPER_INSIDE_LVL_2
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINE_LINE_BLOCK
            + TEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + EIGHT_LINE_BLOCK
            + BLUE
            + THREE_LINE_BLOCK
            + RESET
            + EIGHT_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + SIX_LINE_BLOCK
            + BLUE
            + SEVEN_LINE_BLOCK
            + RESET
            + SIX_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + FIVE_LINE_BLOCK
            + BLUE
            + NINE_LINE_BLOCK
            + RESET
            + FIVE_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + SIX_LINE_BLOCK
            + BLUE
            + SEVEN_LINE_BLOCK
            + RESET
            + SIX_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + EIGHT_LINE_BLOCK
            + BLUE
            + THREE_LINE_BLOCK
            + RESET
            + EIGHT_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + NINE_LINE_BLOCK
            + TEN_LINE_BLOCK
            + PURPLE
            + VERTICAL_LINE
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + VERTICAL_DOUBLE_LINE
            + PURPLE
            + LOWER_INSIDE_LVL_2
            + RESET
            + VERTICAL_DOUBLE_LINE_RIGHT
            + BOTTOM_LVL_1
      };

  static {
    GOD_MAP_SIDE_MENU.put(
        "ARES",
        YELLOW
            + "REMOVELEVEL (no args)/REMOVELEVEL <row> <col>:"
            + RESET
            + " print spaces/remove a block.");
    GOD_MAP_SIDE_MENU.put(
        "ATLAS",
        YELLOW
            + "PLACEDOME (no args)/PLACEDOME <row> <col>:"
            + RESET
            + " print spaces/build dome.");
    GOD_MAP_SIDE_MENU.put(
        "CHARON",
        YELLOW
            + "FORCEWORKER (no args)/FORCEWORKER <row> <col>:"
            + RESET
            + " print spaces/move enemy worker into specular cell.");
  }

  /**
   * Method getLEVELS returns the LEVELS of this Printable object.
   *
   * @return the LEVELS (type String[]) of this Printable object.
   */
  public static String[] getLEVELS() {
    return LEVELS;
  }

  /**
   * Method getLEVELSWITHDOME returns the LEVELSWITHDOME of this Printable object.
   *
   * @return the LEVELSWITHDOME (type String[]) of this Printable object.
   */
  public static String[] getLEVELSWITHDOME() {
    return LEVELS_WITH_DOME;
  }

  /**
   * Method getGodMapSideMenu returns the godMapSideMenu of this Printable object.
   *
   * @return the godMapSideMenu (type HashMap<String, String>) of this Printable object.
   */
  public static Map<String, String> getGodMapSideMenu() {
    return GOD_MAP_SIDE_MENU;
  }
}
