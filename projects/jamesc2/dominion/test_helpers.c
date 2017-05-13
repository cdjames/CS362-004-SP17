
#include "test_helpers.h"


int assertTrue(const char* test, int num, int left, int right, const char* lmsg, const char* rmsg, int reverse) {
	const char* msg;
	int failure = 0;
	if (reverse){
		if (left != right)
			msg = "    PASSED TEST ";
		else {
			msg = "    FAILED TEST ";
			failure = 1;
		}
	} else {
		if (left == right)
			msg = "    PASSED TEST ";
		else {
			msg = "    FAILED TEST ";
			failure = 1;
		}
	}

	printf("%s%d, %s: %s = %d, %s = %d\n", msg, num, test, lmsg, left, rmsg, right);

	return failure;
}

void printTestStart(const char* name) {
	printf("\n######################### Testing %s #########################\n\n", name);
}

void printTestEnd(const char* name, int fail, int tests) {
	if(fail)
		printf("\n------------ FAILURE - %s: %d/%d tests passed\n\n", name, tests-fail, tests);
	else
		printf("\n++++++++++++ SUCCESS - %s: %d/%d tests passed\n\n", name, tests, tests);
}

int getRandom(const int max) {
	return (floor(Random() * max));
}

void createRandomState(struct gameState * g, int * pl){
	// create a random state with counts at sane values
	// uses ideas from Lecture 11 
	int p = 2; // current player max
	int divisor = getRandom(5)+5;
	*pl = getRandom(p);
	int i;
	for (i = 0; i < sizeof(struct gameState); i++)
	{
		( ((char*)g)[i] = getRandom(256) );
	}
	g->numPlayers = MAX_PLAYERS;
	g->whoseTurn = *pl;
	g->handCount[*pl] = getRandom(MAX_DECK);
	g->discardCount[*pl] = getRandom(MAX_DECK);
	g->deckCount[*pl] = getRandom(MAX_DECK);
	// g->deckCount[*pl] = 0; // testing empty deck
	// make sure you have some treasure cards in deck 
	for (i = 0; i < g->deckCount[*pl]; i++)
	{
		// printf("i=%d, imod5=%d\n", i, i%divisor);
		if(i%divisor == 0)
			g->deck[*pl][i] = getRandom(duchy)+copper; // get random copper, silver, gold
		else
			g->deck[*pl][i] = getRandom(treasure_map);
	}

	// make sure you have some treasure cards in discard 
	for (i = 0; i < g->discardCount[*pl]; i++)
	{
		// printf("i=%d, imod5=%d\n", i, i%divisor);
		if(i%divisor == 0)
			g->discard[*pl][i] = getRandom(duchy)+copper; // get random copper, silver, gold
		else
			g->discard[*pl][i] = getRandom(treasure_map);
	}
}