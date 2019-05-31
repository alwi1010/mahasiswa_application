package com.example.mahasiswa;

import android.os.Parcel;
import android.os.Parcelable;

public class Mahasiswa implements Parcelable {
    private String Nim, Nama, Jurusan, Jekel, Tgllahir, Alamat;

    public Mahasiswa() {
    }

    public Mahasiswa(String nim, String nama, String jurusan, String jekel, String tgllahir, String alamat) {
        Nim = nim;
        Nama = nama;
        Jurusan = jurusan;
        Jekel = jekel;
        Tgllahir = tgllahir;
        Alamat = alamat;
    }

    public String getNim() {
        return Nim;
    }

    public void setNim(String nim) {
        Nim = nim;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getJurusan() {
        return Jurusan;
    }

    public void setJurusan(String jurusan) {
        Jurusan = jurusan;
    }

    public String getJekel() {
        return Jekel;
    }

    public void setJekel(String jekel) {
        Jekel = jekel;
    }

    public String getTgllahir() {
        return Tgllahir;
    }

    public void setTgllahir(String tgllahir) {
        Tgllahir = tgllahir;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Nim);
        dest.writeString(this.Nama);
        dest.writeString(this.Jurusan);
        dest.writeString(this.Jekel);
        dest.writeString(this.Tgllahir);
        dest.writeString(this.Alamat);
    }

    protected Mahasiswa(Parcel in) {
        this.Nim = in.readString();
        this.Nama = in.readString();
        this.Jurusan = in.readString();
        this.Jekel = in.readString();
        this.Tgllahir = in.readString();
        this.Alamat = in.readString();
    }

    public static final Creator<Mahasiswa> CREATOR = new Creator<Mahasiswa>() {
        @Override
        public Mahasiswa createFromParcel(Parcel source) {
            return new Mahasiswa(source);
        }

        @Override
        public Mahasiswa[] newArray(int size) {
            return new Mahasiswa[size];
        }
    };
}
