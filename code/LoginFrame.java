package code;

import code.model.User; // Asumsi kelas User ada dan terstruktur dengan benar
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        // Mengatur judul frame dan properti dasar
        setTitle("Game Center Login");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Menempatkan frame di tengah layar

        // Mengatur warna latar belakang untuk seluruh frame agar sesuai dengan gambar pertama
        // Gambar pertama memiliki latar belakang gelap, bukan terbagi dua panel
        getContentPane().setBackground(new Color(30, 30, 30)); // Warna abu-abu sangat gelap

        // Menggunakan BorderLayout untuk menempatkan panel utama di tengah
        setLayout(new BorderLayout());

        // --- Panel Utama Konten (menggantikan mainPanel, leftPanel, rightPanel) ---
        // Panel ini akan menampung judul dan form login, dipusatkan di frame
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout()); // Menggunakan GridBagLayout untuk penempatan komponen yang presisi
        contentPanel.setBackground(new Color(30, 30, 30)); // Latar belakang gelap yang sama

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Padding antar komponen
        gbc.fill = GridBagConstraints.HORIZONTAL; // Membuat komponen mengisi ruang secara horizontal
        gbc.anchor = GridBagConstraints.CENTER; // Menempatkan komponen di tengah sel grid mereka

        // --- Judul "Game Center" ---
        JLabel titleLabel = new JLabel("Game Center");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Font lebih besar dan tebal
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Mengambil 2 kolom untuk judul
        gbc.insets = new Insets(0, 0, 40, 0); // Jarak lebih besar di bawah judul
        contentPanel.add(titleLabel, gbc);

        // --- Form Login ---
        // Mengatur ulang gbc untuk komponen form
        gbc.gridwidth = 1; // Kembali ke 1 kolom
        gbc.insets = new Insets(10, 0, 5, 0); // Padding untuk label

        // Username Label (opsional, karena gambar pertama memiliki placeholder)
        // Untuk Java Swing, kita tetap pakai JLabel di atas field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE); // Warna teks putih
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; // Rata kiri
        contentPanel.add(usernameLabel, gbc);

        // Username Field
        usernameField = new JTextField(25); // Ukuran field yang lebih sesuai
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setBackground(Color.WHITE); // Latar belakang putih
        usernameField.setForeground(Color.BLACK); // Teks hitam
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1), // Border abu-abu terang
            BorderFactory.createEmptyBorder(10, 15, 10, 15) // Padding internal
        ));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0); // Jarak di bawah field
        gbc.anchor = GridBagConstraints.CENTER; // Pusatkan
        contentPanel.add(usernameField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 5, 0); // Padding untuk label
        gbc.anchor = GridBagConstraints.WEST; // Rata kiri
        contentPanel.add(passwordLabel, gbc);

        // Password Field
        passwordField = new JPasswordField(25); // Ukuran field yang lebih sesuai
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.BLACK);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 30, 0); // Jarak di bawah field
        gbc.anchor = GridBagConstraints.CENTER; // Pusatkan
        contentPanel.add(passwordField, gbc);

        // --- Panel Tombol Login/Register (Side-by-Side) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Tengah, jarak 20px
        buttonPanel.setBackground(new Color(30, 30, 30)); // Latar belakang sama dengan contentPanel

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(255, 165, 0)); // Oranye
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(150, 45)); // Ukuran tombol
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        // Untuk efek rounded corner, perlu custom UI atau set BorderFactory.createEmptyBorder
        // dan menggambar shape di paintComponent. Untuk kesederhanaan, kita biarkan kotak.
        buttonPanel.add(loginButton);
        loginButton.addActionListener(this::performLogin);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setBackground(new Color(76, 175, 80)); // Hijau
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(150, 45)); // Ukuran tombol
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        buttonPanel.add(registerButton);
        registerButton.addActionListener(e -> {
            new RegisterFrame().setVisible(true); // Asumsi RegisterFrame ada
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Mengambil 2 kolom untuk panel tombol
        gbc.insets = new Insets(0, 0, 0, 0); // Tanpa jarak ekstra
        contentPanel.add(buttonPanel, gbc);

        // Menambahkan contentPanel ke tengah frame
        add(contentPanel, BorderLayout.CENTER);
    }

    // Metode performLogin tetap sama seperti sebelumnya
    private void performLogin(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password harus diisi");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) { // Asumsi kelas DBConnection ada
            String sql = "SELECT user_id, username, nickname, is_admin FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    password, // Catatan: Menyimpan password di objek User umumnya tidak direkomendasikan untuk keamanan
                    rs.getString("nickname")
                );

                boolean isAdmin = rs.getBoolean("is_admin");

                if (isAdmin) {
                    new AdminFrame(user).setVisible(true); // Asumsi AdminFrame ada
                } else {
                    new DashboardFrame(user).setVisible(true); // Asumsi DashboardFrame ada
                }

                dispose(); // Menutup frame login setelah berhasil
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error koneksi database: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Metode main untuk pengujian (opsional, biasanya di kelas Main terpisah)
    public static void main(String[] args) {
        // Memastikan pembaruan UI Swing dilakukan di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
