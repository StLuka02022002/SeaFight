package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Ship {
    private static final String SHIP_FILE_NAME_FORMAT = "image/ship/Ship%d%d.png";
    private static final String SHIP_IN_FIRE_FILE_NAME_FORMAT = "image/fire/ShipFire%d%d.png";

    private Orientation orientation;
    private int size;
    private ArrayList<Cell> allCells;
    private ArrayList<ArrayList<Image>> allImage;
    private int poz_X;
    private int poz_Y;
    private boolean isKill = true;
    private int countWounded = 0;

    public Ship(int size, boolean fire) {
        this(size, 0, 0, fire);
    }


    public Ship(int size, int poz_X, int poz_Y, boolean fire) {
        this(size, poz_X, poz_Y, Orientation.RIGHT, fire);
    }

    public Ship(int size, int poz_X, int poz_Y, Orientation orientation, boolean fire) {
        this.orientation = orientation;
        this.size = size;
        this.poz_X = poz_X;
        this.poz_Y = poz_Y;
        allCells = new ArrayList<>();
        allImage = new ArrayList<>();
        addCells();
        setAllImageCell(fire);
        setOrientation(orientation);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        if (allCells.isEmpty() || allCells.size() != size) {
            //TODO выдать, вывести исключение
            return;
        }
        this.orientation = orientation;
        for (Cell cell :
                allCells) {
            cell.setOrientation(orientation);
        }
        if (orientation.equals(Orientation.LEFT)) {
            for (int i = 0; i < size; i++) {
                allCells.get(size - i - 1).setPoz(poz_X + i, poz_Y);
            }
        }
        if (orientation.equals(Orientation.RIGHT)) {
            for (int i = 0; i < size; i++) {
                allCells.get(i).setPoz(poz_X + i, poz_Y);
            }
        }
        if (orientation.equals(Orientation.UP)) {
            for (int i = 0; i < size; i++) {
                allCells.get(size - i - 1).setPoz(poz_X, poz_Y + i);
            }
        }
        if (orientation.equals(Orientation.DOWN)) {
            for (int i = 0; i < size; i++) {
                allCells.get(i).setPoz(poz_X, poz_Y + i);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Cell> getAllCells() {
        return allCells;
    }

    public void setAllCells(ArrayList<Cell> allCells) {
        this.allCells = allCells;
    }

    public int getPoz_X() {
        return poz_X;
    }

    public void setPoz_X(int poz_X) {
        this.poz_X = poz_X;
    }

    public void setPoz(int X, int Y) {
        poz_X = X;
        poz_Y = Y;
    }

    public int getPoz_Y() {
        return poz_Y;
    }

    public void setPoz_Y(int poz_Y) {
        this.poz_Y = poz_Y;
    }

    public boolean isKill() {
        return isKill;
    }

    public void setKill(boolean kill) {
        isKill = kill;
    }

    public int getCountWounded() {
        return countWounded;
    }

    public void setCountWounded(int countWounded) {
        this.countWounded = countWounded;
    }

    public void killed() {
        countWounded++;
    }

    public boolean isKillShip() {
        return countWounded == size;
    }

    public boolean isCell(Cell cellOfShip) {
        for (Cell cell :
                allCells) {
            if (cellOfShip == cell) {
                return true;
            }
        }
        return false;
    }

    private void setAllImage(String fileFormat) {
        allImage.clear();
        ArrayList<Image> listImage;
        listImage = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            String imageFileName = String.format(fileFormat, size, i);
            Image image = new Image(Main.class.getResourceAsStream(imageFileName));
            listImage.add(image);
        }
        allImage.add(listImage);
    }

    private void setAllImage(boolean fire) {
        if (!fire) setAllImage(SHIP_FILE_NAME_FORMAT);
        else {
            setAllImage(SHIP_IN_FIRE_FILE_NAME_FORMAT);
        }
    }

    public void setAllImageCell(boolean fire) {
        setAllImage(fire);
        if (!allImage.isEmpty() && !allCells.isEmpty()) {
            ArrayList<Image> listImage = allImage.get(0);
            int i = 0;
            for (Cell cell :
                    allCells) {
                cell.setImage(listImage.get(i));
                i++;
            }

        }
    }

    public void addCell(Cell cell) {
        allCells.add(cell);
    }

    private void addCells() {
        for (int i = 0; i < size; i++) {
            Cell cell = new Cell(0, 0, Orientation.RIGHT);
            cell.setShip(true);
            addCell(cell);
        }
    }

    public Cell getCell(Cell cell) {
        for (Cell cellShip :
                allCells) {
            if (cellShip.equals(cell)) {
                return cellShip;
            }
        }
        return null;
    }

    public Cell getCell(int poz_X, int poz_Y) {
        for (Cell cellShip :
                allCells) {
            if (cellShip.getPoz_X() == poz_X &&
                    cellShip.getPoz_Y() == poz_Y) {
                return cellShip;
            }
        }
        return null;
    }

}
