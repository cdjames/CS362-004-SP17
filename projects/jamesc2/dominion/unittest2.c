/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A unit test for buyCard() from dominion.c
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
		discardCount = 0,
		coins = 0,
		numBuys = 0,
		failure = 0,
		i,
		currPlayer = 0;
	int k[10] = {adventurer, embargo, village, minion, mine, cutpurse,
			sea_hag, tribute, smithy, council_room};

	struct gameState testG, Game;
	initializeGame(numPlayers, k, seed, &Game);

	printTestStart("buyCard()");
	// copy the game
	memcpy(&testG, &Game, sizeof(struct gameState));

	// set number of buys to 0
	Game.numBuys = 0;
	Game.whoseTurn = currPlayer;
	failure += assertTrue("no buys", 1, buyCard(adventurer, &Game), -1, "buyCard result", "expected", 0);

	// set supply of card to 0
	memcpy(&Game, &testG, sizeof(struct gameState));
	Game.numBuys = 1;
	Game.whoseTurn = currPlayer;
	Game.supplyCount[adventurer] = 0;
	failure += assertTrue("no card in supply", 2, buyCard(adventurer, &Game), -1, "buyCard result", "expected", 0);

	// set coins to be less than card to buy
	memcpy(&Game, &testG, sizeof(struct gameState));
	Game.numBuys = 1;
	Game.whoseTurn = currPlayer;
	Game.supplyCount[adventurer] = 5;
	Game.coins = getCost(adventurer) - 1;
	failure += assertTrue("not enough coins", 3, buyCard(adventurer, &Game), -1, "buyCard result", "expected", 0);

	// set valid buy conditions
	memcpy(&Game, &testG, sizeof(struct gameState));
	Game.numBuys = numBuys = 1;
	Game.whoseTurn = currPlayer;
	Game.supplyCount[adventurer] = 6;
	discardCount = Game.discardCount[currPlayer];
	Game.coins = coins = getCost(adventurer);
	failure += assertTrue("successful buy", 4, buyCard(adventurer, &Game), 0, "buyCard result", "expected", 0);
	failure += assertTrue("discardCount increased", 5, Game.discardCount[currPlayer], discardCount+1, "discardCount after buy", "expected", 0);
	failure += assertTrue("numBuys decreased", 6, Game.numBuys, numBuys-1, "numBuys after buy", "expected", 0);
	failure += assertTrue("coins decreased", 7, Game.coins, coins-getCost(adventurer), "numBuys after buy", "expected", 0);


	printTestEnd("buyCard()", failure);
	return 0;
}
