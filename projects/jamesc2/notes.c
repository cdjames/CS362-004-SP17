Re-factoring:
I could only run random tests on adventurer since I used refactored code to test the cards; in other words, my teammate chose to refactor different card functions than I did.
I was able to run all unit tests and card unit tests.

make: unittests worked; cardtests worked; randomtestadventurer did not work since function was named differently

Refactoring randomtestadventurer for make:

- had to use Adventurer(state) instead of playAdventurer(g, player, &cd, temphand, &dtreas, &tmpsize)
- had to change checkCard() function to use fewer parameters since they were not handled by Adventurer()
- that's about it; overall changed and removed about 5 lines of code; 15 minutes

Running notes:

unittests - all worked, all succeeded
cardtests - 
	cardtest1: ran, failed 2/4 tests
	cardtest2: ran, failed 4/8 tests
	cardtest3: ran, failed 9/12 tests
	cardtest4: ran, all passed
randomtestadventurer - ran, failed 99/8000 tests

Bug-Reports:

Bugs (informal): 
	- randomtestadventurer: memcmp result is different, hand count is 1 off, top card in hand is different (in first run through test)
./cardtest* | grep -e FAILED -e Iteration
	- cardtest1: played card not discarded
	- cardtest2: played card not discarded; treasure cards in hand does not increase
	- cardtest3: played card not discarded; curse card not top card when done; deck counts different; discard counts off; deck counts off

Bug report (formal):
[Title]: Empty deck is not shuffled [Sea Hag]
[ID]: 0001
[Priority]: A
[Pre-conditions]: build cardtest3 binary
[Steps]: 
1. run cardtest3 binary
2. look at results of test "P2 deck now size of discard"
[Expected]: Player 2 deck size should be size of previous discard pile
[Observed]: Player 2 deck size is -3
[Specification]: http://wiki.dominionstrategy.com/index.php/Sea_Hag
"A player with no cards left in his deck shuffles first in order to get a card to discard."
[Cause]: There is no check for deck size in the sea_hag case (lines 1359-1370)

[Title]: Curse is not top card after Sea Hag
[ID]: 0002
[Priority]: A
[Pre-conditions]: build cardtest3 binary
[Steps]: 
1. run cardtest3 binary
2. look at results of test "Curse top in p. 2 deck" in iteration 1.
[Expected]: Top card in player 2's hand should be curse
[Observed]: Top card in player 2's hand is not curse
[Specification]: http://wiki.dominionstrategy.com/index.php/Sea_Hag
"...puts Curse cards right on top of an opponent's deck"
[Cause]: In-place decrement is used errantly on lines 1364, 1365, and 1367 (should be no decrements)

Debugging:
Bug report:
[Title]: playAdventurer - different deck (and counts) when only 1 treasure in deck
[ID]: 0003
[Priority]: A
[Pre-conditions]: build randomtestadventurer binary
[Steps]: 
1. run randomtestadventurer binary
2. look at results of test "deck count same" when
[Expected]: deck counts should be same
[Observed]: deck counts are off by 3
[Specification]: http://wiki.dominionstrategy.com/index.php/Adventurer
"Reveal cards until you reveal 2 Treasure cards...discard other revealed cards"
"If you run out of cards after shuffling and still only have one Treasure, you get just that one Treasure."
[Cause]: (see below)
[Fix]: (see below)

