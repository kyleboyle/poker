package net.kyleboyle;

import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AppTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  public void testCardInputParse() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertNotNull(hand);
    assertEquals(5, hand.cards.size());
    assertTrue(hand.cards.contains(new Card(CardType.ACE, Suit.HEART)));
    /* TODO test more error scenarios and test all enums */
  }


  public void testHandFlush() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkFlush());
    assertNull(hand.category);

    hand = App.getHand("TD,AD,3D,KD,2D");
    assertTrue(hand.checkFlush());
    assertEquals(Category.FLUSH, hand.category);

    hand = App.getHand("TD,AD,3D,KD,2H");
    assertFalse(hand.checkFlush());
  }

  public void testHandStraight() {
    Hand hand = App.getHand("TD,AH,3C,KS,2S");
    assertFalse(hand.checkStraight());
    assertNull(hand.category);

    hand = App.getHand("9D,8S,TD,QD,JD");
    assertTrue(hand.checkStraight());
    assertEquals(Category.STRAIGHT, hand.category);
  }

  public void testHandStraightFlush() {
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
}
