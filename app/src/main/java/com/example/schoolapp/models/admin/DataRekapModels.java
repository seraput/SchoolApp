package com.example.schoolapp.models.admin;

public class DataRekapModels {
    private String tanggal, idTugas, modul,mapel, nilai;

    public DataRekapModels(){

    }

    public DataRekapModels(String tanggal, String idTugas, String modul, String mapel, String nilai) {
        this.tanggal = tanggal;
        this.idTugas = idTugas;
        this.modul = modul;
        this.mapel = mapel;
        this.nilai = nilai;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getIdTugas() {
        return idTugas;
    }

    public void setIdTugas(String idTugas) {
        this.idTugas = idTugas;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }
}
