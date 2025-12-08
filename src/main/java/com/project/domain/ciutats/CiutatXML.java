package com.project.domain.ciutats;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.domain.ciutadans.ICiutada;

public class CiutatXML implements ICiutat, Serializable {

    private long ciutatId;
    private String nom;
    private String pais;
    private int poblacio;
    private Set<ICiutada> ciutandans = new HashSet<>();

    public CiutatXML() {}

    public CiutatXML(String nom, String pais, int poblacio) {
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
        return ciutandans;
    }

    @Override
    public void setCiutadans(Set<ICiutada> ciutadans) {
        this.ciutandans = ciutadans;
    }

    @Override
    public void addCiutada(ICiutada ciutada) {
        ciutandans.add(ciutada);
    }
    
    @Override
    public void removeCiutada(ICiutada ciutada) {
        ciutandans.remove(ciutada);
    }  


     @Override
    public String toString() {
        String llistaCiutadans = "[]";
        
        if (ciutandans != null && !ciutandans.isEmpty()) {
            llistaCiutadans = ciutandans.stream()
                .map(ICiutada::getFullName)
                .collect(Collectors.joining(", ", "[", "]"));
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