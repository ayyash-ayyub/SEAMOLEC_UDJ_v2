package ayyash.app.seamolec_udj;

/**
 * Created by Abdul Rizal Adompo on 9/18/2016.
 */
public class ModelQuiz {

    int id_kelas;
    int id_quiz;
    int durasi;
    String nama_quiz;
    String tgl_selesai;

    public int getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(int id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNama_quiz() {
        return nama_quiz;
    }

    public void setNama_quiz(String nama_quiz) {
        this.nama_quiz = nama_quiz;
    }

    public String getTgl_selesai() {
        return tgl_selesai;
    }

    public void setTgl_selesai(String tgl_selesai) {
        this.tgl_selesai = tgl_selesai;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

}
