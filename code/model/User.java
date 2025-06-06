package code.model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String nickname;
    // Atribut is_admin tidak perlu ada di sini karena akan ditangani saat pembuatan objek Admin
    // atau di database secara langsung.

    public User(int userId, String username, String password, String nickname) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    // Getter methods
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; } // Hati-hati dengan ini, password plaintext tidak disarankan
    public String getNickname() { return nickname; }

    // Setter methods
    public void setPassword(String password) { this.password = password; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}