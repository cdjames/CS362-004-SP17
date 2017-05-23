/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A unit test for cardEffect(Sea Hag) from dominion.c
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
		deckCount1 = 0,
		currPlayer = 0,
		p1discard = 0,
		p1curse = 0,
		found = 0,
		curseBefore = 0,
		i = 0,
		iter = 1,
		bonus = 0, 
		failure = 0;
	int k[10] = {adventurer, embargo, village, minion, mine, cutpurse,
			sea_hag, tribute, smithy, council_room};

	struct gameState testG, Game;
	initializeGame(numPlayers, k, seed, &Game);

	printTestStart("cardEffect(Sea Hag)");

	// plant the sea_hag card
	Game.hand[currPlayer][0] = smithy;
	Game.hand[currPlayer][1] = adventurer;
	Game.hand[currPlayer][2] = cutpurse;
	Game.hand[currPlayer][3] = sea_hag;
	Game.hand[currPlayer][4] = mine;

	// copy the game
	memcpy(&testG, &Game, sizeof(struct gameState));

	// // general card effect test -- performed in smithy
	// cardCount0 = Game.handCount[currPlayer];
	// cardEffect(adventurer, 0, 0, 0, &Game, 3, &bonus);
	// Game.whoseTurn = 3;
	// failure += assertTrue("Handle cycle players", 1, Game.handCount[currPlayer], cardCount0+1, "cards in p.1 after", "expected", 0);

	for (iter = 1; iter < 3; iter++)
		{
		if(iter == 1)
			printf("**Iteration 1: deck has cards\n");
		if(iter == 2) {
			printf("**Iteration 2: deck is empty (expect shuffling of deck)\n");
			// try with empty deck
			memcpy(&Game, &testG, sizeof(struct gameState)); // reset game
			deckCount1 = Game.deckCount[currPlayer+1];
			// printf("deckCount=%d\n", Game.deckCount[currPlayer+1]);
			// copy deck to discard
			for (i = 0; i < deckCount1; i++)
			{
				// printf("iteration=%d\n", i);
				Game.discard[currPlayer+1][i] = Game.deck[currPlayer+1][i];
				Game.discardCount[currPlayer+1]++;
				Game.deckCount[currPlayer+1]--;
			}
			// printf("deckCount=%d\n", Game.deckCount[currPlayer+1]);
			// printf("discardCount=%d\n", Game.discardCount[currPlayer+1]);
			Game.deckCount[currPlayer+1] = 0;
			found = p1curse = curseBefore = 0;
		}
		// Sea Hag specific tests
		// memcpy(&Game, &testG, sizeof(struct gameState));
		cardCount0 = Game.handCount[currPlayer];
		deckCount1 = Game.deckCount[currPlayer+1];
		p1discard = Game.discardCount[currPlayer+1];
		Game.whoseTurn = currPlayer;
		for (i = 0; i < deckCount1; i++)
		{
			if(Game.deck[currPlayer+1][i] == curse)
				curseBefore += 1;
		}
		cardEffect(sea_hag, 0, 0, 0, &Game, 3, &bonus);
		failure += assertTrue("P. 1 count decreases by 1", 1, Game.handCount[currPlayer], cardCount0-1, "cards in p.1 after", "expected", 0);
		cardCount0 = Game.handCount[currPlayer];
		for (i = 0; i < cardCount0; i++)
		{
			if(Game.hand[currPlayer][i] == sea_hag)
				found += 1;
		}

		failure += assertTrue("Sea Hag not in hand", 2, found, 0, "sea_hag cards", "expected", 0);
		// check player 1's deck for curse and total count
		for (i = 0; i < deckCount1; i++)
		{
			if(Game.deck[currPlayer+1][i] == curse)
				p1curse += 1;
		}

		failure += assertTrue("Curse in p. 2 deck", 3, p1curse, curseBefore+1, "num curses", "expected", 0);
		failure += assertTrue("Curse top in p. 2 deck", 4, Game.deck[currPlayer+1][Game.deckCount[currPlayer+1]--], curse, "top card", "expected", 0);
		if(iter == 1)
			failure += assertTrue("P2 deck count same", 5, Game.deckCount[currPlayer+1], deckCount1, "p2 deck count", "expected", 0);
		if(iter == 2)
			failure += assertTrue("P2 deck now size of discard", 5, Game.deckCount[currPlayer+1], p1discard, "p2 deck count", "expected", 0);
		if(iter == 1)
			failure += assertTrue("P2 discard count = +1", 6, Game.discardCount[currPlayer+1], p1discard+1, "p2 discard count", "expected", 0);
		if(iter == 2)
			failure += assertTrue("P2 discard count = 1", 6, Game.discardCount[currPlayer+1], 1, "p2 discard count", "expected", 0);			
	}



	printTestEnd("cardEffect(Sea Hag)", failure, 6);
	return 0;
}
