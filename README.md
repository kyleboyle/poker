# poker

Given input of two 5-card poker hands, which one is more valuable.

## Input
Two csv strings representing the two hands to evaluate. Each element in the CSV list is a concatenated tuple of (card, suit), eg:

`4D,6S,9H,QH,QC 2H,2D,4C,4D,4S`

The possible card identifiers are: 2, 3, 4, 5, 6, 7, 8, 9, T (10), J (Jack), Q (Queen), K, (King), A (Ace)

The suit identifiers are D (Diamonds), S (Spades), C (Clubs), H (Hearts)

## Rules

In the card game poker, a hand consists of five cards and are ranked, from lowest to highest, in the following way:

- High Card: Highest value card.
- One Pair: Two cards of the same value.
- Two Pairs: Two different pairs.
- Three of a Kind: Three cards of the same value.
- Straight: All cards are consecutive values.
- Flush: All cards of the same suit.
- Full House: Three of a kind and a pair.
- Four of a Kind: Four cards of the same value.
- Straight Flush: All cards are consecutive values of same suit.

If the hands are tied based on above category, the hands are ranked based on card value.

The cards are valued in the order:
2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace.


## Output
A summary of the hands and which one wins, eg:
```
4D,6S,9H,QH,QC vs 2H,2D,4C,4D,4S
PAIR vs FULL_HOUSE
second wins - FULL_HOUSE - 2H,2D,4C,4D,4S
```

## Build

### compile & test & package
```
mvn package
```

### run
```
mvn exec:java -Dexec.args="4D,6S,9H,QH,QC 2H,2D,4C,4D,4S"
# or
java -jar target/poker-1.0-SNAPSHOT.jar 4D,6S,9H,QH,QC 2H,2D,4C,4D,4S
```
