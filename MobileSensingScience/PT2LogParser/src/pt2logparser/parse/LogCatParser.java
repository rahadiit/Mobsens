/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt2logparser.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import pt2logparser.logCat.AppEvent;
import pt2logparser.logCat.LogCatEntry;

/**
 *
 * @author henny
 */
public class LogCatParser {

    private Collection<LogCatEntry> entries;
    private List<String> file;

    public LogCatParser(List<String> file) {
        this.entries = new ArrayList<>();
        this.file = file;
        parseFile();
    }

    public LogCatParser(File file) throws IOException {
        this.file = FileUtils.readLines(file);
    }

    public Collection<LogCatEntry> getEntries() {
        return entries;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
        parseFile();
    }

    private LogCatEntry parseLine(String line, AppEvent app) {

        //04-01 15:33:34.984: I/ftpLog(6990): fileUploaded 1396359214995 /storage/emulated/0/10000.gz
        String relevant = line.substring(line.indexOf(app.toString()) + app.toString().length());
        //1396359214995 /storage/emulated/0/10000.gz
        Scanner scan = new Scanner(relevant);
        long time = scan.nextLong();

        LogCatEntry lce = null;

        if (app.equals(AppEvent.FINISHED_SENDING)) {
            String[] data = scan.next().split("/");
            String fileName = data[data.length - 2] + "/s";
            lce = new LogCatEntry(app, time, fileName);
        }
        else{
            String fileName = scan.next();
            lce = new LogCatEntry(app, time, fileName);
        }
        
        return lce;
    }

    /**
     *
     * @param appEvent something like the finished-Event of e.g. sending.
     *
     */
    public void parseAppEvents(AppEvent appEvent) {

        ArrayList<LogCatEntry> logCatArray = (ArrayList<LogCatEntry>) this.getEntries();
        for (int i = 1; i < logCatArray.size(); i++) {
            LogCatEntry current = logCatArray.get(i);
            LogCatEntry currentPre = logCatArray.get(i - 1);

            if (i % 2 == 1 && current.getEvent().equals(appEvent)) {

                current.setCpuCurrentConsumption(current.getCpuConsumption() - currentPre.getCpuConsumption());
                current.setWifiCurrentConsumption(current.getWifiConsumption() - currentPre.getWifiConsumption());
                current.setThreeGCurrentConsumption(current.getThreeGConsumption() - currentPre.getThreeGConsumption());
                current.setLcdCurrentConsumption(current.getLcdConsumption() - currentPre.getLcdConsumption());
                
                current.setTimeSpan(current.getTime()- currentPre.getTime());
            } else {

                current.setCpuCurrentConsumption(0);
                current.setWifiCurrentConsumption(0);
                current.setThreeGCurrentConsumption(0);
                current.setLcdCurrentConsumption(0);

            }

        }

    }

    public void parseFile() {

        for (AppEvent app : AppEvent.values()) {

            file.stream().filter(x -> x.contains(app.toString())).forEach(
                    line -> entries.add(parseLine(line, app))
            );
        }

        Collections.sort((List<LogCatEntry>) entries);
    }

}
