public class Calculator {
    private double num1;  // Первое число
    private double num2;  // Второе число
    private char operator; // Последний оператор

    // Конструктор по умолчанию
    public Calculator() {
        this.num1 = 0;
        this.num2 = 0;
        this.operator = ' ';
    }

    // Метод для выполнения операции (внешние данные)
    public double calc(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Деление на ноль невозможно.");
                }
                return a / b;
            case '^': // Возведение в степень
                return Math.pow(a, b);
            case '%': // Вычисление процента
                return (a * b) / 100;
            default:
                throw new IllegalArgumentException("Некорректный оператор: " + operator);
        }
    }

    // Перегрузка метода calc для работы с внутренними переменными
    public double calc() {
        return calc(this.num1, this.num2, this.operator);
    }

    // Методы для установки чисел и оператора
    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    // Методы для получения текущих значений
    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public char getOperator() {
        return operator;
    }

    // Метод для сложения
    public double add(double a, double b) {
        return a + b;
    }

    // Метод для вычитания
    public double sub(double a, double b) {
        return a - b;
    }

    // Метод для умножения
    public double mul(double a, double b) {
        return a * b;
    }

    // Метод для деления
    public double div(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на ноль невозможно.");
        }
        return a / b;
    }

    // Метод для квадрата числа
    public double square(double a) {
        return a * a;
    }

    // Метод для квадратного корня
    public double sqrt(double a) {
        if (a < 0) {
            throw new ArithmeticException("Корень из отрицательного числа невозможен.");
        }
        return Math.sqrt(a);
    }

    // Метод для обратного значения
    public double reciprocal(double a) {
        if (a == 0) {
            throw new ArithmeticException("Обратное значение для нуля невозможно.");
        }
        return 1 / a;
    }

    // Метод для вычисления выражений
    public double evaluateExpression(String expression) {
        // Простая поддержка сложных выражений, используя рекурсию и стек.
        return evaluate(expression.replaceAll("\\s+", ""));
    }

    private double evaluate(String expression) {
        if (expression.isEmpty()) return 0;
        double result = 0;
        char lastOperator = '+';
        int index = 0;

        while (index < expression.length()) {
            char currentChar = expression.charAt(index);

            if (Character.isDigit(currentChar) || currentChar == '.') {
                StringBuilder number = new StringBuilder();
                while (index < expression.length() &&
                        (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.')) {
                    number.append(expression.charAt(index++));
                }
                double currentNumber = Double.parseDouble(number.toString());
                result = applyOperation(result, currentNumber, lastOperator);
                index--; // Компенсируем дополнительный инкремент
            } else if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                lastOperator = currentChar;
            }
            index++;
        }
        return result;
    }

    private double applyOperation(double a, double b, char operator) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Деление на ноль невозможно.");
                return a / b;
            default: throw new IllegalArgumentException("Некорректный оператор: " + operator);
        }
    }

    // Метод для сложения дробей
    public String addFractions(String fraction1, String fraction2) {
        String[] frac1 = fraction1.split("/");
        String[] frac2 = fraction2.split("/");

        int num1 = Integer.parseInt(frac1[0]);
        int den1 = Integer.parseInt(frac1[1]);
        int num2 = Integer.parseInt(frac2[0]);
        int den2 = Integer.parseInt(frac2[1]);

        int numerator = num1 * den2 + num2 * den1;
        int denominator = den1 * den2;

        return simplifyFraction(numerator, denominator);
    }

    private String simplifyFraction(int numerator, int denominator) {
        int gcd = findGCD(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        return numerator + "/" + denominator;
    }

    private int findGCD(int a, int b) {
        if (b == 0) return a;
        return findGCD(b, a % b);
    }
}
