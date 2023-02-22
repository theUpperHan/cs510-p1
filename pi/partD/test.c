#include "stdio.h"

void scope1();
void scope2();
void scope3();
void scope4();
void scope5();
void scope6();

void A();
void B();
void C();
void D();



int main() {
  scope1();
  scope2();
  scope3();
  scope4();
  scope5();
  scope6();
  
  return 0;
}

void scope1() {
  printf("A\n");
  printf("A\n");
  printf("A\n");
  printf("A\n");
}

void scope2() {
  printf("A\n");
  printf("A\n");
  printf("A\n");
}

void scope3() {
  printf("A\n");
  printf("A\n");
}

void scope4() {
  printf("A\n");
  printf("A\n");
  printf("A\n");
}

void scope5() {
  printf("A\n");
  printf("A\n");
  printf("A\n");
}

void scope6() {
  printf("A\n");
  printf("A\n");
}

void A() {
  printf("A\n");
}

void B() {
  printf("B\n");
}

void C() {
  printf("C\n");
}

void D() {
  printf("D\n");
}