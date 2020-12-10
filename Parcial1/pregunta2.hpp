#include <iostream>

// Dado un arreglo de N elementos, implemente una función recursiva que elimine todos sus elementos repetidos

int *deduplicate(int array[], int length, int ind = 0) {
    int *newarray = new int[length];
    if (array[ind] == NULL) {
        return newarray;
    }
    int val = array[ind];
    int dupes = 0;
    for (int i = 0; i < length; ++i) {
        if (array[i] != val) {
            newarray[i-dupes] = array[i];
        } else {
            ++dupes;
        }
    }
    for (int i = 0; i < dupes; ++i){
        newarray[length-dupes+i] = NULL;
    }
    ++ind;
    return deduplicate(newarray, length, ind);
}
