import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        long val1 = scanner.nextLong();
        char op = scanner.next().charAt(0);
        long val2 = scanner.nextLong();


        switch (op) {
            case '+':
                System.out.println(val1 + val2);
                break;

            case '-':
                System.out.println(val1 - val2);
                break;

            case '/':
                if (val2 == 0) {
                    System.out.println("Division by 0!");
                } else {
                    System.out.println(val1 / val2);
                }
                break;

            case '*':
                System.out.println(val1 * val2);
                break;

            default:
                System.out.println("Unknown operator");
        }


    }
}