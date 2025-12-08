package com.project.domain.ciutats;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.project.domain.ciutadans.CiutadaJPA;
import com.project.domain.ciutadans.ICiutada;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ciutats")
public class CiutatJPA implements ICiutat, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ciutatId", unique=true, nullable=false)
    private long ciutatId;

    private String nom;
    private String pais;
    private int poblacio;

    @OneToMany(mappedBy = "ciutat", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CiutadaJPA> ciutandans = new HashSet<>();

    @Column(name = "uuid", nullable = false, updatable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    public CiutatJPA() {}

    public CiutatJPA(String nom, String pais, int poblacio) {
        this.nom = nom;
        this.pais = pais;
        this.poblacio = poblacio;
    }

    @Override
    public long getCiutatId() {
        return ciutatId;
    }

    @Override
    public void setCiutatId(long ciutatId) {
        this.ciutatId = ciutatId;
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
    public String getPais() {
        return pais;
    }

    @Override
    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public int getPoblacio() {
        return poblacio;
    }

    @Override
    public void setPoblacio(int poblacio) {
        this.poblacio = poblacio;
    }

    @Override
    public Set<ICiutada> getCiutadans() {
        Set<ICiutada> ciut = new HashSet<>();

        for (CiutadaJPA ciuatada : ciutandans) {
            ciut.add(ciuatada);
        }

        return ciut;
    }

    @Override
    public void setCiutadans(Set<ICiutada> ciutadans) {
        
        ciutadans.clear();
        
        for (ICiutada ciutada : ciutadans) {
            addCiutada(ciutada);
        } 
    }

    @Override
    public void addCiutada(ICiutada ciutada) {
        ciutandans.add((CiutadaJPA)ciutada);
    }

    @Override
    public void removeCiutada(ICiutada ciutada) {
        ciutandans.remove((CiutadaJPA)ciutada);
    }

     @Override
    public String toString() {
        String llistaCiutadans = "[]";
        
        if (ciutandans != null && !ciutandans.isEmpty()) {
            llistaCiutadans = ciutandans.stream()
                .map(ICiutada::getFullName)
                .collect(Collectors.joining(" | ", "[", "]"));
        }

        return String.format("%s (%s), Població: %d, Ciutadans: %s", nom, pais, poblacio, llistaCiutadans);
    }
    
    /**
     * EQUALS PER ENTITATS JPA: Compara per ID si l'entitat està persistida.
     * Si l'ID és 0 (no persistida), compara per referència d'objecte.
     * Això evita problemes quan l'ID s'assigna després de persistir.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ICiutat ciutat = (CiutatXML) o;
        if (ciutatId == 0 || ciutat.getCiutatId() == 0) return this == ciutat;
        return ciutatId == ciutat.getCiutatId();
    }
    
    /**
     * HASHCODE PER ENTITATS JPA: Si està persistida (ID > 0), utilitza l'ID.
     * Si no, utilitza el hashCode per defecte de Object.
     * IMPORTANT: equals i hashCode han de ser coherents entre si.
     */
    @Override
    public int hashCode() {
        return (ciutatId > 0) ? Objects.hash(ciutatId) : super.hashCode();
    }
}
