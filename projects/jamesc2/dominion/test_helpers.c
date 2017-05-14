
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

void createRandomState(struct gameState * g, int * pl, int special){
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

	int specialdeck = getRandom(g->deckCount[*pl]-1);
	int specialdisc = getRandom(g->discardCount[*pl]-1);
	// g->deckCount[*pl] = 0; // testing empty deck
	// make sure you have some treasure cards in deck 
	
	for (i = 0; i < g->deckCount[*pl]; i++)
	{
		// printf("i=%d, imod5=%d\n", i, i%divisor);
		
		g->deck[*pl][i] = getRandom(treasure_map);
		if(special == 0) {
			if(i%divisor == 0)
				g->deck[*pl][i] = getRandom(duchy)+copper; // get random copper, silver, gold
		}

		else {// make sure there are no treasure cards
			if(g->deck[*pl][i] >= copper && g->deck[*pl][i] <= gold)
				g->deck[*pl][i] = getRandom((treasure_map-7)+7); // get a card that's not a treasure
			
		}		
	}
	// special case where deck only has one treasure
	if(special == 1)
		g->deck[*pl][specialdeck] = getRandom(duchy)+copper;

	// make sure you have some treasure cards in discard 
	for (i = 0; i < g->discardCount[*pl]; i++)
	{
		g->discard[*pl][i] = getRandom(treasure_map);
		// printf("i=%d, imod5=%d\n", i, i%divisor);
		if(special == 0) {
			if(i%divisor == 0)
				g->discard[*pl][i] = getRandom(duchy)+copper; // get random copper, silver, gold
		}
		else {// make sure there are no treasure cards
			if(g->discard[*pl][i] >= copper && g->discard[*pl][i] <= gold)
				g->discard[*pl][i] = getRandom((treasure_map-7)+7); // get a card that's not a treasure
		}
	}
	// special case where discard only has one treasure
	if(special == 2)
		g->discard[*pl][specialdisc] = getRandom(duchy)+copper;

}

void createRandomStateSH(struct gameState * g, int pl){
	// create a random state with counts at sane values
	// uses ideas from Lecture 11 
	int p = 2; // current player max
	int special = 0;
	int divisor = getRandom(5)+5; 
	// *pl = getRandom(p)+2;
	int i;
	g->numPlayers = MAX_PLAYERS;
	g->handCount[pl] = getRandom(MAX_DECK);
	g->discardCount[pl] = getRandom(MAX_DECK);
	g->deckCount[pl] = getRandom(MAX_DECK);

	int specialdeck = getRandom(g->deckCount[pl]-1);
	int specialdisc = getRandom(g->discardCount[pl]-1);
	// g->deckCount[*pl] = 0; // testing empty deck
	// make sure you have some treasure cards in deck 
	
	for (i = 0; i < g->deckCount[pl]; i++)
	{
		// printf("i=%d, imod5=%d\n", i, i%divisor);
		
		g->deck[pl][i] = getRandom(treasure_map);
		// if(special == 0) {
		// 	if(i%divisor == 0)
		// 		g->deck[pl][i] = getRandom(duchy)+copper; // get random copper, silver, gold
		// }

		// else {// make sure there are no treasure cards
		// 	if(g->deck[pl][i] >= copper && g->deck[pl][i] <= gold)
		// 		g->deck[pl][i] = getRandom((treasure_map-7)+7); // get a card that's not a treasure
			
		// }		
	}
	// special case where deck only has one treasure
	// if(special == 1)
	// 	g->deck[pl][specialdeck] = getRandom(duchy)+copper;

	// make sure you have some treasure cards in discard 
	for (i = 0; i < g->discardCount[pl]; i++)
	{
		g->discard[pl][i] = getRandom(treasure_map);
		// printf("i=%d, imod5=%d\n", i, i%divisor);
		// if(special == 0) {
		// 	if(i%divisor == 0)
		// 		g->discard[pl][i] = getRandom(duchy)+copper; // get random copper, silver, gold
		// }
		// else {// make sure there are no treasure cards
		// 	if(g->discard[pl][i] >= copper && g->discard[pl][i] <= gold)
		// 		g->discard[pl][i] = getRandom((treasure_map-7)+7); // get a card that's not a treasure
		// }
	}
	// special case where discard only has one treasure
	// if(special == 2)
	// 	g->discard[pl][specialdisc] = getRandom(duchy)+copper;

}

void createRandomStateEm(struct gameState * g, int pl, int choice, int special){
	// create a random state with counts at sane values
	// uses ideas from Lecture 11 
	int p = 2; // current player max
	int divisor = getRandom(5)+5; 
	// *pl = getRandom(p)+2;
	int i;
	// randomize game
	for (i = 0; i < sizeof(struct gameState); i++)
	{
		( ((char*)g)[i] = getRandom(256) );
	}

	g->numPlayers = MAX_PLAYERS;
	g->handCount[pl] = getRandom(MAX_DECK);
	g->discardCount[pl] = getRandom(MAX_DECK);
	g->deckCount[pl] = getRandom(MAX_DECK);
	g->coins = getRandom(256);

	// set sane values for embargo piles (0) and supply count
	for(i=0; i <= treasure_map; i++){
		g->embargoTokens[i] = 0;
		g->supplyCount[i] = getRandom(100);
	}

	// special case: supply count is not in play
	if(special)
		g->supplyCount[choice] = -1;
	// g->deckCount[*pl] = 0; // testing empty deck
	// make sure you have some treasure cards in deck 
	
	for (i = 0; i < g->deckCount[pl]; i++)
	{
		// printf("i=%d, imod5=%d\n", i, i%divisor);
		
		g->deck[pl][i] = getRandom(treasure_map);
		// if(special == 0) {
		// 	if(i%divisor == 0)
		// 		g->deck[pl][i] = getRandom(duchy)+copper; // get random copper, silver, gold
		// }

		// else {// make sure there are no treasure cards
		// 	if(g->deck[pl][i] >= copper && g->deck[pl][i] <= gold)
		// 		g->deck[pl][i] = getRandom((treasure_map-7)+7); // get a card that's not a treasure
			
		// }		
	}
	// special case where deck only has one treasure
	// if(special == 1)
	// 	g->deck[pl][specialdeck] = getRandom(duchy)+copper;

	// make sure you have some treasure cards in discard 
	for (i = 0; i < g->discardCount[pl]; i++)
	{
		g->discard[pl][i] = getRandom(treasure_map);
		// printf("i=%d, imod5=%d\n", i, i%divisor);
		// if(special == 0) {
		// 	if(i%divisor == 0)
		// 		g->discard[pl][i] = getRandom(duchy)+copper; // get random copper, silver, gold
		// }
		// else {// make sure there are no treasure cards
		// 	if(g->discard[pl][i] >= copper && g->discard[pl][i] <= gold)
		// 		g->discard[pl][i] = getRandom((treasure_map-7)+7); // get a card that's not a treasure
		// }
	}
	// special case where discard only has one treasure
	// if(special == 2)
	// 	g->discard[pl][specialdisc] = getRandom(duchy)+copper;

}