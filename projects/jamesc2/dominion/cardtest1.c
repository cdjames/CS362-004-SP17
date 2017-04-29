/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A unit test for cardEffect(Smithy) from dominion.c
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
	int seed = 999,
		numPlayers = 2,
		cardCount0 = 0,
		cardCount1 = 0,
		currPlayer = 0,
		discard = 0,
		bonus = 0,
		found = 0,  
		i = 0,
		failure = 0;
	int k[10] = {adventurer, embargo, village, minion, mine, cutpurse,
			sea_hag, tribute, smithy, council_room};

	struct gameState testG, Game;
	initializeGame(numPlayers, k, seed, &Game);

	printTestStart("cardEffect(Smithy)");

	// plant the smithy card
	Game.hand[currPlayer][0] = smithy;
	Game.hand[currPlayer][1] = adventurer;
	Game.hand[currPlayer][2] = cutpurse;
	Game.hand[currPlayer][3] = sea_hag;
	Game.hand[currPlayer][4] = mine;

	// copy the game
	memcpy(&testG, &Game, sizeof(struct gameState));

	// general card effect test
	cardCount0 = Game.handCount[currPlayer];
	cardEffect(smithy, 0, 0, 0, &Game, 0, &bonus);
	Game.whoseTurn = 3;
	failure += assertTrue("Handle cycle players", 1, Game.handCount[currPlayer], cardCount0+2, "cards in p.1 after", "expected", 0);

	// Smithy specific tests
	memcpy(&Game, &testG, sizeof(struct gameState));
	cardCount0 = Game.handCount[currPlayer];
	cardCount1 = Game.handCount[currPlayer+1];
	cardEffect(smithy, 0, 0, 0, &Game, 0, &bonus);
	Game.whoseTurn = 0;
	failure += assertTrue("P. 1 count increases by 2 (1 discarded)", 2, Game.handCount[currPlayer], cardCount0+2, "cards in p.1 after", "expected", 0);
	failure += assertTrue("P. 2 count does not increase", 3, Game.handCount[currPlayer+1], cardCount1, "cards in p.2 after", "expected", 0);

	for (i = 0; i <= Game.handCount[currPlayer]; i++)
	{
		if(Game.hand[currPlayer][i] == smithy)
			found += 1;
	}

	failure += assertTrue("Smithy not in hand", 4, found, 0, "smithy cards", "expected", 0);


	printTestEnd("cardEffect(Smithy)", failure);
	return 0;
}
