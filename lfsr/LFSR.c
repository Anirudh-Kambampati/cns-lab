/* Write a program on a 5-bit Linear Feedback Shift Register (LFSR) using a primitive polynomial.
Primitive Polynomial Choice : For a 5-bit LFSR, a common primitive polynomial is: x^5+x^2+1
which corresponds to feedback taps at bit positions 5 and 2.*/

#include <stdio.h>

#define LFSR_SIZE 5  // 5-bit LFSR

// Function to print the LFSR state
void print_lfsr(int lfsr[]) {
    for (int i = 0; i < LFSR_SIZE; i++) {
        printf("%d", lfsr[i]);
    }
}

// Function to update LFSR using x^5 + x^2 + 1
void lfsr_5bit(int lfsr[], int cycles) {
    printf("Cycle\tState\n");

    for (int i = 0; i < cycles; i++) {
        printf("%2d\t", i + 1);
        print_lfsr(lfsr);
        printf("\n");

        // Compute the feedback bit (XOR of bit 5 and bit 2)
        int feedback = lfsr[4] ^ lfsr[1];

        // Shift LFSR left and insert feedback bit at LSB
        for (int j = LFSR_SIZE - 1; j > 0; j--) {
            lfsr[j] = lfsr[j - 1];
        }
        lfsr[0] = feedback;
    }
}

int main() {
    int lfsr[LFSR_SIZE] = {0, 0, 0, 0, 1};  // Seed (00001)
    int cycles = 33;  // Number of cycles to run

    lfsr_5bit(lfsr, cycles);
    return 0;
}






/*
Explanation

    LFSR State
        The LFSR is stored in a 5-bit register (lfsr).
        The feedback taps are at bit positions 5 and 2 (polynomial: x5+x2+1x5+x2+1).

    Bitwise Feedback Calculation
        (lfsr >> 4): Extracts bit 5.
        (lfsr >> 1): Extracts bit 2.
        XOR them to get the new bit.

    Shifting Operation
        Left shift the LFSR.
        Insert the new feedback bit at LSB.
        Ensure it remains 5-bit using & LFSR_MASK.

    Output Format
        Displays the binary state of the LFSR in each cycle.
*/


/*
Sample Output

Cycle	State
 1	    00001
 2	    00010
 3	    00100
 4	    01000
 5	    10001
 6	    00011
 7	    00110
 8	    01100
 9	    11001
10	    10011
11	    00111
12	    01110
13	    11101
14	    11011
15	    10111
16	    01111
17	    11110
18	    11100
19	    11000
20	    10000
*/

/*
Notes

    The seed should be nonzero; otherwise, the LFSR will lock in state 0.
    The sequence will repeat after 31 cycles, as the maximum length of a 5-bit LFSR is 25−1=3125−1=31 (excluding all zeros).
*/
