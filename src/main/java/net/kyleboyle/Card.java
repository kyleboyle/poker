package net.kyleboyle;

import java.util.Objects;

public class Card implements Comparable<Card> {

  public final CardType type;
  public final Suit suit;

  public Card(CardType type, Suit suit) {
    this.type = type;
    this.suit = suit;
  }

  /**
   * A card's value / rank is not influenced by its suit. This will sort the natural order in descending
   * value.
   *
   * @param other the other card to compare to this card
   * @return the inverse of the typical natural ordering:  a positive integer, zero, or a negative
   * integer as this object is less than, equal to, or greater than the specified card.
   */
  @Override
  public int compareTo(Card other) {
    return -1 * this.type.compareTo(other.type);
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
    return type +"-" + suit;
  }
}