How I found the bug:
I started by modifying randomtestadventurer to only do one test, as it was clear which part of the random testing was revealing the bug.
- created print statements to ensure that the random deck was valid and only contained 1 treasure
- created print statements to ensure that random discard contained no treasure cards
- ran randomtestadventurer with gdb with breakpoint at playAdventurer
- watched variables such as drawntreasure, cardDrawn, and deckCount
- stepped into shuffle() when deck count was 0 and noticed that shuffle() doesn't actually return a new deck
- also stepped into drawCard(), which actually does shuffle the discard pile back into the deck
- So one of the problems is that there should be another check in the while loop to make sure that only 1 shuffle is done. The other problem is that this situation should result in an endless loop, but does not, meaning that cardDrawn is getting a second treasure somehow
- ran with gdb again, this time noting initial deck:
{1, 21, 16, 4, 7, 19, 11, 23, 10
	and initial discard:
{1, 16, 12, 9, 12, 19, 12, 13, 2, 14, 8, 12, 14, 8, 15, 9, 21, 0, 12, 9, 19, 15, 11, 18, 0, 0, 
  25, 14, 7, 14, 13, 18, 15, 9, 17, 15, 12, 16, 14, 8, 25, 17, 23, 13, 12, 20, 9, 21, 15, 25, 25, 
  15, 8, 25, 13, 0, 25, 13, 2, 14, 20, 1, 19, 23, 15, 18, 23, 12, 16, 23, 17, 21, 2, 0, 19, 16, 9, 
  17, 11, 1, 25, 19, 12, 0, 22, 20, 20, 2, 21, 23, 3, 22, 10, 25, 18, 7, 12, 15, 15, 0, 16, 16, 9, 
  13, 9, 9, 8, 1, 10, 2, 17, 21, 17, 12, 9, 10, 22, 1, 20, 21, 1, 9, 22, 19, 2, 15, 13, 8, 11, 19, 
  7, 7, 19, 3, 0, 12, 3, 11, 10, 3, 14, 17, 16, 9, 19, 19, 20, 11, 9, 24, 21, 15, 15, 12, 21, 8, 20, 
  9, 20, 14, 25, 19, 23, 11, 9, 12, 13, 9, 19, 2, 18, 8, 19, 18, 1, 0, 11, 8, 12, 21, 0, 24, 1,
  and hand: (no valid values)

After shuffling:
shuffled deck:
{1, 0, 15, 14, 23, 11, 12, 13, 2, 7, 18, 16, 7, 21, 12, 12, 9, 11, 15, 20, 12, 19, 2, 19, 1, 
  2, 15, 25, 21, 19, 23, 20, 21, 9, 15, 19, 14, 2, 15, 8, 1, 10, 0, 25, 1, 9, 13, 16, 12, 14, 23, 
  17, 11, 1, 20, 10, 9, 9, 9, 19, 18, 20, 12, 0, 0, 0, 18, 20, 25, 19, 10, 11, 8, 22, 9, 11, 25, 25, 
  2, 11, 17, 12, 14, 15, 9, 18, 9, 7, 10, 13, 22, 21, 16, 22, 8, 0, 24, 20, 9, 15, 12, 19, 14, 9, 9, 
  17, 9, 25, 12, 21, 8, 16, 25, 12, 19, 17, 17, 14, 25, 12, 3, 17, 16, 12, 12, 16, 2, 13, 9, 0, 3, 
  8, 0, 8, 17, 20, 15, 11, 18, 15, 19, 8, 21, 1, 8, 19, 19, 1, 21, 3, 13, 14, 0, 21, 23, 14, 21, 8, 
  23, 13, 12, 20, 1, 15, 0, 13, 25, 15, 24, 16, 13, 9, 19, 2, 9, 19, 22, 3, 7, 15, 23, 12, 18,

  discard is Empty

  - playAdventurer correctly goes through all cards in newly shuffled deck (no treasure cards)
  - drawCard attempts to shuffle again, but cannot because discardCount is 0
  - the result is that handCount is left at 27, which accesses the only treasure card currently in the hand errantly, raising drawntreasure to 2 and exiting playAdventurer

  The Fix:
  To prevent this errant behavior, an additional counter must be added which checks for a deckCount of zero. This counter must not be allowed to go past 1, since only 1 shuffle should be allowed, and must be checked for in the main loop. 

  See gdbsession.txt for the output of my gdb session. 

  Agans Principles:
  1. Know the manual - It was important for me to understand the specification of the adventurer card when debugging the special case of only 1 treasure card in the deck.
  2. Make it fail - I was able to reduce the failures to the special case mentioned above and consistently was able to make it fail.
  3. Quit thinking and look - Although I did make a hypothesis before looking, I then examined the lines of code that I thought were at fault using print statements and gdb.
  4. Divide and conquer - I used this method to pinpoint the problem in the original inspection, eliminating lines of code until the failure disappeared.
  5. Change one thing at a time - I tried to make minimal changes to the code when I was fixing the bug.
  6. Keep an audit trail - the sections above represent the audit trail that I kept during my debugging session.
  7. Check the plug - I did not trust my tests and did additional testing to make sure that the data sets my tests were supplying were correct.
  8. Get a fresh view - I was not able to use this principle during debugging.
  9. If you didn't fix it, it ain't fixed - I was able to get my special test cases to pass after changing the faulty code.
