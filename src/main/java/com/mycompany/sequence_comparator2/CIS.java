/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sequence_comparator2;

/**
 *
 * @author brunetgabriel
 */
class CIS {
    protected String name, sequence_CIS;
    protected int position;
    
    public CIS(String name, String sequence_CIS, int position){
        this.name = name;
        this.sequence_CIS = sequence_CIS;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getSequence_CIS() {
        return sequence_CIS;
    }

    public int getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSequence_CIS(String sequence_CIS) {
        this.sequence_CIS = sequence_CIS;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
