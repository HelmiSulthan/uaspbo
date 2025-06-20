package code.model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String nickname;

    public User(int userId, String username, String password, String nickname) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }


    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; } 
    public String getNickname() { return nickname; }


    public void setPassword(String password) { this.password = password; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}