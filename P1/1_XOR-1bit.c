/* Write a C program to generate the truth table for the XOR (⊕) operation */
#include <stdio.h>

int main() {
    printf(" A  ⊕  B  |  Result\n");
    printf("--------------------\n");

    for (int A = 0; A <= 1; A++) {
        for (int B = 0; B <= 1; B++) {
            printf(" %d  ⊕  %d  |    %d\n", A, B, A ^ B);
        }
    }
    return 0;
}

