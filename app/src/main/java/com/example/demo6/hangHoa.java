package com.example.demo6;

import java.io.Serializable;

public class hangHoa implements Serializable {
    private String ten;
    private String hang;
    private int nam;
    private Double gia;


    public hangHoa(String ten, String hang, int nam, Double gia) {
        this.ten = ten;
        this.hang = hang;
        this.nam = nam;
        this.gia = gia;

    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }
}
