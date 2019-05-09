package net.kyleboyle;

public enum Suit {

  HEART,

  DIAMOND,

  SPADE,

  CLUB,

  NONE;

  public static Suit of(char value) {
    switch (value) {
      case 'H':
        return HEART;
      case 'D':
        return DIAMOND;
      case 'S':
        return SPADE;
      case 'C':
        return CLUB;
    }
    return NONE;
  }
}
