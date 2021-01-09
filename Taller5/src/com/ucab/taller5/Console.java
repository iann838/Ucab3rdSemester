package com.ucab.taller5;

import java.io.IOException;

public class Console {

    public static void clear() {
        try {
            final String operatingSystem = System.getProperty("os.name");
    
            if (operatingSystem .contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            }
            else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException e) {};
    }
}
