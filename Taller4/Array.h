void llenado(int array[],int longitud,int limite){
    for(int i = 0;(i+1) < longitud;i++){
        array[i] = (rand() % limite) + 1;
    }
}
int *arreglo(int longitud, int limite){
    int *array = new int[longitud];
    llenado(array,longitud,limite);
    return array;
}
void printA(int array[],int longitud){
	for(int i = 0;(i+1) < longitud;i++){
        cout << array[i] << endl;
    }
}