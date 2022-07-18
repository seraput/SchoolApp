package com.example.schoolapp.models.admin;

public class CountDashboard {

    String siswa, tugas, informasi;

    public CountDashboard(){

    }

    public CountDashboard(String siswa, String tugas, String informasi) {
        this.siswa = siswa;
        this.tugas = tugas;
        this.informasi = informasi;
    }

    public String getSiswa() {
        return siswa;
    }

    public void setSiswa(String siswa) {
        this.siswa = siswa;
    }

    public String getTugas() {
        return tugas;
    }

    public void setTugas(String tugas) {
        this.tugas = tugas;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }
}
