package ersa97.raven.eyeson;

public class Students {

    String nama;
    String alamat;
    String deskripsi;
    String photoUrl;
    String izin;


    public Students(){

    }

    public Students(String nama, String alamat, String deskripsi, String izin, String photoUrl) {
        this.nama = nama;
        this.alamat = alamat;
        this.deskripsi = deskripsi;
        this.photoUrl = photoUrl;
        this.izin = izin;
    }

    public String getIzin(){
        return izin;
    }

    public void setIzin(String izin) {
        this.izin = izin;
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
}
