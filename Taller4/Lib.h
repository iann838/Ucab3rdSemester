#include <iostream>
#include <chrono>
#include <tuple>
#include <random>


using namespace std;


random_device rd;
mt19937 gen(rd());


int randint(int limit) {
    uniform_real_distribution<> distribution(1, limit);

    int random = distribution(gen);
    return random;
}


void fillArray(int array[], int length, int limit){
    for(int i = 0; i < length; ++i){
        array[i] = randint(limit);
    }
}


int *createArray(int length, int limit) {
    int *array = new int[length];
    fillArray(array, length, limit);
    return array;
}


chrono::_V2::steady_clock::time_point dateNow() {
    return chrono::steady_clock::now();
}


double dateDiff(chrono::_V2::steady_clock::time_point first, chrono::_V2::steady_clock::time_point second) {
    chrono::duration<double> duration = first - second;
    return duration.count();
}


tuple<double, int, int> bubleSort(int array[], int length) {
    int comparisons = 0;
    int exchanges = 0;
    auto start = dateNow();

    for (int step = 0; step < length - 1; ++step) {
        for (int i = 0; i < length - step - 1; ++i) {
            ++comparisons;
            if (array[i] > array[i + 1]) {
                int aux = array[i];
                array[i] = array[i + 1];
                array[i + 1] = aux;
                ++exchanges;
            }
        }
    }

    return make_tuple(dateDiff(dateNow(), start), comparisons, exchanges);
}


void printArray(int array[], int length){
    cout << "\n[";
	for(int i = 0; i < length; ++i) {
        cout << array[i];
        if (i == length - 1) {
            cout << "]" << endl;
        } 
        else {
            cout << ", ";
        }
    }
}
