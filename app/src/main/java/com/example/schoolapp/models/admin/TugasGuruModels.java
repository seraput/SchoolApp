package com.example.schoolapp.models.admin;

public class TugasGuruModels {
    private String id, pelajaran, modul, tanggal, expired, guru_id, guru_nama, jenis, status;

    public TugasGuruModels(){

    }

    public TugasGuruModels(String id, String pelajaran, String modul, String tanggal, String expired, String guru_id, String guru_nama, String jenis, String status) {
        this.id = id;
        this.pelajaran = pelajaran;
        this.modul = modul;
        this.tanggal = tanggal;
        this.expired = expired;
        this.guru_id = guru_id;
        this.guru_nama = guru_nama;
        this.jenis = jenis;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPelajaran() {
        return pelajaran;
    }

    public void setPelajaran(String pelajaran) {
        this.pelajaran = pelajaran;
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

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getGuru_id() {
        return guru_id;
    }

    public void setGuru_id(String guru_id) {
        this.guru_id = guru_id;
    }

    public String getGuru_nama() {
        return guru_nama;
    }

    public void setGuru_nama(String guru_nama) {
        this.guru_nama = guru_nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
