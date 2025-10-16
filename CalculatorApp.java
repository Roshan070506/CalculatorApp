import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField display;
    private String operator;
    private double firstOperand;
    private boolean startNewNumber = true;

    public CalculatorApp() {
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            if (startNewNumber) {
                display.setText(command.equals(".") ? "0." : command);
                startNewNumber = false;
            } else {
                if (command.equals(".") && display.getText().contains(".")) {
                    return; // Prevent multiple decimals
                }
                display.setText(display.getText() + command);
            }
        } else if (command.equals("=")) {
            calculate(Double.parseDouble(display.getText()));
            operator = null;
            startNewNumber = true;
        } else { // Operator pressed
            if (operator != null && !startNewNumber) {
                calculate(Double.parseDouble(display.getText()));
            } else {
                firstOperand = Double.parseDouble(display.getText());
            }
            operator = command;
            startNewNumber = true;
        }
    }

    private void calculate(double secondOperand) {
        switch (operator) {
            case "+":
                firstOperand += secondOperand;
                break;
            case "-":
                firstOperand -= secondOperand;
                break;
            case "*":
                firstOperand *= secondOperand;
                break;
            case "/":
                if (secondOperand == 0) {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                firstOperand /= secondOperand;
                break;
        }
        display.setText(String.valueOf(firstOperand));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorApp().setVisible(true);
        });
    }
}
