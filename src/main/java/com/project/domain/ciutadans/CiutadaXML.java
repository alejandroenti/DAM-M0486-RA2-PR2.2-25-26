package com.project.domain.ciutadans;

import java.io.Serializable;
import java.util.Objects;

import com.project.domain.ciutats.ICiutat;

public class CiutadaXML implements ICiutada, Serializable{

    private long ciutadaId;
    private String nom;
    private String cognom;
    private int edat;
    private ICiutat ciutat;

    public CiutadaXML() {}

    public CiutadaXML(String nom, String cognom, int edat) {
        this.nom = nom;
        this.cognom = cognom;
        this.edat = edat;
    }

    @Override
    public long getCiutadaId() {
        return ciutadaId;
    }

    @Override
    public void setCiutadaId(long ciutadaId) {
        this.ciutadaId = ciutadaId;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getCognom() {
        return cognom;
    }

    @Override
    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    @Override
    public int getEdat() {
        return edat;
    }

    @Override
    public void setEdat(int edat) {
        this.edat = edat;
    }

    @Override
    public ICiutat getCiutat() {
        return ciutat;
    }

    @Override
    public void setCiutat(ICiutat ciutat) {
        this.ciutat = ciutat;
    }


    @Override
    public String toString() {
        return String.format("%s %s (%d anys)", nom, cognom, edat);
        // Tony Updated Happy (20 anys)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ICiutada ciutada = (ICiutada) o;
        if (ciutadaId == 0 || ciutada.getCiutadaId() == 0) return this == ciutada;
        return ciutadaId == ciutada.getCiutadaId();
    }
    
    @Override
    public int hashCode() {
        return (ciutadaId > 0) ? Objects.hash(ciutadaId) : super.hashCode();
    } 
}
