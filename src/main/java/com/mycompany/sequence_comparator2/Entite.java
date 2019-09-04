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
class Entite {
    protected String name, sequence_ADN;
    protected ARN sequence_ARN;
    public static ObservableList<CIS> list_CIS;
    
    public Entite(String name, String sequence_ADN, ObservableList<CIS> list_CIS, ARN ARN_assoc){
        this.name = name;
        this.sequence_ARN = ARN_assoc;
        this.sequence_ADN = sequence_ADN;
        this.list_CIS = list_CIS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSequence_ADN() {
        return sequence_ADN;
    }

    public ARN getSequence_ARN() {
        return sequence_ARN;
    }

    public static ObservableList<CIS> getList_CIS() {
        return list_CIS;
    }

    public void setSequence_ADN(String sequence_ADN) {
        this.sequence_ADN = sequence_ADN;
    }

    public void setSequence_ARN(ARN sequence_ARN) {
        this.sequence_ARN = sequence_ARN;
    }

    public static void setList_CIS(ObservableList<CIS> list_CIS) {
        Entite.list_CIS = list_CIS;
    }
   
}