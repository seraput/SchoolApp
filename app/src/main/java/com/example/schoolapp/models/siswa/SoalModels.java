package com.example.schoolapp.models.siswa;

public class SoalModels {
    private String tugas_id, no, pertanyaan, jawab1, jawab2, jawab3, jawab4, jawaban;

    public SoalModels(){

    }

    public SoalModels(String tugas_id, String no, String pertanyaan, String jawab1, String jawab2, String jawab3, String jawab4, String jawaban) {
        this.tugas_id = tugas_id;
        this.no = no;
        this.pertanyaan = pertanyaan;
        this.jawab1 = jawab1;
        this.jawab2 = jawab2;
        this.jawab3 = jawab3;
        this.jawab4 = jawab4;
        this.jawaban = jawaban;
    }

    public String getTugas_id() {
        return tugas_id;
    }

    public void setTugas_id(String tugas_id) {
        this.tugas_id = tugas_id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawab1() {
        return jawab1;
    }

    public void setJawab1(String jawab1) {
        this.jawab1 = jawab1;
    }

    public String getJawab2() {
        return jawab2;
    }

    public void setJawab2(String jawab2) {
        this.jawab2 = jawab2;
    }

    public String getJawab3() {
        return jawab3;
    }

    public void setJawab3(String jawab3) {
        this.jawab3 = jawab3;
    }

    public String getJawab4() {
        return jawab4;
    }

    public void setJawab4(String jawab4) {
        this.jawab4 = jawab4;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
