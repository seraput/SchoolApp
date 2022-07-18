package com.example.schoolapp.models.siswa;

public class InformationModels {

    private String id, judul, ucapan, isi, penutup, penting, created, tanggal, status;

    public InformationModels(){

    }

    public InformationModels(String id, String judul, String ucapan, String isi, String penutup, String penting, String created, String tanggal, String status) {
        this.id = id;
        this.judul = judul;
        this.ucapan = ucapan;
        this.isi = isi;
        this.penutup = penutup;
        this.penting = penting;
        this.created = created;
        this.tanggal = tanggal;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getUcapan() {
        return ucapan;
    }

    public void setUcapan(String ucapan) {
        this.ucapan = ucapan;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getPenutup() {
        return penutup;
    }

    public void setPenutup(String penutup) {
        this.penutup = penutup;
    }

    public String getPenting() {
        return penting;
    }

    public void setPenting(String penting) {
        this.penting = penting;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
