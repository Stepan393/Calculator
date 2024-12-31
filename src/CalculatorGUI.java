import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class CalculatorGUI extends JFrame {
    private JTextField display; // Поле для отображения
    private JTextArea historyArea; // История вычислений
    private Calculator calculator; // Логика калькулятора
    private StringBuilder currentInput; // Текущий ввод
    private double memory; // Переменная памяти

    public CalculatorGUI() {
        calculator = new Calculator(); // Используем ранее созданный класс
        currentInput = new StringBuilder();
        memory = 0;

        // Настройка окна
        setTitle("Калькулятор");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setFocusable(true);

        // Поле для отображения результата
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Область для истории вычислений
        historyArea = new JTextArea();
        historyArea.setFont(new Font("Arial", Font.PLAIN, 14));
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setPreferredSize(new Dimension(450, 100));
        add(scrollPane, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 4)); // Увеличиваем размер сетки для новых кнопок

        String[] buttons = {
                "MC", "MR", "M+", "M-",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "←", "x²", "√x",
                "%", "1/x", "sin", "cos",
                "tan", "ln", "log", "n!"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        // Подключаем глобальный обработчик клавиш
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    handleKeyInput(e);
                }
                return false;
            }
        });
    }

    // Обработчик нажатий кнопок
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleInput(e.getActionCommand());
        }
    }

    // Метод для обработки ввода
    private void handleInput(String command) {
        try {
            switch (command) {
                case "=":
                    calculator.setNum2(Double.parseDouble(currentInput.toString()));
                    double result = calculator.calc();
                    display.setText(String.valueOf(result));
                    historyArea.append(calculator.getNum1() + " " + calculator.getOperator() + " " +
                            calculator.getNum2() + " = " + result + "\n");
                    currentInput.setLength(0); // Очищаем ввод
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    if (currentInput.length() > 0) {
                        calculator.setNum1(Double.parseDouble(currentInput.toString()));
                        calculator.setOperator(command.charAt(0));
                        currentInput.setLength(0); // Очищаем ввод
                    }
                    break;
                case "C": // Полная очистка
                    currentInput.setLength(0);
                    display.setText("");
                    calculator.setNum1(0);
                    calculator.setNum2(0);
                    calculator.setOperator(' ');
                    break;
                case "←": // Удаление последнего символа
                    if (currentInput.length() > 0) {
                        currentInput.deleteCharAt(currentInput.length() - 1);
                        display.setText(currentInput.toString());
                    }
                    break;
                case "MC": // Очистка памяти
                    memory = 0;
                    break;
                case "MR": // Чтение из памяти
                    display.setText(String.valueOf(memory));
                    currentInput.setLength(0);
                    currentInput.append(memory);
                    break;
                case "M+": // Добавить в память
                    memory += Double.parseDouble(currentInput.toString());
                    break;
                case "M-": // Вычесть из памяти
                    memory -= Double.parseDouble(currentInput.toString());
                    break;
                case "x²": // Квадрат числа
                    double squared = calculator.square(Double.parseDouble(currentInput.toString()));
                    display.setText(String.valueOf(squared));
                    historyArea.append(currentInput.toString() + "² = " + squared + "\n");
                    currentInput.setLength(0);
                    break;
                case "√x": // Квадратный корень
                    double sqrt = calculator.sqrt(Double.parseDouble(currentInput.toString()));
                    display.setText(String.valueOf(sqrt));
                    historyArea.append("√" + currentInput.toString() + " = " + sqrt + "\n");
                    currentInput.setLength(0);
                    break;
                case "sin": // Синус
                    double sin = Math.sin(Math.toRadians(Double.parseDouble(currentInput.toString())));
                    display.setText(String.valueOf(sin));
                    historyArea.append("sin(" + currentInput.toString() + ") = " + sin + "\n");
                    currentInput.setLength(0);
                    break;
                case "cos": // Косинус
                    double cos = Math.cos(Math.toRadians(Double.parseDouble(currentInput.toString())));
                    display.setText(String.valueOf(cos));
                    historyArea.append("cos(" + currentInput.toString() + ") = " + cos + "\n");
                    currentInput.setLength(0);
                    break;
                case "tan": // Тангенс
                    double tan = Math.tan(Math.toRadians(Double.parseDouble(currentInput.toString())));
                    display.setText(String.valueOf(tan));
                    historyArea.append("tan(" + currentInput.toString() + ") = " + tan + "\n");
                    currentInput.setLength(0);
                    break;
                case "ln": // Натуральный логарифм
                    double ln = Math.log(Double.parseDouble(currentInput.toString()));
                    display.setText(String.valueOf(ln));
                    historyArea.append("ln(" + currentInput.toString() + ") = " + ln + "\n");
                    currentInput.setLength(0);
                    break;
                case "log": // Десятичный логарифм
                    double log = Math.log10(Double.parseDouble(currentInput.toString()));
                    display.setText(String.valueOf(log));
                    historyArea.append("log(" + currentInput.toString() + ") = " + log + "\n");
                    currentInput.setLength(0);
                    break;
                case "n!": // Факториал
                    int n = Integer.parseInt(currentInput.toString());
                    int factorial = 1;
                    for (int i = 1; i <= n; i++) {
                        factorial *= i;
                    }
                    display.setText(String.valueOf(factorial));
                    historyArea.append(n + "! = " + factorial + "\n");
                    currentInput.setLength(0);
                    break;
                case "Frac": // Работа с дробями
                    String[] fractions = currentInput.toString().split("\\+");
                    if (fractions.length == 2) {
                        String fractionResult = calculator.addFractions(fractions[0], fractions[1]);
                        display.setText(fractionResult);
                        historyArea.append(currentInput.toString() + " = " + fractionResult + "\n");
                        currentInput.setLength(0);
                    }
                    break;
                default: // Числа и точка
                    currentInput.append(command);
                    display.setText(currentInput.toString());
                    break;
            }
        } catch (Exception ex) {
            display.setText("Ошибка");
        }
    }


    // Метод для обработки ввода с клавиатуры
    private void handleKeyInput(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar) || keyChar == '.') {
            handleInput(String.valueOf(keyChar));
        } else if (keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/') {
            handleInput(String.valueOf(keyChar));
        } else if (keyChar == '=' || e.getKeyCode() == KeyEvent.VK_ENTER) {
            handleInput("=");
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            handleInput("C");
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            handleInput("←");
        }
    }

    // Запуск приложения
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI gui = new CalculatorGUI();
            gui.setVisible(true);
        });
    }
}
