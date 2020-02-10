package com.luv2code.kartotekaweb.entity;

import javax.validation.constraints.Max;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class Pacijent {
    private String imePrezime;
    private String datumRodjenja;
    private String adresa;
    private String mesto;
    private String telefon;
    private String zanimanje;
    private String pm;
    private String lAnamneza;
    private String pAnamneza;
    private String krvnaGrupa;
    private String rh;
    private String osetljivost;
    private String porodjaj;
    private String abortus;
    private List<Bolest> bolesti;

    public Pacijent(){

    }

    public Pacijent(String imePrezime, String datumRodjenja, String adresa, String mesto,
                    String telefon, String zanimanje, String pm, String lAnamneza, String pAnamneza,
                    String krvnaGrupa, String rh, String osetljivost, String porodjaj, String abortus) {
        this.imePrezime = imePrezime;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
        this.mesto = mesto;
        this.telefon = telefon;
        this.zanimanje = zanimanje;
        this.pm = pm;
        this.lAnamneza = lAnamneza;
        this.pAnamneza = pAnamneza;
        this.krvnaGrupa = krvnaGrupa;
        this.rh = rh;
        this.osetljivost = osetljivost;
        this.porodjaj = porodjaj;
        this.abortus = abortus;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getZanimanje() {
        return zanimanje;
    }

    public void setZanimanje(String zanimanje) {
        this.zanimanje = zanimanje;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getlAnamneza() {
        return lAnamneza;
    }

    public void setlAnamneza(String lAnamneza) {
        this.lAnamneza = lAnamneza;
    }

    public String getpAnamneza() {
        return pAnamneza;
    }

    public void setpAnamneza(String pAnamneza) {
        this.pAnamneza = pAnamneza;
    }

    public String getKrvnaGrupa() {
        return krvnaGrupa;
    }

    public void setKrvnaGrupa(String krvnaGrupa) {
        this.krvnaGrupa = krvnaGrupa;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getOsetljivost() {
        return osetljivost;
    }

    public void setOsetljivost(String osetljivost) {
        this.osetljivost = osetljivost;
    }

    public String getPorodjaj() {
        return porodjaj;
    }

    public void setPorodjaj(String porodjaj) {
        this.porodjaj = porodjaj;
    }

    public String getAbortus() {
        return abortus;
    }

    public void setAbortus(String abortus) {
        this.abortus = abortus;
    }

    @XmlElementWrapper(name="bolesti")
    @XmlElement(name = "bolest")
    public List<Bolest> getBolesti() {
        return bolesti;
    }

    public void setBolesti(List<Bolest> bolesti) {
        this.bolesti = bolesti;
    }
}
