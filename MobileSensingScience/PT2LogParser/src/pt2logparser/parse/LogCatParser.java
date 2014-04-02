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
        String data = scan.next();

        LogCatEntry lce = new LogCatEntry(app, time, data);

        return lce;
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
