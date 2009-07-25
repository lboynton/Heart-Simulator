#include <stdio.h>
#include <math.h>

#define NX 89   /* Dimensions of box enclosing heart geometry */
#define NY 86
#define NZ 79

#define INHEART 255 /* Used in geometry files to denote whether point */
#define INSPACE 0   /* in box is within or outside myocardium */

int main(void)
{
  char point;
  int i,j,k,num = 0;
  FILE *infile;

  /* Build heart geometry and coupling */
  infile = fopen("heart_lattice", "r");

  getw(infile); 
  getw(infile); 
  getw(infile);
  for (i=0;i<NX;i++)
    for (j=0;j<NY;j++)
      for (k=0;k<NZ;k++) {
         point = getc(infile);
         if (point == (char) INHEART) 
	    // print out a vetical slice of the heart
	    if(k==40)
            printf("%d %d\n", i, j);
      }  
} 
