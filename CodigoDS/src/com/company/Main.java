package com.company;

public class Main {

    public static void main(String[] args) {
        // Main
        Client c = new Client();
        boolean menuActivo = true;

        do {
            menuActivo = c.menuCliente();

        } while (menuActivo != false);

        System.exit(0);
    }

}
