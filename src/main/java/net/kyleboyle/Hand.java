package net.kyleboyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Store the 5 cards and the highest value result (category) for the cards. Provides logic for
 * calculating which category this hand belongs to.
 */
public class Hand {

  public final List<Card> cards;

  /**
   * The highest ranked category for this hand
   */
  public Category category;

  /**
   * the list of cards that satisfy the category. useful for breaking ties (who has the best version
   * of the category)
   */
  public List<Card> categoryCards;

  /**
   * the list of cards that are not in the category, used for breaking category ties (who has high
   * card)
   */
  public List<Card> nonCategoryCards;


  public Hand(List<Card> cards) {
    this.cards = new ArrayList<>(cards);
    // sort in desc order
    this.cards.sort(null);
    Collections.reverse(this.cards);
  }

  /**
   * figures out which category the hand is valued at and which cards belong to it, as well as which
   * cards do not belong to it.
   */
  public void calculateCategory() {
    // evaluate high value first to avoid having check all
    boolean match = this.checkStraightFlush()
        || this.checkQuadruple()
        || this.checkFullHouse()
        || this.checkFlush()
        || this.checkStraight()
        || this.checkTriple()
        || this.checkTwoPair()
        || this.checkPair()
        || this.checkHighcard();

    this.computeNonCategoryCards();
    this.categoryCards.sort(null);
    Collections.reverse(categoryCards);
    if (this.nonCategoryCards != null) {
      this.nonCategoryCards.sort(null);
      Collections.reverse(categoryCards);
    }
  }

  /**
   * do all cards satisfy the straight and flush categories
   */
  boolean checkStraightFlush() {
    if (checkFlush() && checkStraight()) {
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

    // check for ace low straight first since it's not in straight order.
    if (cards.get(0).type.equals(CardType.ACE)
        && cards.get(1).type.equals(CardType.FIVE)
        && cards.get(2).type.equals(CardType.FOUR)
        && cards.get(3).type.equals(CardType.THREE)
        && cards.get(4).type.equals(CardType.TWO)
    ) {
      this.categoryCards = new ArrayList<>(this.cards);
      // replace the high ace with a low ace placeholder so card ordering / comparisons work
      this.categoryCards.set(0, new Card(CardType.ACE_LOW, this.categoryCards.get(0).suit));
      this.category = Category.STRAIGHT;
      return true;
    } else {
      for (int i = 0; i < cards.size() - 1; i++) {
        // make sure the next card in the descending sorted sequence is one value lower
        if (cards.get(i).compareTo(cards.get(i + 1)) != 1) {
          return false;
        }
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
    for (Card card : this.cards) {
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

  /**
   * is there a card type that appears 4 times (eg four Jacks)
   */
  boolean checkQuadruple() {
    for (int i = 0; i < 2; i++) {
      int count = 1; // one count for the card at i
      for (int j = i + 1; j < this.cards.size(); j++) {
        if (this.cards.get(i).type.equals(this.cards.get(j).type)) {
          count++;
        }
      }
      if (count == 4) {
        this.category = Category.QUADRUPLE;
        final CardType filterType = this.cards.get(i).type;
        this.categoryCards = this.cards.stream().filter(c -> c.type.equals(filterType))
            .collect(Collectors.toList());
        return true;
      }
    }
    return false;
  }

  /**
   * is there a card type that appears 3 times (eg three Jacks)
   */
  boolean checkTriple() {
    for (int i = 0; i < 3; i++) {
      int count = 1; // one count for the card at i
      for (int j = i + 1; j < this.cards.size(); j++) {
        if (this.cards.get(i).type.equals(this.cards.get(j).type)) {
          count++;
        }
      }
      if (count == 3) {
        this.category = Category.TRIPLE;
        final CardType filterType = this.cards.get(i).type;
        this.categoryCards = this.cards.stream().filter(c -> c.type.equals(filterType))
            .collect(Collectors.toList());
        return true;
      }
    }
    return false;
  }

  /**
   * do all cards give a 3 of a kind and a pair
   */
  boolean checkFullHouse() {
    if (this.checkTriple()) {
      this.computeNonCategoryCards(); // this should now contain a pair if we have a full house.
      List<Card> pair = findPair(this.nonCategoryCards);
      if (pair != null) {
        this.category = Category.FULL_HOUSE;
        return true;
      }
    }
    this.category = null;
    this.categoryCards = null;
    this.nonCategoryCards = null;
    return false;
  }

  /**
   * are there 2 different pairs
   */
  boolean checkTwoPair() {
    List<Card> firstPair = findPair(this.cards);
    if (firstPair != null) {
      ArrayList<Card> remainder = new ArrayList<>(this.cards);
      remainder.removeAll(firstPair);
      List<Card> secondPair = findPair(remainder);
      if (secondPair != null) {
        this.category = Category.TWO_PAIR;
        this.categoryCards = firstPair;
        this.categoryCards.addAll(secondPair);
        return true;
      }
    }
    return false;
  }

  /**
   * is there at least one pair
   */
  boolean checkPair() {
    List<Card> pair = findPair(this.cards);
    if (pair != null) {
      this.category = Category.PAIR;
      this.categoryCards = pair;
      return true;
    }
    return false;
  }

  /**
   * classifies the hand as high card
   */
  boolean checkHighcard() {
    this.category = Category.HIGH_CARD;
    this.categoryCards = this.cards;
    return true;
  }

  /**
   * find a pair of cards from the input list
   */
  private List<Card> findPair(List<Card> toCheck) {
    if (toCheck.size() < 2) {
      return null;
    }
    for (int i = 0; i < toCheck.size() - 1; i++) {
      int count = 1; // one count for the card at i
      for (int j = i + 1; j < toCheck.size(); j++) {
        if (toCheck.get(i).type.equals(toCheck.get(j).type)) {
          count++;
        }
      }
      if (count == 2) {
        final CardType filterType = toCheck.get(i).type;
        return toCheck.stream().filter(c -> c.type.equals(filterType))
            .collect(Collectors.toList());
      }
    }
    return null;
  }

  /**
   * set the nonCategoryCards member with the list of cards that aren't used for the main category.
   * Can be used for comparing high cards.
   */
  public void computeNonCategoryCards() {
    if (this.categoryCards != null && this.categoryCards.size() < 5) {
      this.nonCategoryCards = new ArrayList<>(this.cards);
      this.nonCategoryCards.removeAll(this.categoryCards);
    }
  }
}
