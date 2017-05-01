/*********************************************************************
** Author: Collin James
** Date: April 30, 2017
** Description: A random tester (explanation)
*********************************************************************/

/*

I wasn't sure how you wanted us to approach this (i.e. how random it needed
	to be), so I created two implementations: a fast one with more 
	randomness, and a slow one with less.

In the slow method, which I have chosen to submit by default, inputChar()
	produces random characters between ' ' and '}', so it gets its 
	characters from a pool of 94. inputString() produces random strings
	by choosing characters between 'e' and 't', for a pool of 16 characters.
	In my brief testing it took between 13474 and 1904061 iterations for the
	program to exit, although it could take more or less. 
	I also tried with the whole lowercase alphabet, so 26 characters. This took
	about 4543175 in my one test. Feel free to uncomment this in randChar() if you'd like.
	I had trouble remembering how to form strings correctly in C, so I got a little help from
	http://stackoverflow.com/questions/14416759/return-char-string-from-a-function#14416798

The fast method took between 277 and 1852 iterations, although it could take more or less.
	Both inputChar() and inputString() work by creating a short master string 
	from which it will pull values, either to return or to assemble a new random
	string. Please feel free to uncomment the fast fersions if you don't want to wait!

*/