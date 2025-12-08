package com.project.domain.factories;

import com.project.domain.ciutadans.ICiutada;
import com.project.domain.ciutats.ICiutat;

public interface IFactory {

    public ICiutat createCiutat(String nom, String pais, int poblacio);
    public ICiutada createCiutada(String nom, String cognom, int edat);
    public Class<? extends ICiutat> getCiutatClass();
    public Class<? extends ICiutada> getCiutadaClass();
}