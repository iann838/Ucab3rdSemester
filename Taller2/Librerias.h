#include <iostream>
#include <string.h>
#include <cstring>

using namespace std;

struct Data {
    string chain;
    char character;
};

void crear (Data *&ptr) {
    ptr = new Data;
}

void contar (const string& chain, const char& character) {
    int * count = new int (0);
    char * upper_character = new char (toupper(character));
    
    int * i = new int (0);

    for (; *i < chain.length(); (*i) ++){
        if ((chain[*i] == character) || (chain[*i] == *upper_character)) {
            (*count)++;
        }
    }

    delete i;

    cout << "La cadena '" << chain << "' posee " << *count << " letras '" << character << "'" << endl;
    delete count;
    delete upper_character;
}
