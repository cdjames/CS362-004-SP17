#include "test_helpers.h"

void assertTrue(int left, int right, const char* lmsg, const char* rmsg, int reverse) {
	const char* msg;

	if (reverse){
		if (left != right)
			msg = "PASSED";
		else
			msg = "FAILED";
	} else {
		if (left == right)
			msg = "PASSED";
		else
			msg = "FAILED";
	}

	printf("%s: %s = %d, %s = %d\n", msg, lmsg, left, rmsg, right);
}