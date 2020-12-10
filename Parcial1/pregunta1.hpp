#include <iostream>


struct Node {
    int value;
    struct Node *next;
};


typedef struct Node *List;


void appendNode(List &list, int value) { //Agrega nodo
    List q = new (struct Node);

    q->value = value;
    q->next = NULL;

    if (list == NULL) {
        list = q;
    } else {
        List t = list;
        while( t->next != NULL ) {
            t = t->next;
        }
        t->next = q;
    }
}


bool inList (List &list, int value) { // Busca si un numero esta en la lista
    if (list == NULL) {
        return false;
    }

    List t = list;

    while ( t != NULL ) {
        if (t->value == value) {
            return true;
        }
        t = t->next;
    }
    return false;
}


List sortUnique(List &A) { //sortea la lista sin repetir
    List B = NULL;

    List a = A;
    while (a != NULL) {
        if (B == NULL) { //Si B es vacio
            appendNode(B, a->value);
        } else if (!inList(B, a->value)) { //Si existe en B
            List a2 = a; //copia para no modificar apuntadores originales
            List b = B;
            while (b != NULL) {
                List aux = b->next; //Accede al proximo node de manera auxiliar
                if (aux == NULL) { //Si es el ultimo
                    a2->next = NULL;
                    b->next = a2;
                    b = NULL;
                } else if (aux->value > a2->value) { //O que sea mayor
                    a2->next = b->next;
                    b->next = a2;
                    b = NULL;
                } else { //O nada y sigue ...
                    b = b->next;
                }
            }
        }
        a = a->next;
    }

    return B;
}
