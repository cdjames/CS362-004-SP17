/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A random tester for cardEffect(Adventurer) from dominion.c
*********************************************************************/

#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>
#include "dominion.h"
#include "dominion_helpers.h"
#include "test_helpers.h"
#include "rngs.h"

#define NUM_RUNS 300


void checkCard(
		int result, 
		int * failure,
		int * num,
		int player,
		int choice,
		int handpos,
		int special,
		struct gameState *post, 
		struct gameState *pre)
{
	// uses ideas from Lecture 11
	/* recreate the state of post using pre */
	
	// see what we've got
	// printf("discard count pre=%d, post=%d\n", pre->discardCount[player], post->discardCount[player]);
	// printf("deck count pre=%d, post=%d\n", pre->deckCount[player], post->deckCount[player]);
	// printf("hand count pre=%d, post=%d\n", pre->handCount[player], post->handCount[player]);
	// printf("tmpsize=%d\n", tmpsize);
	int i,
		hcount = pre->handCount[player],
		expected = (special) ? -1 : 0;
	
	/* increase coins by 2; put embargo on top of supply pile; discard card */
	pre->coins += 2;

	// supply pile is not empty, so add the embargo and trash the card
	if(!special){
		// add embargo
		pre->embargoTokens[choice]++;
		// trash card
		pre->discard[player][pre->discardCount[player]-1] = pre->hand[player][handpos];
		pre->discardCount[player]++;
		if(handpos < hcount-1){ // if not last card
			// simulate discarding
			for (i = 0; i < hcount-2; i++)
			{
				pre->hand[player][handpos+i] = pre->hand[player][handpos+i+1];
			}
		}

		pre->handCount[player]--;
	}

	*failure += assertTrue("Return is correct", *num, result, expected, "returned", "expected", 0);
	*failure += assertTrue("Pre & post is same", ++(*num), (memcmp( pre, post, sizeof(struct gameState))), 0, "memcmp pre/post", "expected", 0);
	*failure += assertTrue("coins same", ++(*num), pre->coins, post->coins, "pre", "post", 0);
	*failure += assertTrue("embargo # same", ++(*num), pre->embargoTokens[choice], post->embargoTokens[choice], "pre", "post", 0);
	*failure += assertTrue("hand count same", ++(*num), pre->handCount[player], post->handCount[player], "pre", "post", 0);
	*failure += assertTrue("deck count same", ++(*num), pre->deckCount[player], post->deckCount[player], "pre", "post", 0);
	*failure += assertTrue("discard count matches", ++(*num), pre->discardCount[player], post->discardCount[player], "pre", "post", 0);


	// printf("AFTER ADJUSTMENTS\n");
	// printf("discard count pre=%d, post=%d\n", pre->discardCount[player], post->discardCount[player]);
	// printf("deck count pre=%d, post=%d\n", pre->deckCount[player], post->deckCount[player]);
	// printf("hand count pre=%d, post=%d\n", pre->handCount[player], post->handCount[player]);
	// printf("top card in hand pre=%d, post=%d\n", pre->hand[player][pre->handCount[player]-1], post->hand[player][post->handCount[player]-1]);
	// printf("top card in deck pre=%d, post=%d\n", pre->deck[player][pre->deckCount[player]-1], post->deck[player][post->deckCount[player]-1]);
	// printf("top card in discard pre=%d, post=%d\n", pre->discard[player][pre->discardCount[player]-1], post->discard[player][post->discardCount[player]-1]);
}


int runTest(int num, int * failure, int special) {
	/* variables */
	int result,
		cd = 0,
		temphand[MAX_HAND],
		tmpsize = 0,
		dtreas = 0,
		i,
		p = 2, // current player max
		player = getRandom(p),
		choice = getRandom(treasure_map),
		handpos;
	struct gameState * g;
	struct gameState * pre;

	/* generate inputs */
	// generate random gamestate & player
	g = newGame();
	pre = newGame();
	// create decks for the player 
	createRandomStateEm(g, player, choice, special);
	handpos = getRandom(g->handCount[player]-1);
	// put an embargo card on top of player's hand
	g->hand[player][handpos] = embargo;
	// printf("coins=%d\n", g->coins);
	g->whoseTurn = player;
	memcpy(pre, g, sizeof(struct gameState));
	// run the function
	result = playEmbargo(g, choice, player, handpos);
	// printf("deckCount=%d\n", g->deckCount[player]);
	// 
	// check the results
	
	checkCard(result, failure, &num, player, choice, handpos, special, g, pre);
	// printf("got here\n");
	
	free(g);
	// printf("freeing\n");
	free(pre);
	return ++num;
}

int main()
{
	/* variables */
	int seed = 1,
		num = 1,
		failure = 0,
		i;
	// seed the random generator
	SelectStream(1);
	PutSeed((long)seed);

	// initiate test
	printTestStart("playEmbargo");
	for (i = 0; i < NUM_RUNS; i++)
	{
		num = runTest(num, &failure, 0);
	}

	// if time add any special cases
	printf("**SUPPLY COUNT EMPTY\n");
	num = runTest(num, &failure, 1);
	

	printTestEnd("playEmbargo", failure, --num);
	return 0;
}
