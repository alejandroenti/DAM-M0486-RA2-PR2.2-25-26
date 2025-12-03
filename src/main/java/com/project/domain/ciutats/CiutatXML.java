package com.project.domain.ciutats;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.domain.ciutadans.ICiutada;

public class CiutatXML implements ICiutat, Serializable {

    private long _ciutatId;
    private String _nom;
    private String _pais;
    private int _poblacio;
    private Set<ICiutada> _ciutandans = new HashSet<>();

    public CiutatXML() {}

    public CiutatXML(String nom, String pais, int poblacio) {
        _nom = nom;
        _pais = pais;
        _poblacio = poblacio;
    }

    @Override
    public long getCiutatId() {
        return _ciutatId;
    }

    @Override
    public void setCiutatId(long ciutatId) {
        _ciutatId = ciutatId;
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
    public String getPais() {
        return _pais;
    }

    @Override
    public void setPais(String pais) {
        _pais = pais;
    }

    @Override
    public int getPoblacio() {
        return _poblacio;
    }

    @Override
    public void setPoblacio(int poblacio) {
        _poblacio = poblacio;
    }

    @Override
    public Set<ICiutada> getCiutadans() {
        return _ciutandans;
    }

    @Override
    public void setCiutadans(Set<ICiutada> ciutadans) {
        _ciutandans = ciutadans;
    }

    @Override
    public void add(ICiutada ciutada) {
        _ciutandans.add(ciutada);
    }
    

     @Override
    public String toString() {
        String llistaCiutadans = "[]";
        
        if (_ciutandans != null && !_ciutandans.isEmpty()) {
            llistaCiutadans = _ciutandans.stream()
                .map(ICiutada::getNom)
                .collect(Collectors.joining(", ", "[", "]"));
        }

        return String.format("%s (%s), Població: %d, Ciutadans: %s", _nom, _pais, _poblacio, llistaCiutadans);
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
        if (_ciutatId == 0 || ciutat.getCiutatId() == 0) return this == ciutat;
        return _ciutatId == ciutat.getCiutatId();
    }
    
    /**
     * HASHCODE PER ENTITATS JPA: Si està persistida (ID > 0), utilitza l'ID.
     * Si no, utilitza el hashCode per defecte de Object.
     * IMPORTANT: equals i hashCode han de ser coherents entre si.
     */
    @Override
    public int hashCode() {
        return (_ciutatId > 0) ? Objects.hash(_ciutatId) : super.hashCode();
    }    
}