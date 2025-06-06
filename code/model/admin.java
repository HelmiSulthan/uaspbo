package code.model;

public class Admin extends User {
    // Konstruktor untuk Admin
    // Admin secara default akan memiliki isAdmin = true
    public Admin(int userId, String username, String password, String nickname) {
        super(userId, username, password, nickname);
        // Karena ini adalah kelas Admin, kita bisa secara implisit tahu dia admin.
        // Di database, kolom is_admin akan diset true untuk user ini.
        // Tidak perlu menambahkan atribut isAdmin di sini jika sudah ditangani di level database.
    }

    // Anda bisa menambahkan metode atau atribut khusus Admin di sini jika diperlukan di masa depan.
    // Misalnya, void manageUsers() {}, void generateReports() {}, dll.
}