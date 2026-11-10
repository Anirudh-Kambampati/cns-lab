/* "Write a C program to generate the truth table for the XOR (⊕) operation on 4-bit binary numbers."*/
#include <stdio.h>

// Function to print binary representation of a number
void print_binary(int num, int bits) {
    for (int i = bits - 1; i >= 0; i--) {
        printf("%d", (num >> i) & 1);
    }
}

// Function to generate the XOR table for 4-bit numbers
void xor_table(int bits) {
    printf("\n A      ⊕      B   |  Result\n");
    printf("--------------------------------\n");

    for (int a = 0; a < (1 << bits); a++) {
        for (int b = 0; b < (1 << bits); b++) {
            // Print binary representation of A and B
            print_binary(a, bits);
            printf("  ⊕  ");
            print_binary(b, bits);
            printf("  |  ");
            
            // Print binary result of A ⊕ B
            print_binary(a ^ b, bits);
            printf("\n");
        }
    }
}

int main() {
    int bits = 4;  // Change to 8 for full byte XOR
    xor_table(bits);
    return 0;
}

