package com.example.schoolapp.models.admin;

public class KoreksiGroupModels {

    private String id_tugas, mapel, modul, tanggal, total;

    public KoreksiGroupModels(){

    }

    public KoreksiGroupModels(String id_tugas, String mapel, String modul, String tanggal, String total) {
        this.id_tugas = id_tugas;
        this.mapel = mapel;
        this.modul = modul;
        this.tanggal = tanggal;
        this.total = total;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
