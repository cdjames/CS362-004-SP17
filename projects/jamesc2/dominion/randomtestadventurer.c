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

#define NUM_RUNS 3000

void adventurerGetTreasure(struct gameState * g, int p) {
	// remove treasure cards from deck and put in hand; inc hand/dec deck
	int drawntreasure = 0,
		cardDrawn,
		temphand[MAX_DECK],
		size = 0,
		i;
	// printf("deckCount=%d\n", g->deckCount[p]);
	while(drawntreasure<2){
		// printf("deckCount=%d\n", g->deckCount[p]);
		// going to draw a card, so decrement deckCount
		// top card of index is most recently drawn card.
	    cardDrawn = g->deck[p][--g->deckCount[p]]; 
	    // printf("cardDrawn=%d\n", cardDrawn);
	    if (cardDrawn == copper || cardDrawn == silver || cardDrawn == gold) {
	      // add drawn card to hand and increment drawntreasure
	      g->hand[p][g->handCount[p] + drawntreasure++] = cardDrawn;
		}
		else {
			temphand[size++] = cardDrawn;
		}
	  }
	// put correct cards in discard pile
	for (i = size-1; i >= 0; i--)
	{
		// printf("cardDrawn=%d\n", temphand[i]);
		g->discard[p][g->discardCount[p]++] = temphand[i]; // discard all cards in play that have been drawn
	}
}

void checkCard(
		int result, 
		int * failure,
		int * num,
		int player,
		int * tmphand,
		int tmpsize,
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
	if(pre->deckCount[player] > post->deckCount[player]) { // can simulate discard and drawing

		// create an equal discard pile and count
		// adventurerDiscard(pre, player, tmphand, tmpsize);
		// add correct cards to hand and decrement deck count
		adventurerGetTreasure(pre, player);
	}
	else { // need to simulate shuffle; not sure how else to do this besides copying all of the following
		// printf("got here\n");
		// copy deck and discard piles from post
		memcpy(pre->deck[player], post->deck[player], sizeof(int) * (MAX_DECK));
		memcpy(pre->discard[player], post->discard[player], sizeof(int) * (MAX_DECK));
		memcpy(pre->hand[player], post->hand[player], sizeof(int) * (MAX_DECK));
		// get correct counts for piles
		// pre->discardCount[player] += tmpsize;
		
		pre->discardCount[player] = post->discardCount[player];
		// pre->deckCount[player] -= (tmpsize + 2);
		pre->deckCount[player] = post->deckCount[player];
	}
	// increase handcount by 2 (in all cases)
	pre->handCount[player] += 2;
	// printf("AFTER ADJUSTMENTS\n");
	// printf("discard count pre=%d, post=%d\n", pre->discardCount[player], post->discardCount[player]);
	// printf("deck count pre=%d, post=%d\n", pre->deckCount[player], post->deckCount[player]);
	// printf("hand count pre=%d, post=%d\n", pre->handCount[player], post->handCount[player]);
	// printf("top card in hand pre=%d, post=%d\n", pre->hand[player][pre->handCount[player]-1], post->hand[player][post->handCount[player]-1]);
	// printf("top card in deck pre=%d, post=%d\n", pre->deck[player][pre->deckCount[player]-1], post->deck[player][post->deckCount[player]-1]);
	// printf("top card in discard pre=%d, post=%d\n", pre->discard[player][pre->discardCount[player]-1], post->discard[player][post->discardCount[player]-1]);
	// int i;
	// for (i = 0; i < post->discardCount[player]; i++)
	// {
	// 	printf("card in discard pre=%d, post=%d\n", pre->discard[player][i], post->discard[player][i]);
	// }
	// get results
	*failure += assertTrue("Return is 0", *num, result, 0, "returned", "expected", 0);
	*failure += assertTrue("Pre & post is same", ++(*num), (memcmp( pre, post, sizeof(struct gameState))), 0, "memcmp pre/post", "expected", 0);
	*failure += assertTrue("discard count matches", ++(*num), pre->discardCount[player], post->discardCount[player], "pre", "post", 0);
	*failure += assertTrue("deck count same", ++(*num), pre->deckCount[player], post->deckCount[player], "pre", "post", 0);
	*failure += assertTrue("hand count same", ++(*num), pre->handCount[player], post->handCount[player], "pre", "post", 0);
	*failure += assertTrue("top card in hand same", ++(*num), pre->hand[player][pre->handCount[player]-1], post->hand[player][post->handCount[player]-1], "pre", "post", 0);
	*failure += assertTrue("top card in deck same", ++(*num), pre->deck[player][pre->deckCount[player]-1], post->deck[player][post->deckCount[player]-1], "pre", "post", 0);
	*failure += assertTrue("top card in discard same", ++(*num), pre->discard[player][pre->discardCount[player]-1], post->discard[player][post->discardCount[player]-1], "pre", "post", 0);
}


int runTest(int num, int * failure) {
	/* variables */
	int player,
		result,
		cd = 0,
		temphand[MAX_HAND],
		tmpsize = 0,
		dtreas = 0;
	struct gameState * g;
	struct gameState * pre;

	/* generate inputs */
	// generate random gamestate & player
	g = newGame();
	pre = newGame();
	createRandomState(g, &player);
	memcpy(pre, g, sizeof(struct gameState));
	// run the function
	result = playAdventurer(g, player, &cd, temphand, &dtreas, &tmpsize);
	// printf("deckCount=%d\n", g->deckCount[player]);
	// 
	// check the results
	// printf("got here\n");
	checkCard(result, failure, &num, player, temphand, tmpsize, g, pre);
	
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
	printTestStart("playAdventurer");
	for (i = 0; i < NUM_RUNS; i++)
	{
		num = runTest(num, &failure);
	}

	printTestEnd("playAdventurer", failure, --num);
	return 0;
}
