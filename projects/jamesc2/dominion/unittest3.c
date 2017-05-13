/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A unit test for isGameOver() from dominion.c
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
		failure = 0,
		empty = 0,
		i;
	int k[10] = {adventurer, embargo, village, minion, mine, cutpurse,
			sea_hag, tribute, smithy, council_room};

	struct gameState testG, Game;
	initializeGame(numPlayers, k, seed, &Game);

	printTestStart("isGameOver()");

	// fill up stacks
	for (i = curse; i <= treasure_map; i++)
	{
		Game.supplyCount[i] = 5;
	}
	// copy the game
	memcpy(&testG, &Game, sizeof(struct gameState));

	// test full stack
	failure += assertTrue("Stacks are not empty", 1, isGameOver(&Game), 0, "isGameOver result", "expected", 0);

	// no province cards left
	Game.supplyCount[province] = 0;
	failure += assertTrue("Province stack empty", 2, isGameOver(&Game), 1, "isGameOver result", "expected", 0);

	// 2 stacks empty
	memcpy(&Game, &testG, sizeof(struct gameState));
	for (i = curse; i <= 1; i++)
		Game.supplyCount[i] = 0;
	failure += assertTrue("2 stacks empty", 3, isGameOver(&Game), 0, "isGameOver result", "expected", 0);

	// 3 stacks empty
	memcpy(&Game, &testG, sizeof(struct gameState));
	for (i = curse; i <= 2; i++)
		Game.supplyCount[i] = 0;
	failure += assertTrue("3 stacks empty", 4, isGameOver(&Game), 1, "isGameOver result", "expected", 0);

	// more than 3 stacks empty
	memcpy(&Game, &testG, sizeof(struct gameState));
	empty = floor(Random() * (treasure_map-4)) + 4;
	// printf("%d\n", empty);
	for (i = curse; i <= empty; i++)
		Game.supplyCount[i] = 0;
	failure += assertTrue(">3 stacks empty", 5, isGameOver(&Game), 1, "isGameOver result", "expected", 0);


	printTestEnd("isGameOver()", failure, 5);
	return 0;
}
