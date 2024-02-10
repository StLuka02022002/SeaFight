package sample;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class BadeField {
    private static Field[] badeField;
    private static GridPane[] gridPanes;
    private static ArrayList<Cell> allCell = new ArrayList<>();
    private static int size = 0;
    private static Ship nuwShip = null;
    private static Cell nuwCell = null;
    private boolean[] hod;
    private int h = 0;

    public BadeField(int size) {
        this.size = size;
        badeField = new Field[size];
        hod = new boolean[size];
        gridPanes = new GridPane[size];
        Arrays.fill(hod, false);
        hod[h] = true;
    }

    public void addField(Field field) {
        int i;
        for (i = 0; i < size; i++) {
            if (badeField[i] == null) {
                badeField[i] = field;
                break;
            }
        }
    }

    public Field[] getBadeField() {
        return badeField;
    }

    public GridPane[] getGridPanes() {
        return gridPanes;
    }

    public void setGridPanes(GridPane[] gridPanes) {
        this.gridPanes = gridPanes;
    }

    public void addGridPane(GridPane gridPane) {
        int i;
        for (i = 0; i < size; i++) {
            if (gridPanes[i] == null) {
                gridPanes[i] = gridPane;
                break;
            }
        }
    }

    public GridPane getGridPanes(int i) {
        return gridPanes[i];
    }

    public Field getBadeField(int i) {
        return badeField[i];
    }

    public void setBadeField(Field[] badeField) {
        this.badeField = badeField;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void onFireShip(int i) {
        if (i < size) {
            Field field = badeField[i];
            for (Ship ship :
                    field.getAllShip()) {
                onShip(ship);
            }
        }

    }

    public static void onMISS(Cell cell, int i) {
        if (!cell.isShip()) {
            if (i == 1) {
                FadeTransition fd = new FadeTransition(Duration.seconds(0.8), cell);
                fd.setAutoReverse(true);
                fd.setFromValue(1.0);
                fd.setToValue(0.0);
                fd.play();
                cell.setDisable(true);
                TextTable.MIMO(AIfight.getHod(), cell.getPoz_X(), cell.getPoz_Y());
                if (AIfight.getHod() == 1) {
                    AIfight.setHod(0);
                    AIfight.getAllCell().add(String.valueOf(cell.getPoz_X()) + String.valueOf(cell.getPoz_Y()));
                    AIfight.randomCell();
                } else {
                    AIfight.setHod(1);
                    if (AIfight.isOnFireShip()) {
                        AIfight.randomCell(nuwShip, nuwCell);
                    } else AIfight.randomCell();
                }
            }
            if (i == 2) {
                if (cell.getOpacity() == 1)
                    cell.setOpacity(0.6);
                else cell.setOpacity(1);
            }
        }
    }


    public static void onWOUNDED(Cell cell) {
        if (cell.isShip()) {
            cell.setBackgroundCell(cell.getImage());
            cell.setOpacity(1);
            cell.setMouseTransparent(true);
            Ship ship = AIfight.isCellOfShip(cell);
            ship.killed();
            allCell.add(cell);
            if (ship.isKillShip()) {
                AIfight.getWin()[AIfight.getHod()]++;
                if (AIfight.getHod() == 1) {
                    AIfight.setOnFireShip(false);
                    int[] OnFS = {-1, -1, -1, -1};
                    AIfight.setOnFireShipOr(OnFS);
                    for (Cell cell1 :
                            ship.getAllCells()) {
                        AIfight.getAllCell().add(String.valueOf(cell1.getPoz_X()) + String.valueOf(cell1.getPoz_Y()));
                    }
                }
            }
            if (AIfight.getWin()[AIfight.getHod()] == Field.SIZE) {
                TextTable.STOP(AIfight.getHod(), cell.getPoz_X(), cell.getPoz_Y(), ship);
                onWIN();
            } else {
                TextTable.WOUNDED(AIfight.getHod(), cell.getPoz_X(), cell.getPoz_Y(), ship);
                if (AIfight.getHod() == 1) {
                    AIfight.getAllCell().add(String.valueOf(cell.getPoz_X()) + String.valueOf(cell.getPoz_Y()));
                    nuwShip = ship;
                    nuwCell = cell;
                    if (ship.isKillShip()) {
                        AIfight.randomCell();
                    } else
                        AIfight.randomCell(ship, cell);
                } else AIfight.randomCell();
            }

        }
    }

    private static void onWIN() {
        for (int i = 0; i < size; i++) {
            Field fields = badeField[i];
            for (Ship ship :
                    fields.getAllShip()) {
                ship.setAllImageCell();
                ship.setOrientir(ship.getOrientation());
                for (Cell cell :
                        allCell) {
                    cell.setBackgroundCell(cell.getImage());
                }
            }
            GridPane gridPane = gridPanes[i];
            for (int j = 1; j < gridPane.getChildren().size(); j++) {
                Cell cell = (Cell) gridPane.getChildren().get(j);
                if (!cell.isShip()) {
                    cell.setBackgroundCell((Background) null);
                }
            }
        }
    }

    private void onShip(Ship ship) {
        for (Cell cell :
                ship.getAllCells()) {
            cell.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    cell.setOrientir(ship.getOrientation());
                    onWOUNDED(cell);
                }
                if (event.getButton() == MouseButton.SECONDARY) {
                    if (cell.getOpacity() == 1)
                        cell.setOpacity(0.6);
                    else cell.setOpacity(1);
                }

            });
        }
    }
}
