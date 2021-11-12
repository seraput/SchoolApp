package com.example.schoolapp.models.siswa;

public class BiodataModels {
    private String kelas, agama, kelamin, tgl_lahir, alamat, telp, foto, absen;

    public BiodataModels(){

    }

    public BiodataModels(String kelas, String agama, String kelamin, String tgl_lahir, String alamat, String telp, String foto, String absen) {
        this.kelas = kelas;
        this.agama = agama;
        this.kelamin = kelamin;
        this.tgl_lahir = tgl_lahir;
        this.alamat = alamat;
        this.telp = telp;
        this.foto = foto;
        this.absen = absen;
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

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
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
