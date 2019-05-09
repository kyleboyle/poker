package net.kyleboyle;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println(
          "require 2 csv strings of poker hands as input, eg 4D,6S,9H,QH,QC 2H,2D,4C,4D,4S");
    }

    /* marshal input into internal data model */
    Hand hand1 = getHand(args[0]);
    Hand hand2 = getHand(args[0]);

    System.out.println("todo");
  }

  /**
   * Convert a CSV string of card identifiers (type, suit) into the internal card data model.
   *
   * @return A Hand containing the cards identified by the csv list.
   */
  private static Hand getHand(String csvCardList) {
    return null;
  }
}
