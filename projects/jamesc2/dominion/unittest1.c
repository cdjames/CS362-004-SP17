/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A unit test for shuffle() from dominion.c
*********************************************************************/

#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>
#include "dominion.h"
#include "dominion_helpers.h"
#include "test_helpers.h"
#include "rngs.h"

int main()
{
	int seed = 999, // bug appears if set to 100 (shuffle returns same card pattern)
		numPlayers = 2,
		same = 0,
		failure = 0,
		i,
		currPlayer = 0;
	int k[10] = {adventurer, embargo, village, minion, mine, cutpurse,
			sea_hag, tribute, smithy, council_room};

	struct gameState testG, Game;
	initializeGame(numPlayers, k, seed, &Game);

	printTestStart("shuffle()");
	// copy the game
	memcpy(&testG, &Game, sizeof(struct gameState));

	failure += assertTrue("deck counts match", 1, testG.deckCount[currPlayer], Game.deckCount[currPlayer], "testG deckCount", "Game deckCount", 0);
	for (i = 0; i < testG.deckCount[currPlayer]; i++)
	{
		if (testG.deck[currPlayer][i] == Game.deck[currPlayer][i]){
			same++;
			// printf("t=%d\n", testG.deck[currPlayer][i]);
			// printf("g=%d\n", Game.deck[currPlayer][i]);
		}
	}

	failure += assertTrue("deck same before shuffle", 2, testG.deckCount[currPlayer], same, "testG deckCount", "#same cards", 0);

	// do shuffle
	shuffle(currPlayer, &Game);

	same = 0;
	for (i = 0; i < testG.deckCount[currPlayer]; i++)
	{
		if (testG.deck[currPlayer][i] == Game.deck[currPlayer][i]){
			same++;
			// printf("t=%d\n", testG.deck[currPlayer][i]);
			// printf("g=%d\n", Game.deck[currPlayer][i]);
		}
	}

	failure += assertTrue("deck not same after shuffle", 3, testG.deckCount[currPlayer], same, "testG deckCount", "#same cards", 1);

	printTestEnd("shuffle()", failure);
	return 0;
}
