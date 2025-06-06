package code.model;
import java.sql.Timestamp;

public class Score {
    private int scoreId;
    private int userId;
    private String gameName;
    private int score;
    private Timestamp lastPlayed;
    
    public Score(int scoreId, int userId, String gameName, int score, Timestamp lastPlayed) {
        this.scoreId = scoreId;
        this.userId = userId;
        this.gameName = gameName;
        this.score = score;
        this.lastPlayed = lastPlayed;
    }
    
    // Getter methods
    public int getScoreId() { return scoreId; }
    public int getUserId() { return userId; }
    public String getGameName() { return gameName; }
    public int getScore() { return score; }
    public Timestamp getLastPlayed() { return lastPlayed; }
}