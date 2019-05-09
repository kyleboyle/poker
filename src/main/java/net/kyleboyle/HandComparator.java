package net.kyleboyle;

import java.util.Comparator;
import java.util.List;

/**
 * Helper for comparing hands to each another. Can be broken into 3 stages:
 * <p>
 * - Check for difference in category (eg full house vs a pair). if there is a difference then the
 * hands can be ordered based on this.
 * <p>
 * - If category is the same, check for the value of the cards within that category. eg if both
 * hands have a pair, which pair is of higher card value. If there is a difference in the card
 * value, then the hands can be ordered base on the card value.
 * <p>
 * - If the cards within the same category are also equivalent, compare the value of the remaining
 * cards not used by the category (high cards / kicker) in each hand.
 */
public class HandComparator implements Comparator<Hand> {

  @Override
  public int compare(Hand h1, Hand h2) {

    int categoryCmp = h1.category.compareTo(h2.category);
    if (categoryCmp != 0) {
      return categoryCmp;
    }
    // categories are the same (same type of hand). Which hand has the better cards within
    // that category (eg the highest valued full house)
    int cardCmp = this.compareCardLists(h1.categoryCards, h2.categoryCards);
    if (cardCmp != 0) {
      return cardCmp;
    }

    // the hand category cards are the same, the only thing left to check are the highcards.

    if (h1.nonCategoryCards == null && h2.nonCategoryCards == null) {
      return 0; // no high cards to check
    }
    if (h1.nonCategoryCards == null || h2.nonCategoryCards == null) {
      throw new RuntimeException("attempt to compare high card one high card list not set");
    }

    return compareCardLists(h1.nonCategoryCards, h2.nonCategoryCards);
  }

  /**
   * Find comparator result of a list of cards by comparing each card in order and comparing the
   * next items if the current are equal. Will only compare items in the list until it reaches the
   * end of either list. different list lengths do not result in inequality.
   */
  private int compareCardLists(List<Card> cards1, List<Card> cards2) {
    for (int i = 0; i < Math.min(cards1.size(), cards2.size()); i++) {
      int cardCompare = cards1.get(i).compareTo(cards2.get(i));
      if (cardCompare != 0) {
        return cardCompare;
      }
    }
    return 0; // equal
  }
}
