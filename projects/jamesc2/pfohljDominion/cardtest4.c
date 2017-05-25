/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A unit test for cardEffect(Cutpurse) from dominion.c
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
		p1copper = 0,
		copperBefore = 0,
		found = 0,
		i = 0,
		bonus = 0, 
		failure = 0,
		numtests = 0,
		iter = 1;
	int k[10] = {adventurer, embargo, village, minion, mine, cutpurse,
			sea_hag, tribute, smithy, council_room};

	struct gameState testG, Game;
	initializeGame(numPlayers, k, seed, &Game);

	printTestStart("cardEffect(Cutpurse)");

	// plant the cutpurse card
	Game.hand[currPlayer][0] = smithy;
	Game.hand[currPlayer][1] = adventurer;
	Game.hand[currPlayer][2] = cutpurse;
	Game.hand[currPlayer][3] = sea_hag;
	Game.hand[currPlayer][4] = mine;
	memcpy(&testG, &Game, sizeof(struct gameState));

	for (iter = 1; iter < 3; iter++)
	{
		if(iter == 1)
			printf("**Iteration 1: no cards in p2's hand\n");

		if(iter == 2) {
			memcpy(&Game, &testG, sizeof(struct gameState)); // reset game
			Game.hand[currPlayer+1][0] = smithy;
			Game.hand[currPlayer+1][1] = copper;
			Game.hand[currPlayer+1][2] = cutpurse;
			Game.hand[currPlayer+1][3] = sea_hag;
			Game.hand[currPlayer+1][4] = mine;
			Game.handCount[currPlayer+1] = 5;
			found = p1copper = copperBefore = 0;
			printf("**Iteration 2: copper in p2's hand\n");
		}

		// // general card effect test -- performed in smithy
		// cardCount0 = Game.handCount[currPlayer];
		// cardEffect(adventurer, 0, 0, 0, &Game, 3, &bonus);
		// Game.whoseTurn = 3;
		// failure += assertTrue("Handle cycle players", 1, Game.handCount[currPlayer], cardCount0+1, "cards in p.1 after", "expected", 0);

		// Cutpurse specific tests
		cardCount0 = Game.handCount[currPlayer];
		cardCount1 = Game.handCount[currPlayer+1];
		printf("cardCount1=%d\n", cardCount1);
		Game.whoseTurn = currPlayer;
		for (i = 0; i < cardCount1; i++)
		{
			if(Game.hand[currPlayer+1][i] == copper)
				copperBefore += 1;
		}
		// play the card
		cardEffect(cutpurse, 0, 0, 0, &Game, 2, &bonus);

		// check discard
		failure += assertTrue("P. 1 count decreases by 1", ++numtests, Game.handCount[currPlayer], cardCount0-1, "cards in p.1 after", "expected", 0);
		for (i = 0; i < Game.handCount[currPlayer]; i++)
		{
			if(Game.hand[currPlayer][i] == cutpurse)
				found += 1;
		}
		failure += assertTrue("Cutpurse not in hand", ++numtests, found, 0, "cutpurse cards", "expected", 0);

		// check player 1's hand for copper and total count
		for (i = 0; i < Game.handCount[currPlayer+1]; i++)
		{
			if(Game.hand[currPlayer+1][i] == copper)
				p1copper += 1;
		}
		if(copperBefore > 0){
			failure += assertTrue("Coppers in p. 2 hand", ++numtests, p1copper, copperBefore-1, "num coppers", "expected", 0);
			// check num cards in player 2's hand
			failure += assertTrue("# cards p. 2 hand", ++numtests, Game.handCount[currPlayer+1], cardCount1-1, "num cards", "expected", 0);
		}
		else {
			failure += assertTrue("Coppers in p. 2 hand", ++numtests, p1copper, copperBefore, "num coppers", "expected", 0);
			failure += assertTrue("# cards p. 2 hand", ++numtests, Game.handCount[currPlayer+1], cardCount1, "num cards", "expected", 0);
		}
		
		// check coins (+2)
		updateCoins(currPlayer, &testG, 0);
		failure += assertTrue("P1 Coins", ++numtests, Game.coins, testG.coins+2, "coins", "expected", 0);
	}

	printTestEnd("cardEffect(Cutpurse)", failure, numtests);
	return 0;
}
