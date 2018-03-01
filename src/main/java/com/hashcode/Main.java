package com.hashcode;

import java.io.*;
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
    static ArrayList<Car> cars = new ArrayList<Car>();

    public static void main(final String args[]) {

        // READ FILE
        readInput();

        // PROCESS DATA
        processData();

        // WRITE OUTPUT
        writeOutput();
    }

    private static void processData() {
//        GA.execute();
    }

    private static void readInput() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            String line = null;
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            String[] tokens = line.split(" ");
            R = Integer.parseInt(tokens[0]);
            C = Integer.parseInt(tokens[1]);
            F = Integer.parseInt(tokens[2]);
            N = Integer.parseInt(tokens[3]);
            B = Integer.parseInt(tokens[4]);
            T = Integer.parseInt(tokens[5]);

            int a, b, x, y, s, f;

            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                tokens = line.split(" ");
                a = Integer.parseInt(tokens[0]);
                b = Integer.parseInt(tokens[1]);
                x = Integer.parseInt(tokens[2]);
                y = Integer.parseInt(tokens[3]);
                s = Integer.parseInt(tokens[4]);
                f = Integer.parseInt(tokens[5]);
                rides.add(new Ride(index, a, b, x, y, s, f));
                index++;
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeOutput() {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter("output/solution.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            StringBuffer stringBuffer = new StringBuffer();

            Ride ride;
            for (Car car : cars) {
                int count = car.rides.size();
                stringBuffer.append(count + " ");
                for (int i = 0; i < count; i++) {
                    stringBuffer.append(car.rides.get(i).index + (i == count-1 ? "\n" : " "));
                }
                bufferedWriter.write(stringBuffer.toString());
                stringBuffer.setLength(0);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
