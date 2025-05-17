package task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class task2 {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Ошибка: java task2 <file1> <file2>");
            System.exit(1);
        }

        String fileName = args[0];
        String fileName2 = args[1];

        int x = 0, y = 0, r = 0, numLines = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName2))) {
            while (br.readLine() != null) {
                numLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<int[]> values = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String[] firstLineValues = br.readLine().split(" ");
            String secondLineValue = br.readLine();

            x = Integer.parseInt(firstLineValues[0]);
            y = Integer.parseInt(firstLineValues[1]);
            r = Integer.parseInt(secondLineValue);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName2))) {
            String line;
            int index = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int[] lineValues = new int[parts.length];

                for (int i = 0; i < parts.length; i++) {
                    lineValues[i] = Integer.parseInt(parts[i]);
                }
                values.add(lineValues);

                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numLines; i++) {
            int h = values.get(i)[0];
            int k = values.get(i)[1];

            int d = (int) sqrt(Math.pow((h - x), 2) + Math.pow((k - y), 2));

            if (d < r) {
                System.out.println(1);
            } else if (d == r) {
                System.out.println(0);
            } else if (d > r) {
                System.out.println(2);
            }
        }
    }
}