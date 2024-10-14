package GUIs.objects;

import java.awt.*;

public class ColorScheme {
    private final Color backgroundColor;
    private final Color textColor;
    public ColorScheme(Color backgroundColor, Color textColor){
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public Color getBackgroundColor(){
        return this.backgroundColor;
    }

    public Color getTextColor(){
        return this.textColor;
    }
}
