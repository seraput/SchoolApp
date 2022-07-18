package com.example.schoolapp.models.siswa;

public class BiodataModels {
    private String nis, nama, username, kelas, agama, kelamin, tanggal, alamat, telp, foto, absen;

    public BiodataModels(){

    }

    public BiodataModels(String nis, String nama, String username, String kelas, String agama, String kelamin, String tanggal, String alamat, String telp, String foto, String absen) {
        this.nis = nis;
        this.nama = nama;
        this.username = username;
        this.kelas = kelas;
        this.agama = agama;
        this.kelamin = kelamin;
        this.tanggal = tanggal;
        this.alamat = alamat;
        this.telp = telp;
        this.foto = foto;
        this.absen = absen;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getAbsen() {
        return absen;
    }

    public void setAbsen(String absen) {
        this.absen = absen;
    }
}
