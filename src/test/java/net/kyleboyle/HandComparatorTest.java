package net.kyleboyle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HandComparatorTest extends TestCase {

  public HandComparatorTest(String testName) {
    super(testName);
  }
  public static Test suite() {
    return new TestSuite(HandComparatorTest.class);
  }


  public void testHandCompare() {
    // pair vs straight flush
    assertFalse(hand1IsBetter("TD,TH,3C,KS,2S", "TS,AS,QS,KS,JS"));
    assertTrue(hand1IsBetter("TS,AS,QS,KS,JS", "TD,TH,3C,KS,2S"));

    // 4 of a kind
    assertTrue(hand1IsBetter("JD,JH,JC,JS,2S", "TD,TH,TC,TS,2C"));
    assertFalse(hand1IsBetter("TD,TH,TC,TS,2C", "JD,JH,JC,JS,2S"));

    // same two pair different high card
    assertTrue(hand1IsBetter("4H,4D,3C,3S,AH", "4C,4S,3H,3S,KD"));

    // same two pair same high card
    assertTrue(handsAreTied("4H,4D,3C,3S,AH", "4C,4S,3H,3S,AD"));

    // same high card
    assertTrue(handsAreTied("2H,3D,4H,5D,AH", "2S,3C,4S,5C,AD"));
  }

  private boolean handsAreTied(String hand1, String hand2) {
    Hand h1 = App.getHand(hand1);
    Hand h2 = App.getHand(hand2);
    h1.calculateCategory();
    h2.calculateCategory();
    HandComparator comp = new HandComparator();
    return comp.compare(h1, h2) == 0;
  }

  private boolean hand1IsBetter(String hand1, String hand2) {
    Hand h1 = App.getHand(hand1);
    Hand h2 = App.getHand(hand2);
    h1.calculateCategory();
    h2.calculateCategory();
    HandComparator comp = new HandComparator();
    return comp.compare(h1, h2) > 0;
  }

}
