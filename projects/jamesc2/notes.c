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

possible bugs: 
	- randomtestadventurer: memcmp result is different, hand count is 1 off, top card in hand is different (in first run through test)
./cardtest* | grep -e FAILED -e Iteration
	- cardtest1: wrong # cards in hand
	- cardtest2: played card not discarded; treasure cards in hand does not increase
	- cardtest3: played card not discarded; curse card not top card when done; deck counts different; discard counts off; deck counts off

