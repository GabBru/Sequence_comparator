/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sequence_comparator2;

import javafx.collections.ObservableList;

/**
 *
 * @author brunetgabriel
 */
public class Plante {
    
    protected String name;
    public static ObservableList<Entite> list_Entite;
    
    public Plante(String name, ObservableList<Entite> list_Entite){
        this.name = name;
        this.list_Entite = list_Entite;
    }

    public String getName() {
        return name;
    }

    public static ObservableList<Entite> getList_Entite() {
        return list_Entite;
    }

    public void setName(String name) {
        this.name = name;
    }
}


