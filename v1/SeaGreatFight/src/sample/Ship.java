package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Ship {
    private String orientation;
    public final static String CELL_LEFT = "left";
    public final static String CELL_RIGHT = "right";
    public final static String CELL_UP = "up";
    public final static String CELL_DOWN = "down";
    private int size;
    private ArrayList<Cell> allCells;
    private ArrayList<ArrayList<Image>> allImage;
    private int poz_X;
    private int poz_Y;
    private boolean isKill = true;
    private int countWounded = 0;

    public Ship(int size, int poz_X, int poz_Y, String orientation) {
        this.orientation = orientation;
        this.size = size;
        this.poz_X = poz_X;
        this.poz_Y = poz_Y;
        allCells = new ArrayList<>();
        allImage = new ArrayList<>();
        addCells();
        setAllImageCell(true);
        allImage = new ArrayList<>();
        setAllImage();
        setAllImageCell();
        setOrientir(orientation);
    }

    public Ship(int size, int poz_X, int poz_Y, String orientation, boolean fire) {
        this.orientation = orientation;
        this.size = size;
        this.poz_X = poz_X;
        this.poz_Y = poz_Y;
        allCells = new ArrayList<>();
        allImage = new ArrayList<>();
        addCells();
        setAllImageCell(fire);
        setOrientir(orientation);
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
        setOrientir(orientation);
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
        if (countWounded == size) return true;
        return false;
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

    public void setOrientir(String s) {
        if (!allCells.isEmpty() && allCells.size() == size) {
            int i;
            for (Cell cell :
                    allCells) {
                cell.setOrientir(s);
            }
            if (s.equals(CELL_LEFT)) {
                for (i = 0; i < size; i++) {
                    allCells.get(size - i - 1).setPoz(poz_X + i, poz_Y);
                }
            }
            if (s.equals(CELL_RIGHT)) {
                for (i = 0; i < size; i++) {
                    allCells.get(i).setPoz(poz_X + i, poz_Y);
                }
            }
            if (s.equals(CELL_UP)) {
                for (i = 0; i < size; i++) {
                    allCells.get(size - i - 1).setPoz(poz_X, poz_Y + i);
                }
            }
            if (s.equals(CELL_DOWN)) {
                for (i = 0; i < size; i++) {
                    allCells.get(i).setPoz(poz_X, poz_Y + i);
                }
            }
        }
    }

    private void setAllImage() {
        allImage.clear();
        ArrayList<Image> listImage = new ArrayList<>();
        Image image = new Image(Main.class.getResourceAsStream("Ship11.png"));
        listImage.add(image);
        allImage.add(listImage);
        listImage = new ArrayList<>();
        image = new Image(Main.class.getResourceAsStream("Ship21.png"));
        listImage.add(image);
        image = new Image(Main.class.getResourceAsStream("Ship22.png"));
        listImage.add(image);
        allImage.add(listImage);
        listImage = new ArrayList<>();
        image = new Image(Main.class.getResourceAsStream("Ship31.png"));
        listImage.add(image);
        image = new Image(Main.class.getResourceAsStream("Ship32.png"));
        listImage.add(image);
        image = new Image(Main.class.getResourceAsStream("Ship33.png"));
        listImage.add(image);
        allImage.add(listImage);
        listImage = new ArrayList<>();
        image = new Image(Main.class.getResourceAsStream("Ship41.png"));
        listImage.add(image);
        image = new Image(Main.class.getResourceAsStream("Ship42.png"));
        listImage.add(image);
        image = new Image(Main.class.getResourceAsStream("Ship43.png"));
        listImage.add(image);
        image = new Image(Main.class.getResourceAsStream("Ship44.png"));
        listImage.add(image);
        allImage.add(listImage);
    }

    private void setAllImage(boolean fire) {
        if (!fire) setAllImage();
        else {
            allImage.clear();
            ArrayList<Image> listImage = new ArrayList<>();
            Image image = new Image(Main.class.getResourceAsStream("ShipFire11.png"));
            listImage.add(image);
            allImage.add(listImage);
            listImage = new ArrayList<>();
            image = new Image(Main.class.getResourceAsStream("ShipFire21.png"));
            listImage.add(image);
            image = new Image(Main.class.getResourceAsStream("ShipFire22.png"));
            listImage.add(image);
            allImage.add(listImage);
            listImage = new ArrayList<>();
            image = new Image(Main.class.getResourceAsStream("ShipFire31.png"));
            listImage.add(image);
            image = new Image(Main.class.getResourceAsStream("ShipFire32.png"));
            listImage.add(image);
            image = new Image(Main.class.getResourceAsStream("ShipFire33.png"));
            listImage.add(image);
            allImage.add(listImage);
            listImage = new ArrayList<>();
            image = new Image(Main.class.getResourceAsStream("ShipFire41.png"));
            listImage.add(image);
            image = new Image(Main.class.getResourceAsStream("ShipFire42.png"));
            listImage.add(image);
            image = new Image(Main.class.getResourceAsStream("ShipFire43.png"));
            listImage.add(image);
            image = new Image(Main.class.getResourceAsStream("ShipFire44.png"));
            listImage.add(image);
            allImage.add(listImage);
        }
    }

    public void setAllImageCell() {
        setAllImage();
        if (!allImage.isEmpty() && !allCells.isEmpty()) {
            ArrayList<Image> listImage = allImage.get(size - 1);
            int i = 0;
            for (Cell cell :
                    allCells) {
                cell.setBackgroundCell(listImage.get(i));
                i++;
            }
        }
    }

    public void setAllImageCell(boolean fire) {
        if (!fire) {
            setAllImageCell();
        } else {
            setAllImage(true);
            if (!allImage.isEmpty() && !allCells.isEmpty()) {
                ArrayList<Image> listImage = allImage.get(size - 1);
                int i = 0;
                for (Cell cell :
                        allCells) {
                    cell.setImage(listImage.get(i));
                    i++;
                }

            }
        }
    }

    public void addCell(Cell cell) {
        allCells.add(cell);
    }

    private void addCells() {
        int i;
        for (i = 0; i < size; i++) {
            Cell cell = new Cell(0, 0, "right");
            cell.setShip();
            addCell(cell);
        }
    }
}
