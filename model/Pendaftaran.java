package id.rancangrupa.kelasync.model;

import java.time.LocalDate;

// MODEL: Pendaftaran
// Merepresentasikan data pendaftaran kursus pada sistem Kelasync 
// dari tabel `pendaftaran` di database

public class Pendaftaran {

    private int idPendaftaran; // Id Unik `id_pendaftaran`
    private int idPeserta; // Relasi dengan tabel `peserta`
    private int idKursus; // Relasi dengan tabel `kursus`
    private LocalDate tanggalDaftar; // Pakai LocalDate supaya aman dan konsisten

    // Konstruktor kosong
    // Dipakai saat objek dibuat tanpa data awal
    public Pendaftaran() {
    }

    // Konstruktor berparamater lengkap
    // Dipakai saat ambil data dari database
    public Pendaftaran(int idPendaftaran, int idPeserta, int idKursus, LocalDate tanggalDaftar) {
        this.idPendaftaran = idPendaftaran;
        this.idPeserta = idPeserta;
        this.idKursus = idKursus;
        this.tanggalDaftar = tanggalDaftar;
    }

    // Getter & Setter
    public int getIdPendaftaran() {
        return idPendaftaran;
    }

    public void setIdPendaftaran(int idPendaftaran) {
        this.idPendaftaran = idPendaftaran;
    }

    public int getIdPeserta() {
        return idPeserta;
    }

    public void setIdPeserta(int idPeserta) {
        this.idPeserta = idPeserta;
    }

    public int getIdKursus() {
        return idKursus;
    }

    public void setIdKursus(int idKursus) {
        this.idKursus = idKursus;
    }

    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(LocalDate tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }
}