GameCenter

GameCenter adalah aplikasi desktop berbasis Java GUI yang memungkinkan pengguna untuk mendaftar, login, bermain game kasual, melacak skor, dan melihat papan peringkat. Aplikasi ini juga dilengkapi dengan panel administrasi untuk mengelola pengguna dan data skor.

Fitur Utama :
  - Sistem Login & Registrasi Multi-Role: Pengguna dapat membuat akun baru dan login sebagai pengguna biasa atau administrator. 
  - Dashboard Pengguna Interaktif: Menampilkan skor pribadi pengguna untuk setiap game dan papan peringkat global.
  - Koleksi Game:
  -Tic Tac Toe: Permainan 2 pemain di papan 15x15 dengan pelacakan kemenangan.
  -Snake Game: Permainan 1 pemain dengan pelacakan skor tertinggi.
  -Manajemen Skor Otomatis: Skor game disimpan dan diperbarui secara otomatis di database.
  -Papan Peringkat (Leaderboards): Menampilkan pemain-pemain teratas untuk setiap game.
  -Dashboard Admin: Administrator dapat melihat, mengelola (menghapus), dan mengedit data pengguna serta skor game melalui antarmuka tabel.
  -Koneksi Database MySQL: Seluruh data pengguna dan skor disimpan secara persisten di database MySQL.

Konsep Pemrograman Berorientasi Objek (OOP) yang Diterapkan
Proyek ini dirancang dengan kuat berdasarkan prinsip-prinsip OOP untuk memastikan modularitas, skalabilitas, dan kemudahan pemeliharaan:
- Class dan Object: Setiap entitas kunci dalam sistem (seperti User, Score) dan setiap komponen GUI (misalnya, LoginFrame, GameTicTacToe) direpresentasikan sebagai sebuah kelas, yang kemudian diinstansiasi menjadi objek saat aplikasi berjalan.
- Enkapsulasi (Encapsulation): Data sensitif dalam kelas model (seperti username dan password di User.java) dideklarasikan sebagai private dan hanya dapat diakses atau dimodifikasi melalui metode getter dan setter publik. Ini melindungi integritas data dan mengontrol akses.
- Pewarisan (Inheritance): Konsep inheritance diterapkan dengan adanya kelas Admin.java yang extends User.java. Ini secara eksplisit menyatakan bahwa seorang Admin adalah jenis User yang memiliki semua properti dan perilaku dasar User, namun dengan peran dan hak akses yang terspesialisasi.
- Polimorfisme (Polymorphism): Dengan User dan Admin: Objek User dan Admin dapat diperlakukan secara polimorfik di LoginFrame, di mana keputusan untuk menampilkan DashboardFrame atau AdminFrame dibuat berdasarkan jenis objek aktual pada runtime.
- Penanganan Pengecualian (Exception Handling): Aplikasi menggunakan blok try-catch secara ekstensif, terutama untuk operasi database (seperti SQLException) dan penanganan input pengguna yang tidak valid, memastikan aplikasi tetap stabil dan memberikan feedback yang relevan saat terjadi kesalahan.
- Modularisasi Kode: Kode diatur ke dalam package yang logis (code/ untuk logika aplikasi dan GUI, code/model/ untuk model data), mempromosikan pemisahan tanggung jawab dan kode yang lebih bersih.

KONTRIBUTOR
- MUHAMAD ALI AKBAR
- HELMI SULTHAN MUHAMMAD
- RESTU NUR ALBAR
- ABIZARD ZUFAR FATAHILLAH
