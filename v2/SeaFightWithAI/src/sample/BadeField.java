package sample;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class BadeField {
    private static Field enemyField;
    private static Field youField;
    private static Players player;
    private static AIFight aiFight;
    private static Timer timer;
    private static GridPane gridPane;

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
        player = Players.YOU;
        TextTable.START(player);
        aiFight = new EasyAIFight(youField);
        //timer = new Timer();
        //timer.schedule(new AlarmTask(),2000);
    }

    public static GridPane getGridPane() {
        return gridPane;
    }

    public static void setGridPane(GridPane gridPane) {
        BadeField.gridPane = gridPane;
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

    public static Field getEnemyField() {
        return enemyField;
    }

    public static void setEnemyField(Field enemyField) {
        BadeField.enemyField = enemyField;
    }

    public static Field getYouField() {
        return youField;
    }

    public static void setYouField(Field youField) {
        BadeField.youField = youField;
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

    /*public void onFireShip(int i) {
        if (i < size) {
            Field field = badeField[i];
            for (Ship ship :
                    field.getAllShip()) {
                onShip(ship);
            }
        }

    }
     */

    public static Cell onFire(Cell cell) {
        Field field = getField(player);
        Cell cellEnemy = field.getCell(cell.getPoz_X(), cell.getPoz_Y());
        if (cellEnemy.isShip()) {
            Ship ship = field.getShipByCell(cellEnemy);
            cell.setImage(cellEnemy.getImage());
            cell.setOrientation(cellEnemy.getOrientation());
            cell.setShip(cellEnemy.isShip());
            woundedCell(cell, ship);
        } else {
            missedCell(cell);
        }
        return cell;
    }

    public static void missedCell(Cell cell) {
        FadeTransition fd = new FadeTransition(Duration.seconds(0.8), cell);
        fd.setAutoReverse(true);
        fd.setFromValue(1.0);
        fd.setToValue(0.0);
        fd.setOnFinished((event) -> {

        });
        fd.play();
        Platform.runLater(() -> {
            step();
            TextTable.missed(player, cell.getPoz_X(), cell.getPoz_Y());
        });
        cell.setDisable(true);
    }

    public static void woundedCell(Cell cell, Ship ship) {
        cell.setBackgroundCell(cell.getImage());
        cell.setMark(false);
        cell.setMouseTransparent(true);
        ship.killed();
        Platform.runLater(() -> {
            TextTable.wounded(player, cell.getPoz_X(), cell.getPoz_Y(), ship);
        });
        Players player = isWin();
        if (player != null) {
            TextTable.STOP(player, cell.getPoz_X(), cell.getPoz_Y(), ship);
            for (ArrayList<Cell> line :
                    youField.getFieldCell()) {
                for (Cell cellYou :
                        line) {
                    if (!cellYou.isShip()) {
                        cellYou.setBackgroundCell((Background) null);
                    }
                }
            }
            for (Node node :
                    gridPane.getChildren()) {
                if (node instanceof Cell) {
                    Cell cellEnemy = (Cell) node;
                    if (!cellEnemy.isShip()) {
                        cellEnemy.setBackgroundCell((Background) null);
                    }
                }
            }
            gridPane.setMouseTransparent(true);
            timer.cancel();
        }
    }

    public static Players isWin() {
        if (isAllShipKill(youField)) {
            return Players.ENEMY;
        }
        if (isAllShipKill(enemyField)) {
            return Players.YOU;
        }
        return null;
    }

    public static boolean isAllShipKill(Field field) {
        for (Ship ship :
                field.getAllShip()) {
            if (!ship.isKillShip()) {
                return false;
            }
        }
        return true;
    }


    public static Field getField(Players player) {
        if (player == Players.ENEMY) {
            return youField;
        } else if (player == Players.YOU) {
            return enemyField;
        }
        return null;
    }

    public static void step() {
        if (player == Players.ENEMY) {
            player = Players.YOU;
            gridPane.setMouseTransparent(false);
            timer.cancel();
        } else {
            player = Players.ENEMY;
            timer = new Timer();
            gridPane.setMouseTransparent(true);
            timer.schedule(new AlarmTask(), 2000, 2000);
        }
    }

    static class AlarmTask extends TimerTask {

        @Override
        public void run() {
            Platform.runLater(() -> {
                Cell cell = aiFight.getCell();
                onFire(cell);
            });
        }
    }

    /*public static void onMISS(Cell cell, int i) {
        if (!cell.isShip()) {
            if (i == 1) {
                FadeTransition fd = new FadeTransition(Duration.seconds(0.8), cell);
                fd.setAutoReverse(true);
                fd.setFromValue(1.0);
                fd.setToValue(0.0);
                fd.play();
                cell.setDisable(true);
                TextTable.missed(AIfightk.getHod(), cell.getPoz_X(), cell.getPoz_Y());
                if (AIfightk.getHod() == 1) {
                    AIfightk.setHod(0);
                    AIfightk.getAllCell().add(String.valueOf(cell.getPoz_X()) + String.valueOf(cell.getPoz_Y()));
                    AIfightk.randomCell();
                } else {
                    AIfightk.setHod(1);
                    if (AIfightk.isOnFireShip()) {
                        AIfightk.randomCell(nuwShip, nuwCell);
                    } else AIfightk.randomCell();
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
            Ship ship = AIfightk.isCellOfShip(cell);
            ship.killed();
            allCell.add(cell);
            if (ship.isKillShip()) {
                AIfightk.getWin()[AIfightk.getHod()]++;
                if (AIfightk.getHod() == 1) {
                    AIfightk.setOnFireShip(false);
                    int[] OnFS = {-1, -1, -1, -1};
                    AIfightk.setOnFireShipOr(OnFS);
                    for (Cell cell1 :
                            ship.getAllCells()) {
                        AIfightk.getAllCell().add(String.valueOf(cell1.getPoz_X()) + String.valueOf(cell1.getPoz_Y()));
                    }
                }
            }
            if (AIfightk.getWin()[AIfightk.getHod()] == Field.SIZE) {
                TextTable.STOP(AIfightk.getHod(), cell.getPoz_X(), cell.getPoz_Y(), ship);
                onWIN();
            } else {
                TextTable.wounded(AIfightk.getHod(), cell.getPoz_X(), cell.getPoz_Y(), ship);
                if (AIfightk.getHod() == 1) {
                    AIfightk.getAllCell().add(String.valueOf(cell.getPoz_X()) + String.valueOf(cell.getPoz_Y()));
                    nuwShip = ship;
                    nuwCell = cell;
                    if (ship.isKillShip()) {
                        AIfightk.randomCell();
                    } else
                        AIfightk.randomCell(ship, cell);
                } else AIfightk.randomCell();
            }

        }
    }

    private static void onWIN() {
        for (int i = 0; i < size; i++) {
            Field fields = badeField[i];
            for (Ship ship :
                    fields.getAllShip()) {
                ship.setAllImageCell(false);
                ship.setOrientation(ship.getOrientation());
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
                    cell.setOrientation(ship.getOrientation());
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
    */
}
