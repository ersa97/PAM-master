package ersa97.raven.eyeson;

import com.google.firebase.Timestamp;

public class Students {

    String nama;
    String alamat;
    String deskripsi;
    String photoUrl;
    String izin;
    String id;
    String alasankeluar;
    String waktuizinkeluar;
    String waktuizinmasuk;
    Timestamp timestampKeluar;
    Timestamp timestampMasuk;

    public Students(String nama, String alamat, String deskripsi, String photoUrl, String izin, String id, String alasankeluar, String waktuizinkeluar, String waktuizinmasuk, Timestamp timestampKeluar, Timestamp timestampMasuk) {
        this.nama = nama;
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.photoUrl = photoUrl;
        this.izin = izin;
        this.id = id;
        this.alasankeluar = alasankeluar;
        this.waktuizinkeluar = waktuizinkeluar;
        this.waktuizinmasuk = waktuizinmasuk;
        this.timestampKeluar = timestampKeluar;
        this.timestampMasuk = timestampMasuk;
    }


    public Students(){

    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIzin() {
        return izin;
    }

    public void setIzin(String izin) {
        this.izin = izin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlasankeluar() {
        return alasankeluar;
    }

    public void setAlasankeluar(String alasankeluar) {
        this.alasankeluar = alasankeluar;
    }

    public String getWaktuizinkeluar() {
        return waktuizinkeluar;
    }

    public void setWaktuizinkeluar(String waktuizinkeluar) {
        this.waktuizinkeluar = waktuizinkeluar;
    }

    public String getWaktuizinmasuk() {
        return waktuizinmasuk;
    }

    public void setWaktuizinmasuk(String waktuizinmasuk) {
        this.waktuizinmasuk = waktuizinmasuk;
    }

    public Timestamp getTimestampKeluar() {
        return timestampKeluar;
    }

    public void setTimestampKeluar(Timestamp timestampKeluar) {
        this.timestampKeluar = timestampKeluar;
    }

    public Timestamp getTimestampMasuk() {
        return timestampMasuk;
    }

    public void setTimestampMasuk(Timestamp timestampMasuk) {
        this.timestampMasuk = timestampMasuk;
    }





}
