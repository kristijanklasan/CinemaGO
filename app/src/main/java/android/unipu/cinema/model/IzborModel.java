package android.unipu.cinema.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IzborModel {

    @SerializedName("id")
    @Expose
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    @SerializedName("id_korisnik")
    @Expose
    private int id_korisnik;

    public void setId_korisnik(int id_korisnik) {
        this.id_korisnik= id_korisnik;
    }

    public int getId_korisnik() {
        return id_korisnik;
    }


    @SerializedName("id_film")
    @Expose
    private int id_film;

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public int getId_film() {
        return id_film;
    }

    @SerializedName("datum_dodavanja")
    @Expose
    private String datum;

    public String getDatum(){
        return datum;
    }

    public void setDatum(String datum){
        this.datum = datum;
    }
}
