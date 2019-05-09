package net.kyleboyle;

import java.util.List;

/** Store the 5 cards and valuation for the cards */
public class Hand {

  List<Card> cards;

  /** The highest ranked category for this hand */
  Category category;

  /** the list of cards that satisfy the category */
  List<Card> categoryCards;

  /** the list of cards that are not in the category, used for breaking ties*/
  List<Card> nonCategoryCards;


}
