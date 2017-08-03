package Math_Evaluation_Library.GraphingTechnology;

import java.awt.*;

/**
 * Created by Antonio on 2017-07-22.
 */
public class _Font_ {

    private static final String CALIBRI = "Calibri";
    private static final String TIMES_NEW_ROMAN = "Times New Roman";

    private static String defaultFont = "Calibri";

    public static Font getFont(int size){
        return new Font(defaultFont, Font.PLAIN, size);
    }
    public static Font getFont(String type, int size){
        return new Font(type, Font.PLAIN, size);
    }

    public static String getDefaultFont() {
        return defaultFont;
    }
    public static void setDefaultFont(String defaultFont) {
        _Font_.defaultFont = defaultFont;
    }
}
