package com.example.schoolapp.models.admin;

public class DataSiswaModels {
    private String id, nama, username,level, kelas, agama, kelamin, tanggal, alamat, telp, foto;

    public DataSiswaModels(){

    }

    public DataSiswaModels(String id, String nama, String username, String level, String kelas, String agama, String kelamin, String tanggal, String alamat, String telp, String foto) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.level = level;
        this.kelas = kelas;
        this.agama = agama;
        this.kelamin = kelamin;
        this.tanggal = tanggal;
        this.alamat = alamat;
        this.telp = telp;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
}
