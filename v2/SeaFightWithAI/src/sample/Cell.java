package sample;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Objects;

public class Cell extends Button {
    private Orientation orientation;
    private Image image;
    private int poz_X;
    private int poz_Y;
    private boolean isShip = false;
    private boolean mark = false;

    public Cell(int X, int Y, Orientation orientation) {
        poz_X = X;
        poz_Y = Y;
        this.orientation = orientation;
        setPrefSize(1000, 1000);
        setOrientation(orientation);
    }

    public int getPoz_X() {
        return poz_X;
    }

    public void setPoz_X(int poz_X) {
        this.poz_X = poz_X;
    }

    public int getPoz_Y() {
        return poz_Y;
    }

    public void setPoz_Y(int poz_Y) {
        this.poz_Y = poz_Y;
    }

    public void setPoz(int X, int Y) {
        poz_X = X;
        poz_Y = Y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        if (orientation.equals(Orientation.LEFT)) {
            setRotate(180);
        }
        if (orientation.equals(Orientation.RIGHT)) {
            setRotate(0);
        }
        if (orientation.equals(Orientation.UP)) {
            setRotate(-90);
        }
        if (orientation.equals(Orientation.DOWN)) {
            setRotate(90);
        }
    }

    public void setBackgroundCell(Background background) {
        setBackground(background);
    }

    public void setBackgroundCell(Image image) {
        Background background = new Background(new BackgroundImage(
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
        setBackgroundCell(image);
    }

    public void setShip(boolean isShip) {
        this.isShip = isShip;
    }

    public boolean isShip() {
        return isShip;
    }

    public boolean isMark() {
        return mark;
    }

    public void mark() {
        setMark(!mark);
    }

    public void setMark(boolean isMark) {
        if (isMark && !isShip) {
            mark = true;
            setOpacity(0.6);
        } else {
            mark = false;
            setOpacity(1.0);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return poz_X == cell.poz_X && poz_Y == cell.poz_Y && isShip == cell.isShip;
    }

    @Override
    public int hashCode() {
        return Objects.hash(poz_X, poz_Y, isShip);
    }
}
