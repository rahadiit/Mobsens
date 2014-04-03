/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt2logparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.io.FileUtils;
import pt2logparser.parse.LogCatParser;
import pt2logparser.parse.PT2LogParser;
import pt2logparser.pt2data.AppComponent;
import pt2logparser.pt2data.PT2LogEntry;

/**
 *
 * @author henny
 */
public class Main {

    public static Collection<String> filterMe(Collection<String> list, Predicate p) {
        Collection<String> result = new ArrayList<>();
        list.stream().filter(p).forEach((x) -> result.add((String) x));

        return result;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            // /Users/henny/Desktop/sdcard/logCat010414.txt
            // /Users/henny/Desktop/sdcard/PowerTrace1396358921109.log
            boolean one=true;
            if (one) {
                List<String> lines = FileUtils.readLines(new File("/Users/henny/Desktop/sdcard/logCat010414.txt"));
                LogCatParser logCatparser = new LogCatParser(lines);

                logCatparser.getEntries().stream().forEach((lce) -> {
                    System.out.println(lce);
                });
            }
            
            if(!one){
                List<String> lines = FileUtils.readLines(new File("/Users/henny/Desktop/sdcard/PowerTrace1396358921109.log"));
                PT2LogParser pt2 = new PT2LogParser(lines);
                
                pt2.getEntries().stream().forEach((entry) -> {
                    entry.getAppComponents().stream().filter
                                (appcomp -> appcomp.getAppName().toLowerCase().contains("sendtest")).
                            forEach(appComp -> System.out.println(appComp));
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
