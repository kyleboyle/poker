package net.kyleboyle;

import java.util.Comparator;
import java.util.List;

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
