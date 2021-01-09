package com.ucab.taller5;

public class Chain {

    public String value;

    public Chain(String str) {
        value = str;
    }

    public int countChar(char compare) {
        int count = 0;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (Character.toLowerCase(ch) == compare) ++count;
        }
        return count;
    }

}
