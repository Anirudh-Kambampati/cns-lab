/* Write a C program that contains a string (char pointer) with a value ‘Hello world’. The program should AND or and XOR each character in this string with 127 and display the result.  */

#include <stdio.h>
#include <string.h>
int main() {
    // Initialize the string
    char *str = "Hello World";
    char and_result[50]; // Array to store the AND results
    char xor_result[50]; // Array to store the XOR results

    // Process each character in the string
    for (int i = 0; str[i] != '\0'; i++) {
        and_result[i] = str[i] & 127; // Perform AND operation with 127
        xor_result[i] = str[i] ^ 127; // Perform XOR operation with 127
    }

    // Null-terminate the result strings
    and_result[strlen(str)] = '\0';
    xor_result[strlen(str)] = '\0';

    // Display the original string
    printf("Original String: %s\n", str);

    // Display the results of the AND operation
    printf("Result after AND with 127:\n");
    for (int i = 0; and_result[i] != '\0'; i++) {
        printf("%c", and_result[i]);
    }
    printf("\n");

    // Display the results of the XOR operation
    printf("\nResult after XOR with 127:\n");
    for (int i = 0; xor_result[i] != '\0'; i++) {
        printf("%X ", xor_result[i]);
       // printf("%c ", xor_result[i]);
    }
    printf("\n");

    return 0;
}

