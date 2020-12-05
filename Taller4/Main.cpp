#include <iostream>
#include <tuple>
#include "Lib.h"


using namespace std;


int main()
{
    int option;
    int *array = NULL;
    int length;
    cout << fixed;
    do {
        cout << "\n\t\t\t...:MENU:..." << endl;
        cout << "1. Generar y llenar aleatoriamente un array de 100 elementos con enteros del rango [1, 1000]." << endl;
        cout << "2. Generar y llenar aleatoriamente un array de 10000 elementos con enteros del rango [1, 1000000]." << endl;
        cout << "3. Ordenar el array generado aplicando BubbleSort con las estadisticas de tiempo, comparasiones e intercambios." << endl;
        cout << "4. Imprimir el array generado u ordenado en caso de ser aplicado el #3." << endl;
        cout << "5. Salir del programa." << endl;
        cout << "\n> Introduzca una opcion por favor: ";
        cin >> option;
        cin.clear();
        switch (option) {
            case 1:
                length = 100;
				array = createArray(100, 1000);
                cout << "\n> Se a generado y llenado el array" << endl;
                break;
            case 2:
                length = 10000;
              	array = createArray(10000, 1000000);
                cout << "\n> Se a generado y llenado el array" << endl;
                break;
            case 3: 
                if (array != NULL) {
                    cout << "\n> Sorteo array de " << length << " elementos:" << endl;
                    tuple<double, int, int> stats = bubleSort(array, length);
                    cout << "- Tiempo usado: " << get<0>(stats) << "s" << endl;
                    cout << "- Comparaciones: " << get<1>(stats) << endl;
                    cout << "- Intercambios: " << get<2>(stats) << endl;
                } else {
                    cout << "\n> Aun no ha generado un array, porfavor genere uno" << endl;
                }
                break;
            case 4:
                if (array != NULL) {
                    printArray(array, length);
                } else {
                    cout << "> Aun no ha generado un array, porfavor genere uno" << endl;
                }
                break;
            case 5:
            	cout << "\n> Bye";
                break;
            default:
                cout << "\n> La opcion introducida no es valida"<< endl;
        }
    } while (option != 5);
    delete array;
    return 0;
}
