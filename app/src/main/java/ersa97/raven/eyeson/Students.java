package ersa97.raven.eyeson;

import com.google.firebase.Timestamp;
import java.text.DateFormat;


public class Students {

    String nama;
    String kelas;
    String deskripsi;
    String photoUrl;
    String izin;
    String id;
    String ustad;
    String alasankeluar;
    String waktuKeluar;
    String waktuMasuk;
    Timestamp WaktuSignOut;
    Timestamp WaktuSignIn;



    public Students(String nama, String kelas, String deskripsi, String photoUrl, String izin, String id, String ustad,String alasankeluar, String waktuKeluar, String waktuMasuk, DateFormat dateFormat, DateFormat dateFormat1) {

    }


    public Students(){

    }

    public Students(String nama, String kelas, String deskripsi, String photoUrl, String izin, String id, String ustad, String alasankeluar, String waktuKeluar, String waktuMasuk, Timestamp waktuSignOut, Timestamp waktuSignIn) {
        this.nama = nama;
        this.kelas = kelas;
        this.deskripsi = deskripsi;
        this.photoUrl = photoUrl;
        this.izin = izin;
        this.id = id;
        this.ustad = ustad;
        this.alasankeluar = alasankeluar;
        this.waktuKeluar = waktuKeluar;
        this.waktuMasuk = waktuMasuk;
        WaktuSignOut = waktuSignOut;
        WaktuSignIn = waktuSignIn;
    }



    public Timestamp getWaktuSignOut() {
        return WaktuSignOut;
    }

    public void setWaktuSignOut(Timestamp waktuSignOut) {
        WaktuSignOut = waktuSignOut;
    }

    public Timestamp getWaktuSignIn() {
        return WaktuSignIn;
    }

    public void setWaktuSignIn(Timestamp waktuSignIn) {
        WaktuSignIn = waktuSignIn;
    }




    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
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

    public String getUstad() {
        return ustad;
    }

    public void setUstad(String ustad) {
        this.ustad = ustad;
    }

    public String getAlasankeluar() {
        return alasankeluar;
    }

    public void setAlasankeluar(String alasankeluar) {
        this.alasankeluar = alasankeluar;
    }

    public String getWaktuKeluar() {
        return waktuKeluar;
    }

    public void setWaktuKeluar(String waktuKeluar) {
        this.waktuKeluar = waktuKeluar;
    }

    public String getWaktuMasuk() {
        return waktuMasuk;
    }

    public void setWaktuMasuk(String waktuMasuk) {
        this.waktuMasuk = waktuMasuk;
    }


}
