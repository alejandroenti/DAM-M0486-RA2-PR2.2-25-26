package com.project.application.factories;

import com.project.domain.ciutadans.CiutadaXML;
import com.project.domain.ciutadans.ICiutada;
import com.project.domain.ciutats.CiutatXML;
import com.project.domain.ciutats.ICiutat;
import com.project.domain.factories.IFactory;

public class FactoryXML implements IFactory {

    @Override
    public ICiutat createCiutat(String nom, String pais, int poblacio) {
        return new CiutatXML(nom, pais, poblacio);
    }

    @Override
    public ICiutada createCiutada(String nom, String cognom, int edat) {
        return new CiutadaXML(nom, cognom, edat);
    }

}
