package id.rancangrupa.kelasync.model;

public class Peserta {

    private int idPeserta;
    private String namaPeserta;
    private String noHpPeserta;
    private String alamatPeserta;

    // constructor kosong
    public Peserta() {
    }

    // constructor lengkap
    public Peserta(int idPeserta, String namaPeserta, String noHpPeserta, String alamatPeserta) {
        this.idPeserta = idPeserta;
        this.namaPeserta = namaPeserta;
        this.noHpPeserta = noHpPeserta;
        this.alamatPeserta = alamatPeserta;
    }

    // constructor tanpa id (untuk INSERT)
    public Peserta(String namaPeserta, String noHpPeserta, String alamatPeserta) {
        this(0, namaPeserta, noHpPeserta, alamatPeserta);
    }

    // ===== getter & setter =====
    public int getIdPeserta() {
        return idPeserta;
    }

    public void setIdPeserta(int idPeserta) {
        this.idPeserta = idPeserta;
    }

    public String getNamaPeserta() {
        return namaPeserta;
    }

    public void setNamaPeserta(String namaPeserta) {
        this.namaPeserta = namaPeserta;
    }

    public String getNoHpPeserta() {
        return noHpPeserta;
    }

    public void setNoHpPeserta(String noHpPeserta) {
        this.noHpPeserta = noHpPeserta;
    }

    public String getAlamatPeserta() {
        return alamatPeserta;
    }

    public void setAlamatPeserta(String alamatPeserta) {
        this.alamatPeserta = alamatPeserta;
    }

    // validasi sederhana
    public void validateOrThrow() {
        if (namaPeserta == null || namaPeserta.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama peserta harus diisi");
        }

        if (noHpPeserta == null || noHpPeserta.trim().isEmpty()) {
            throw new IllegalArgumentException("No HP peserta harus diisi");
        }

        if (!noHpPeserta.matches("[0-9+ ]{8,15}")) {
            throw new IllegalArgumentException("No HP tidak valid");
        }

        if (alamatPeserta == null || alamatPeserta.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat peserta harus diisi");
        }
    }
}