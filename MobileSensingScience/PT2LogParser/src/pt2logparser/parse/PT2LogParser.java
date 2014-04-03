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
import java.util.Set;
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

    public PT2LogParser(List<String> file) {
        entries = new ArrayList<>();
        this.file = file;
        parserFile();
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
        String[] lines = line.split("-");
        
        if(lines.length>0)
            return new AppComponent(lines[lines.length-1], comp, time);
        else
            throw new IllegalArgumentException("unconform line-format");
    }

    public void parserFile() {
        
        for (int i = 0; i < file.size()-1; i++) {

            long time;
            Set<AppComponent> acSet = new HashSet<>();
            String line = file.get(i);
            
            if (line.startsWith("time")) {
                time = Long.parseLong(line.substring("time".length()).trim());
                
                while(++i<file.size() && (line = file.get(i))!=null && !line.startsWith("time ")){
                    
                    for(Component comp: Component.values()){
                        if(line.startsWith(comp.toString()+"-TOTAL")){
                            acSet.add(parseLine(line,comp,time));
                        }
                    }
                }
                entries.add(new PT2LogEntry(time, acSet));
                i--;
            }
        }
        Collections.sort((List<PT2LogEntry>) entries);
    }
    
    public double getConsumption(long start, long end, String appname){
        
        for(int i=0;i<getEntries().size();i++){
            PT2LogEntry le = entries.get(i);
            
        }
        return 0;
    }
}
