// lembrando a você, que vai mexer nisso aqui, que deve existir um .java pra cada workload. portanto, crie
// um Spike.java, Randomic.java e Periodic.java :)

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
