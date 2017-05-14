/*********************************************************************
** Author: Collin James
** Date: 04-29-17
** Description: Supporting functions for a random tester for dominion.c
*********************************************************************/
#ifndef _TEST_HELPERS_H
#define _TEST_HELPERS_H

#include <stdio.h>
#include <math.h>
#include "rngs.h"
#include "dominion.h"



int assertTrue(const char* test, int num, int left, int right, const char* lmsg, const char* rmsg, int reverse);
void printTestStart(const char* name);

void printTestEnd(const char* name, int fail, int tests);

int getRandom(const int max);

void createRandomState(struct gameState * g, int * pl, int special);
void createRandomStateSH(struct gameState * g, int pl, int special);
void createRandomStateEm(struct gameState * g, int pl, int choice, int special);

#endif
