package code;

import code.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class GameTicTacToe extends JFrame {
    private final User player1;
    private final User player2;
    private JButton[][] buttons = new JButton[15][15];
    private boolean xTurn = true;
    private JLabel statusLabel;

    public GameTicTacToe(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;

        setTitle(String.format("Tic Tac Toe - %s vs %s", player1.getNickname(), player2.getNickname()));
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel panel = new BackgroundPanel("/assets/board.jpg");

        statusLabel = new JLabel("Giliran " + player1.getNickname() + " (X)", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setOpaque(false);
        statusLabel.setForeground(Color.WHITE);
        panel.add(statusLabel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(15, 15));
        gamePanel.setOpaque(false); // transparan agar background terlihat
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttons[i][j].setOpaque(false);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setForeground(Color.BLACK);
                buttons[i][j].addActionListener(this::buttonClicked);
                gamePanel.add(buttons[i][j]);
            }
        }
        panel.add(gamePanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Kembali ke Dashboard");
        backButton.addActionListener(e -> {
            new DashboardFrame(player1).setVisible(true);
            dispose();
        });
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);
    }

    private void buttonClicked(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (!button.getText().isEmpty()) return;

        if (xTurn) {
            button.setText("X");
            statusLabel.setText("Giliran " + player2.getNickname() + "(O)");
        } else {
            button.setText("O");
            statusLabel.setText("Giliran " + player1.getNickname() + "(X)");
        }

        xTurn = !xTurn;

        checkWinner();
    }

    private void checkWinner() {
        String currentSymbol = xTurn ? "O" : "X";

        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                if (buttons[row][col].getText().equals(currentSymbol)) {
                    if (checkDirection(row, col, 0, 1, currentSymbol) ||
                        checkDirection(row, col, 1, 0, currentSymbol) ||
                        checkDirection(row, col, 1, 1, currentSymbol) ||
                        checkDirection(row, col, 1, -1, currentSymbol)) {
                        
                        User winningPlayer = currentSymbol.equals("X") ? player1 : player2;
                        JOptionPane.showMessageDialog(this, winningPlayer.getNickname() + " wins!");
                        saveScore(winningPlayer);
                        resetGame();
                        return;
                    }
                }
            }
        }

        if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "DRAW!");
            resetGame();
        }
    }

    private boolean checkDirection(int row, int col, int dx, int dy, String symbol) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            int r = row + i * dx;
            int c = col + i * dy;
            if (r >= 0 && r < 15 && c >= 0 && c < 15 && buttons[r][c].getText().equals(symbol)) {
                count++;
            } else {
                break;
            }
        }
        return count == 5;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                buttons[i][j].setText("");
            }
        }
        xTurn = true;
        statusLabel.setText("GILIRAN (X)" + player1.getNickname());
    }

    private void saveScore(User winner) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO game_scores (user_id, game_name, score) VALUES (?, 'Tic Tac Toe', 1) " +
                         "ON DUPLICATE KEY UPDATE score = score + 1, last_played = CURRENT_TIMESTAMP";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, winner.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan score: " + e.getMessage());
        }
    }

    // Background panel class
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "gagal menggunakan baackground image: " + e.getMessage());
            }
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
