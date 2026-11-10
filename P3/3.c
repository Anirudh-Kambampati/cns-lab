/* Write a C program that contains a string (char pointer) with a value ‘Hello world’. The program should XOR each character in this string with 0 and display the result */
#include <stdio.h>

int main() {
    // Declare the string with the value "Hello world"
    char str[] = "Hello world";
    
    // XOR each character with 0 and print the result
    printf("Original string: %s\n", str);
    printf("XOR with 0 result:\n");
    for (int i = 0; str[i] != '\0'; i++) {
        char result = str[i] ^ 0; // XOR with 0
        printf("Character: %c, XOR result: %c\n", str[i], result);
    }
    
    return 0;
}

