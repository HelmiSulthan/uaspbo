package code;

import code.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class GameUlar extends JFrame {
    private final User user;
    private static final int TILE_SIZE = 25;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int GAME_SPEED = 150;
    
    private ArrayList<Point> snake;
    private Point food;
    private char direction = 'R';
    private boolean isRunning = false;
    private Timer timer;
    private int score = 0;
    private JLabel scoreLabel;
    private Image backgroundImage;
    
    public GameUlar(User user) {
        this.user = user;
        setTitle("Snake Game - " + user.getNickname());
        setSize(WIDTH * TILE_SIZE + 10, HEIGHT * TILE_SIZE + 70);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/assets/snakeboard.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error loading background image for Snake Game: " + e.getMessage());
            backgroundImage = null;
        }

        JPanel panel = new JPanel(new BorderLayout());
        
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(Color.WHITE); 
        panel.add(scoreLabel, BorderLayout.NORTH);
        
        GamePanel gamePanel = new GamePanel();
        panel.add(gamePanel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            new DashboardFrame(user).setVisible(true);
            dispose();
        });
        panel.add(backButton, BorderLayout.SOUTH);
        
        add(panel);
        
        startGame();
    }
    
    private void startGame() {
        snake = new ArrayList<>();
        snake.add(new Point(5, 5));
        snake.add(new Point(4, 5));
        snake.add(new Point(3, 5));
        
        spawnFood();
        direction = 'R';
        score = 0;
        scoreLabel.setText("Score: 0");
        isRunning = true;
        
        timer = new Timer(GAME_SPEED, e -> gameLoop());
        timer.start();
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (direction != 'D') direction = 'U';
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') direction = 'D';
                        break;
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') direction = 'L';
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') direction = 'R';
                        break;
                }
            }
        });
        
        setFocusable(true);
        requestFocusInWindow();
    }
    
    private void gameLoop() {
        if (!isRunning) return;
        
        // Move snake
        Point head = snake.get(0);
        Point newHead;
        
        switch (direction) {
            case 'U':
                newHead = new Point(head.x, head.y - 1);
                break;
            case 'D':
                newHead = new Point(head.x, head.y + 1);
                break;
            case 'L':
                newHead = new Point(head.x - 1, head.y);
                break;
            case 'R':
                newHead = new Point(head.x + 1, head.y);
                break;
            default:
                return;
        }
        
        // Check collision with walls
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT) {
            gameOver();
            return;
        }
        
      
        for (Point segment : snake) {
            if (segment.equals(newHead)) {
                gameOver();
                return;
            }
        }
        
        snake.add(0, newHead);
        
       
        if (newHead.equals(food)) {
            score += 10;
            scoreLabel.setText("Score: " + score);
            spawnFood();
        } else {
            snake.remove(snake.size() - 1);
        }
        
        repaint();
    }
    
    private void spawnFood() {
        Random random = new Random();
        int x, y;
        
        do {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
            food = new Point(x, y);
        } while (snake.contains(food));
    }
    
    private void gameOver() {
        isRunning = false;
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        saveScore();
    }
    
    private void saveScore() {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "INSERT INTO game_scores (user_id, game_name, score) VALUES (?, 'Snake Game', ?) " +
                    "ON DUPLICATE KEY UPDATE score = GREATEST(score, VALUES(score)), last_played = CURRENT_TIMESTAMP";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, user.getUserId());
        stmt.setInt(2, score);
        stmt.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error saving score: " + e.getMessage());
    }
}
    
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
           
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
            }
            // Draw snake
            for (Point p : snake) {
                g.setColor(Color.GREEN);
                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.BLACK); // Border snake
                g.drawRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
            // Draw food
            if (food != null) {
                g.setColor(Color.RED);
                g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            } 
            g.setColor(new Color(50, 50, 50, 100));
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    g.drawRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
}