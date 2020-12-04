#include <iostream>
#include <random>
using namespace std;

void llenado(int array[],int longitud,int limite){
    for(int i = 0;(i+1) < longitud;i++){
        array[i] = (rand() % limite) + 1;
    }
}
int *arreglo(int longitud, int limite){
    int array[longitud];
    llenado(array,longitud,limite);
    return &(array[0]);
}
int main()
{
    int opcion, control = 0;
    int *array = NULL;
    do{
        cout << "\n\n\t\t\t...:MENU:..." << endl;
        cout << "\n\t\tEste programa fue elaborado para dar el valor ASCII de un caracter dado, "
                << "en las siguientes opciones podra elegir que accion realizar" << endl;
        cout << "\n\t\tMarca 1 para generar y llenar un array de 100 posiciones" << endl;
        cout << "\n\t\tMarca 2 para generar y llenar un aray de 10.000 posiciones" << endl;
        cout << "\n\t\tMarca 3 para ordenarlo aplicacndo BubbleSort y mostrarlo en pantalla" << endl;
        cout << "\n\t\tMarca 4 para salir del programa" << endl;
        cout << "\n\t\t\tintroduzca una opcion por favor"<< endl;
        cin >> opcion;
        switch(opcion){
            case 1: 
					array = arreglo(100, 1000);
                    /*int array[100];
                    llenado(array,1000);
                    control = 1;*/
                break;
                
            case 2: 
              		array = arreglo(10000, 1000000);
                    /*int array[10000];
                    llenado(array,1000000);
                    control = 1;*/
                break;
            case 3: 
                    if(array != NULL){//if (control != 0){
                        //pones tu logica Jian
                    }else{
                        cout << "\n\t\t\tAun no ha generado un array, porfavor genere uno"<< endl;
                    }
                break;
            case 4: opcion = 0;
            			cout<<"\n\n\t\tHasta pronto...\n\n";
                break;
            default :cout << "\n\t\t\tLa opcion introducida no es valida"<< endl;
        }
    }while(opcion != 0);
    delete(array);
    return 0;
}
