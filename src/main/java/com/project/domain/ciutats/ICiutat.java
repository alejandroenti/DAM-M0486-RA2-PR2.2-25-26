package com.project.domain.ciutats;

import java.util.Set;

import com.project.domain.ciutadans.ICiutada;

public interface ICiutat {

    public long getCiutatId();
    public void setCiutatId(long ciutatId);
    public String getNom();
    public void setNom(String nom);
    public String getPais();
    public void setPais(String pais);
    public int getPoblacio();
    public void setPoblacio(int poblacio);
    public Set<ICiutada> getCiutadans();
    public void setCiutadans(Set<ICiutada> ciutadans);
}
