package code;

public class Main {
    public static void main(String[] args) {
        // Initialize database connection
        try {
            DBConnection.getConnection();
            System.out.println("DATABASE TERHUBUNG");
        } catch (Exception e) {
            System.err.println("GAGAL MENYAMBUNG DATABASE: " + e.getMessage());
            return;
        }
        
        // Start the application
        new LoginFrame().setVisible(true);
    }
}