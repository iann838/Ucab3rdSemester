#include <iostream>
#include <string.h>
#include <cstring>

using namespace std;

struct Data {
    string chain;
    char character;
};

void crear (Data *&ptr) {
    // Crea la estructura fuera del main() .... ... .. . . .
    ptr = new Data;
}

int complicarLaVidaRecursivamente (const string& chain, const char& character, const char& upper, const int index, int count) {
    // Si es fin, retorna, si no sigue
    if (index == chain.length()) {
        return count;
    }
    if ((chain[index] == character) || (chain[index] == upper)) {
        (count)++;
    }
    return complicarLaVidaRecursivamente(chain, character, upper, index + 1, count);
}

void contar (const string& chain, const char& character) {
    // Cuenta la ocurrencia de character en chain

    char * upper_character = new char (toupper(character));

    int count = complicarLaVidaRecursivamente(chain, character, *upper_character, 0, 0);

    cout << "La cadena '" << chain << "' posee " << count << " letras '" << character << "'" << endl;

    delete upper_character;
}
