package code;

import code.model.User; // Asumsi kelas User ada dan terstruktur dengan benar
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class DashboardFrame extends JFrame {
    private final User user;
    private JTextArea userScoresArea;
    private JTextArea leaderboardArea;

    public DashboardFrame(User user) {
        this.user = user;
        setTitle("Game Center - Welcome " + user.getNickname()); // Mengubah judul
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Mengatur latar belakang utama frame
        getContentPane().setBackground(new Color(30, 30, 30)); // Latar belakang gelap

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15)); // Menambah jarak antar panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding lebih besar
        mainPanel.setBackground(new Color(30, 30, 30)); // Latar belakang gelap

        // --- Top Panel for Welcome Label and Logout Button ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 30)); // Latar belakang gelap

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getNickname() + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Font lebih besar
        welcomeLabel.setForeground(Color.WHITE); // Warna teks putih
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(220, 53, 69)); // Warna merah untuk logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setPreferredSize(new Dimension(100, 35)); // Ukuran tombol konsisten
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true); // Kembali ke LoginFrame
            dispose(); // Tutup DashboardFrame saat logout
        });
        JPanel logoutButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // Untuk menempatkan tombol di kanan tanpa padding ekstra
        logoutButtonPanel.setBackground(new Color(30, 30, 30)); // Latar belakang gelap
        logoutButtonPanel.add(logoutButton);
        topPanel.add(logoutButtonPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- Center panel for scores and leaderboard ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 10)); // Menambah jarak antar panel
        centerPanel.setBackground(new Color(30, 30, 30)); // Latar belakang gelap

        // User scores panel
        JPanel userScoresPanel = new JPanel(new BorderLayout());
        userScoresPanel.setBackground(new Color(45, 45, 45)); // Warna gelap sedikit lebih terang
        userScoresPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(75, 75, 75)), // Border warna abu-abu
            "Your Scores", // Judul
            javax.swing.border.TitledBorder.LEFT, // Posisi judul
            javax.swing.border.TitledBorder.TOP, // Posisi judul
            new Font("Arial", Font.BOLD, 16), // Font judul
            Color.WHITE // Warna judul
        ));
        userScoresArea = new JTextArea();
        userScoresArea.setEditable(false);
        userScoresArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Font monospaced untuk tabel
        userScoresArea.setBackground(new Color(60, 60, 60)); // Latar belakang area teks gelap
        userScoresArea.setForeground(Color.WHITE); // Warna teks putih
        JScrollPane userScoresScrollPane = new JScrollPane(userScoresArea);
        userScoresScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Menghilangkan border scrollpane
        userScoresScrollPane.getViewport().setBackground(new Color(60, 60, 60)); // Latar belakang viewport
        userScoresPanel.add(userScoresScrollPane, BorderLayout.CENTER);
        centerPanel.add(userScoresPanel);

        // Leaderboard panel
        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        leaderboardPanel.setBackground(new Color(45, 45, 45)); // Warna gelap sedikit lebih terang
        leaderboardPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(75, 75, 75)), // Border warna abu-abu
            "Leaderboards", // Judul
            javax.swing.border.TitledBorder.LEFT, // Posisi judul
            javax.swing.border.TitledBorder.TOP, // Posisi judul
            new Font("Arial", Font.BOLD, 16), // Font judul
            Color.WHITE // Warna judul
        ));
        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        leaderboardArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Font monospaced untuk tabel
        leaderboardArea.setBackground(new Color(60, 60, 60)); // Latar belakang area teks gelap
        leaderboardArea.setForeground(Color.WHITE); // Warna teks putih
        JScrollPane leaderboardScrollPane = new JScrollPane(leaderboardArea);
        leaderboardScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Menghilangkan border scrollpane
        leaderboardScrollPane.getViewport().setBackground(new Color(60, 60, 60)); // Latar belakang viewport
        leaderboardPanel.add(leaderboardScrollPane, BorderLayout.CENTER);
        centerPanel.add(leaderboardPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Game buttons panel ---
        JPanel gamePanel = new JPanel(new GridLayout(1, 2, 20, 10)); // Menambah jarak antar tombol
        gamePanel.setBackground(new Color(30, 30, 30)); // Latar belakang gelap

        // Tombol Tic Tac Toe dengan foto
        JButton ticTacToeButton = new JButton();
        ticTacToeButton.setFont(new Font("Arial", Font.BOLD, 16));
        ticTacToeButton.setBackground(new Color(255, 152, 0)); // Oranye cerah
        ticTacToeButton.setForeground(Color.WHITE);
        ticTacToeButton.setFocusPainted(false);
        ticTacToeButton.setBorderPainted(false);
        ticTacToeButton.setPreferredSize(new Dimension(250, 200)); // Ukuran tombol konsisten
        
        try {
            ImageIcon ticTacToeIcon = new ImageIcon(getClass().getResource("/assets/tictactoe.png"));
            if (ticTacToeIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                // Skala gambar agar pas di tombol
                Image scaledTicTacToeImage = ticTacToeIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ticTacToeButton.setIcon(new ImageIcon(scaledTicTacToeImage));
                
                ticTacToeButton.setText("Play Tic Tac Toe");
                ticTacToeButton.setHorizontalTextPosition(SwingConstants.CENTER);
                ticTacToeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            } else {
                ticTacToeButton.setText("Play Tic Tac Toe (Image not found)");
                System.err.println("Tic Tac Toe image not found or loaded incorrectly.");
            }
        } catch (Exception ex) {
            ticTacToeButton.setText("Play Tic Tac Toe (Error loading image)");
            System.err.println("Error loading Tic Tac Toe image: " + ex.getMessage());
        }
        ticTacToeButton.addActionListener(e -> playTicTacToe());
        gamePanel.add(ticTacToeButton);

        // Tombol Snake Game dengan foto
        JButton snakeGameButton = new JButton();
        snakeGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        snakeGameButton.setBackground(new Color(76, 175, 80)); // Hijau cerah
        snakeGameButton.setForeground(Color.WHITE);
        snakeGameButton.setFocusPainted(false);
        snakeGameButton.setBorderPainted(false);
        snakeGameButton.setPreferredSize(new Dimension(250, 200)); // Ukuran tombol konsisten

        try {
            ImageIcon snakeGameIcon = new ImageIcon(getClass().getResource("/assets/snakegame.jpg"));
            if (snakeGameIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                // Skala gambar agar pas di tombol
                Image scaledSnakeGameImage = snakeGameIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                snakeGameButton.setIcon(new ImageIcon(scaledSnakeGameImage));

                snakeGameButton.setText("Play Snake Game");
                snakeGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
                snakeGameButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            } else {
                snakeGameButton.setText("Play Snake Game (Image not found)");
                System.err.println("Snake Game image not found or loaded incorrectly.");
            }
        } catch (Exception ex) {
            snakeGameButton.setText("Play Snake Game (Error loading image)");
            System.err.println("Error loading Snake Game image: " + ex.getMessage());
        }
        snakeGameButton.addActionListener(e -> playSnakeGame());
        gamePanel.add(snakeGameButton);

        mainPanel.add(gamePanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load data when frame is shown
        loadUserScores();
        loadLeaderboards();
    }

    private void loadUserScores() {
        try (Connection conn = DBConnection.getConnection()) {
            StringBuilder sb = new StringBuilder();

            // Get Tic Tac Toe win count
            String ticTacToeSql = "SELECT COALESCE(SUM(score), 0) as win_count FROM game_scores " +
                                  "WHERE user_id = ? AND game_name = 'Tic Tac Toe'";
            PreparedStatement ticTacToeStmt = conn.prepareStatement(ticTacToeSql);
            ticTacToeStmt.setInt(1, user.getUserId());
            ResultSet ticTacToeRs = ticTacToeStmt.executeQuery();
            
            if (ticTacToeRs.next()) {
                sb.append("Tic Tac Toe Wins: ").append(ticTacToeRs.getInt("win_count")).append("\n\n");
            }

            // Get Snake Game high score
            String snakeSql = "SELECT COALESCE(MAX(score), 0) as high_score FROM game_scores " +
                              "WHERE user_id = ? AND game_name = 'Snake Game'";
            PreparedStatement snakeStmt = conn.prepareStatement(snakeSql);
            snakeStmt.setInt(1, user.getUserId());
            ResultSet snakeRs = snakeStmt.executeQuery();
            
            if (snakeRs.next()) {
                sb.append("Snake Game High Score: ").append(snakeRs.getInt("high_score"));
            }

            userScoresArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading user scores: " + e.getMessage());
        }
    }

    private void loadLeaderboards() {
        try (Connection conn = DBConnection.getConnection()) {
            StringBuilder sb = new StringBuilder();

            // Tic Tac Toe Leaderboard (most wins)
            sb.append("=== Tic Tac Toe Top Players ===\n");
            sb.append(String.format("%-15s %-10s\n", "Player", "Wins"));
            
            String ticTacToeLeaderboardSql = "SELECT u.nickname, SUM(gs.score) as wins " +
                                             "FROM game_scores gs JOIN users u ON gs.user_id = u.user_id " +
                                             "WHERE gs.game_name = 'Tic Tac Toe' " +
                                             "GROUP BY u.user_id, u.nickname " +
                                             "ORDER BY wins DESC LIMIT 5";
            PreparedStatement ticTacToeLeaderboardStmt = conn.prepareStatement(ticTacToeLeaderboardSql);
            ResultSet ticTacToeLeaderboardRs = ticTacToeLeaderboardStmt.executeQuery();
            
            while (ticTacToeLeaderboardRs.next()) {
                sb.append(String.format("%-15s %-10d\n", 
                    ticTacToeLeaderboardRs.getString("nickname"),
                    ticTacToeLeaderboardRs.getInt("wins")));
            }

            sb.append("\n=== Snake Game Top Players ===\n");
            sb.append(String.format("%-15s %-10s\n", "Player", "High Score"));
            
            // Snake Game Leaderboard (highest scores)
            String snakeLeaderboardSql = "SELECT u.nickname, MAX(gs.score) as high_score " +
                                         "FROM game_scores gs JOIN users u ON gs.user_id = u.user_id " +
                                         "WHERE gs.game_name = 'Snake Game' " +
                                         "GROUP BY u.user_id, u.nickname " +
                                         "ORDER BY high_score DESC LIMIT 5";
            PreparedStatement snakeLeaderboardStmt = conn.prepareStatement(snakeLeaderboardSql);
            ResultSet snakeLeaderboardRs = snakeLeaderboardStmt.executeQuery();
            
            while (snakeLeaderboardRs.next()) {
                sb.append(String.format("%-15s %-10d\n", 
                    snakeLeaderboardRs.getString("nickname"),
                    snakeLeaderboardRs.getInt("high_score")));
            }

            leaderboardArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading leaderboards: " + e.getMessage());
        }
    }

    private void playTicTacToe() {
        new Player2LoginFrame(user).setVisible(true); // Asumsi Player2LoginFrame ada
        dispose();
    }

    private void playSnakeGame() {
        new GameUlar(user).setVisible(true); // Asumsi GameUlar ada
        dispose();
    }
}
