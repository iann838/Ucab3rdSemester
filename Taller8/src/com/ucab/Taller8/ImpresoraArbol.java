package com.ucab.Taller8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ImpresoraArbol {

    public static String ImprimirNodo(Nodo root) {
        int mayorNivel = ImpresoraArbol.mayorNivel(root);
        String printed = ImprimirNodoInterno("", Collections.singletonList(root), 1, mayorNivel);
        return printed;
    }

    private static String ImprimirNodoInterno(String printed, List<Nodo> nodes, int level, int mayorNivel) {
        if (nodes.isEmpty() || ImpresoraArbol.todoNull(nodes))
            return printed;

        int floor = mayorNivel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
        
        printed = ImpresoraArbol.ImprimirEspacios(printed, firstSpaces);
        
        List<Nodo> newNodes = new ArrayList<Nodo>();
        for (Nodo node : nodes) {
            if (node != null) {
                printed += node.getDato();
                newNodes.add(node.getHijoI());
                newNodes.add(node.getHijoD());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                printed += " ";
            }

            printed = ImpresoraArbol.ImprimirEspacios(printed, betweenSpaces);
        }
        printed += "\n";
        
        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printed = ImpresoraArbol.ImprimirEspacios(printed, firstSpaces - i);
                if (nodes.get(j) == null) {
                    printed = ImpresoraArbol.ImprimirEspacios(printed, endgeLines + endgeLines + i + 1);
                    continue;
                }
                
                if (nodes.get(j).getHijoI() != null)
                    printed += "/";
                    else
                    printed = ImpresoraArbol.ImprimirEspacios(printed, 1);

                    printed = ImpresoraArbol.ImprimirEspacios(printed, i + i - 1);
                    
                    if (nodes.get(j).getHijoD() != null)
                    printed += "\\";
                else
                printed = ImpresoraArbol.ImprimirEspacios(printed, 1);

                printed = ImpresoraArbol.ImprimirEspacios(printed, endgeLines + endgeLines - i);
            }
            
            printed += "\n";
        }
        
        printed = ImprimirNodoInterno(printed, newNodes, level + 1, mayorNivel);
        return printed;
    }

    private static String ImprimirEspacios(String printed, int count) {
        for (int i = 0; i < count; i++)
            printed += " ";
        return printed;
    }

    private static int mayorNivel(Nodo node) {
        if (node == null)
            return 0;

        return Math.max(ImpresoraArbol.mayorNivel(node.getHijoI()), ImpresoraArbol.mayorNivel(node.getHijoD())) + 1;
    }

    private static boolean todoNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}