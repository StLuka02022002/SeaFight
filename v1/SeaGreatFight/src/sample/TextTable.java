package sample;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TextTable {

    private static TextFlow textPaneFight;
    private static Label labelWin;
    private static final String TEXT_ENEMY = "Враг:\n";
    private static final String TEXT_YOU = "Игрок:\n";
    private static final String TEXT_ON_MISS = "МИМО\n";
    private static final String TEXT_ON_WOUNDED = "ПОПАЛ\n";
    private static final String TEXT_ON_KILL = "УБИЛ ";
    private static final Font FONT_OF_PLAYER = Font.font("Verdana", 20);
    private static final Font FONT_OF_HIT = Font.font("Verdana", FontPosture.ITALIC, 16);

    public TextTable(TextFlow textPaneFight) {
        this.textPaneFight = textPaneFight;
    }

    public static Label getLabelWin() {
        return labelWin;
    }

    public static void setLabelWin(Label labelWin) {
        TextTable.labelWin = labelWin;
    }

    public static TextFlow getTextPaneFight() {
        return textPaneFight;
    }

    public static void setTextPaneFight(TextFlow textPaneFight) {
        TextTable.textPaneFight = textPaneFight;
    }

    public static void MIMO(int i, int x, int y) {
        String s = String.valueOf(x + 1) + " " + String.valueOf(y + 1) + ": ";
        Text t1 = new Text(), t2 = new Text(s), t3 = new Text(TEXT_ON_MISS);
        if (i == 0) {
            t1 = new Text(TEXT_ENEMY);
        }
        if (i == 1) {
            t1 = new Text(TEXT_YOU);
        }
        t1.setFill(Color.GREEN);
        t1.setFont(FONT_OF_PLAYER);
        t2.setFill(Color.BLACK);
        t2.setFont(FONT_OF_HIT);
        t3.setFill(Color.BLUE);
        t3.setFont(FONT_OF_HIT);
        TextFlow textField = new TextFlow(t2, t3);
        textPaneFight.getChildren().add(textField);
        textPaneFight.getChildren().add(new Text("\n"));
        textPaneFight.getChildren().add(t1);
    }

    public static void WOUNDED(int i, int x, int y, Ship ship) {
        String s = String.valueOf(x + 1) + " " + String.valueOf(y + 1) + ": ";
        Text t1 = new Text(), t2 = new Text(s), t3 = new Text(), t4 = new Text();
        if (ship.isKillShip()) {
            if (i == 1) {
                t1 = new Text(TEXT_ENEMY);
            }
            if (i == 0) {
                t1 = new Text(TEXT_YOU);
            }
            t3 = new Text(TEXT_ON_KILL);
            String str = String.valueOf(ship.getSize()) + "-линейный корабль";
            t4 = new Text(str + "\n");
        } else {
            if (i == 1) {
                t1 = new Text(TEXT_ENEMY);
            }
            if (i == 0) {
                t1 = new Text(TEXT_YOU);
            }
            t3 = new Text(TEXT_ON_WOUNDED);
        }
        t1.setFill(Color.GREEN);
        t1.setFont(FONT_OF_PLAYER);
        t2.setFill(Color.BLACK);
        t2.setFont(FONT_OF_HIT);
        t3.setFill(Color.RED);
        t3.setFont(FONT_OF_HIT);
        t4.setFill(Color.BLACK);
        t4.setFont(FONT_OF_HIT);
        TextFlow textField = new TextFlow(t2, t3, t4);
        textPaneFight.getChildren().add(textField);
        textPaneFight.getChildren().add(new Text("\n"));
        textPaneFight.getChildren().add(t1);
    }

    public static void START(int i){
        Text t1 = new Text();
        if (i == 1) {
            t1 = new Text(TEXT_ENEMY);
        }
        if (i == 0) {
            t1 = new Text(TEXT_YOU);
        }
        t1.setFill(Color.GREEN);
        t1.setFont(FONT_OF_PLAYER);
        textPaneFight.getChildren().add(t1);
    }

    public static void STOP(int i, int x, int y, Ship ship){
        String s = String.valueOf(x + 1) + " " + String.valueOf(y + 1) + ": ";
        Text t2 = new Text(s), t3 = new Text(), t4 = new Text();
        if (ship.isKillShip()) {
            t3 = new Text(TEXT_ON_KILL);
            String str = String.valueOf(ship.getSize()) + "-линейный корабль";
            t4 = new Text(str + "\n");
        } else {
            t3 = new Text(TEXT_ON_WOUNDED);
        }
        t2.setFill(Color.BLACK);
        t2.setFont(FONT_OF_HIT);
        t3.setFill(Color.RED);
        t3.setFont(FONT_OF_HIT);
        t4.setFill(Color.BLACK);
        t4.setFont(FONT_OF_HIT);
        TextFlow textField = new TextFlow(t2, t3, t4);
        textPaneFight.getChildren().add(textField);
        textPaneFight.getChildren().add(new Text("\n"));
        labelWin.setVisible(true);
        if (i == 1) {
            s = TEXT_ENEMY;
        }
        if (i == 0) {
            s = TEXT_YOU;
        }
        s = s.replace(':',' ');
        labelWin.setText("Победил " + s);
    }
}
