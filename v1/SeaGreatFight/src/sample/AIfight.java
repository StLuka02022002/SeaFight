package sample;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class AIfight {

    private static int hod = 0;
    private static int[] win;
    private static GridPane paneOfEnemy;
    private static GridPane paneOfYou;
    private static BadeField bd;
    private static Field fieldOfEnemy;
    private static Field fieldOfYou;
    private static ArrayList<String> allCell = new ArrayList<>();
    private static Timer timer;
    private static boolean onFireShip = false;
    private static int[] onFireShipOr = {-1, -1, -1, -1};

    public AIfight(BadeField bd) {
        this.bd = bd;
        win = new int[bd.getSize()];
        Arrays.fill(win, 0);
        randomRight();
    }

    public static BadeField getBd() {
        return bd;
    }

    public void setBd(BadeField bd) {
        this.bd = bd;
        win = new int[bd.getSize()];
        Arrays.fill(win, 0);
    }

    public static int[] getWin() {
        return win;
    }

    public static void setWin(int[] win) {
        AIfight.win = win;
    }

    public AIfight(GridPane paneOfEnemy, GridPane paneOfYou) {
        this.paneOfEnemy = paneOfEnemy;
        this.paneOfYou = paneOfYou;
        randomRight();
    }

    public static int getHod() {
        return hod;
    }

    public static void setHod(int hod) {
        AIfight.hod = hod;
    }

    public GridPane getPaneOfEnemy() {
        return paneOfEnemy;
    }

    public void setPaneOfEnemy(GridPane paneOfEnemy) {
        this.paneOfEnemy = paneOfEnemy;
    }

    public static Field getFieldOfEnemy() {
        return fieldOfEnemy;
    }

    public static void setFieldOfEnemy(Field fieldOfEnemy) {
        AIfight.fieldOfEnemy = fieldOfEnemy;
    }

    public static Field getFieldOfYou() {
        return fieldOfYou;
    }

    public static void setFieldOfYou(Field fieldOfYou) {
        AIfight.fieldOfYou = fieldOfYou;
    }

    public GridPane getPaneOfYou() {
        return paneOfYou;
    }

    public void setPaneOfYou(GridPane paneOfYou) {
        this.paneOfYou = paneOfYou;
    }

    public static boolean isOnFireShip() {
        return onFireShip;
    }

    public static void setOnFireShip(boolean onFireShip) {
        AIfight.onFireShip = onFireShip;
    }

    public static int[] getOnFireShipOr() {
        return onFireShipOr;
    }

    public static void setOnFireShipOr(int[] onFireShipOr) {
        AIfight.onFireShipOr = onFireShipOr;
    }

    public void setField() {
        if (bd.getSize() != 0) {
            fieldOfEnemy = bd.getBadeField()[0];
            fieldOfYou = bd.getBadeField()[1];
        }
    }

    public static ArrayList<String> getAllCell() {
        return allCell;
    }

    public static void setAllCell(ArrayList<String> allCell) {
        AIfight.allCell = allCell;
    }

    private void randomRight() {
        hod = rnd(0, 2);
        TextTable.START(hod);
    }

    public static void randomCell() {
        if (hod == 1) {
            paneOfEnemy.setMouseTransparent(true);
            timer = new Timer();
            timer.schedule(new AlarmTask(), 2000);
        } else {
            paneOfEnemy.setMouseTransparent(false);
        }
    }

    public static void randomCell(Ship ship, Cell cell) {
        onFireShip = true;
        allCell.add(String.valueOf(cell.getPoz_X()) + String.valueOf(cell.getPoz_Y()));
        if (ship.getCountWounded() > 1) {
            for (Cell cell1 :
                    ship.getAllCells()) {
                int allCellsFire = 0;
                String str = String.valueOf(cell1.getPoz_X()) + String.valueOf(cell1.getPoz_Y());
                for (String s :
                        allCell) {
                    if (!str.equals(s)) {
                        allCellsFire++;
                    } else break;
                }
                if (allCellsFire == allCell.size()) {
                    allCell.add(str);
                    timer = new Timer();
                    timer.schedule(new AlarmTask1(cell1), 2000);
                    break;
                }
            }
        } else {
            int x = cell.getPoz_X(), dx = 0;
            int y = cell.getPoz_Y(), dy = 0;
            int np = 0;
            int g = 0;
            while (g < 4) {
                g = 0;
                np = rnd(0, 4);
                for (int i = 0; i < 4; i++) {
                    if (np != onFireShipOr[i]) {
                        g++;
                    } else break;
                }
            }
            g = 0;
            while (onFireShipOr[g] != -1) {
                g++;
            }
            onFireShipOr[g] = np;
            if (np == 0 || np == 2) {
                if (x == 0) {
                    dx = 1;
                } else if (x == 9) {
                    dx = -1;
                } else if (np == 0) {
                    dx = 1;
                } else if (np == 2) {
                    dx = -1;
                }
            }
            if (np == 1 || np == 3) {
                if (y == 0) {
                    dy = 1;
                } else if (y == 9) {
                    dy = -1;
                } else if (np == 1) {
                    dy = 1;
                } else if (np == 3) {
                    dy = -1;
                }
            }
            x += dx;
            y += dy;
            Cell cell1 = null;
            for (int i = 1; i < paneOfYou.getChildren().size(); i++) {
                cell1 = (Cell) paneOfYou.getChildren().get(i);
                if (x == cell1.getPoz_X() && y == cell1.getPoz_Y()) {
                    break;
                }
            }
            while (!strSCell(cell1.getPoz_X(), cell1.getPoz_Y())) {
                while (g < 4) {
                    g = 0;
                    np = rnd(0, 4);
                    for (int i = 0; i < 4; i++) {
                        if (np != onFireShipOr[i]) {
                            g++;
                        } else break;
                    }
                }
                g = 0;
                while (onFireShipOr[g] != -1) {
                    g++;
                }
                onFireShipOr[g] = np;
                if (np == 0 || np == 2) {
                    if (x == 0) {
                        dx = 1;
                    } else if (x == 9) {
                        dx = -1;
                    } else if (np == 0) {
                        dx = 1;
                    } else if (np == 2) {
                        dx = -1;
                    }
                }
                if (np == 1 || np == 3) {
                    if (y == 0) {
                        dy = 1;
                    } else if (y == 9) {
                        dy = -1;
                    } else if (np == 1) {
                        dy = 1;
                    } else if (np == 3) {
                        dy = -1;
                    }
                }
                x += dx;
                y += dy;
                for (int i = 1; i < paneOfYou.getChildren().size(); i++) {
                    cell1 = (Cell) paneOfYou.getChildren().get(i);
                    if (x == cell1.getPoz_X() && y == cell1.getPoz_Y()) {
                        break;
                    }
                }
            }
            allCell.add(String.valueOf(cell1.getPoz_X()) + String.valueOf(cell1.getPoz_Y()));
            timer = new Timer();
            timer.schedule(new AlarmTask1(cell1), 2000);
        }
    }

    public static int rnd(int min, int max) {
        int count = (int) ((double) Math.random() * (max - min)) + min;
        return count;
    }

    public static Cell onHit(int x, int y) throws IOException {
        return (Cell) paneOfYou.getChildren().get(x * 10 + y + 1);
    }

    public static Ship isCellOfShip(Cell cellOfShip) {
        for (Ship ship :
                bd.getBadeField(hod).getAllShip()) {
            if (ship.isCell(cellOfShip)) {
                return ship;
            }
        }
        return null;
    }

    private static boolean strSCell(int x, int y) {
        String s = String.valueOf(x) + String.valueOf(y);
        if (allCell.isEmpty()) {
            return true;
        } else {
            for (String str :
                    allCell) {
                if (s.equals(str)) {
                    return false;
                }
            }
            return true;
        }
    }

    static class AlarmTask extends TimerTask {

        @Override
        public void run() {
            Platform.runLater(() -> {
                timer.cancel();
                int x = rnd(0, 10), y = rnd(0, 10);
                Cell cell = null;
                try {
                    cell = onHit(x, y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (!strSCell(cell.getPoz_X(), cell.getPoz_Y())) {
                    x = rnd(0, 10);
                    y = rnd(0, 10);
                    try {
                        cell = onHit(x, y);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                allCell.add(String.valueOf(cell.getPoz_X()) + String.valueOf(cell.getPoz_Y()));

                if (cell.isShip()) {
                    BadeField.onWOUNDED(cell);
                } else {
                    BadeField.onMISS(cell, 1);
                }
            });
        }
    }

    static class AlarmTask1 extends TimerTask {
        private Cell cell;

        public AlarmTask1(Cell cell) {
            this.cell = cell;
        }

        @Override
        public void run() {
            Platform.runLater(() -> {
                timer.cancel();
                if (cell.isShip()) {
                    BadeField.onWOUNDED(cell);
                } else {
                    BadeField.onMISS(cell, 1);
                }
            });
        }
    }
}
