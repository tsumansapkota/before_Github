#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <string>
#include <Windows.h>

using namespace std;  

int ftwo(void);

FILE *rout;
//FILE *rout=fopen("routine.txt","r");

int main(void)
{
	AGAIN:
	rout=fopen("routine.txt","r");
	time_t tim=time(0);
	char *times=ctime(&tim);
	char nowtim[40];
	struct tm *tims;
	tims=localtime(&tim);
	strcpy(nowtim,times);
	char wday[3];
	char fwday[3];
	sprintf(wday,"%c%c%c",nowtim[0],nowtim[1],nowtim[2]);
	printf("%s\t",wday);
	while(!feof(rout))
	{
		fscanf(rout,"%s",fwday);
		if(!strcmp(fwday,wday)) break;
	}
	
	int hh,mm, ss, h[8], a, state, hour, minute, h5, m5, rhr, rmn;
	
	hh=tims->tm_hour;
	mm=tims->tm_min;
	ss=tims->tm_sec;
	
	for(a=0; a<=8;a++)
	{
	h[a]=ftwo();
	printf("%d\t",h[a]);		
	}
	printf("\n%d\t%d\t%d\n",hh,mm,ss);
	fseek(rout,2L,1);
	if(!strcmp(wday,"Sat")) fseek(rout,3L,0);
	h5=ftwo();
	m5=ftwo();
	if ((hh == h[0] and mm < h[1]) or hh < h[0]){
    printf("i m at 1");
    state = 1;
    hour = h[0];
    minute = h[1];
    
	} 
	else if((hh == h[2] and mm < h[3]) or hh < h[2] ){
    printf("i m at 2");
    state = 0;
    hour = h[2];
    minute = h[3]	;
    
	} 
	else if((hh == h[4] and mm < h[5]) or hh < h[4] ){
    printf("i m at 3");
    state = 1;
    hour = h[4];
    minute = h[5];

    
	} else if ((hh == h[6] and mm < h[7]) or hh < h[6] ){
    printf("i m at 4");
    state = 0;
    hour = h[6];
    minute = h[7];
    
    }
	else
	{
    printf("i m at 5\n");
    state = 1;
    hour=h5+24;
    minute=m5;
    
	}
	
	if(minute>mm){
  	rhr=hour-hh;
	rmn=minute-mm;
	}
    else{
	rhr=hour-hh-1;
	rmn=minute-mm+60;
	}	
	
	printf("\n%d   %d   %d\n", hour, minute, state);
	printf("%d   %d \n", rhr, rmn);
	FILE *rem;
	rem=fopen("remain.txt","w");
	
	fprintf(rem,"[Variables]\nRem=%02d : %02d\nsch1=%02d : %02d - %02d : %02d \nsch2=%02d : %02d - %02d : %02d\nimg= %d.png",rhr,rmn,h[0],h[1],h[2],h[3],h[4],h[5],h[6],h[7],state);
	//printf("%d",rema);
	fclose(rem);
	//goto AGAIN;
	return 0;
	
}



ftwo()
{
	int k;
	char h,i, j[2];
	fseek(rout,1L,1);
	h=fgetc(rout);
	i=fgetc(rout);
	sprintf(j,"%c%c",h,i);
	int hour=atoi(j);
	return hour;

}



