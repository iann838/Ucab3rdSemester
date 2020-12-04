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