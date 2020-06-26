package sample;

import javafx.scene.paint.Color;

public class Cell {

    byte state = 0;
    byte oldState = 0;

    public Cell() {
    }


    public Color getColorDependOfState(){
        if(state ==0){
            return Color.BEIGE;
        }
        if(state ==1){
            return Color.MAGENTA;
        } if(state ==2){
            return Color.AZURE;
        }
        return Color.WHITE;
    }

}
