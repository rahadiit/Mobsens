/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt2logparser.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import pt2logparser.logCat.LogCatEntry;
import pt2logparser.pt2data.AppComponent;
import pt2logparser.pt2data.Component;
import pt2logparser.pt2data.PT2LogEntry;

/**
 *
 * @author henny
 */
public class PT2LogParser {

    private ArrayList<PT2LogEntry> entries;
    private List<String> file;
    private LogCatParser logCatParser;

    public PT2LogParser(List<String> file) {
        entries = new ArrayList<>();
        this.file = file;
        parserFile();
    }

    public LogCatParser getLogCatParser() {
        return logCatParser;
    }

    public void setLogCatParser(LogCatParser logCatParser, String appName) {
        this.logCatParser = logCatParser;
        
        if(this.logCatParser.getEntries()!=null){
            
            for(LogCatEntry entry: this.logCatParser.getEntries()){
                
                entry.setCpuConsumption(getConsumption(entry.getTime(), appName, Component.CPU));
                entry.setWifiConsumption(getConsumption(entry.getTime(), appName, Component.WIFI));
                entry.setThreeGConsumption(getConsumption(entry.getTime(), appName, Component.ThreeG));
                entry.setLcdConsumption(getConsumption(entry.getTime(), appName, Component.LCD));
                
            }
            
        }

    }

    public Collection<PT2LogEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<PT2LogEntry> entries) {
        this.entries = entries;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }

    private AppComponent parseLine(String line, Component comp, long time) {
        //LCD-TOTAL-10101-PowerTutor2 811

        String[] lineSplit = line.split("-");
        String[] lineSplitC = line.split(" ");
        double consumption;
        String lastElement = lineSplit[lineSplit.length - 1];
        Scanner sLE = new Scanner(lastElement);
        String appName = sLE.next();

        try {
            //consumption = Double.parseDouble(line.substring(line.indexOf(lastElement)+lastElement.length()).trim());
            consumption = Double.parseDouble(lineSplitC[lineSplitC.length - 1]);
        } catch (Exception e) {
            System.out.println(line);
            throw new IllegalArgumentException("unconform line-format");
        }

        if (lineSplit.length > 0) {
            return new AppComponent(appName, comp, time, consumption);
        } else {
            throw new IllegalArgumentException("unconform line-format");
        }
    }

    public void parserFile() {

        for (int i = 0; i < file.size() - 1; i++) {

            long time;
            Set<AppComponent> acSet = new HashSet<>();
            String line = file.get(i);

            if (line.startsWith("time")) {
                time = Long.parseLong(line.substring("time".length()).trim());

                while (++i < file.size() && (line = file.get(i)) != null && !line.startsWith("time ")) {

                    for (Component comp : Component.values()) {
                        if (line.startsWith(comp.toString() + "-TOTAL")) {
                            acSet.add(parseLine(line, comp, time));
                        }
                    }
                }
                entries.add(new PT2LogEntry(time, acSet));
                i--;
            }
        }
        Collections.sort((List<PT2LogEntry>) entries);
    }

    public double getConsumption(long time, String appname, Component component) {
        
        PT2LogEntry found = null;
        for (int i = 0; i < getEntries().size() - 1; i++) {
            PT2LogEntry le = entries.get(i);
            PT2LogEntry leSucc = entries.get(i + 1);
            
            if (time == le.getTime() || ( time > le.getTime() && time < leSucc.getTime() ) ) {
                found=le;
                break;
            }
        }
        
        if(found!=null){
            return found.getConsumptionOfApp(appname, component);
        }else{
            System.out.println("found no entry for given time");
        }
        
        return -1;
    }

    public double getConsumption(long start, long end, String appname, Component component) {

        PT2LogEntry startEntry = null;
        PT2LogEntry endEntry = null;

        for (int i = 1; i < getEntries().size(); i++) {
            PT2LogEntry le = entries.get(i);
            PT2LogEntry pre = entries.get(i-1);
            if (startEntry == null) {
                if (start < le.getTime()) {
                    startEntry = pre;
                } else {
                    continue;
                }
            } else if (endEntry == null) {
                if (end < le.getTime()) {
                    endEntry = le;
                }

            } else {
                break;
            }
        }

        if (startEntry != null && endEntry != null) {
            double c1 = startEntry.getConsumptionOfApp(appname, component);
            double c2 = endEntry.getConsumptionOfApp(appname, component);
            if (c1 != -1 && c2 != -1) {
                return c2 - c1;
            }
        }

        return -1;
    }
}
