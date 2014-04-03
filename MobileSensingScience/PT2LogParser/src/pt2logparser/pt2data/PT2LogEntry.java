/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt2logparser.pt2data;

import java.util.Set;

/**
 *
 * @author henny
 */
public class PT2LogEntry implements Comparable<PT2LogEntry> {

    private long time;
    private Set<AppComponent> appComponents;

    public PT2LogEntry(long time, Set<AppComponent> appComponents) {
        this.time = time;
        this.appComponents = appComponents;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Set<AppComponent> getAppComponents() {
        return appComponents;
    }

    public void setAppComponents(Set<AppComponent> appComponents) {
        this.appComponents = appComponents;
    }
    
    public double getConsumptionOfApp(String appName, Component component){
        for(AppComponent comp:appComponents){
            if(comp.getComponent().equals(component) && comp.getAppName().toLowerCase().startsWith(appName.toLowerCase())){
                return comp.getConsumption();
            }
        }
        return -1;
    }

    @Override
    public int compareTo(PT2LogEntry o) {
        if (o != null) {
            if (this.getTime() < o.getTime()) {
                return -1;
            }
            if (this.getTime() > o.getTime()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            throw new IllegalArgumentException("param is null");
        }
    }
}
