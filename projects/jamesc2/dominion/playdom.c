#include "dominion.h"
#include <stdio.h>
#include "rngs.h"
#include <stdlib.h>



int main (int argc, char** argv) {
  struct gameState G;
  int k[10] = {adventurer, gardens, embargo, village, minion, mine, cutpurse,
           sea_hag, tribute, smithy};
  int seedNum = 5;

  printf ("Starting game.\n");

  if (argc == 2)
  {
    seedNum = atoi(argv[1]);
  }
  initializeGame(2, k, seedNum, &G);

  int money = 0;
  int smithyPos = -1;
  int adventurerPos = -1;
  int embargoPos = -1;
  int seaHagPos = -1;
  int cutpursePos = -1;
  int i=0;

  int numSmithies = 0;
  int numEmbargos = 0;
  int numSeaHags = 0;
  int numCutpurses = 0;
  int numAdventurers = 0;

  int buySmithy = 1;
  int buyAdv = 1;
  int buyCutpurse = 0;

  while (!isGameOver(&G)) {
    money = 0;
    smithyPos = -1;
    adventurerPos = -1;
    embargoPos = -1;
    seaHagPos = -1;
    cutpursePos = -1;
    for (i = 0; i < numHandCards(&G); i++) {
      if (handCard(i, &G) == copper)
    money++;
      else if (handCard(i, &G) == silver)
    money += 2;
      else if (handCard(i, &G) == gold)
    money += 3;
      else if (handCard(i, &G) == smithy)
    smithyPos = i;
      else if (handCard(i, &G) == adventurer)
    adventurerPos = i;
      else if (handCard(i, &G) == embargo)
    embargoPos = i;
      else if (handCard(i, &G) == sea_hag)
    seaHagPos = i;
      else if (handCard(i, &G) == cutpurse)
    cutpursePos = i;
    }

    if (whoseTurn(&G) == 0) {
      if (smithyPos != -1) {
        printf("0: smithy played from position %d\n", smithyPos);
        playCard(smithyPos, -1, -1, -1, &G);
        printf("smithy played.\n");
        money = 0;
        i=0;
        while(i<numHandCards(&G)){
          if (handCard(i, &G) == copper){
            playCard(i, -1, -1, -1, &G);
            money++;
          }
          else if (handCard(i, &G) == silver){
            playCard(i, -1, -1, -1, &G);
            money += 2;
          }
          else if (handCard(i, &G) == gold){
            playCard(i, -1, -1, -1, &G);
            money += 3;
          }
          i++;
        }
      }
      else if (embargoPos != -1) {
        printf("0: embargo played from position %d\n", embargoPos);
        playCard(embargoPos, -1, -1, -1, &G);
        printf("embargo played.\n");
        money = 0;
        i=0;
        while(i<numHandCards(&G)){
          if (handCard(i, &G) == copper){
            playCard(i, -1, -1, -1, &G);
            money++;
          }
          else if (handCard(i, &G) == silver){
            playCard(i, -1, -1, -1, &G);
            money += 2;
          }
          else if (handCard(i, &G) == gold){
            playCard(i, -1, -1, -1, &G);
            money += 3;
          }
          i++;
        }
      }

      if (money >= 8) {
        printf("0: bought province\n");
        buyCard(province, &G);
      }
      else if (money >= 6) {
        printf("0: bought gold\n");
        buyCard(gold, &G);
      }
      else if (money >= 4) {
        if (numEmbargos < 2 && !buySmithy) {
          printf("0: bought embargo\n");
          buyCard(embargo, &G);
          numEmbargos++;
          buySmithy = 1;
        } 
        else if (numSmithies < 2 && buySmithy) {
          printf("0: bought smithy\n");
          buyCard(smithy, &G);
          numSmithies++;
          buySmithy = 0;
        }
      }
      else if (money >= 3) {
        printf("0: bought silver\n");
        buyCard(silver, &G);
      }

      printf("0: end turn\n");
      endTurn(&G);
    }
    else {
      if (adventurerPos != -1) {
        printf("1: adventurer played from position %d\n", adventurerPos);
        playCard(adventurerPos, -1, -1, -1, &G);
        money = 0;
        i=0;
        while(i<numHandCards(&G)){
          if (handCard(i, &G) == copper){
            playCard(i, -1, -1, -1, &G);
            money++;
          }
          else if (handCard(i, &G) == silver){
            playCard(i, -1, -1, -1, &G);
            money += 2;
          }
          else if (handCard(i, &G) == gold){
            playCard(i, -1, -1, -1, &G);
            money += 3;
          }
          i++;
        }
      }
      else if (seaHagPos != -1) {
        printf("1: sea hag played from position %d\n", seaHagPos);
        playCard(seaHagPos, -1, -1, -1, &G);
        money = 0;
        i=0;
        while(i<numHandCards(&G)){
          if (handCard(i, &G) == copper){
            playCard(i, -1, -1, -1, &G);
            money++;
          }
          else if (handCard(i, &G) == silver){
            playCard(i, -1, -1, -1, &G);
            money += 2;
          }
          else if (handCard(i, &G) == gold){
            playCard(i, -1, -1, -1, &G);
            money += 3;
          }
          i++;
        }
      }
      else if (cutpursePos != -1) {
        printf("1: cutpurse played from position %d\n", cutpursePos);
        playCard(cutpursePos, -1, -1, -1, &G);
        money = 0;
        i=0;
        while(i<numHandCards(&G)){
          if (handCard(i, &G) == copper){
            playCard(i, -1, -1, -1, &G);
            money++;
          }
          else if (handCard(i, &G) == silver){
            playCard(i, -1, -1, -1, &G);
            money += 2;
          }
          else if (handCard(i, &G) == gold){
            playCard(i, -1, -1, -1, &G);
            money += 3;
          }
          i++;
        }
      }

      if (money >= 8) {
        printf("1: bought province\n");
        buyCard(province, &G);
      }
      else if (money >= 6) {
        if (numCutpurses < 1 && buyCutpurse) {
          printf("1: bought cutpurse\n");
          buyCard(cutpurse, &G);
          numCutpurses++;
          buyAdv = 1;
          buyCutpurse = 0;
        }
        else if (numAdventurers < 2 && buyAdv) {
          printf("1: bought adventurer\n");
          buyCard(adventurer, &G);
          numAdventurers++;
          buyAdv = 0;
        }
        else if (numSeaHags < 2 && !buyAdv){
          printf("1: bought sea hag\n");
          buyCard(sea_hag, &G);
          numSeaHags++;
          buyAdv = 1;
          buyCutpurse = 1;
        }
      }else if (money >= 6){
        printf("1: bought gold\n");
	    buyCard(gold, &G);
        }
      else if (money >= 3){
        printf("1: bought silver\n");
	    buyCard(silver, &G);
      }
      printf("1: endTurn\n");

      endTurn(&G);
    }
  } // end of While

  printf ("Finished game.\n");
  printf ("Player 0: %d\nPlayer 1: %d\n", scoreFor(0, &G), scoreFor(1, &G));

  return 0;
}
