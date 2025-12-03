package com.project.domain.ciutadans;

import java.io.Serializable;
import java.util.Objects;

import com.project.domain.ciutats.ICiutat;

public class CiutadaXML implements ICiutada, Serializable{

    private long _ciutadaId;
    private String _nom;
    private String _cognom;
    private int _edat;
    private ICiutat _ciutat;

    public CiutadaXML() {}

    public CiutadaXML(String nom, String cognom, int edat) {
        _nom = nom;
        _cognom = cognom;
        _edat = edat;
    }

    @Override
    public long getCiutadaId() {
        return _ciutadaId;
    }

    @Override
    public void setCiutadaId(long ciutadaId) {
        _ciutadaId = ciutadaId;
    }

    @Override
    public String getNom() {
        return _nom;
    }

    @Override
    public void setNom(String nom) {
        _nom = nom;
    }

    @Override
    public String getCognom() {
        return _cognom;
    }

    @Override
    public void setCognom(String cognom) {
        _cognom = cognom;
    }

    @Override
    public int getEdat() {
        return _edat;
    }

    @Override
    public void setEdat(int edat) {
        _edat = edat;
    }

    @Override
    public ICiutat getCiutat() {
        return _ciutat;
    }

    @Override
    public void setCiutat(ICiutat ciutat) {
        _ciutat = ciutat;
    }


    @Override
    public String toString() {
        return String.format("%s %s (%d anys)", _nom, _cognom, _edat);
        // Tony Updated Happy (20 anys)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ICiutada ciutada = (ICiutada) o;
        if (_ciutadaId == 0 || ciutada.getCiutadaId() == 0) return this == ciutada;
        return _ciutadaId == ciutada.getCiutadaId();
    }
    
    @Override
    public int hashCode() {
        return (_ciutadaId > 0) ? Objects.hash(_ciutadaId) : super.hashCode();
    } 
}
