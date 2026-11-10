#include <stdio.h>
#include <string.h>

int main() {
    char str[] = "Hello World";
    int len = strlen(str);
    char result[len + 1];

    printf("Original String : %s\n", str);
    printf("XOR with 0 Result: ");

    for (int i = 0; i < len; i++) {
        result[i] = str[i] ^ 0;
        printf("%c", result[i]);
    }
    result[len] = '\0';

    printf("\n\n--- Bitwise Details ---\n");
    printf("Char\tASCII\tBinary\t\tXOR 0\t\tResult\n");
    for (int i = 0; i < len; i++) {
        printf("'%c'\t%d\t", str[i], str[i]);
        for (int b = 7; b >= 0; b--)
            printf("%d", (str[i] >> b) & 1);
        printf("\t");
        for (int b = 7; b >= 0; b--)
            printf("%d", (0 >> b) & 1);
        printf("\t");
        for (int b = 7; b >= 0; b--)
            printf("%d", (result[i] >> b) & 1);
        printf("\n");
    }

    return 0;
}
