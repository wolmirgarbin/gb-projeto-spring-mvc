/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garbo.sites.enumerations;

import lombok.Getter;


public enum SimNao {
    
    S("Sim"), N("Não");
    
    @Getter
    private String label;
    
    private SimNao(final String label) {
        this.label = label;
    }
    
}