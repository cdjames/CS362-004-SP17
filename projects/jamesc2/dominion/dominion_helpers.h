#ifndef _DOMINION_HELPERS_H
#define _DOMINION_HELPERS_H

#include "dominion.h"

int drawCard(int player, struct gameState *state);
int updateCoins(int player, struct gameState *state, int bonus);
int discardCard(int handPos, int currentPlayer, struct gameState *state, 
		int trashFlag);
int gainCard(int supplyPos, struct gameState *state, int toFlag, int player);
int getCost(int cardNumber);
int cardEffect(int card, int choice1, int choice2, int choice3, 
	       struct gameState *state, int handPos, int *bonus);

int playSmithy(struct gameState *state, int currentPlayer, int handPos);

int playAdventurer(struct gameState *state, int currentPlayer, int * cardDrawn, int *temphand, int * drawntreasure);

int playCutpurse(struct gameState *state, int currentPlayer, int handPos);

int playSeaHag(struct gameState *state, int currentPlayer);

int playEmbargo(struct gameState *state, int choice1, int currentPlayer, int handPos);



#endif
