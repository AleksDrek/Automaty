package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class Controller {

    public TextField pointX;
    public TextField pointY;

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

    List<Point> punktyStartowe = new ArrayList<>();

    public void cleanCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (array != null && arraySize != 0) {
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {
                    array[i][j].state = 0;
                }
            }
        }
        punktyStartowe.clear();
        putCellInTheMiddle();
        dodajZiarno();
    }

    public void start() {
        initializeData();
        cleanCanvas();
    }

    public void putCellInTheMiddle() {

        if (array != null) {
            array[arraySize / 2][arraySize / 2].state = 1;
            displayData();
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

        dodajZiarno();
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                array[i][j] = new Cell();
            }
        }

    }

    void CalculateNextGridFromCoordinates() {
        //Faza 1
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                if (array[i][j].state == 0) {
                    if (array[i][j].time <= iterations * 1.1) {
                        array[i][j].state = 2;
                    }
                }
            }
        }
        //Faza 2
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                if (array[i][j].state == 2) {
                    array[i][j].state = 1;
                }
            }
        }
        displayData();
    }


    private void RecalcalculateOriginForCoords() {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {

                if (array[i][j].state != 1) {
                    double distance = Double.MAX_VALUE;
                    double tempDist = 0;
                    Point nearestPoint = new Point(-1, -1);
                    //sprawdzam ktory punkt jest najblizej potencjalnego zarodka

                    for (Point p : punktyStartowe) {
                        tempDist = Math.pow(p.posX - i, 2.0) + Math.pow(p.posY - j, 2.0);
                        if (tempDist < distance) {
                            nearestPoint = p;
                            distance = tempDist;
                        }
                    }
                    distance = Math.sqrt(distance);
                    array[i][j].time = distance;
                }
            }
        }
    }


    public void startMe() {
        final long startTime = System.currentTimeMillis();
        RecalcalculateOriginForCoords();

        iterations = 1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    CalculateNextGridFromCoordinates();
                    iterations++;
                } while (!isFull());
                System.out.println("operacja wykonała się w czasie::"+(System.currentTimeMillis()-startTime)+" milisekund");
            }
        }).start();


    }

    private boolean isFull() {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                if (array[i][j].state != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void zmienIlosc() {
        initializeData();
        punktyStartowe.clear();
        putCellInTheMiddle();
        dodajZiarno();
    }

    public void dodajZiarno() {
        punktyStartowe.add(new Point(arraySize / 2 + Integer.parseInt(pointX.getText()), arraySize / 2 + Integer.parseInt(pointY.getText())));
    }
}
