/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A unit test for numHandCards() from dominion.c
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
		failure = 0;
	int k[10] = {adventurer, embargo, village, minion, mine, cutpurse,
			sea_hag, tribute, smithy, council_room};

	struct gameState testG, Game;
	initializeGame(numPlayers, k, seed, &Game);

	printTestStart("numHandCards()");

	// copy the game
	memcpy(&testG, &Game, sizeof(struct gameState));

	Game.whoseTurn = 0;
	failure += assertTrue("Player 1 count", 1, numHandCards(&Game), 5, "numHandCards result", "expected", 0);

	Game.whoseTurn = 1;
	failure += assertTrue("Player 2 count", 2, numHandCards(&Game), 0, "numHandCards result", "expected", 0);

	Game.whoseTurn = 2;
	failure += assertTrue("invalid player count", 3, numHandCards(&Game), 0, "numHandCards result", "expected", 0);

	printTestEnd("numHandCards()", failure);
	return 0;
}
