import java.util.*;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in);
        int[] box1 = new int[3];
        int[] box2 = new int[3];

        for (int i = 0; i < box1.length; i++) {
            box1[i] = sc.nextInt();
        }
        int sum1 = 0;
        for (int x : box1) {
            sum1 = sum1 + x;
        }

        for (int i = 0; i < box2.length; i++) {
            box2[i] = sc.nextInt();
        }
        int sum2 = 0;
        for (int x : box2) {
            sum2 = sum2 + x;
        }

        if ((sum2 - sum1) >= 3) {
            System.out.println("Box 1 < Box 2");
        } else if ((sum1 - sum2) >= 3) {
            System.out.println("Box 1 > Box 2");
        } else {
            System.out.println("Incompatible");
        }



    }
}
