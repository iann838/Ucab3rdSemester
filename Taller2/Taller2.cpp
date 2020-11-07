#include <iostream>
#include <string>
#include <stdlib.h>
#include "Librerias.h"

using namespace std;

int main() {
    Data * data;
    crear(data); 
    int * op = new int;
    do { 
        cout << "***                MENU                ***" << endl;
        cout << "  1.Ingresar cadena de caracateres.\n";
        cout << "  2.Contar ocurrencias de un caracter en la cadena.\n";
        cout << "  3.Salir.\n";
        cout << "\nIngrese una opcion para continuar: ";
        cin >> *op;
        system("cls");
        switch(*op){
            case 1:
                cout << "Introduzca una frase: ";
                getline(cin.ignore(), data->chain);
                break;        
            case 2:
                cout << "Introduzca el caracter a contar: ";
                cin.ignore();
                cin.get(data->character);
                contar(data->chain, data->character);
                system("pause");
                break;
            case 3:
                break;
            default:
                cout << "Ingrese un numero del menu" << endl;
                system("pause");
                break;
        };
        system("cls");
    } while(*op != 3);
    delete data;
    delete op;
    return 0;
}