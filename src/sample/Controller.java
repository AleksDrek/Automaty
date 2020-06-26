package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Random;


public class Controller {

    @FXML
    Canvas canvas;
    @FXML
    TextField size;
    @FXML
    TextField iterationsTextField;
    int iterations;
    int arraySize;
    private double cellSize;
    private Cell[][] array;


    public void cleanCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (array != null && arraySize != 0) {
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {
                    array[i][j].oldState = 0;
                    array[i][j].state = 0;
                }
            }
        }
        putCellInTheMiddle();
    }

    public void start() {
        initializeData();
        cleanCanvas();
    }

    public void putCellInTheMiddle() {

        if(array!=null){
            array[arraySize / 2][arraySize / 2].state = 1;
            array[arraySize / 2][arraySize / 2].oldState = 1;
            displayData();
        }

    }


    public void mooreAlgorythmSimulation() {

        int interations = Integer.parseInt(iterationsTextField.getText());

        for (int k = 0; k < interations; k++) {
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {

                    if (array[i][j].oldState == 1) {
                        moore(i, j);
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


    private void moore(int i, int j) {
        //skośny górny lewy
        if (i > 0 && j > 0 && array[i - 1][j - 1].oldState == 0) {
            array[i - 1][j - 1].state = 1;
        }
        //skośny górny prawy
        if (i > 0 && j < arraySize - 1 && array[i - 1][j + 1].oldState == 0) {
            array[i - 1][j + 1].state = 1;
        }
        //skośny dolny lewy
        if (i < arraySize - 1 && j > 0 && array[i + 1][j - 1].oldState == 0) {
            array[i + 1][j - 1].state = 1;
        }
        //skośny dolny prawy
        if (i < arraySize - 1 && j < arraySize - 1 && array[i + 1][j + 1].oldState == 0) {
            array[i + 1][j + 1].state = 1;
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

    public void vonNeumanAlgorythm() {

        for (int k = 0; k < iterations; k++) {
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {

                    if (array[i][j].oldState == 1) {
                        vonNeuman(i, j);
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

    private void vonNeuman(int i, int j) {
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
        iterations = Integer.parseInt(iterationsTextField.getText());
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

    public void hexagonalSymetricSimulation() {
        for (int k = 0; k < iterations; k++) {
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {

                    if (array[i][j].oldState == 1) {
                        if (i % 2 == 0)
                            vonNeuman(i, j);
                        else
                            moore(i, j);
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

    public void pentagonalSymetricSimulation() {
        for (int k = 0; k < iterations; k++) {
            int counter = 0;
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {

                    if (array[i][j].oldState == 1) {
                        if (counter == 0)
                            moore(i, j);
                        else
                            vonNeuman(i, j);
                    }
                }
                counter++;
                if (counter > 3) {
                    counter = 0;
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

    public void heptagonalSymetricSimulation() {
        for (int k = 0; k < iterations; k++) {
            int counter = 0;
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {

                    if (array[i][j].oldState == 1) {
                        if (counter == 0)
                            vonNeuman(i, j);
                        else
                            moore(i, j);

                    }
                }
                counter++;
                if (counter > 3) {
                    counter = 0;
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

    public void pseudoHexagonalSimulation() {
        Random rand = new Random();

        for (int k = 0; k < iterations; k++) {

            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {

                    if (array[i][j].oldState == 1) {
                        if (rand.nextInt(3) == 1)
                            moore(i, j);
                        else
                            vonNeuman(i, j);

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
}
