package com.example.schoolapp.models.admin;

public class AbsensiDetailModels {

    private String nis, nama, tanggal, jam, kelamin;

    public AbsensiDetailModels(){

    }

    public AbsensiDetailModels(String nis, String nama, String tanggal, String jam, String kelamin) {
        this.nis = nis;
        this.nama = nama;
        this.tanggal = tanggal;
        this.jam = jam;
        this.kelamin = kelamin;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }
}
