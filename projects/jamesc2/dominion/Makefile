CFLAGS = -Wall -fpic -coverage -lm

rngs.o: rngs.h rngs.c
	gcc -c rngs.c -g  $(CFLAGS)

dominion.o: dominion.h dominion.c rngs.o
	gcc -fprofile-arcs -ftest-coverage -c dominion.c -g  $(CFLAGS)

playdom: dominion.o playdom.c
	gcc -o playdom playdom.c -g dominion.o rngs.o $(CFLAGS)

testDrawCard: testDrawCard.c dominion.o rngs.o
	gcc -o testDrawCard -g  testDrawCard.c dominion.o rngs.o $(CFLAGS)

badTestDrawCard: badTestDrawCard.c dominion.o rngs.o
	gcc -o badTestDrawCard -g  badTestDrawCard.c dominion.o rngs.o $(CFLAGS)

testBuyCard: testDrawCard.c dominion.o rngs.o
	gcc -o testDrawCard -g  testDrawCard.c dominion.o rngs.o $(CFLAGS)

testAll: dominion.o testSuite.c
	gcc -o testSuite testSuite.c -g  dominion.o rngs.o $(CFLAGS)

interface.o: interface.h interface.c
	gcc -c interface.c -g  $(CFLAGS)

randomtestadventurer: test_helpers.o dominion.o rngs.o
	gcc -o randomtestadventurer randomtestadventurer.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

randomtestcard1: test_helpers.o dominion.o rngs.o
	gcc -o randomtestcard1 randomtestcard1.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

randomtestcard2: test_helpers.o dominion.o rngs.o
	gcc -o randomtestcard2 randomtestcard2.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

test_helpers.o: rngs.o test_helpers.h dominion.h
	gcc -c test_helpers.c -g  $(CFLAGS)

unittest1: test_helpers.o dominion.o rngs.o
	gcc -o unittest1 unittest1.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

unittest2: test_helpers.o dominion.o rngs.o
	gcc -o unittest2 unittest2.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

unittest3: test_helpers.o dominion.o rngs.o
	gcc -o unittest3 unittest3.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

unittest4: test_helpers.o dominion.o rngs.o
	gcc -o unittest4 unittest4.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

cardtest1: test_helpers.o dominion.o rngs.o
	gcc -o cardtest1 cardtest1.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

cardtest2: test_helpers.o dominion.o rngs.o
	gcc -o cardtest2 cardtest2.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

cardtest3: test_helpers.o dominion.o rngs.o
	gcc -o cardtest3 cardtest3.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

cardtest4: test_helpers.o dominion.o rngs.o
	gcc -o cardtest4 cardtest4.c -g test_helpers.o dominion.o rngs.o $(CFLAGS)

test_helpers.o: test_helpers.h
	gcc -c test_helpers.c -g  $(CFLAGS)

runtests: testDrawCard 
	./testDrawCard &> unittestresult.out
	gcov dominion.c >> unittestresult.out
	cat dominion.c.gcov >> unittestresult.out

randomtestadventurer.out: randomtestadventurer dominion.o 
	./randomtestadventurer &> randomtestadventurer.out
	gcov dominion.c >>  randomtestadventurer.out
	cat dominion.c.gcov >> randomtestadventurer.out
	gcov -b dominion.c >> randomtestadventurer.out
	cat dominion.c.gcov >> randomtestadventurer.out

randomtestcard1.out: randomtestcard1 dominion.o 
	./randomtestcard1 &> randomtestcard1.out
	gcov dominion.c >>  randomtestcard1.out
	cat dominion.c.gcov >> randomtestcard1.out
	gcov -b dominion.c >> randomtestcard1.out
	cat dominion.c.gcov >> randomtestcard1.out

randomtestcard2.out: randomtestcard2 dominion.o 
	./randomtestcard2 &> randomtestcard2.out
	gcov dominion.c >>  randomtestcard2.out
	cat dominion.c.gcov >> randomtestcard2.out
	gcov -b dominion.c >> randomtestcard2.out
	cat dominion.c.gcov >> randomtestcard2.out

all.out: randomtestadventurer.out randomtestcard1.out randomtestcard2.out dominion.o 
	cat randomtestadventurer.out > randomtestresults.out
	cat randomtestcard1.out >> randomtestresults.out
	cat randomtestcard2.out >> randomtestresults.out

unittestresults.out: unittest1 unittest2 unittest3 unittest4 cardtest1 cardtest2 cardtest3 cardtest4 dominion.o 
	./unittest1 &> unittestresults.out
	./unittest2 &>> unittestresults.out
	./unittest3 &>> unittestresults.out
	./unittest4 &>> unittestresults.out
	./cardtest1 &>> unittestresults.out
	./cardtest2 &>> unittestresults.out
	./cardtest3 &>> unittestresults.out
	./cardtest4 &>> unittestresults.out
	gcov -b dominion.c >> unittestresults.out
	cat dominion.c.gcov >> unittestresults.out

player: player.c interface.o
	gcc -o player player.c -g  dominion.o rngs.o interface.o $(CFLAGS)

all: playdom player testDrawCard testBuyCard badTestDrawCard

clean:
	rm -f *.o randomtestadventurer randomtestcard1 randomtestcard2 cardtest1 cardtest2 cardtest3 cardtest4 unittest1 unittest2 unittest3 unittest4 playdom.exe playdom test.exe test player player.exe testInit testInit.exe *.gcov *.gcda *.gcno *.so
