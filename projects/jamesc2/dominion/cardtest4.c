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
		deckCount1 = 0,
		currPlayer = 0,
		discard = 0,
		discardBefore = 0,
		curse = 0,
		found = 0,
		curseBefore = 0,
		money = 0,
		i = 0,
		bonus = 0, 
		failure = 0;
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

	// copy the game
	memcpy(&testG, &Game, sizeof(struct gameState));

	// // general card effect test -- performed in smithy
	// cardCount0 = Game.handCount[currPlayer];
	// cardEffect(adventurer, 0, 0, 0, &Game, 3, &bonus);
	// Game.whoseTurn = 3;
	// failure += assertTrue("Handle cycle players", 1, Game.handCount[currPlayer], cardCount0+1, "cards in p.1 after", "expected", 0);

	// Sea Hag specific tests
	// memcpy(&Game, &testG, sizeof(struct gameState));
	cardCount0 = Game.handCount[currPlayer];
	// printf("cards=%d\n", cardCount0);
	cardCount1 = Game.handCount[currPlayer+1];
	deckCount1 = Game.deckCount[currPlayer+1];
	// discardCount1 = Game.discardCount[currPlayer+1];
	printf("deckCount1=%d\n", deckCount1);
	Game.whoseTurn = currPlayer;
	for (i = 0; i < deckCount1; i++)
	{
		if(Game.deck[currPlayer+1][i] == curse)
			curseBefore += 1;
	}
	cardEffect(cutpurse, 0, 0, 0, &Game, 2, &bonus);
	failure += assertTrue("P. 1 count decreases by 1", 1, Game.handCount[currPlayer], cardCount0-1, "cards in p.1 after", "expected", 0);
	for (i = 0; i < Game.handCount[currPlayer]; i++)
	{
		if(Game.hand[currPlayer][i] == cutpurse)
			found += 1;
	}

	failure += assertTrue("Sea Hag not in hand", 2, found, 0, "cutpurse cards", "expected", 0);
	// check player 1's deck for curse and total count
	for (i = 0; i < deckCount1; i++)
	{
		if(Game.deck[currPlayer+1][i] == curse)
			curse += 1;
	}

	failure += assertTrue("Curse in p. 2 deck", 3, curse, curseBefore+1, "num curses", "expected", 0);
	failure += assertTrue("Curse top in p. 2 deck", 4, Game.deck[currPlayer+1][Game.deckCount[currPlayer+1]--], curse, "top card", "expected", 0);
	failure += assertTrue("P2 deck count same", 5, Game.deckCount[currPlayer+1], deckCount1, "p2 deck", "expected", 0);




	printTestEnd("cardEffect(Cutpurse)", failure);
	return 0;
}
