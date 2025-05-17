package task4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.abs;

public class task4 {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Ошибка: java task4 <file1>");
            System.exit(1);
        }

        String fileName = args[0];

        int numLines = 0, tmp = 0, tmp2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.readLine() != null) {
                numLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] mass = new int[numLines];

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            int num = 0;

            while ((line = br.readLine()) != null) {

                mass[num] = Integer.parseInt(line.trim());

                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < numLines; i++){
            tmp +=mass[i];
        }

        int middle = tmp / numLines;

        for (int i = 0; i < numLines; i++){
            tmp2 += abs(middle - mass[i]);
        }
        System.out.println(tmp2);
    }
}