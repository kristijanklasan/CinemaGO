package android.unipu.cinema.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RezervacijaModel {

    @SerializedName("naziv")
    @Expose
    private String Film;

    public void setFilm(String Film) {
        this.Film = Film;
    }

    public String getFilm() {
        return Film;
    }

    @SerializedName("slika")
    @Expose
    private String slikaFilm;

    public String getSlikaFilm() {
        return slikaFilm;
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

    @SerializedName("id")
    @Expose
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @SerializedName("cijena")
    @Expose
    private int cijena;

    public void setCijena(int cijena) {
        this.cijena = cijena;
    }

    public int getCijena() {
        return cijena;
    }

    @SerializedName("red")
    @Expose
    private int red;

    public void setRed(int red) {
        this.red = red;
    }

    public int getRed() {
        return red;
    }

    @SerializedName("sjedalo")
    @Expose
    private int sjedalo;

    public void setSjedalo(int sjedalo) {
        this.sjedalo = sjedalo;
    }

    public int getSjedalo() {
        return sjedalo;
    }

    @SerializedName("ime")
    @Expose
    private String ime;

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getIme() {
        return ime;
    }


    @SerializedName("prezime")
    @Expose
    private String prezime;

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getPrezime() {
        return prezime;
    }

    @SerializedName("trajanje")
    @Expose
    private String trajanje;

    public String getTrajanje() {
        return trajanje;
    }

    @SerializedName("zanr")
    @Expose
    private String zanr;

    public String getZanr(){
        return zanr;
    }

    public void setZanr(String zanr){
        this.zanr = zanr;
    }

    @SerializedName("ocjena")
    @Expose
    private double ocjena;

    public double getOcjena(){
        return ocjena;
    }

}
