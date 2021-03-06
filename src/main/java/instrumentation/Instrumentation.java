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

    /**
     * Starts the timer and logs a comment
     * @param comment Comment to log
     */
    public void startTiming(String comment) {
        if (!ACTIVE) {
            return;
        }

        log.add(TABS + "STARTTIMING: " + comment);
        TABS = TABS.concat("| ");

        startTimesStack.push(nanoTime());
    }

    /**
     * Stops the timer and logs a comment
     * @param comment Comment to log
     */
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

    /**
     * Adds a comment to the log
     * @param comment Comment to log
     */
    public void comment(String comment) {
        if (!ACTIVE) {
            return;
        }
        log.add(TABS + "COMMENT: " + comment);
    }

    /**
     * Dumps the log to a file
     * @param filename where to write the file
     */
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

        // Reset the instrumentation state to default values
        resetState();
    }

    /**
     * Activates or deactivates the logger
     * @param onoff Value to control the timer, True for activating, False for deactivating
     */
    public void activate(boolean onoff) {
        ACTIVE = onoff;
    }

    private void resetState() {
        TABS = "";
        startTimesStack = new Stack();
        totalTime = 0;
        log = new ArrayList();
        ACTIVE = false;
    }

    /**
     * Remove last 2 chars from a string
     * @param str String to remove the last 2 chars from
     * @return
     */
    private static String removeIndentation(String str) {
        if (str != null && str.length() >= 2) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}

