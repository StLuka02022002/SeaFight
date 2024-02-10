package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
    private static final String LINE_SHIP = "-линейный корабль";
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

    public static void missed(Players player, int x, int y) {
        TextFlow textFlow = getFlowPane(false, false, x, y, 0);
        Text text = getEnemy(player);
        print(textFlow, text);
    }

    public static Text getEnemy(Players player) {
        Text text;
        if (player == Players.ENEMY) {
            text = new Text(TEXT_ENEMY);
        } else {
            text = new Text(TEXT_YOU);
        }
        text.setFill(Color.GREEN);
        text.setFont(FONT_OF_PLAYER);
        return text;
    }

    private static TextFlow getFlowPane(boolean isKill, boolean isHit, int x, int y, int shipSize) {
        String s = (x + 1) + " " + (y + 1) + ": ";
        Text t2, t3, t4;
        TextFlow textFlow;
        t2 = new Text(s);
        t2.setFill(Color.BLACK);
        t2.setFont(FONT_OF_HIT);
        if (isHit) {
            if (isKill) {
                t3 = new Text(TEXT_ON_KILL);
                t4 = new Text(shipSize + LINE_SHIP + "\n");
            } else {
                t3 = new Text(TEXT_ON_WOUNDED);
                t4 = new Text();
            }
            t3.setFill(Color.RED);
            textFlow = new TextFlow(t2, t3, t4);
        } else {
            t3 = new Text(TEXT_ON_MISS);
            t3.setFill(Color.BLUE);
            textFlow = new TextFlow(t2, t3);
        }
        t3.setFont(FONT_OF_HIT);
        return textFlow;
    }

    private static void print(TextFlow textFlow, Text text) {
        textPaneFight.getChildren().add(textFlow);
        textPaneFight.getChildren().add(new Text("\n"));
        textPaneFight.getChildren().add(text);
    }

    public static void wounded(Players player, int x, int y, Ship ship) {
        TextFlow textFlow = getFlowPane(ship.isKillShip(), true, x, y, ship.getSize());
        Text text = getEnemy(player);
        print(textFlow, text);
    }

    public static void START(Players player) {
        Text t1 = getEnemy(player);
        t1.setFill(Color.GREEN);
        t1.setFont(FONT_OF_PLAYER);
        textPaneFight.getChildren().add(t1);
    }

    public static void STOP(Players player, int x, int y, Ship ship) {
        TextFlow textFlow = getFlowPane(ship.isKillShip(), true, x, y, ship.getSize());
        Text text = new Text();
        print(textFlow, text);
        String winter = "";
        if (player == Players.ENEMY) {
            winter = TEXT_ENEMY;
        } else {
            winter = TEXT_YOU;
        }
        winter.replace(':', ' ');
        labelWin.setText("Победил" + winter);
        labelWin.setVisible(true);
    }
}
