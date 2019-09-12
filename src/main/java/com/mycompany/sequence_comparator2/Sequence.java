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
    String nom, details;
    
    public Sequence (CheckBox selection, String nom, String details ) {
        this.selection=selection;
        this.nom=nom;
        this.details=details;
}

    public CheckBox getSelection() {
        return selection;
    }

    public String getNom() {
        return nom;
    }

    public String getDetails() {
        return details;
    }
    
}
