package task1;

import java.util.Scanner;

public class task1 {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        while (true){
            try{
                System.out.print("n: ");

                int n = in.nextInt();

                    if (n < 0){
                        System.out.println("Значение n должно быть больше 0");
                    }
                    else {
                        System.out.print("m: ");

                        int m = in.nextInt();

                        if( m < 0){
                            System.out.println("Значение m должно быть больше 0");
                        }
                        else{
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
                            System.out.println();
                        }

                    }

            } catch (Exception e) {
                System.out.println("Вы ввели неверное значение");
                in.next();
            }

        }

    }
}