import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Welcome to the console Calculator!");

        try {
            System.out.println("Input first number:");
            calculator.setNum1(scanner.nextDouble());

            System.out.println("Input operator (+, -. *, /):");
            calculator.setOperator(scanner.next().charAt(0));

            System.out.println("Input second number:");
            calculator.setNum2(scanner.nextDouble());

            double result = calculator.calc();

            // Вывод результата
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: incorrect input!");
        } finally {
            scanner.close();
        }
    }
}