package net.kyleboyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Store the 5 cards and valuation for the cards
 */
public class Hand {

  public final List<Card> cards;

  /**
   * The highest ranked category for this hand
   */
  public Category category;

  /**
   * the list of cards that satisfy the category
   */
  public List<Card> categoryCards;

  /**
   * the list of cards that are not in the category, used for breaking ties
   */
  public List<Card> nonCategoryCards;


  public Hand(List<Card> cards) {
    this.cards = new ArrayList<>(cards);
    // sort in desc order
    this.cards.sort(null);
  }

  /**
   * figures out which category the hand is valued at and which cards belong to it, as well as which
   * cards do not belong to it.
   *
   *
   */
  public void calculateCategory() {

  }

  boolean checkStraightFlush() {
    if (checkFlush() && checkStraight()) {
      this.categoryCards = new ArrayList<>(this.cards);
      this.categoryCards.sort(null);
      this.category = Category.STRAIGHT_FLUSH;
      return true;
    }
    this.category = null;
    this.categoryCards = null;
    return false;
  }

  /**
   * do all cards in the hand form a sequence
   */
  boolean checkStraight() {
    for(int i = 0; i < cards.size() - 1; i++) {
      if (cards.get(i).compareTo(cards.get(i+1)) != -1) {
        return false;
      }
    }
    this.categoryCards = new ArrayList<>(this.cards);
    this.category = Category.STRAIGHT;
    return true;
  }

  /**
   * are all cards in the hand of the same suit
   */
  boolean checkFlush() {
    Suit suit = null;
    for(Card card : this.cards) {
      if (suit == null) {
        suit = card.suit;
      }
      if (!suit.equals(card.suit)) {
        return false;
      }
    }
    this.categoryCards = new ArrayList<>(this.cards);
    this.category = Category.FLUSH;
    return true;
  }
}
