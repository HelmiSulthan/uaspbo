package code;

import code.model.User;
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
        setTitle("Game Center - Login Player 2");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Mengatur warna latar belakang untuk seluruh frame
        getContentPane().setBackground(new Color(30, 30, 30));

        // Menggunakan BorderLayout untuk frame utama: judul di atas, form di tengah
        setLayout(new BorderLayout());

        // panel player 2 login
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 30, 30)); 
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JLabel title = new JLabel("PLAYER 2 LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        // --- Panel Form Login (konten utama) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(45, 45, 45)); 
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER; 

        // Label Username
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setForeground(new Color(200, 200, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; 
        formPanel.add(userLabel, gbc);

        // Field Username
        usernameField = new JTextField(25); 
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setBackground(Color.WHITE); 
        usernameField.setForeground(Color.BLACK); 
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1), 
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        gbc.gridy = 1;
        gbc.weightx = 1.0; 
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(usernameField, gbc);

        // Label Password
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setForeground(new Color(200, 200, 200));
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passLabel, gbc);

        // Field Password
        passwordField = new JPasswordField(25);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.BLACK);
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 30, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(passwordField, gbc);

        // Panel Tombol Login/Cancel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); 
        buttonPanel.setBackground(new Color(45, 45, 45)); 

        // Login button
        JButton loginButton = new JButton("LOGIN"); 
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(76, 175, 80)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(150, 45)); 
        buttonPanel.add(loginButton);
        loginButton.addActionListener(this::performLogin);

        // Cancel button
        JButton cancelButton = new JButton("CANCEL"); 
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(220, 53, 69)); 
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(new Dimension(150, 45)); 
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(e -> {
            new DashboardFrame(player1).setVisible(true);
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        formPanel.add(buttonPanel, gbc);

        // Menambahkan formPanel ke panel wrapper untuk penempatan di tengah frame
        JPanel centerWrapperPanel = new JPanel(new GridBagLayout());
        centerWrapperPanel.setBackground(new Color(30, 30, 30));
        GridBagConstraints gbcWrapper = new GridBagConstraints();
        gbcWrapper.gridx = 0;
        gbcWrapper.gridy = 0;
        gbcWrapper.weightx = 1.0;
        gbcWrapper.weighty = 1.0;
        gbcWrapper.anchor = GridBagConstraints.CENTER;
        centerWrapperPanel.add(formPanel, gbcWrapper);
        add(centerWrapperPanel, BorderLayout.CENTER);
    }

    // Metode performLogin tetap sama seperti sebelumnya
    private void performLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(player1.getUsername())) {
            JOptionPane.showMessageDialog(this, "Player 2 must be different from Player 1");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) { 
            String sql = "SELECT user_id, nickname FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User player2 = new User(
                    rs.getInt("user_id"),
                    username,
                    password,
                    rs.getString("nickname")
                );

                new GameTicTacToe(player1, player2).setVisible(true); 
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
