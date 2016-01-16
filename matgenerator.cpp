#include<stdio.h>
#include<fstream>
#include<iostream>
#include<string.h>
#include<stdlib.h>
#include<math.h>
using namespace std;
int main(int argc,char*argv[])
{
ofstream fout;
fout.open("mat.txt");
int m=atoi(argv[1]);
int n=atoi(argv[2]);
int p=atoi(argv[3]);
float a=5.0f;
for(int i=0;i<m;i++)

for(int j=0;j<n;j++)
{
float x = ((float)rand()/(float)(RAND_MAX)) * a;
fout<<"A"<<","<<i<<","<<j<<","<<x<<endl;

}

for(int j=0;j<n;j++)
for(int k=0;k<p;k++)
{
float x = ((float)rand()/(float)(RAND_MAX)) * a;
fout<<"B"<<","<<j<<","<<k<<","<<x<<endl;
}
fout.close();
}
