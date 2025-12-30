package id.rancangrupa.kelasync.model;

public class Pengajar {
    public int id_pengajar;
    public String nama_pengajar;
    public String keahlian_pengajar;
    public String no_hp_pengajar;
    public String alamat_pengajar;

    public Pengajar() {}

    public Pengajar(int id, String nama, String keahlian, String noHp, String alamat) {
        this.id_pengajar = id;
        this.nama_pengajar = nama;
        this.keahlian_pengajar = keahlian;
        this.no_hp_pengajar = noHp;
        this.alamat_pengajar = alamat;
    }
}