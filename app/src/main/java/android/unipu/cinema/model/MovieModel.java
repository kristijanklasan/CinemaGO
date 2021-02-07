package android.unipu.cinema.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieModel {

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

    @SerializedName("trajanje")
    @Expose
    private String trajanje;

    public String getTrajanje() {
        return trajanje;
    }

    @SerializedName("strani_naziv")
    @Expose
    private String straniNaziv;

    public String getStraniNaziv(){
        return straniNaziv;
    }

    public void setStraniNaziv(String straniNaziv){
        this.straniNaziv = straniNaziv;
    }

    @SerializedName("redatelji")
    @Expose
    private String redatelji;

    public String getRedatelji(){
        return redatelji;
    }

    public void setRedatelji(String redatelji){
        this.redatelji = redatelji;
    }

    @SerializedName("glumci")
    @Expose
    private String glumci;

    public String getGlumci(){
        return glumci;
    }

    public void setGlumci(String glumci){
        this.glumci = glumci;
    }

    @SerializedName("drzava")
    @Expose
    private String drzava;

    public String getDrzava(){
        return drzava;
    }

    public void setDrzava(String drzava){
        this.drzava = drzava;
    }

    @SerializedName("audio")
    @Expose
    private String audio;

    public String getAudio(){
        return audio;
    }

    public void setAudio(String audio){
        this.audio = audio;
    }

    @SerializedName("titlovi")
    @Expose
    private String titlovi;

    public String getTitlovi(){
        return titlovi;
    }

    public void setTitlovi(String titlovi){
        this.titlovi = titlovi;
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

    public void setOcjena(double ocjena){
        this.ocjena = ocjena;
    }

    @SerializedName("slika_pozadina")
    @Expose
    private String slika_pozadina;

    public String getSlikaPozadina(){
        return slika_pozadina;
    }

    public void setSlikaPozadina(String slika_pozadina){
        this.slika_pozadina = slika_pozadina;
    }

    @SerializedName("potvrda")
    @Expose
    private boolean potvrda;

    public boolean getPotvrda(){
        return potvrda;
    }

    public void setPotvrda(boolean potvrda){
        this.potvrda = potvrda;
    }


    @SerializedName("datum_dodavanja")
    @Expose
    private String datum_dodavanja;

    public String getDatum_dodavanja(){
        return datum_dodavanja;
    }

    public void setDatum_dodavanja(String datum_dodavanja){
        this.datum_dodavanja = datum_dodavanja;
    }

    @SerializedName("listaFilm")
    @Expose
    private static ArrayList movieList;

    public ArrayList getMovieList(){
        return movieList;
    }

    public void setMovieList(ArrayList movieList){
        this.movieList = movieList;
    }

    @SerializedName("godina_proizvodnje")
    @Expose
    private int godina;

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public int getGodina() {
        return godina;
    }

    @SerializedName("trailer")
    @Expose
    private String trailer;

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getTrailer() {
        return trailer;
    }
}
