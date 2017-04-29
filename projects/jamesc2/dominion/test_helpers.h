#ifndef _TEST_HELPERS_H
#define _TEST_HELPERS_H

#include <stdio.h>

int assertTrue(const char* test, int left, int right, const char* lmsg, const char* rmsg, int reverse);
void printTestStart(const char* name);

void printTestEnd(const char* name, int fail);
#endif
