package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField, nicknameField;
    private JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("Game Center - Register"); 
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 

        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        JPanel topHeaderPanel = new JPanel();
        topHeaderPanel.setBackground(new Color(30, 30, 30));
        topHeaderPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel topTitleLabel = new JLabel("Game Center"); 
        topTitleLabel.setFont(new Font("Arial", Font.BOLD, 36)); 
        topTitleLabel.setForeground(Color.WHITE); 
        topHeaderPanel.add(topTitleLabel);
        add(topHeaderPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout()); 
        contentPanel.setBackground(new Color(30, 30, 30)); 

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 100, 10, 100); 
        
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        
    
        gbc.anchor = GridBagConstraints.CENTER; 

        // --- Username Field ---
        usernameField = new JTextField("Username", 25); 
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setBackground(Color.WHITE); 
        usernameField.setForeground(Color.GRAY);
        usernameField.setCaretColor(Color.BLACK);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        usernameField.setPreferredSize(new Dimension(300, 45)); 

        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Username");
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
     
        gbc.weightx = 1.0; 
        contentPanel.add(usernameField, gbc);

        // --- Password Field ---
        passwordField = new JPasswordField("Password", 25); 
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.GRAY);
        passwordField.setCaretColor(Color.BLACK);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        // --- PENTING: setPreferredSize() untuk Password Field ---
        passwordField.setPreferredSize(new Dimension(300, 45)); 

        passwordField.setEchoChar((char)0); 
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*'); 
                    passwordField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Password");
                    passwordField.setEchoChar((char)0); 
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridy = 1;
        // mengatur jarak antara field password dan username
        gbc.insets = new Insets(15, 100, 15, 100); 
        contentPanel.add(passwordField, gbc);
        
        // --- Nickname Field ---
        nicknameField = new JTextField("Nickname", 25); 
        nicknameField.setFont(new Font("Arial", Font.PLAIN, 18));
        nicknameField.setBackground(Color.WHITE);
        nicknameField.setForeground(Color.GRAY);
        nicknameField.setCaretColor(Color.BLACK);
        nicknameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        //setPreferredSize() untuk Nickname Field ---
        nicknameField.setPreferredSize(new Dimension(300, 45)); 

        nicknameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nicknameField.getText().equals("Nickname")) {
                    nicknameField.setText("");
                    nicknameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (nicknameField.getText().isEmpty()) {
                    nicknameField.setText("Nickname");
                    nicknameField.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridy = 2;
        // mengatur jarak antara field nickname dan password, serta jarak di bawah field terakhir
        gbc.insets = new Insets(15, 100, 30, 100); 
        contentPanel.add(nicknameField, gbc);


        // --- Panel Tombol Register/Back
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); 
        buttonPanel.setBackground(new Color(30, 30, 30)); 

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setBackground(new Color(76, 175, 80)); 
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(150, 45)); 
        buttonPanel.add(registerButton);
        registerButton.addActionListener(this::performRegister);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(new Color(255, 165, 0)); 
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(150, 45)); 
        buttonPanel.add(backButton);
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 3; 
        gbc.gridwidth = 1; 
        gbc.insets = new Insets(0, 0, 0, 0); 
        contentPanel.add(buttonPanel, gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void performRegister(ActionEvent e) {
        String username = usernameField.getText().trim();
        if (username.equals("Username")) username = ""; 
        
        String password = new String(passwordField.getPassword()).trim();
        if (password.equals("Password")) password = "";

        String nickname = nicknameField.getText().trim();
        if (nickname.equals("Nickname")) nickname = "";

        if (username.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi semua kolom!!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) { 
            String checkSql = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);

            if (checkStmt.executeQuery().next()) {
                JOptionPane.showMessageDialog(this, "Username sudah tersedia");
                return;
            }

            String insertSql = "INSERT INTO users (username, password, nickname) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password); 
            insertStmt.setString(3, nickname);

            int affectedRows = insertStmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil");
                new LoginFrame().setVisible(true);
                dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
