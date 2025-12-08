package com.project.application.factories;

import com.project.domain.ciutadans.CiutadaJPA;
import com.project.domain.ciutadans.ICiutada;
import com.project.domain.ciutats.CiutatJPA;
import com.project.domain.ciutats.ICiutat;
import com.project.domain.factories.IFactory;

public class FactoryJPA implements IFactory {

    @Override
    public ICiutat createCiutat(String nom, String pais, int poblacio) {
        return new CiutatJPA(nom, pais, poblacio);
    }

    @Override
    public ICiutada createCiutada(String nom, String cognom, int edat) {
        return new CiutadaJPA(nom, cognom, edat);
    }

    @Override
    public Class<? extends ICiutat> getCiutatClass() {
        return CiutatJPA.class;
    }

    @Override
    public Class<? extends ICiutada> getCiutadaClass() {
        return CiutadaJPA.class;
    }

}
