/* Write a C program to perform encryption and decryption using the following algorithms a. Ceaser cipher b. Substitution cipher c. Hill Cipher */
#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAX 100

// Function prototypes
void caesarCipher();
void substitutionCipher();
void hillCipher();

int main() {
    int choice;
    printf("Choose the encryption/decryption method:\n");
    printf("1. Caesar Cipher\n");
    printf("2. Substitution Cipher\n");
    printf("3. Hill Cipher\n");
    printf("Enter your choice: ");
    scanf("%d", &choice);
    getchar(); // To consume the newline character

    switch (choice) {
        case 1:
            caesarCipher();
            break;
        case 2:
            substitutionCipher();
            break;
        case 3:
            hillCipher();
            break;
        default:
            printf("Invalid choice.\n");
    }
    return 0;
}

// Caesar Cipher
void caesarCipher() {
    char text[MAX], result[MAX];
    int key, i;

    printf("Enter text: ");
    fgets(text, MAX, stdin);
    text[strcspn(text, "\n")] = '\0'; // Remove trailing newline

    printf("Enter key (shift value): ");
    scanf("%d", &key);

    for (i = 0; text[i] != '\0'; i++) {
        if (isalpha(text[i])) {
            char base = isupper(text[i]) ? 'A' : 'a';

            result[i] = (text[i] - base + key) % 26 + base;
        } else {
            result[i] = text[i];
        }
    }
    result[i] = '\0';

    printf("Encrypted text: %s\n", result);

    // Decrypting
    for (i = 0; text[i] != '\0'; i++) {
        if (isalpha(result[i])) {
            char base = isupper(result[i]) ? 'A' : 'a';
            text[i] = (result[i] - base - key + 26) % 26 + base;
        } else {
            text[i] = result[i];
        }
    }
    text[i] = '\0';

    printf("Decrypted text: %s\n", text);
}

// Substitution Cipher
void substitutionCipher() {
    char text[MAX], encrypted[MAX], decrypted[MAX];
    char key[26], inverseKey[26];
    int i;

    printf("Enter text: ");
    fgets(text, MAX, stdin);
    text[strcspn(text, "\n")] = '\0'; // Remove trailing newline

    printf("Enter 26-character substitution key (e.g., QWERTYUIOPLKJHGFDSAZXCVBNM): ");
    fgets(key, 27, stdin);

    // Create inverse key for decryption
    for (i = 0; i < 26; i++) {
        inverseKey[key[i] - 'A'] = 'A' + i;
    }

    // Encrypt
    for (i = 0; text[i] != '\0'; i++) {
        if (isalpha(text[i])) {
            char base = isupper(text[i]) ? 'A' : 'a';
            encrypted[i] = key[text[i] - base];
        } else {
            encrypted[i] = text[i];
        }
    }
    encrypted[i] = '\0';

    printf("Encrypted text: %s\n", encrypted);

    // Decrypt
    for (i = 0; encrypted[i] != '\0'; i++) {
        if (isalpha(encrypted[i])) {
            char base = isupper(encrypted[i]) ? 'A' : 'a';
            decrypted[i] = inverseKey[encrypted[i] - base];
        } else {
            decrypted[i] = encrypted[i];
        }
    }
    decrypted[i] = '\0';

    printf("Decrypted text: %s\n", decrypted);
}

// Hill Cipher
void hillCipher() {
    int i, j, k, n = 2; // 2x2 matrix for simplicity
    int key[2][2], inverseKey[2][2], determinant = 0, textVector[2], resultVector[2];
    char text[MAX], encrypted[MAX], decrypted[MAX];

    printf("Enter text (length must be even): ");
    fgets(text, MAX, stdin);
    text[strcspn(text, "\n")] = '\0'; // Remove trailing newline

    if (strlen(text) % 2 != 0) {
        printf("Error: Text length must be even.\n");
        return;
    }

    printf("Enter 2x2 key matrix (4 integers):\n");
    for (i = 0; i < n; i++)
        for (j = 0; j < n; j++)
            scanf("%d", &key[i][j]);

    // Calculate determinant and inverse modulo 26
    determinant = key[0][0] * key[1][1] - key[0][1] * key[1][0];
    determinant = (determinant % 26 + 26) % 26; // Make positive mod 26
    int determinantInverse = -1;
    for (i = 1; i < 26; i++) {
        if ((determinant * i) % 26 == 1) {
            determinantInverse = i;
            break;
        }
    }
    if (determinantInverse == -1) {
        printf("Error: Key matrix is not invertible under mod 26.\n");
        return;
    }

    // Compute inverse key matrix
    inverseKey[0][0] = key[1][1] * determinantInverse % 26;
    inverseKey[1][1] = key[0][0] * determinantInverse % 26;
    inverseKey[0][1] = (-key[0][1] + 26) * determinantInverse % 26;
    inverseKey[1][0] = (-key[1][0] + 26) * determinantInverse % 26;

    // Encrypt
    printf("Encrypted text: ");
    for (i = 0; i < strlen(text); i += 2) {
        textVector[0] = text[i] - 'A';
        textVector[1] = text[i + 1] - 'A';

        for (j = 0; j < n; j++) {
            resultVector[j] = 0;
            for (k = 0; k < n; k++) {
                resultVector[j] += key[j][k] * textVector[k];
            }
            resultVector[j] %= 26;
        }
        encrypted[i] = resultVector[0] + 'A';
        encrypted[i + 1] = resultVector[1] + 'A';
    }
    encrypted[i] = '\0';
    printf("%s\n", encrypted);

    // Decrypt
    printf("Decrypted text: ");
    for (i = 0; i < strlen(encrypted); i += 2) {
        textVector[0] = encrypted[i] - 'A';
        textVector[1] = encrypted[i + 1] - 'A';

        for (j = 0; j < n; j++) {
            resultVector[j] = 0;
            for (k = 0; k < n; k++) {
                resultVector[j] += inverseKey[j][k] * textVector[k];
            }
            resultVector[j] %= 26;
        }
        decrypted[i] = resultVector[0] + 'A';
        decrypted[i + 1] = resultVector[1] + 'A';
    }
    decrypted[i] = '\0';
    printf("%s\n", decrypted);
}

