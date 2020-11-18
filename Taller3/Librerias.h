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
    // int * count = new int (0);
    // int count = 0;
    char * upper_character = new char (toupper(character));
    
    // int * i = new int (0);

    // for (; *i < chain.length(); (*i) ++){
    //     if ((chain[*i] == character) || (chain[*i] == *upper_character)) {
    //         (*count)++;
    //     }
    // }
    // int i = 0;

    int count = complicarLaVidaRecursivamente(chain, character, *upper_character, 0, 0);

    // delete i;

    cout << "La cadena '" << chain << "' posee " << count << " letras '" << character << "'" << endl;
    // delete count;
    delete upper_character;
}
