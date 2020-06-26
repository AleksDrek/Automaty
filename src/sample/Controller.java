package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


public class Controller {

    @FXML
    Canvas canvas;
    @FXML
    TextField size;
    @FXML
    TextField iterationsTextField;

    int arraySize;
    private double cellSize;
    private Cell[][] array;


    public void cleanCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if(array!=null && arraySize!=0){
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {
                    array[i][j].oldState = 0;
                    array[i][j].state = 0;
                }
            }
        }
    }

    public void start() {
        cleanCanvas();
        initializeData();
    }

    public void putCellInTheMiddle() {
        array[arraySize / 2][arraySize / 2].state = 1;
        array[arraySize / 2][arraySize / 2].oldState = 1;
        displayData();
    }

    public void mooreAlgorythm() {

        int interations = Integer.parseInt(iterationsTextField.getText());

        for(int k = 0 ; k<interations; k++) {
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {

                    if (array[i][j].oldState == 1) {
                        //skośny górny lewy
                        if(i>0 && j>0 && array[i-1][j-1].oldState==0){
                            array[i-1][j-1].state=1;
                        }
                        //skośny górny prawy
                        if(i>0 && j<arraySize - 1 && array[i-1][j+1].oldState==0){
                            array[i-1][j+1].state=1;
                        }
                        //skośny dolny lewy
                        if(i<arraySize - 1 && j>0 && array[i+1][j-1].oldState==0){
                            array[i+1][j-1].state=1;
                        }
                        //skośny dolny prawy
                        if(i<arraySize - 1 && j<arraySize - 1 && array[i+1][j+1].oldState==0){
                            array[i+1][j+1].state=1;
                        }

                            // gorny środkowy
                            if (i > 0 && array[i - 1][j].oldState == 0) {
                                array[i - 1][j].state = 1;
                            }
                            // dolny środkowy
                            if (i < arraySize - 1 && array[i + 1][j].oldState == 0) {
                                array[i + 1][j].state = 1;
                            }
                            // lewy środkowy
                            if (j > 0 && array[i][j - 1].oldState == 0) {
                                array[i][j - 1].state = 1;
                            }
                            // prawy środkowy
                            if (j < arraySize - 1 && array[i][j + 1].oldState == 0) {
                                array[i][j + 1].state = 1;
                            }
                        }
                }
            }

            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {
                    array[i][j].oldState = array[i][j].state;
                }
            }
        }

        displayData();

    }

    public void vonNeumanAlgorythm() {

        int interations = Integer.parseInt(iterationsTextField.getText());

        for(int k = 0 ; k<interations; k++) {
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {

                    if (array[i][j].oldState == 1) {
                        // gorny środkowy
                        if (i > 0 && array[i - 1][j].oldState == 0) {
                            array[i - 1][j].state = 1;
                        }
                        // dolny środkowy
                        if (i < arraySize - 1 && array[i + 1][j].oldState == 0) {
                            array[i + 1][j].state = 1;
                        }
                        // lewy środkowy
                        if (j > 0 && array[i][j - 1].oldState == 0) {
                            array[i][j - 1].state = 1;
                        }
                        // prawy środkowy
                        if (j < arraySize - 1 && array[i][j + 1].oldState == 0) {
                            array[i][j + 1].state = 1;
                        }
                    }
                }
            }

        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                array[i][j].oldState = array[i][j].state;
            }
        }
        }

        displayData();
    }

    private void displayData() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                gc.setFill(array[i][j].getColorDependOfState());
                gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    private void initializeData() {
        arraySize = Integer.parseInt(size.getText());
        cellSize = canvas.getHeight() / arraySize;
        array = new Cell[arraySize][arraySize];

        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                array[i][j] = new Cell();
            }
        }

    }

    public void confirmChanges() {
        initializeData();
        cleanCanvas();
    }

}
