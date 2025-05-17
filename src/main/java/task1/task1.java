package task1;

import java.util.Scanner;

public class task1 {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.print("n: ");

        int n = in.nextInt();

        System.out.print("m: ");

        int m = in.nextInt();

        int[] mass = new int[n];

        for (int i = 0; i < mass.length; i++){
            mass[i] = i + 1;
        }

        int tmp = 0;

        do
        {
            System.out.print(mass[tmp]);
            tmp = (tmp + m - 1) % n;
        }while(tmp != 0);
        in.close();
    }
}