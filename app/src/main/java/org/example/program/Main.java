package org.example.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }

}
