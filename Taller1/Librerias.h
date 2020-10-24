#include <iostream>
#include <string.h>

using namespace std;

void buscar(string x){
    int ci=0, ca=0;
    
    for (int i=0; i < x.length(); i++){
        if ((x[i] == 'i')||(x[i] == 'I')){
            ci++;
        }
        if ((x[i] == 'a')||(x[i] == 'A')){
            ca++;
        }
    }

    cout << "La frase: '" << x << "' Posee:" << endl;
    cout << "Letras 'A' = " << ca << endl;
    cout << "Letras 'I' = " << ci << endl;
}

