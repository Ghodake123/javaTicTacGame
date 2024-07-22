import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {
    private static final int SIZE = 3;
    private JButton[][] buttons = new JButton[SIZE][SIZE];
    private JTextField symbolField = new JTextField(5);
    private JButton setSymbolButton = new JButton("Set Symbol");
    private String currentSymbol = "X";

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter Symbol:"));
        topPanel.add(symbolField);
        topPanel.add(setSymbolButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(SIZE, SIZE));
        add(gamePanel, BorderLayout.CENTER);

        initializeButtons(gamePanel);

        setSymbolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSymbol = symbolField.getText().trim();
                if (currentSymbol.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Symbol cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void initializeButtons(JPanel panel) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 80));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].setPreferredSize(new Dimension(100, 100));
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                panel.add(buttons[row][col]);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().isEmpty()) {
                buttons[row][col].setText(currentSymbol);
                if (checkWin()) {
                    JOptionPane.showMessageDialog(null, currentSymbol + " wins!");
                    resetGame();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a draw!");
                    resetGame();
                }
            }
        }
    }

    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return true;
            }
        }
        // Check diagonals
        return checkLine(buttons[0][0], buttons[1][1], buttons[2][2]) ||
               checkLine(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    private boolean checkLine(JButton b1, JButton b2, JButton b3) {
        String text = b1.getText();
        return !text.isEmpty() &&
               text.equals(b2.getText()) &&
               text.equals(b3.getText());
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
            }
        }
        currentSymbol = "X"; // Default symbol
        symbolField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
