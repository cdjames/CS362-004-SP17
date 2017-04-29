#include "test_helpers.h"

int assertTrue(const char* test, int num, int left, int right, const char* lmsg, const char* rmsg, int reverse) {
	const char* msg;
	int failure = 0;
	if (reverse){
		if (left != right)
			msg = "    PASSED TEST ";
		else {
			msg = "    FAILED TEST ";
			failure = 1;
		}
	} else {
		if (left == right)
			msg = "    PASSED TEST ";
		else {
			msg = "    FAILED TEST ";
			failure = 1;
		}
	}

	printf("%s%d, %s: %s = %d, %s = %d\n", msg, num, test, lmsg, left, rmsg, right);

	return failure;
}

void printTestStart(const char* name) {
	printf("\n######################### Testing %s #########################\n\n", name);
}

void printTestEnd(const char* name, int fail) {
	if(fail)
		printf("\n:( :( :( FAILURE - %s\n\n", name);
	else
		printf("\n:) :) :) SUCCESS - %s\n\n", name);
}