package com.ucab.taller6;

import java.util.Random;

public class Main {

    public List listA;
    public List listB;

    public Main() {
        listA = new List();
        listB = new List();
    }

    public void parseListA (String input) {
        String[] numbers = input.split(",");
        List newList = new List();
        for (String number: numbers) {
            if (!number.equals(""))
            newList.append(new Node(Integer.parseInt(number)));
        }
        listA = newList;
    }

    public void randomListA () {
        List newList = new List();
        Random random = new Random();
        for (int i = 0; i < 30; ++i) {
            newList.append(new Node(random.nextInt(11)));
        }
        listA = newList;
    }

    public void setListCounter(List list, int number) {
        Boolean isPair = true;
        for (Node node = list.head; node != null; node = node.next) {
            isPair = !isPair;
            if (isPair) continue;
            if (node.value == number) {
                node.next.value += 1;
                return;
            }
        }
        list.append(new Node(number));
        list.append(new Node(1));
    }

    public void generateB () {
        List newList = new List();
        for (Node node = listA.head; node != null; node = node.next) {
            setListCounter(newList, node.value);
        }
        listB = newList;
    }

}
