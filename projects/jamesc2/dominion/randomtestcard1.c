/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: A random tester for playSeaHag() from dominion.c
*********************************************************************/

#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>
#include "dominion.h"
#include "dominion_helpers.h"
#include "test_helpers.h"
#include "rngs.h"

#define NUM_RUNS 1000


void checkCard(
		int result, 
		int * failure,
		int * num,
		int player,
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
	int i;
	(*num)--;
	for(i=0; i < MAX_PLAYERS; i++) {
		/* for each player other than current, check that top card of pre deck is top card in post discard
		and also check that top card of post deck is a curse card. Deck count should be the same*/
		if(i != player) {
			if(pre->deckCount[i] > 0) { // can simulate discard and drawing
				// put top card of deck on the discard pile
				pre->discard[i][pre->discardCount[i]++] = pre->deck[i][pre->deckCount[i]-1];

				// put a curse card on top of deck
				pre->deck[i][pre->deckCount[i]-1] = curse;
			}
			else { // need to simulate shuffle; not sure how else to do this besides copying all of the following
				// printf("got here\n");
				// copy deck and discard piles from post
				memcpy(pre->deck[i], post->deck[i], sizeof(int) * (MAX_DECK));
				memcpy(pre->discard[i], post->discard[i], sizeof(int) * (MAX_DECK));
				// memcpy(pre->hand[player], post->hand[player], sizeof(int) * (MAX_DECK));
				
				pre->deckCount[i] = pre->discardCount[i]; // should be equal to the entirety of the discard pile before playing
				pre->discardCount[i] = 1; // should be only 1 card, discarded from shuffled deck
			}

			printf("**PLAYER %d\n", i);
			*failure += assertTrue("other player discard count same", ++(*num), pre->discardCount[i], post->discardCount[i], "pre", "post", 0);
			*failure += assertTrue("other player deck count same", ++(*num), pre->deckCount[i], post->deckCount[i], "pre", "post", 0);
			*failure += assertTrue("other player hand count same", ++(*num), pre->handCount[i], post->handCount[i], "pre", "post", 0);
			*failure += assertTrue("other player top card in deck same", ++(*num), pre->deck[i][pre->deckCount[i]-1], post->deck[i][post->deckCount[i]-1], "pre", "post", 0);
			*failure += assertTrue("other player top card in deck is curse", ++(*num), post->deck[i][post->deckCount[i]-1], curse, "deck top", "curse", 0);
			*failure += assertTrue("other player top card in discard same", ++(*num), pre->discard[i][pre->discardCount[i]-1], post->discard[i][post->discardCount[i]-1], "pre", "post", 0);
		}
	}

	// printf("AFTER ADJUSTMENTS\n");
	// printf("discard count pre=%d, post=%d\n", pre->discardCount[player], post->discardCount[player]);
	// printf("deck count pre=%d, post=%d\n", pre->deckCount[player], post->deckCount[player]);
	// printf("hand count pre=%d, post=%d\n", pre->handCount[player], post->handCount[player]);
	// printf("top card in hand pre=%d, post=%d\n", pre->hand[player][pre->handCount[player]-1], post->hand[player][post->handCount[player]-1]);
	// printf("top card in deck pre=%d, post=%d\n", pre->deck[player][pre->deckCount[player]-1], post->deck[player][post->deckCount[player]-1]);
	// printf("top card in discard pre=%d, post=%d\n", pre->discard[player][pre->discardCount[player]-1], post->discard[player][post->discardCount[player]-1]);

	// get results
	printf("**CURRENT PLAYER\n");
	*failure += assertTrue("Return is 0", ++(*num), result, 0, "returned", "expected", 0);
	*failure += assertTrue("Pre & post is same", ++(*num), (memcmp( pre, post, sizeof(struct gameState))), 0, "memcmp pre/post", "expected", 0);
	*failure += assertTrue("current player discard count matches", ++(*num), pre->discardCount[player], post->discardCount[player], "pre", "post", 0);
	*failure += assertTrue("current player deck count same", ++(*num), pre->deckCount[player], post->deckCount[player], "pre", "post", 0);
	*failure += assertTrue("current player hand count same", ++(*num), pre->handCount[player], post->handCount[player], "pre", "post", 0);
}


int runTest(int num, int * failure, int special) {
	/* variables */
	int player,
		result,
		i;
	struct gameState * g;
	struct gameState * pre;

	/* generate inputs */
	// generate random gamestate & player
	g = newGame();
	pre = newGame();
	// randomize game
	for (i = 0; i < sizeof(struct gameState); i++)
	{
		( ((char*)g)[i] = getRandom(256) );
	}

	// create decks for each player
	for (i = 0; i < MAX_PLAYERS; i++)
	{
		createRandomStateSH(g, i, special);
	}
	g->whoseTurn = player = getRandom(MAX_PLAYERS-1);
	memcpy(pre, g, sizeof(struct gameState));
	// run the function
	result = playSeaHag(g, player);
	// printf("deckCount=%d\n", g->deckCount[player]);
	// 
	// check the results
	
	checkCard(result, failure, &num, player, special, g, pre);
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
	printTestStart("playSeaHag");
	for (i = 0; i < NUM_RUNS; i++)
	{
		if(i%100){
			printf("**DECK EMPTY\n");
			num = runTest(num, &failure, 1);
		}
		else
			num = runTest(num, &failure, 0);
	}

	// if time add any special cases

	printTestEnd("playSeaHag", failure, --num);
	return 0;
}
