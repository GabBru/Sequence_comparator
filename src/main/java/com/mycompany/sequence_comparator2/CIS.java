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
    protected int start_position, end_position;
    
    public CIS(String name, String sequence_CIS, int start_position, int end_position){
        this.name = name;
        this.sequence_CIS = sequence_CIS;
        this.start_position = start_position;
        this.end_position = end_position;
    }

    public String getName() {
        return name;
    }

    public String getSequence_CIS() {
        return sequence_CIS;
    }

    public int getStart_position() {
        return start_position;
    }

    public int getEnd_position() {
        return end_position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSequence_CIS(String sequence_CIS) {
        this.sequence_CIS = sequence_CIS;
    }

    public void setStart_position(int start_position) {
        this.start_position = start_position;
    }

    public void setEnd_position(int end_position) {
        this.end_position = end_position;
    }
    
}
