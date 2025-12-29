/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.rancangrupa.kelasync.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class Kursus {
    private int idKursus;
    private String namaKursus;       
    private String materi;          
    private String hari;            
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private int idPengajar;         

    public static final List<String> HARI_VALID = Arrays.asList(
            "Senin","Selasa","Rabu","Kamis","Jumat","Sabtu","Minggu"
    );

    public Kursus() {}

    public Kursus(int idKursus, String namaKursus, String materi, String hari, LocalTime jamMulai, LocalTime jamSelesai, int idPengajar) {
        this.idKursus = idKursus;
        this.namaKursus = namaKursus;
        this.materi = materi;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.idPengajar = idPengajar;
    }

    // constructor tanpa id (untuk insert)
    public Kursus(String namaKursus, String materi, String hari, LocalTime jamMulai, LocalTime jamSelesai, int idPengajar) {
        this(0, namaKursus, materi, hari, jamMulai, jamSelesai, idPengajar);
    }

    // --- getters & setters ---
    public int getIdKursus() { return idKursus; }
    public void setIdKursus(int idKursus) { this.idKursus = idKursus; }

    public String getNamaKursus() { return namaKursus; }
    public void setNamaKursus(String namaKursus) { this.namaKursus = namaKursus; }

    public String getMateri() { return materi; }
    public void setMateri(String materi) { this.materi = materi; }

    public String getHari() { return hari; }
    public void setHari(String hari) { this.hari = hari; }

    public LocalTime getJamMulai() { return jamMulai; }
    public void setJamMulai(LocalTime jamMulai) { this.jamMulai = jamMulai; }

    public LocalTime getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(LocalTime jamSelesai) { this.jamSelesai = jamSelesai; }

    public int getIdPengajar() { return idPengajar; }
    public void setIdPengajar(int idPengajar) { this.idPengajar = idPengajar; }

    // --- util ---
    @Override
    public String toString() {
        return "Kursus{" +
                "idKursus=" + idKursus +
                ", namaKursus='" + namaKursus + '\'' +
                ", materi='" + materi + '\'' +
                ", hari='" + hari + '\'' +
                ", jamMulai=" + jamMulai +
                ", jamSelesai=" + jamSelesai +
                ", idPengajar=" + idPengajar +
                '}';
    }

    public void validateOrThrow() {
        if (namaKursus == null || namaKursus.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama kursus harus diisi");
        }
        if (hari == null || !HARI_VALID.contains(hari)) {
            throw new IllegalArgumentException("Hari tidak valid. Pilih salah satu: " + HARI_VALID);
        }
        if (jamMulai == null || jamSelesai == null) {
            throw new IllegalArgumentException("Jam mulai dan jam selesai harus diisi");
        }
        if (!jamMulai.isBefore(jamSelesai)) {
            throw new IllegalArgumentException("Jam mulai harus lebih awal dari jam selesai");
        }
    }
}
