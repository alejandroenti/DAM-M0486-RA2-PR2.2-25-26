package com.project.domain.ciutadans;

import com.project.domain.ciutats.ICiutat;

public interface ICiutada {

    public long getCiutadaId();
    public void setCiutadaId(long ciutadaId);
    public String getNom();
    public void setNom(String nom);
    public String getCognom();
    public void setCognom(String cognom);
    public int getEdat();
    public void setEdat(int edat);
    public ICiutat getCiutat();
    public void setCiutat(ICiutat ciutat);
}
