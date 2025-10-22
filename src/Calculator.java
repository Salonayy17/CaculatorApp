import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField textField;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public Calculator() {
        setTitle("Java Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        textField = new JTextField();
        textField.setBounds(30, 40, 270, 40);
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFont(new Font("Arial", Font.BOLD, 22));
        add(textField);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "DEL"
        };

        int x = 30, y = 100;
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBounds(x, y, 60, 50);
            button.addActionListener(this);
            add(button);

            x += 70;
            if ((i + 1) % 4 == 0) {
                x = 30;
                y += 60;
            }
            if (i == 15) {
                // Next row for C, DEL
                x = 30;
                y += 60;
            }
        }

        getContentPane().setBackground(new Color(240, 248, 255));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals(".")) {
            textField.setText(textField.getText() + command);
        }
        else if (command.equals("C")) {
            textField.setText("");
            num1 = num2 = result = 0;
        }
        else if (command.equals("DEL")) {
            String text = textField.getText();
            if (text.length() > 0)
                textField.setText(text.substring(0, text.length() - 1));
        }
        else if (command.equals("=")) {
            try {
                num2 = Double.parseDouble(textField.getText());

                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/':
                        if (num2 == 0) {
                            JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
                            textField.setText("");
                            return;
                        }
                        result = num1 / num2;
                        break;
                }

                textField.setText(String.valueOf(result));
                num1 = result;
            } catch (Exception ex) {
                textField.setText("Error");
            }
        }
        else {
            // Operator clicked
            try {
                num1 = Double.parseDouble(textField.getText());
                operator = command.charAt(0);
                textField.setText("");
            } catch (Exception ex) {
                textField.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}