package net.kyleboyle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entry point & outputs result of hand comparison
 */
public class App {

  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println(
          "require 2 csv strings of poker hands as input, eg 4D,6S,9H,QH,QC 2H,2D,4C,4D,4S");
      System.exit(-1);
    }

    /* marshal input into internal data model */
    Hand hand1 = getHand(args[0]);
    hand1.calculateCategory();
    Hand hand2 = getHand(args[1]);
    hand2.calculateCategory();

    HandComparator comp = new HandComparator();
    int cmp = comp.compare(hand1, hand2);

    System.out.println();
    System.out.println(args[0] + " vs " + args[1] + "");
    System.out.println(hand1.category + " vs " + hand2.category);

    if (cmp == 0) {
      System.out.println("tie!");
    } else if (cmp > 1) {
      System.out.println("first wins - " + hand1.category + " - " + args[0]);
    } else {
      System.out.println("second wins - " + hand2.category + " - " + args[1]);
    }
    System.out.println();
  }

  /**
   * Convert a CSV string of card identifiers (type, suit) into the internal card data model.
   *
   * @return A Hand containing the cards identified by the csv list.
   */
  public static Hand getHand(String csvCardList) {
    String[] cardStrings = csvCardList.split(",");
    List<Card> cards = Arrays.stream(cardStrings).map(s -> {
      if (s.length() != 2) {
        throw new RuntimeException("invalid card string " + s);
      }
      CardType type = CardType.of(s.charAt(0));
      Suit suit = Suit.of(s.charAt(1));
      if (type == null || suit == null) {
        throw new RuntimeException("invalid type or suit " + s);
      }
      return new Card(type, suit);
    }).collect(Collectors.toList());
    if (cards.size() != 5) {
      throw new RuntimeException("require 5 cards per hand: " + csvCardList);
    }
    // TODO could perform more validation here - eg no duplicates

    return new Hand(cards);
  }
}
