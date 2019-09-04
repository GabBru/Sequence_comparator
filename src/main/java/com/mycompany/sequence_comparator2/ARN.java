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
class ARN {
    protected String sequence_ARN;
    
    public ARN(String sequence_ARN){
        this.sequence_ARN = sequence_ARN;
    }

    public String getSequence_ARN() {
        return sequence_ARN;
    }

    public void setSequence_ARN(String sequence_ARN) {
        this.sequence_ARN = sequence_ARN;
    }
}
