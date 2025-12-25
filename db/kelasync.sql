-- 1. Langkah Pertama : Buat database
CREATE DATABASE kelasync_db;

-- 2. Langkah Kedua : Buat table
USE kelasync_db;
CREATE TABLE peserta (
    id_peserta INT AUTO_INCREMENT PRIMARY KEY,
    nama_peserta VARCHAR(100) NOT NULL,
    no_hp_peserta VARCHAR(15) NOT NULL,
    alamat_peserta TEXT
);

CREATE TABLE pengajar (
    id_pengajar INT AUTO_INCREMENT PRIMARY KEY,
    nama_pengajar VARCHAR(100) NOT NULL,
    keahlian_pengajar VARCHAR(100) NOT NULL,
    no_hp_pengajar VARCHAR(15) NOT NULL,
    alamat_pengajar TEXT
);

CREATE TABLE kursus (
    id_kursus INT AUTO_INCREMENT PRIMARY KEY,
    nama_kursus VARCHAR(100) NOT NULL,
    materi TEXT,
    hari ENUM('Senin','Selasa','Rabu','Kamis','Jumat','Sabtu','Minggu') NOT NULL,
    jam_mulai TIME NOT NULL,
    jam_selesai TIME NOT NULL,
    id_pengajar INT NOT NULL,
    CONSTRAINT fk_kursus_pengajar
        FOREIGN KEY (id_pengajar)
        REFERENCES pengajar(id_pengajar)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE pendaftaran (
    id_pendaftaran INT AUTO_INCREMENT PRIMARY KEY,
    id_peserta INT NOT NULL,
    id_kursus INT NOT NULL,
    tanggal_daftar DATE NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT fk_pendaftaran_peserta
        FOREIGN KEY (id_peserta)
        REFERENCES peserta(id_peserta)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_pendaftaran_kursus
        FOREIGN KEY (id_kursus)
        REFERENCES kursus(id_kursus)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- 3. Langkah Ketiga : Buat dummy data
INSERT INTO peserta (nama_peserta, no_hp_peserta, alamat_peserta) VALUES
('Andi Pratama', '081234567890', 'Jl. Merdeka No.17, Bandung'),
('Siti Rahma', '082345678901', 'Jl. Elang No.08, Bandung'),
('Budi Santoso', '083456789012', 'Jl. Palugada No. 45, Bandung');

INSERT INTO pengajar (nama_pengajar, keahlian_pengajar, no_hp_pengajar, alamat_pengajar) VALUES
('Dewi Lestari', 'Matematika', '081111111111', 'Jl. Balikpapan No.16, Bandung'),
('Rizky Ananda', 'Bahasa Inggris', '082222222222', 'Jl. Sumatera No.20, Bandung');

INSERT INTO kursus (nama_kursus, materi, hari, jam_mulai, jam_selesai, id_pengajar) VALUES
('Matematika Dasar', 'Aljabar dan aritmatika dasar', 'Senin', '16:00:00', '17:30:00', 1),
('English Conversation', 'Latihan percakapan bahasa Inggris', 'Rabu', '18:00:00', '19:30:00', 2);

INSERT INTO pendaftaran (id_peserta, id_kursus, tanggal_daftar) VALUES
(1, 1, '2025-12-01'),
(2, 1, '2025-12-01'),
(3, 2, '2025-12-02'),
(1, 2, '2025-12-03');