package sample;

import java.util.ArrayList;

public class Field {
    private ArrayList<ArrayList<Cell>> fieldCell;
    private int[][] field;
    private boolean[][] onShip;
    private Orientation[][] orShip;
    private ArrayList<Ship> allShip;
    public static final int SIZE = 10;

    public Field() {
        initialize();
        fill();
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public void setFieldOnPoz(int poz_X, int poz_Y, int number) {
        if (isRightPoz(poz_X, poz_Y)) {
            field[poz_Y][poz_X] = number;
        }
    }

    public ArrayList<Ship> getAllShip() {
        return allShip;
    }

    public void setAllShip(ArrayList<Ship> allShip) {
        this.allShip = allShip;
    }

    public ArrayList<ArrayList<Cell>> getFieldCell() {
        return fieldCell;
    }

    public static Field getEmptyField() {
        return new Field();
    }

    private void fill() {
        for (int i = 0; i < SIZE; i++) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = 0;
                onShip[i][j] = false;
                orShip[i][j] = Orientation.RIGHT;
                line.add(new Cell(j, i, Orientation.RIGHT));
            }
            fieldCell.add(line);
        }
    }

    public void setCell(int poz_X, int poz_Y, Cell cell) {
        if (isRightPoz(poz_X, poz_Y)) {
            cell.setPoz(poz_X, poz_Y);
            fieldCell.get(poz_Y).set(poz_X, cell);
        }
    }

    public Cell getCell(int poz_X, int poz_Y) {
        if (isRightPoz(poz_X, poz_Y)) {
            return fieldCell.get(poz_Y).get(poz_X);
        } else {
            return null;
        }
    }

    public Ship getShipByCell(Cell cell) {
        for (Ship ship :
                allShip) {
            for (Cell cellShip :
                    ship.getAllCells()) {
                if (cell.equals(cellShip)) {
                    return ship;
                }
            }
        }
        return null;
    }

    private boolean isRightPoz(int poz_X, int poz_Y) throws IllegalStateException {
        if (poz_X >= 0 && poz_X < SIZE
                && poz_Y >= 0 && poz_Y < SIZE) return true;
        else {
            throw new IllegalStateException("Неверные координаты");
        }
    }


    public void randomGO(Ship ship) {
        int i;
        int sizeShip = ship.getSize();
        Orientation orientation = getRandomOrientation();
        int y = RND.random(0, SIZE), x = RND.random(0, SIZE);
        if (orientation == Orientation.DOWN || orientation == Orientation.UP) {
            while (onShip(x, y, sizeShip, orientation) || y > SIZE - sizeShip) {
                y = RND.random(0, SIZE);
                x = RND.random(0, SIZE);
            }
            if (y != 0) {
                if (x != 0) onShip[y - 1][x - 1] = true;
                onShip[y - 1][x] = true;
                if (x != SIZE - 1) onShip[y - 1][x + 1] = true;
            }
            for (i = 0; i < sizeShip; i++) {
                field[y + i][x] = sizeShip;
                onShip[y + i][x] = true;
                orShip[y + i][x] = orientation;
                if (x != 0) onShip[y + i][x - 1] = true;
                if (x != SIZE - 1) onShip[y + i][x + 1] = true;
            }
            if (y != SIZE - sizeShip) {
                if (x != 0) onShip[y + sizeShip][x - 1] = true;
                onShip[y + sizeShip][x] = true;
                if (x != SIZE - 1) onShip[y + sizeShip][x + 1] = true;
            }
        }
        if (orientation == Orientation.RIGHT || orientation == Orientation.LEFT) {
            while (onShip(x, y, sizeShip, orientation) || x > SIZE - sizeShip) {
                y = RND.random(0, SIZE);
                x = RND.random(0, SIZE);
            }
            if (x != 0) {
                if (y != 0) onShip[y - 1][x - 1] = true;
                onShip[y][x - 1] = true;
                if (y != SIZE - 1) onShip[y + 1][x - 1] = true;
            }
            for (i = 0; i < sizeShip; i++) {
                field[y][x + i] = sizeShip;
                onShip[y][x + i] = true;
                orShip[y][x + i] = orientation;
                if (y != 0) onShip[y - 1][x + i] = true;
                if (y != SIZE - 1) onShip[y + 1][x + i] = true;
            }
            if (x != SIZE - sizeShip) {
                if (y != 0) onShip[y - 1][x + sizeShip] = true;
                onShip[y][x + sizeShip] = true;
                if (y != SIZE - 1) onShip[y + 1][x + sizeShip] = true;
            }
        }
        ship.setPoz(x, y);
        ship.setOrientation(orientation);
        addShipOnField(ship);
    }

    private void addShipOnField(Ship ship) {
        for (Cell cell :
                ship.getAllCells()) {
            fieldCell.get(cell.getPoz_Y()).set(cell.getPoz_X(), cell);
        }
    }

    public void creteFieldRandomGO() {
        for (Ship ship :
                allShip) {
            randomGO(ship);
        }
    }

    private boolean onShip(int x, int y, int sizeShip, Orientation orientation) {
        int startX, startY;
        int endX, endY;
        startX = x;
        startY = y;
        if (orientation == Orientation.RIGHT || orientation == Orientation.LEFT) {
            endX = x + sizeShip - 1;
            endY = y;
        } else {
            endY = y + sizeShip - 1;
            endX = x;
        }
        int i, j;
        for (i = startX; i <= endX; i++) {
            for (j = startY; j <= endY; j++) {
                if (i < SIZE && j < SIZE)
                    if (onShip[j][i]) return true;
            }
        }
        return false;
    }

    private Orientation getRandomOrientation() {
        int orientation = RND.random(0, Orientation.CLOCKWISE.length);
        if (orientation < Orientation.CLOCKWISE.length) {
            return Orientation.CLOCKWISE[orientation];
        } else {
            throw new IllegalStateException("Unexpected value: " + orientation);
        }
    }

    public void addShips(Ship ship) {
        allShip.add(ship);
    }

    public void initialize() {
        fieldCell = new ArrayList<>();
        field = new int[SIZE][SIZE];
        onShip = new boolean[SIZE][SIZE];
        orShip = new Orientation[SIZE][SIZE];
        allShip = new ArrayList<>();
    }

    public void clearField() {
        initialize();
        fill();
    }
}
