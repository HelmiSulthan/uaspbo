package code;

import code.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class AdminFrame extends JFrame {
    private JTable usersTable;
    private JTable scoresTable;
    
    public AdminFrame(User adminUser) {
        setTitle("Admin Dashboard - Log-in sebagai " + adminUser.getNickname());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel utama dengan BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel untuk judul dan tombol logout di bagian atas
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Admin Dashboard - Log-in sebagai " + adminUser.getNickname(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true); // Kembali ke LoginFrame
            dispose(); // Tutup AdminFrame saat logout
        });
        JPanel logoutButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Untuk menempatkan tombol di kanan
        logoutButtonPanel.add(logoutButton);
        topPanel.add(logoutButtonPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Tab 1: Manage Users
        JPanel usersPanel = new JPanel(new BorderLayout());
        usersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton refreshUsersBtn = new JButton("Refresh");
        refreshUsersBtn.addActionListener(e -> loadUsersData());
        
        JButton deleteUserBtn = new JButton("Hapus user yang dipilih");
        deleteUserBtn.addActionListener(this::deleteSelectedUser);
        
        JPanel usersButtonPanel = new JPanel();
        usersButtonPanel.add(refreshUsersBtn);
        usersButtonPanel.add(deleteUserBtn);
        
        usersPanel.add(usersButtonPanel, BorderLayout.NORTH);
        
        usersTable = new JTable();
        usersPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        
        tabbedPane.addTab("Users", usersPanel);
        
        // Tab 2: Manage Scores
        JPanel scoresPanel = new JPanel(new BorderLayout());
        scoresPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton refreshScoresBtn = new JButton("Refresh");
        refreshScoresBtn.addActionListener(e -> loadScoresData());
        
        JButton editScoreBtn = new JButton("Edit Score");
        editScoreBtn.addActionListener(this::editSelectedScore);
        
        JButton deleteScoreBtn = new JButton("Delete Score");
        deleteScoreBtn.addActionListener(this::deleteSelectedScore);
        
        JPanel scoresButtonPanel = new JPanel();
        scoresButtonPanel.add(refreshScoresBtn);
        scoresButtonPanel.add(editScoreBtn);
        scoresButtonPanel.add(deleteScoreBtn);
        
        scoresPanel.add(scoresButtonPanel, BorderLayout.NORTH);
        
        scoresTable = new JTable();
        scoresPanel.add(new JScrollPane(scoresTable), BorderLayout.CENTER);
        
        tabbedPane.addTab("Scores", scoresPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER); // Tambahkan tabbedPane ke CENTER mainPanel
        add(mainPanel); // Tambahkan mainPanel ke JFrame
        
        loadUsersData();
        loadScoresData();
    }
    
    private void loadUsersData() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, username, nickname, is_admin FROM users";
            Statement stmt = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = stmt.executeQuery(sql);
            
            usersTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage());
            e.printStackTrace();
            // Fallback - create empty table model
            usersTable.setModel(new DefaultTableModel());
        }
    }
    
    private void loadScoresData() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT gs.score_id, u.username, u.nickname, gs.game_name, gs.score, gs.last_played " +
                    "FROM game_scores gs JOIN users u ON gs.user_id = u.user_id";
            Statement stmt = conn.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = stmt.executeQuery(sql);
        
            scoresTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading scores: " + e.getMessage());
            e.printStackTrace();
            // Fallback - create empty table model
            scoresTable.setModel(new DefaultTableModel());
        }
    }
    
    private void deleteSelectedUser(ActionEvent e) {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin di hapus");
            return;
        }
        
        int userId = (Integer) usersTable.getValueAt(selectedRow, 0);
        String username = (String) usersTable.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Apakh yakin menghapus '" + username + "' ?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                // Delete scores first (due to foreign key constraint)
                String deleteScoresSql = "DELETE FROM game_scores WHERE user_id = ?";
                PreparedStatement deleteScoresStmt = conn.prepareStatement(deleteScoresSql);
                deleteScoresStmt.setInt(1, userId);
                deleteScoresStmt.executeUpdate();
                
                // Then delete user
                String deleteUserSql = "DELETE FROM users WHERE user_id = ?";
                PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserSql);
                deleteUserStmt.setInt(1, userId);
                
                int affectedRows = deleteUserStmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Menghapus user berhasil!");
                    loadUsersData();
                    loadScoresData();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage());
            }
        }
    }
    
    private void editSelectedScore(ActionEvent e) {
        int selectedRow = scoresTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "pilih score yang ingin anda edit");
            return;
        }
        
        int scoreId = (Integer) scoresTable.getValueAt(selectedRow, 0);
        String gameName = (String) scoresTable.getValueAt(selectedRow, 3);
        int currentScore = (Integer) scoresTable.getValueAt(selectedRow, 4);
        
        String newScoreStr = JOptionPane.showInputDialog(
            this, 
            "Edit score for " + gameName + ":\nCurrent score: " + currentScore,
            currentScore
        );
        
        if (newScoreStr != null && !newScoreStr.isEmpty()) {
            try {
                int newScore = Integer.parseInt(newScoreStr);
                
                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "UPDATE game_scores SET score = ? WHERE score_id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, newScore);
                    stmt.setInt(2, scoreId);
                    
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Score berhasil di update!");
                        loadScoresData();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error update score: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukan nomor yang benar");
            }
        }
    }
    
    private void deleteSelectedScore(ActionEvent e) {
        int selectedRow = scoresTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih Score yang ingin di hapus");
            return;
        }
        
        int scoreId = (Integer) scoresTable.getValueAt(selectedRow, 0);
        String gameName = (String) scoresTable.getValueAt(selectedRow, 3);
        int score = (Integer) scoresTable.getValueAt(selectedRow, 4);
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Hapus score untuk " + gameName + ": " + score + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM game_scores WHERE score_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, scoreId);
                
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Score deleted successfully");
                    loadScoresData();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting score: " + ex.getMessage());
            }
        }
    }
}