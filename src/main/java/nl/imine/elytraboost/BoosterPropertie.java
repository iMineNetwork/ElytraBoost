/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost;

/**
 *
 * @author Dennis
 */
public enum BoosterPropertie {
    effectradius("effectradius"), power("power"), cooldowntime("cooldowntime");

    String property;

    BoosterPropertie(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
    
    
    
}
