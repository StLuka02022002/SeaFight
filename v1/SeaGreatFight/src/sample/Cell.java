package sample;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class Cell extends Button {
    private Background background = getBackground();
    private Image image;
    private int poz_X;
    private int poz_Y;
    private String orientation;

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    private boolean isShip = false;
    public final static String CELL_LEFT = "left";
    public final static String CELL_RIGHT = "right";
    public final static String CELL_UP = "up";
    public final static String CELL_DOWN = "down";

    public Cell(int X, int Y, String s) {
        poz_X = X;
        poz_Y = Y;
        orientation = s;
        setPrefSize(1000, 1000);
        setOrientir(s);
    }

    public int getPoz_X() {
        return poz_X;
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

    public Background getBackgroundCell() {
        return background;
    }

    public void setBackgroundCell(Background background) {
        this.background = background;
        setBackground(background);
    }

    public void setBackgroundCell(Image image) {
        background = new Background(new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.05, 1.05, true, true, false, false)
        ));
        setBackgroundCell(background);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPoz_X(int poz_X) {
        this.poz_X = poz_X;
    }

    public void setOrientir(String s) {
        if (s.equals(CELL_LEFT)) {
            setRotate(180);
        }
        if (s.equals(CELL_RIGHT)) {
            setRotate(0);
        }
        if (s.equals(CELL_UP)) {
            setRotate(-90);
        }
        if (s.equals(CELL_DOWN)) {
            setRotate(90);
        }
    }

    public void setShip(){
        isShip = true;
    }

    public boolean isShip(){
        return isShip;
    }

    public boolean isCell(int X, int Y) {
        return (poz_X == X && poz_Y == Y);
    }
}
