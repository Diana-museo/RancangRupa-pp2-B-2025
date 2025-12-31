package id.rancangrupa.kelasync.model;

import java.time.LocalTime;

public class Kursus {

    // ---- Deklarasi Properti -----
    private int idKursus;          // ID kursus (primary key)
    private String namaKursus;     // Nama kursus
    private String materi;         // Materi kursus
    private String hari;           // Hari kursus
    private LocalTime jamMulai;    // Jam mulai kursus
    private LocalTime jamSelesai;  // Jam selesai kursus
    private int idPengajar;        // ID pengajar yang mengajar kursus

    // ---- Konstruktor Default -----
    public Kursus() {}

    // ---- Konstruktor Parameter -----
    public Kursus(int idKursus, String namaKursus, String materi, String hari, LocalTime jamMulai, LocalTime jamSelesai, int idPengajar) {
        this.idKursus = idKursus;
        this.namaKursus = namaKursus;
        this.materi = materi;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.idPengajar = idPengajar;
    }

    // ---- Getter dan Setter -----

    public int getIdKursus() { 
        return idKursus; 
    }

    public void setIdKursus(int idKursus) { 
        this.idKursus = idKursus; 
    }

    public String getNamaKursus() { 
        return namaKursus; 
    }

    public void setNamaKursus(String namaKursus) { 
        this.namaKursus = namaKursus; 
    }

    public String getMateri() { 
        return materi; 
    }

    public void setMateri(String materi) { 
        this.materi = materi; 
    }

    public String getHari() { 
        return hari; 
    }

    public void setHari(String hari) { 
        this.hari = hari; 
    }

    public LocalTime getJamMulai() { 
        return jamMulai; 
    }

    public void setJamMulai(LocalTime jamMulai) { 
        this.jamMulai = jamMulai; 
    }

    public LocalTime getJamSelesai() { 
        return jamSelesai; 
    }

    public void setJamSelesai(LocalTime jamSelesai) { 
        this.jamSelesai = jamSelesai; 
    }

    public int getIdPengajar() { 
        return idPengajar; 
    }

    public void setIdPengajar(int idPengajar) { 
        this.idPengajar = idPengajar; 
    }
}
