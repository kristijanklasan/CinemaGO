package android.unipu.cinema.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PitanjaModel {

    @SerializedName("id")
    @Expose
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    @SerializedName("naziv")
    @Expose
    private String naziv;

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    @SerializedName("opis")
    @Expose
    private String opis;

    public String getOpis(){
        return opis;
    }

    public void setOpis(String opis){
        this.opis = opis;
    }
}
