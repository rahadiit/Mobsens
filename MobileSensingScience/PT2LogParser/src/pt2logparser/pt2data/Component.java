/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt2logparser.pt2data;

/**
 *
 * @author henny
 */
public enum Component {

    CPU, LCD, WIFI, ThreeG;

    
    @Override
    public String toString(){
        if (this.equals(ThreeG)) {
            return "3G";
        } else {
            return this.name();
        }
    }
}
