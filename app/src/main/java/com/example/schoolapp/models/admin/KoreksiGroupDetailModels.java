package com.example.schoolapp.models.admin;

public class KoreksiGroupDetailModels {
    private String id_tugas, mapel, modul, Jenis, id_siswa, nama, benar, salah, tanggal, jam, nilai, dikoreksi;

    public KoreksiGroupDetailModels(){

    }

    public KoreksiGroupDetailModels(String id_tugas, String mapel, String modul, String jenis, String id_siswa, String nama, String benar, String salah, String tanggal, String jam, String nilai, String dikoreksi) {
        this.id_tugas = id_tugas;
        this.mapel = mapel;
        this.modul = modul;
        Jenis = jenis;
        this.id_siswa = id_siswa;
        this.nama = nama;
        this.benar = benar;
        this.salah = salah;
        this.tanggal = tanggal;
        this.jam = jam;
        this.nilai = nilai;
        this.dikoreksi = dikoreksi;
    }

    public String getId_tugas() {
        return id_tugas;
    }

    public void setId_tugas(String id_tugas) {
        this.id_tugas = id_tugas;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getJenis() {
        return Jenis;
    }

    public void setJenis(String jenis) {
        Jenis = jenis;
    }

    public String getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(String id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBenar() {
        return benar;
    }

    public void setBenar(String benar) {
        this.benar = benar;
    }

    public String getSalah() {
        return salah;
    }

    public void setSalah(String salah) {
        this.salah = salah;
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

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getDikoreksi() {
        return dikoreksi;
    }

    public void setDikoreksi(String dikoreksi) {
        this.dikoreksi = dikoreksi;
    }
}
