Re-factoring notes:
Can only run random test on adventurer since used refactored code to test the cards
Can run all unit tests and card unit tests since classmate only refactored those cards

make: unittests worked; cardtests worked; randomtestadventurer did not work since function was named differently

Refactoring randomtestadventurer for make:

- had to use Adventurer(state) instead of playAdventurer(g, player, &cd, temphand, &dtreas, &tmpsize)
- had to change checkCard() function to use fewer parameters since they were not handled by 
Adventurer()
- that's about it; overall changed and removed about 5 lines of code; 15 minutes


Running notes:

unittests - all worked, all succeeded
cardtests - 
	cardtest1: ran, failed 2/4 tests
	cardtest2: ran, failed 4/8 tests
	cardtest3: ran, failed 9/12 tests
	cardtest4: ran, all passed
randomtestadventurer - ran, failed 99/8000 tests

Bug notes:

Bugs: 
	- randomtestadventurer: memcmp result is different, hand count is 1 off, top card in hand is different (in first run through test)
./cardtest* | grep -e FAILED -e Iteration
	- cardtest1: played card not discarded
	- cardtest2: played card not discarded; treasure cards in hand does not increase
	- cardtest3: played card not discarded; curse card not top card when done; deck counts different; discard counts off; deck counts off

Bug report:
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

Debugging notes:
 
[Title]: different hands (and counts) when only 1 treasure in deck
[ID]: 0003
[Priority]: A
[Pre-conditions]: build randomtestadventurer binary
[Steps]: 
1. run randomtestadventurer binary
2. look at results of test "hand count same" when
[Expected]: hand counts should be same
[Observed]: hand counts are 1 off
[Specification]: http://wiki.dominionstrategy.com/index.php/Adventurer
"If you run out of cards after shuffling and still only have one Treasure, you get just that one Treasure."
[Cause]: (see below)

How I found the bug:
I started by modifying randomtestadventurer to only do one test, as it was clear which part of the random testing was revealing the bug.

idea - above bugs are pretty much the same as the bugs in my code, so look to fix a couple of them