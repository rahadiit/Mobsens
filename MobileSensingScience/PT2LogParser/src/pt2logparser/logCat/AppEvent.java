/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt2logparser.logCat;

/**
 *
 * @author henny
 */
public enum AppEvent {
    
    START_SENDING, FINISHED_SENDING, START_ZIP, FINISHED_ZIP;
    
    @Override 
    public String toString(){
        switch(this){
            case START_SENDING:
                return "startSending";
            case FINISHED_SENDING:
                return "finishedSending";
            case START_ZIP:
                return "startZip";
            case FINISHED_ZIP:
                return "finishedZip";
            default:
                return this.name();
        }
        
    }
    
    
}
