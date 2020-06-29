package sample;

import javafx.scene.paint.Color;

public class Cell {


    byte state = 0;
    byte temporaryState = 0;
    double time = Double.MAX_VALUE;

    public Cell() {
    }

    public void changeState(){
        if (state == 0)
        {
            state = 2;
        }
        else if (state == 2)
        {
            state = 1;
        }
    }

    public Color getColorDependOfState() {
        if (state == 0) {
            return Color.BEIGE;
        }
        if (state == 1) {
            return Color.MAGENTA;
        }
        if (state == 2) {
            return Color.AZURE;
        }
        return Color.WHITE;
    }

}
