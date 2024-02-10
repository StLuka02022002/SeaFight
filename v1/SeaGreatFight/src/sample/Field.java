package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Field {
    private int[][] field;
    private boolean[][] onShip;
    private String[][] orShip;
    private ArrayList<Ship> allShip;
    public static final int SIZE = 10;

    public Field() {
        field = new int[10][10];
        onShip = new boolean[10][10];
        orShip = new String[10][10];
        allShip = new ArrayList<>();
        fill();
    }

    public int[][] getField() {
        return field;
    }

    public ArrayList<Ship> getAllShip() {
        return allShip;
    }

    public void setAllShip(ArrayList<Ship> allShip) {
        this.allShip = allShip;
    }

    private void fill() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = 0;
                onShip[i][j] = false;
                orShip[i][j] = "right";
            }
        }
    }

    public void randomGO(Ship ship) {
        int i;
        int sizeShip = ship.getSize();
        String str = rndStr();
        int y = rnd(0, 10), x = rnd(0, 10);
        if (str.equals(Cell.CELL_DOWN) || str.equals(Cell.CELL_UP)) {
            while (onShip(x, y, sizeShip, 1) || y > 10 - sizeShip) {
                y = rnd(0, 10);
                x = rnd(0, 10);
            }
            if (y != 0) {
                if (x != 0) onShip[y - 1][x - 1] = true;
                onShip[y - 1][x] = true;
                if (x != 9) onShip[y - 1][x + 1] = true;
            }
            for (i = 0; i < sizeShip; i++) {
                field[y + i][x] = sizeShip;
                onShip[y + i][x] = true;
                orShip[y + i][x] = "" + str;
                if (x != 0) onShip[y + i][x - 1] = true;
                if (x != 9) onShip[y + i][x + 1] = true;
            }
            if (y != 10 - sizeShip) {
                if (x != 0) onShip[y + sizeShip][x - 1] = true;
                onShip[y + sizeShip][x] = true;
                if (x != 9) onShip[y + sizeShip][x + 1] = true;
            }
        }
        if (str.equals(Cell.CELL_RIGHT) || str.equals(Cell.CELL_LEFT)) {
            while (onShip(x, y, sizeShip, 0) || x > 10 - sizeShip) {
                y = rnd(0, 10);
                x = rnd(0, 10);
            }
            if (x != 0) {
                if (y != 0) onShip[y - 1][x - 1] = true;
                onShip[y][x - 1] = true;
                if (y != 9) onShip[y + 1][x - 1] = true;
            }
            for (i = 0; i < sizeShip; i++) {
                field[y][x + i] = sizeShip;
                onShip[y][x + i] = true;
                orShip[y][x + i] = "" + str;
                if (y != 0) onShip[y - 1][x + i] = true;
                if (y != 9) onShip[y + 1][x + i] = true;
            }
            if (x != 10 - sizeShip) {
                if (y != 0) onShip[y - 1][x + sizeShip] = true;
                onShip[y][x + sizeShip] = true;
                if (y != 9) onShip[y + 1][x + sizeShip] = true;
            }
        }
        ship.setPoz(x, y);
        ship.setOrientation(str);
    }

    public void creteFieldRandomGO() {
        for (Ship ship :
                allShip) {
            randomGO(ship);
            for (Cell cell :
                    ship.getAllCells()) {
                cell.setOrientir(Ship.CELL_RIGHT);
            }
        }
    }

    private boolean onShip(int x, int y, int sizeShip, int l) {
        int startX, startY;
        int endX, endY;
        startX = x;
        startY = y;
        if (l == 0) {
            endX = x + sizeShip - 1;
            endY = y;
        } else {
            endY = y + sizeShip - 1;
            endX = x;
        }
        int i, j;
        for (i = startX; i <= endX; i++) {
            for (j = startY; j <= endY; j++) {
                if (i < 10 && j < 10)
                    if (onShip[j][i]) return true;
            }
        }
        return false;
    }

    private String rndStr() {
        int or = rnd(1, 5);
        String s = "";
        switch (or) {
            case 1:
                s += Cell.CELL_RIGHT;
                break;
            case 2:
                s += Cell.CELL_LEFT;
                break;
            case 3:
                s += Cell.CELL_DOWN;
                break;
            case 4:
                s += Cell.CELL_UP;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + or);
        }
        return s;
    }

    private int rnd(int min, int max) {
        int count = (int) (Math.random() * (max - min)) + min;
        return count;
    }

    public void addShips(Ship ship) {
        allShip.add(ship);
    }

}
