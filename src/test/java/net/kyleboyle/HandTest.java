package net.kyleboyle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HandTest extends TestCase {

  public HandTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(HandTest.class);
  }

  public void testFlush() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkFlush());
    assertNull(hand.category);

    hand = App.getHand("TD,AD,3D,KD,2D");
    assertTrue(hand.checkFlush());
    assertEquals(Category.FLUSH, hand.category);

    hand = App.getHand("TD,AD,3D,KD,2H");
    assertFalse(hand.checkFlush());
  }

  public void testStraight() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkStraight());
    assertNull(hand.category);

    hand = App.getHand("9D,8S,TD,QD,JD");
    assertTrue(hand.checkStraight());
    assertEquals(Category.STRAIGHT, hand.category);

    // check gap
    hand = App.getHand("9D,8S,TD,QD,KD");
    assertFalse(hand.checkStraight());
    assertNull(hand.category);
  }

  public void testAceLowStraight() {
    Hand hand = App.getHand("2D,AH,3C,4S,6S");
    assertFalse(hand.checkStraight());
    assertNull(hand.category);

    hand = App.getHand("2D,AH,3C,4S,5S");
    assertTrue(hand.checkStraight());
    assertEquals(Category.STRAIGHT, hand.category);
  }

  public void testStraightFlush() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkStraightFlush());
    assertNull(hand.category);

    hand = App.getHand("TS,AS,QS,KS,JS");
    assertTrue(hand.checkStraightFlush());
    assertEquals(Category.STRAIGHT_FLUSH, hand.category);

    hand = App.getHand("TS,AS,QS,KS,JH");
    assertFalse(hand.checkStraightFlush());
    assertNull(hand.category);
  }

  public void testQuadruple() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkQuadruple());
    assertNull(hand.category);

    hand = App.getHand("TS,TH,QS,TD,TC");
    assertTrue(hand.checkQuadruple());
    assertEquals(Category.QUADRUPLE, hand.category);

    hand = App.getHand("TS,TH,QS,TD,2C");
    assertFalse(hand.checkQuadruple());
    assertNull(hand.category);
  }

  public void testTriple() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkTriple());
    assertNull(hand.category);

    hand = App.getHand("TD,TH,3C,3S,2S");
    assertFalse(hand.checkTriple());
    assertNull(hand.category);

    hand = App.getHand("2S,8H,8S,2C,2D");
    assertTrue(hand.checkTriple());
    assertEquals(Category.TRIPLE, hand.category);

    hand = App.getHand("2S,8H,9S,2C,2D");
    assertTrue(hand.checkTriple());
    assertEquals(Category.TRIPLE, hand.category);
  }

  public void testFullHouse() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkFullHouse());
    assertNull(hand.category);

    hand = App.getHand("TD,TH,3S,TC,3D");
    assertTrue(hand.checkFullHouse());
    assertEquals(Category.FULL_HOUSE, hand.category);

    hand = App.getHand("TD,TH,TC,2S,3D");
    assertFalse(hand.checkFullHouse());
    assertNull(hand.category);
  }

  public void testTwoPair() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkTwoPair());
    assertNull(hand.category);

    hand = App.getHand("TD,TH,3S,4C,3D");
    assertTrue(hand.checkTwoPair());
    assertEquals(Category.TWO_PAIR, hand.category);

    hand = App.getHand("TD,TH,5C,2S,3D");
    assertFalse(hand.checkTwoPair());
    assertNull(hand.category);
  }

  public void testPair() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkPair());
    assertNull(hand.category);

    hand = App.getHand("TD,TH,3S,4C,5D");
    assertTrue(hand.checkPair());
    assertEquals(Category.PAIR, hand.category);
  }

  public void testHighcard() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertTrue(hand.checkHighcard());
    assertEquals(Category.HIGH_CARD, hand.category);
  }

  public void testGetHighestCategory() {
    Hand hand = checkCategory("TD,AH,3C,KS,2S", Category.HIGH_CARD);
    assertEquals(new Card(CardType.ACE, Suit.HEART), hand.categoryCards.get(0));
    checkCategory("TD,TH,3C,KS,2S", Category.PAIR);
    checkCategory("TD,KH,3C,KS,TS", Category.TWO_PAIR);
    checkCategory("TD,TH,3C,KS,TC", Category.TRIPLE);
    checkCategory("3D,4H,6C,5S,7S", Category.STRAIGHT);
    checkCategory("4D,TD,3D,KD,2D", Category.FLUSH);
    checkCategory("TD,TH,TC,KS,KH", Category.FULL_HOUSE);
    checkCategory("TD,TH,TC,TS,2S", Category.QUADRUPLE);
    checkCategory("TS,AS,QS,KS,JS", Category.STRAIGHT_FLUSH);
  }

  private Hand checkCategory(String cards, Category category) {
    Hand hand = App.getHand(cards);
    hand.calculateCategory();
    assertEquals(category, hand.category);
    return hand;
  }
}
