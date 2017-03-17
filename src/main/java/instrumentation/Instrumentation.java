package instrumentation;

import java.lang.String;
import java.util.*;

/**
 * Created by Yohanna on 2017-03-16.
 */
public class Instrumentation {

    private String TABS = "\t";
    private Stack titles = new Stack();

    private static Instrumentation ourInstance = new Instrumentation();

    public static Instrumentation getInstance() {
        return ourInstance;
    }

    private Instrumentation() {
    }

//    private void

    private String removeChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
