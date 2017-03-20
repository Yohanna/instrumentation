package instrumentation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import static java.lang.System.nanoTime;

/**
 * Created by Yohanna on 2017-03-16.
 */

public class Instrumentation {

    private String TABS = "";
    private Stack startTimesStack = new Stack();
    private double totalTime = 0;
    private ArrayList<String> log = new ArrayList();
    private static boolean ACTIVE = false;

    private static Instrumentation ourInstance = new Instrumentation();

    public static Instrumentation getInstance() {
        return ourInstance;
    }

    private Instrumentation() {
    }

    public void startTiming(String comment) {
        if (!ACTIVE) {
            return;
        }

        log.add(TABS + "STARTTIMING: " + comment);
        TABS = TABS.concat("| ");

        startTimesStack.push(nanoTime());
    }

    public void stopTiming(String comment) {
        if (!ACTIVE) {
            return;
        }

        long estimatedTime = nanoTime() - (Long) startTimesStack.pop();

        estimatedTime = TimeUnit.NANOSECONDS.toMillis(estimatedTime);

        if (startTimesStack.empty()) {
            totalTime = estimatedTime;
        }

        TABS = removeIndentation(TABS);

        log.add(TABS + "STOPTIMING: " + comment + " " + Long.toString(estimatedTime) + "ms");
    }

    public void comment(String comment) {
        if (!ACTIVE) {
            return;
        }
        log.add(TABS + "COMMENT: " + comment);
    }

    public void dump(String filename) {
        if (!ACTIVE) {
            return;
        }
//        System.out.format("TOTAL TIME: %fms",  TimeUnit.NANOSECONDS.toMillis((long)totalTime));
        log.add("TOTAL TIME: " + totalTime + "ms");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); // instrumentationddyyMMhhmmss
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Path out = null;

        try {
            if (filename == null || filename.isEmpty()) {
                out = Paths.get("instrumentation." + sdf.format(timestamp) + ".log");
            } else {
                out = Paths.get(filename);
            }

            Files.write(out, log, Charset.defaultCharset());

        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }


//        for (String item : log) {
//            System.out.println(item);
//        }
    }

    public void activate(boolean onoff) {
        if (onoff) ACTIVE = true;
    }

    // Remove last 2 chars
    private static String removeIndentation(String str) {
        if (str != null && str.length() >= 2) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}

