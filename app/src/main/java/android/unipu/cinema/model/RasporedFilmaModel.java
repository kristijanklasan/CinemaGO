package android.unipu.cinema.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RasporedFilmaModel {

    @SerializedName("id")
    @Expose
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @SerializedName("id_filma")
    @Expose
    private int id_filma;

    public void setId_filma(int id_filma) {
        this.id_filma = id_filma;
    }

    public int getId_filma() {
        return id_filma;
    }

    @SerializedName("naziv")
    @Expose
    private String Film;

    public void setFilm(String Film) {
        this.Film = Film;
    }

    public String getFilm() {
        return Film;
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

    @SerializedName("datum_prikazivanja")
    @Expose
    private String datum_prikazivanja;

    public void setDatum_prikazivanja(String datum_prikazivanja) {
        this.datum_prikazivanja = datum_prikazivanja;
    }

    public String getDatum_prikazivanja() {
        return datum_prikazivanja;
    }

    @SerializedName("vrijeme_prikazivanja")
    @Expose
    private String vrijeme_prikazivanja;

    public void setVrijeme_prikazivanja(String vrijeme_prikazivanja) {
        this.vrijeme_prikazivanja = vrijeme_prikazivanja;
    }

    public String getVrijeme_prikazivanja() {
        return vrijeme_prikazivanja;
    }

    @SerializedName("max_ulaznica")
    @Expose
    private String max_ulaznica;

    public void setMax_ulaznica(String max_ulaznica) {
        this.max_ulaznica = max_ulaznica;
    }

    public String getMax_ulaznica() {
        return max_ulaznica;
    }

    @SerializedName("trenutno_ulaznica")
    @Expose
    private String trenutno_ulaznica;

    public void setTrenutno_ulaznica(String trenutno_ulaznica) {
        this.trenutno_ulaznica = trenutno_ulaznica;
    }

    public String getTrenutno_ulaznica() {
        return trenutno_ulaznica;
    }


    @SerializedName("dvorana")
    @Expose
    private String dvorana;

    public void setDvorana(String dvorana) {
        this.dvorana = dvorana;
    }

    public String getDvorana() {
        return dvorana;
    }


    @SerializedName("temp_ulaznica")
    @Expose
    private int temp_ulaznica;

    public void setTemp_ulaznica(int temp_ulaznica) {
        this.temp_ulaznica = temp_ulaznica;
    }

    public int getTemp_ulaznica() {
        return temp_ulaznica;
    }
}
