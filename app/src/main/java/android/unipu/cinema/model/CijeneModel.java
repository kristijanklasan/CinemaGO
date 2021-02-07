package android.unipu.cinema.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CijeneModel {

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

    @SerializedName("cijene_djeca")
    @Expose
    private int cijene_djeca;

    public void setCijene_djeca(int cijene_djeca) {
        this.cijene_djeca = cijene_djeca;
    }

    public int getCijene_djeca() {
        return cijene_djeca;
    }


    @SerializedName("cijene_odrasli")
    @Expose
    private int cijene_odrasli;

    public void setCijene_odrasli(int cijene_odrasli) {
        this.cijene_odrasli = cijene_odrasli;
    }

    public int getCijene_odrasli() {
        return cijene_odrasli;
    }


    @SerializedName("cijene_studenti")
    @Expose
    private int cijene_studenti;

    public void setCijene_studenti(int cijene_studenti) {
        this.cijene_studenti = cijene_studenti;
    }

    public int getCijene_studenti() {
        return cijene_studenti;
    }

    @SerializedName("datum_unosa")
    @Expose
    private String datum_unosa;

    public void setDatum_unosa(String datum_unosa) {
        this.datum_unosa = datum_unosa;
    }

    public String getDatum_unosa() {
        return datum_unosa;
    }

}
