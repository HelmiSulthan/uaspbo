package code;

import code.model.User; // Asumsi kelas User ada dan terstruktur dengan benar
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Player2LoginFrame extends JFrame {
    private final User player1;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Player2LoginFrame(User player1) {
        this.player1 = player1;
        setTitle("Game Center - Login Player 2"); // Judul yang lebih konsisten
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Tetap DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);

        // Mengatur warna latar belakang untuk seluruh frame
        getContentPane().setBackground(new Color(30, 30, 30)); // Latar belakang gelap

        // Menggunakan BorderLayout untuk frame utama: judul di atas, form di tengah
        setLayout(new BorderLayout());

        // --- Panel Header (untuk judul "Player 2 Login") ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 30, 30)); // Menyesuaikan dengan latar belakang frame
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); // Padding atas dan bawah

        JLabel title = new JLabel("PLAYER 2 LOGIN"); // Huruf kapital untuk penekanan
        title.setFont(new Font("Arial", Font.BOLD, 36)); // Font lebih besar dan tebal
        title.setForeground(Color.WHITE); // Warna putih untuk judul
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH); // Menambahkan panel header ke bagian atas frame

        // --- Panel Form Login (konten utama) ---
        JPanel formPanel = new JPanel(new GridBagLayout()); // Menggunakan GridBagLayout untuk kontrol posisi yang presisi
        formPanel.setBackground(new Color(45, 45, 45)); // Warna gelap sedikit lebih terang untuk latar belakang form
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80)); // Padding internal untuk form

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Padding antar komponen
        gbc.fill = GridBagConstraints.HORIZONTAL; // Membuat komponen mengisi ruang secara horizontal
        gbc.anchor = GridBagConstraints.CENTER; // Menempatkan komponen di tengah sel grid mereka

        // Label Username
        JLabel userLabel = new JLabel("USERNAME"); // Huruf kapital
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setForeground(new Color(200, 200, 200)); // Abu-abu terang untuk label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Mengatur label ke kiri
        formPanel.add(userLabel, gbc);

        // Field Username
        usernameField = new JTextField(25); // Ukuran field yang lebih sesuai
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setBackground(Color.WHITE); // Latar belakang field input putih
        usernameField.setForeground(Color.BLACK); // Teks hitam
        usernameField.setCaretColor(Color.BLACK); // Warna kursor hitam
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1), // Border abu-abu terang
            BorderFactory.createEmptyBorder(10, 15, 10, 15) // Padding internal
        ));
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Memungkinkan ekspansi horizontal
        gbc.anchor = GridBagConstraints.CENTER; // Menempatkan field di tengah
        formPanel.add(usernameField, gbc);

        // Label Password
        JLabel passLabel = new JLabel("PASSWORD"); // Huruf kapital
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setForeground(new Color(200, 200, 200));
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 10, 0); // Jarak lebih di atas label password
        gbc.anchor = GridBagConstraints.WEST; // Mengatur label ke kiri
        formPanel.add(passLabel, gbc);

        // Field Password
        passwordField = new JPasswordField(25); // Ukuran field yang lebih sesuai
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.BLACK);
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 30, 0); // Jarak lebih di bawah field password
        gbc.anchor = GridBagConstraints.CENTER; // Menempatkan field di tengah
        formPanel.add(passwordField, gbc);

        // --- Panel Tombol Login/Cancel (Side-by-Side) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Tengah, jarak 20px
        buttonPanel.setBackground(new Color(45, 45, 45)); // Latar belakang sama dengan formPanel

        // Login button
        JButton loginButton = new JButton("LOGIN"); // Huruf kapital
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(76, 175, 80)); // Hijau cerah
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(150, 45)); // Ukuran tombol konsisten
        buttonPanel.add(loginButton);
        loginButton.addActionListener(this::performLogin);

        // Cancel button
        JButton cancelButton = new JButton("CANCEL"); // Huruf kapital
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(220, 53, 69)); // Merah
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 45)); // Ukuran tombol konsisten
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(e -> {
            new DashboardFrame(player1).setVisible(true); // Kembali ke DashboardFrame
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Mengambil 2 kolom untuk panel tombol
        gbc.insets = new Insets(0, 0, 0, 0); // Tanpa jarak ekstra
        formPanel.add(buttonPanel, gbc);

        // Menambahkan formPanel ke panel wrapper untuk penempatan di tengah frame
        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(new Color(30, 30, 30)); // Menyesuaikan dengan latar belakang frame
        GridBagConstraints gbcWrapper = new GridBagConstraints();
        gbcWrapper.gridx = 0;
        gbcWrapper.gridy = 0;
        gbcWrapper.weightx = 1.0; // Memungkinkan wrapper untuk ekspansi horizontal
        gbcWrapper.weighty = 1.0; // Memungkinkan wrapper untuk ekspansi vertikal
        gbcWrapper.anchor = GridBagConstraints.CENTER; // Menempatkan formPanel di tengah
        centerWrapperPanel.add(formPanel, gbcWrapper);
        add(centerWrapperPanel, BorderLayout.CENTER); // Menambahkan wrapper ke bagian tengah frame
    }

    // Metode performLogin tetap sama seperti sebelumnya
    private void performLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(player1.getUsername())) {
            JOptionPane.showMessageDialog(this, "Player 2 must be different from Player 1");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) { // Asumsi kelas DBConnection ada
            String sql = "SELECT user_id, nickname FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User player2 = new User(
                    rs.getInt("user_id"),
                    username,
                    password, // Catatan: Menyimpan password di objek User umumnya tidak direkomendasikan untuk keamanan
                    rs.getString("nickname")
                );

                new GameTicTacToe(player1, player2).setVisible(true); // Asumsi GameTicTacToe ada
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
