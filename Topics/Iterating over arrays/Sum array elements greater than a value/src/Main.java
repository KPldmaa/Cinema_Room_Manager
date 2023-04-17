import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner (System.in);
        int size = sc.nextInt();

        int [] array = new int [size];
        for (int i=0; i<size; i++) {
            array[i] = sc.nextInt();
        }

        int n = sc.nextInt();
        int sum = 0;
        for (int x : array) {
            if (x > n) {
                sum = sum + x;
            }
        }
        System.out.println(sum);
    }
}