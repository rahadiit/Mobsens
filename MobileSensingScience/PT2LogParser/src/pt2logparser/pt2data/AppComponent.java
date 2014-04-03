/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt2logparser.pt2data;

import java.util.Objects;

/**
 *
 * @author henny
 */
public class AppComponent {

    private String appName;
    private Component component;
    private long time;
    private double consumption;

    public AppComponent(String appName, Component component, long time, double consumption) {
        this.appName = appName;
        this.component = component;
        this.time = time;
        this.consumption = consumption;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
    
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.appName);
        hash = 71 * hash + Objects.hashCode(this.component);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppComponent other = (AppComponent) obj;
        if (!Objects.equals(this.appName, other.appName)) {
            return false;
        }
        if (this.component != other.component) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return time+" "+component.toString()+" "+appName+ " "+consumption;
    }
}
