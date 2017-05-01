/*********************************************************************
** Author: Collin James
** Date: April 30, 2017
** Description: A random tester
*********************************************************************/

#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<time.h>

char inputChar()
{   
    /* fast method: just look through necessary characters */
    // char master[] = "[({ ax})]";
    // int ran = (rand() % 9);
    // return master[ran];

    /* slow method: look characters from first needed to last needed */
    char ran = (rand() % (126-32))+32;
    return ran;
}

char *inputString()
{
  char randChar(){
    // return a random lowercase letter
    return (rand() % (117-101))+101;
  }
    char *mystr = (char *) malloc(sizeof(char) * 5);
    
    /* slow method: look characters from first needed to last needed 
        example # iterations: 13474 - 1904061 (w/ slow inputChar()) */
    char one = randChar();
    char two = randChar();
    char three = randChar();
    char four = randChar();
    char five = randChar();

    mystr[0] = one;
    mystr[1] = two;
    mystr[2] = three;
    mystr[3] = four;
    mystr[4] = five;

    /* fast method: just look through necessary characters 
        example # iterations: 1852 (w/ fast inputChar()) */
    // char master[] = "rest";
    // mystr[0] = master[(rand() % 4)];
    // mystr[1] = master[(rand() % 4)];
    // mystr[2] = master[(rand() % 4)];
    // mystr[3] = master[(rand() % 4)];
    // mystr[4] = master[(rand() % 4)];
    // mystr[5] = '\0';
    return mystr;
}

void testme()
{
  int tcCount = 0;
  char *s;
  char c;
  int state = 0;
  while (1)
  {
    tcCount++;
    c = inputChar();
    s = inputString();
    printf("%s\n", s);
    printf("Iteration %d: c = %c, s = %s, state = %d\n", tcCount, c, s, state);

    if (c == '[' && state == 0) state = 1;
    if (c == '(' && state == 1) state = 2;
    if (c == '{' && state == 2) state = 3;
    if (c == ' '&& state == 3) state = 4;
    if (c == 'a' && state == 4) state = 5;
    if (c == 'x' && state == 5) state = 6;
    if (c == '}' && state == 6) state = 7;
    if (c == ')' && state == 7) state = 8;
    if (c == ']' && state == 8) state = 9;
    if (s[0] == 'r' && s[1] == 'e'
       && s[2] == 's' && s[3] == 'e'
       && s[4] == 't' && s[5] == '\0'
       && state == 9)
    {
      printf("error ");
      exit(200);
    }
  }
}


int main(int argc, char *argv[])
{
    srand(time(NULL));
    testme();
    return 0;
}
