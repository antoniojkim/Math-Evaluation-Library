package Math_Evaluation_Library.GraphingTechnology;

import java.awt.*;

/**
 * Created by Antonio on 2017-07-22.
 */
public class Screen {

    private static double screenWidth = -1, screenHeight = -1, screenDiagonal = -1;
    private static Rectangle max_windowSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

    public static double getWidth(){
        if (screenWidth == -1) {
            screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        }
        return screenWidth;
    }
    public static double getHeight(){
        if (screenHeight == -1) {
            screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        }
        return screenHeight;
    }
    public static double getDiagonal(){
        if (screenDiagonal == -1) {
            screenDiagonal = Math.sqrt(Math.pow(getWidth(), 2)+Math.pow(getHeight(), 2));
        }
        return screenDiagonal;
    }
    public static int getTaskbarHeight(){
        return (int)(getHeight() - max_windowSize.height);
    }

}
