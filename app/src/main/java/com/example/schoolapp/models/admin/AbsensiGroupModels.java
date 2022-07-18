package com.example.schoolapp.models.admin;

public class AbsensiGroupModels {

    private String nis, tanggal, jam, total;

    public AbsensiGroupModels(){

    }

    public AbsensiGroupModels(String nis, String tanggal, String jam, String total) {
        this.nis = nis;
        this.tanggal = tanggal;
        this.jam = jam;
        this.total = total;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
