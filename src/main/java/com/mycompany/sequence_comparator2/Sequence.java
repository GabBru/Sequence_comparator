/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sequence_comparator2;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Natasha
 */
public class Sequence {
    CheckBox selection;
    String nom, details,sequence,CDNA;
    
    public Sequence (CheckBox selection, String nom, String details, String sequence,String CDNA ) {
        this.selection=selection;
        this.nom=nom;
        this.details=details;
        this.sequence=sequence;
        this.CDNA=CDNA;
}

    public CheckBox getSelection() {
        return selection;
    }

    public String getNom() {
        return nom;
    }

    public String getSequence() {
        return sequence;
    }

    public String getDetails() {
        return details;
    }
    
}
