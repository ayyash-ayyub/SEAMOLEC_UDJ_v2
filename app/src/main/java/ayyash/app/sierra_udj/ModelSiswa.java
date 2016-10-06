/*
 * Created by Shaon on 8/14/16 10:38 PM
 * Copyright (c) 2016. This is free to use in any software.
 * You must provide developer name on your project.
 */

package ayyash.app.sierra_udj;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abdul Rizal Adompo on 9/18/2016.
 */
public class ModelSiswa {

    @SerializedName("id")
    private String id;
    @SerializedName("id_kelas")
    private int id_kelas;
    @SerializedName("password")
    private String password;
    @SerializedName("nis")
    private String nis;
    @SerializedName("nama")
    private String nama;

//    public ModelSiswa(String nis, String password, String nama, int id_kelas){
//        this.nis = nis;
//        this.password = password;
//        this.nama = nama;
//        this.id_kelas = id_kelas;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(int id_kelas) {
        this.id_kelas = id_kelas;
    }


}




