package com.hashcode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    static String fileName1 = "a_example.in";
    static String fileName2 = "b_should_be_easy.in";
    static String fileName3 = "c_no_hurry.in";
    static String fileName4 = "d_metropolis.in";
    static String fileName5 = "e_high_bonus.in";
    static String fileName = "input/" + fileName1;

    static int R, C, F, N, B, T;
    static ArrayList<Ride> rides = new ArrayList<Ride>();

    public static void main(final String args[]) {

        // READ FILE
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            String[] tokens = line.split(" ");
            R = Integer.parseInt(tokens[0]);
            C = Integer.parseInt(tokens[1]);
            F = Integer.parseInt(tokens[2]);
            N = Integer.parseInt(tokens[3]);
            B = Integer.parseInt(tokens[4]);
            T = Integer.parseInt(tokens[5]);

            int a, b, x, y, s, f;

            while ((line = bufferedReader.readLine()) != null) {
                tokens = line.split(" ");
                a = Integer.parseInt(tokens[0]);
                b = Integer.parseInt(tokens[1]);
                x = Integer.parseInt(tokens[2]);
                y = Integer.parseInt(tokens[3]);
                s = Integer.parseInt(tokens[4]);
                f = Integer.parseInt(tokens[5]);
                rides.add(new Ride(a, b, x, y, s, f));
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        // PROCESS DATA
        Problem problema = new Problem(R, C, F, B, T, N, rides);

        // WRITE OUTPUT

    }
}
