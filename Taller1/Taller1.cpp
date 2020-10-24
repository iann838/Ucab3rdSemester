#include <iostream>
#include <string>
#include <stdlib.h>
#include "Librerias.h"

using namespace std;

int main(){
    string cadena; int op;
    do { 
        cout << "***                MENU                ***" << endl;
        cout << "  1.Ingresar cadena de caracateres.\n";
        cout << "  2.Contar ocurrencias de 'i' y de 'a'.\n";
        cout << "  3.Salir.\n";
        cout << "\nIngrese una opcion para continuar: ";
        cin >> op;
        system("cls");
        switch(op){
            case 1: cout<<"Introduzca una frase: "; getline(cin.ignore(),cadena); break;        
            case 2: buscar(cadena); system("pause"); break; 
            case 3: break;
            default: cout << "Ingrese un numero del menu" << endl; system("pause"); break;
        };
        system("cls");
    } while(op!=3);
    return 0;
}