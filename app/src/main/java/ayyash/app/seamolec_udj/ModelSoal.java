package ayyash.app.seamolec_udj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdul Rizal Adompo on 24/09/2016.
 */

public class ModelSoal {

    private int id_soal;
    private String text;
    private String gambar;
    private List<ModelOpsiSoal> modelOpsiSoal;

    public ModelSoal(){
        this.modelOpsiSoal = new ArrayList<ModelOpsiSoal>();
    }

    public void addOpsiSoal(ModelOpsiSoal mOpsiSoal){
        this.modelOpsiSoal.add(mOpsiSoal);
    }

    public int getId_soal() {
        return id_soal;
    }

    public void setId_soal(int id_soal) {
        this.id_soal = id_soal;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ModelOpsiSoal> getModelOpsiSoal() {
        return modelOpsiSoal;
    }
    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
