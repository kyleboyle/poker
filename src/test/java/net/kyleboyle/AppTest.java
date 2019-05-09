package net.kyleboyle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testCardInputParse()
    {
      Hand hand = App.getHand("TD,AH,3C,KS,2S");
      assertNotNull(hand);
      assertEquals(5, hand.cards.size());
      assertTrue(hand.cards.contains(new Card(CardType.ACE, Suit.HEART)));
      /* TODO test more error scenarios and test all enums */
    }
}
