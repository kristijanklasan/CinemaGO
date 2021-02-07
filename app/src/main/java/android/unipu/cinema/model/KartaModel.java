package android.unipu.cinema.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class KartaModel {

    @SerializedName("id")
    @Expose
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    @SerializedName("red")
    @Expose
    private  static int red;

    public void setRed(int red) {
        this.red = red;
    }

    public int getRed() {
        return red;
    }

    @SerializedName("sjedalo")
    @Expose
    private static int sjedalo;

    public void setSjedalo(int sjedalo) {
        this.sjedalo = sjedalo;
    }

    public int getSjedalo() {
        return sjedalo;
    }

    @SerializedName("oznaka")
    @Expose
    private int oznaka;

    public void setOznaka(int oznaka) {
        this.oznaka = oznaka;
    }

    public int getOznaka() {
        return oznaka;
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

    @SerializedName("ukupno")
    @Expose
    private Double ukupno;

    public void setUkupno(Double ukupno) {
        this.ukupno = ukupno;
    }

    public Double getUkupno() {
        return ukupno;
    }

    @SerializedName("id_raspored")
    @Expose
    private int id_raspored;

    public void setId_raspored(int id_raspored) {
        this.id_raspored = id_raspored;
    }

    public int getId_raspored() {
        return id_raspored;
    }

    @SerializedName("id_korisnik")
    @Expose
    private int id_korisnik;

    public void setId_korisnik(int id_korisnik) {
        this.id_korisnik = id_korisnik;
    }

    public int getId_korisnik() {
        return id_korisnik;
    }


    @SerializedName("oznaka2")
    @Expose
    private static int oznaka2;

    public void setOznaka2(int oznaka2) {
        this.oznaka2 = oznaka2;
    }

    public int getOznaka2() {
        return oznaka2;
    }

    @SerializedName("lista")
    @Expose
    private static ArrayList lista;

    public void setLista(ArrayList lista) {
        this.lista = lista;
    }

    public ArrayList getLista() {
        return lista;
    }

}
