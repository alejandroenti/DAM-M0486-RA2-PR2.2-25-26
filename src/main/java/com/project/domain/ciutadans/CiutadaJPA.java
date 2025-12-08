package com.project.domain.ciutadans;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.project.domain.ciutats.CiutatJPA;
import com.project.domain.ciutats.ICiutat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ciutadans")
public class CiutadaJPA implements ICiutada, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ciutadaId", unique=true, nullable=false)  
    private long ciutadaId;
    private String nom;
    private String cognom;
    private int edat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ciutatId")
    private CiutatJPA ciutat;

    @Column(name = "uuid", nullable = false, updatable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    public CiutadaJPA() {}

    public CiutadaJPA(String nom, String cognom, int edat) {
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
        this.ciutat = (CiutatJPA)ciutat;
    }

    @Override
    public String getFullName() {
        return String.format("%s %s", nom, cognom);
    }

     @Override
    public String toString() {
        return String.format("%s %s (%d anys)", nom, cognom, edat);
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