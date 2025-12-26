# 📑 Kelasync – Aplikasi Manajemen Kursus

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge&logo=mysql)

Kelasync adalah aplikasi desktop berbasis Java Swing yang digunakan untuk mengelola data kursus, peserta, pengajar, dan pendaftaran.  
Aplikasi ini menyediakan fitur pengolahan data terintegrasi dengan database MySQL melalui mekanisme CRUD dan tampilan tabel berbasis JTable.

Proyek ini dikembangkan sebagai Tugas Besar Praktikum Pemrograman II Tahun 2025.

---

## 🔎 Project Overview

Kelasync dirancang untuk membantu proses pengelolaan data kursus secara terstruktur dan terkomputerisasi.  
Aplikasi ini menitikberatkan pada pemisahan logika program menggunakan arsitektur Model–View–Controller (MVC) agar mudah dikembangkan dan dipelihara.

---

## ⚙️ Fitur Aplikasi

Fitur utama yang tersedia dalam aplikasi Kelasync meliputi:
- Pengelolaan data kursus (CRUD)
- Pengelolaan data peserta (CRUD)
- Pengelolaan data pengajar (CRUD)
- Pengelolaan data pendaftaran (CRUD)
- Validasi input data
- Tampilan data berbasis tabel (JTable)
- Export laporan PDF:
   1. Seluruh pendaftaran kursus
   2. Jadwal kursus per peserta

---

## 🗃️ Prasyarat Sistem

Sebelum menjalankan aplikasi, pastikan sistem telah memenuhi kebutuhan berikut:
- Java Development Kit (JDK) 21 atau versi yang kompatibel
- MySQL Server (XAMPP/Laragon)
- phpMyAdmin
- NetBeans IDE

---

## 🛠️ Teknologi yang Digunakan

| Komponen  | Teknologi |
|----------|----------|
| Bahasa Pemrograman | Java |
| Database | MySQL |
| PDF Library | OpenPDF |
| Antarmuka | Java Swing |
| IDE | NetBeans |

---

## 🗝️ Panduan Instalasi dan Penggunaan

Ikuti langkah-langkah berikut untuk menjalankan aplikasi Kelasync menggunakan Java Maven di NetBeans.

### Langkah 1 – Membuat Project Baru di NetBeans
1. Buka **NetBeans IDE**
2. Pilih menu **File → New Project**
3. Pada **Categories**, pilih **Java with Maven**
4. Pada **Projects**, pilih **Java Application**
5. Klik **Next**
6. Isi konfigurasi project sebagai berikut:
```bash
   Project Name : Kelasync  
   Group ID     : id.rancangrupa  
```
7. Klik **Finish**

### Langkah 2 – Menambahkan Source Code
1. Buka folder project **Kelasync**
2. Masuk ke direktori: `src/main/java/id/rancangrupa/kelasync`
3. Salin seluruh isi source code dari repository GitHub ke dalam folder tersebut
4. Pastikan struktur package utama sesuai dan tidak terjadi error pada NetBeans
```bash
   Kelasync/src/main/java/id/rancangrupa/
   ├── kelasync/
   │ ├── controller/
   │ │ ├── KursusController.java
   │ │ ├── PendaftaranController.java
   │ │ ├── PengajarController.java
   │ │ ├── PesertaController.java
   │ ├── db/
   │ │ ├── kelasync.sql
   │ ├── model/
   │ │ ├── Kursus.java
   │ │ ├── Pendaftaran.java
   │ │ ├── Pengajar.java
   │ │ ├── Peserta.java
   │ ├── util/
   │ │ ├── DBConnection.java
   │ ├── view/
   │ │ ├── KursusView.java
   │ │ ├── PendaftaranView.java
   │ │ ├── PengajarView.java
   │ │ ├── PesertaView.java
   └── Main.java
```

### Langkah 3 – Setup Database
1. Jalankan MySQL Server (XAMPP/Laragon)
2. Buka **phpMyAdmin**
3. Buat database baru dengan nama: `kelasync_db`
4. Import file database: `kelasync.sql`
File tersedia di dalam repository proyek di folder db

### Langkah 4 – Menambahkan Dependencies MySQL (Maven)
1. Klik kanan pada folder **Dependencies** di project NetBeans
2. Pilih **Add Dependency**
3. Tambahkan dependency MySQL:
```bash
   Group ID    : com.mysql
   Artifact ID : mysql-connector-j
   Version  : 9.5.0
```
4. Klik **Add**
5. Tambahkan dependency OpenPDF untuk fitur export PDF:
```bash
   Group ID    : com.github.librepdf
   Artifact ID : openpdf
   Version     : 1.3.30
```
6. Klik **Add**
7. Tunggu hingga Maven selesai mengunduh dependency

### Langkah 5 – Menjalankan Aplikasi
1. Buka file: Main.java
2. Jalankan aplikasi dengan menekan tombol **Run**
3. Aplikasi Kelasync akan tampil dan siap digunakan

### 🗒️ Catatan Tambahan
Semua file PDF yang dihasilkan oleh fitur export laporan akan otomatis disimpan di folder `reports` yang berada di root project:
```bash
   Kelasync/reports
```
Folder `reports` akan dibuat secara otomatis oleh aplikasi jika belum ada.

---

## 👥 Tim Pengembang

Aplikasi Kelasync dikembangkan oleh Kelompok RancangRupa pada Praktikum Pemrograman II Kelas B Tahun 2025, dengan pembagian pengembangan berdasarkan modul dan entitas sistem.

| NIM  | Nama | GitHub Profile | 
|----------|----------|----------|
| `233040065` | Emeralda Iffatud Diana | [Diana-museo](https://github.com/Diana-museo) |
| `233040051` | Haifa Zahirah Ramdhan | [haifazahirahr](https://github.com/haifazahirahr) |
| `233040061` | Ellen Aplida Zalni | [twenteazfour](https://github.com/twenteazfour) |
| `233040086` | Iqbal Nurfikri | [MangEakLur](https://github.com/MangEakLur) |