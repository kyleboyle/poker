package net.kyleboyle;

import java.util.Objects;

/**
 * Encapsulates a cards value and suit and provide value comparision to other cards.
 */
public class Card implements Comparable<Card> {

  public final CardType type;
  public final Suit suit;

  public Card(CardType type, Suit suit) {
    this.type = type;
    this.suit = suit;
  }

  /**
   * A card's value / rank is not influenced by its suit. Compares the natural order of the CardType
   * enum.
   *
   * @param other the other card to compare to this card
   * @return the typical natural ordering: a negative integer, zero, or a positive integer as this
   * object is less than, equal to, or greater than the specified card.
   */
  @Override
  public int compareTo(Card other) {
    return this.type.compareTo(other.type);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return type == card.type &&
        suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, suit);
  }

  @Override
  public String toString() {
    return type + "-" + suit;
  }
}
