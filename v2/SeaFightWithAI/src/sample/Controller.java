package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Locale;

public class Controller {
    @FXML
    AnchorPane anchorPaneWindow;
    @FXML
    AnchorPane anchorPaneWindowSeaYou;
    @FXML
    AnchorPane anchorPaneWindowSeaEnemy;
    @FXML
    ScrollPane scrollPanePanel;
    @FXML
    GridPane fieldOfEnemy;
    @FXML
    GridPane fieldOfYou;
    @FXML
    TextFlow textAreaFight;
    @FXML
    Label labelWin;

    Field enemyField;
    Field youField;

    private Image imageBackgroundOnWindow = new Image(Main.class.getResourceAsStream("image/BackgroundOnWindow.jpg"));
    private Background BackgroundOnWindow = new Background(new BackgroundImage(
            imageBackgroundOnWindow,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(1, 1.05, true, true, false, false)
    ));

    @FXML
    private void initialize() {
        anchorPaneWindowSeaYou.setBackground(BackgroundOnWindow);
        anchorPaneWindowSeaEnemy.setBackground(BackgroundOnWindow);
        enemyField = new Field();
        youField = new Field();
        for (int i = 4; i > 0; i--) {
            for (int j = 1; j <= 5 - i; j++) {
                enemyField.addShips(new Ship(i, true));
                youField.addShips(new Ship(i, false));
            }
        }
        enemyField.creteFieldRandomGO();
        print(enemyField.getFieldCell());
        youField.creteFieldRandomGO();
        addField(Field.getEmptyField(), fieldOfEnemy);
        addField(youField, fieldOfYou);
        scrollPanePanel.vvalueProperty().bind(textAreaFight.heightProperty());
        TextTable.setTextPaneFight(textAreaFight);
        TextTable.setLabelWin(labelWin);
        BadeField.setEnemyField(enemyField);
        BadeField.setYouField(youField);
        BadeField.setGridPane(fieldOfEnemy);
        new BadeField(2);
        addAction();
    }

    private void addAction() {
        fieldOfEnemy.getChildren().forEach(node -> {
            if (node instanceof Cell) {
                node.setOnMouseClicked(event -> {
                    Cell cell = (Cell) node;
                    if (event.getButton() == MouseButton.PRIMARY) {
                        BadeField.onFire(cell);
                    }
                    if (event.getButton() == MouseButton.SECONDARY) {
                        cell.mark();
                    }
                });
            }
        });
        fieldOfYou.setMouseTransparent(true);
    }


    private void print(ArrayList<ArrayList<Cell>> allCell) {
        for (ArrayList<Cell> list :
                allCell) {
            for (Cell cell :
                    list) {
                if (cell.isShip()) {
                    System.out.print(cell.getOrientation().toString().substring(0, 1).toLowerCase(Locale.ROOT));
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }
    }

    private void setOnAction() {

    }


    private void addField(Field field, GridPane gridPane) {
        field.getFieldCell().forEach(cells -> {
            cells.forEach(cell -> {
                addOnFieldCell(cell, gridPane);
            });
        });
    }

    private void addOnFieldShip(Ship ship, GridPane gridPane) {
        for (Cell cell :
                ship.getAllCells()) {
            addOnFieldCell(cell, gridPane);
        }
    }

    private void addOnFieldCell(Cell cell, GridPane gridPane) {
        gridPane.add(cell, cell.getPoz_X(), cell.getPoz_Y());
    }


    @FXML
    private void newGame() {
        fieldOfEnemy.getChildren().remove(1, fieldOfEnemy.getChildren().size());
        fieldOfYou.getChildren().remove(1, fieldOfYou.getChildren().size());
        textAreaFight.getChildren().remove(0, textAreaFight.getChildren().size());
        labelWin.setVisible(false);
        initialize();
    }

    @FXML
    private void exitGame() {
        Platform.exit();
        System.exit(0);
    }
}
