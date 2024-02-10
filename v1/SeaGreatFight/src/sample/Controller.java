package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;

public class Controller {
    @FXML
    AnchorPane AnchorPaneWindow;
    @FXML
    AnchorPane AnchorPaneWindowSea;
    @FXML
    AnchorPane AnchorPaneWindowBackgroundSea;
    @FXML
    ScrollPane PanePanel;
    @FXML
    GridPane paneOfEnemy;
    @FXML
    GridPane paneOfYou;
    @FXML
    TextFlow textAreaFight;
    @FXML
    Label labelWin;

    private Image imageBackgroundOnWindow = new Image(Main.class.getResourceAsStream("BackgroundOnWindow.jpg"));
    private Background BackgroundOnWindow = new Background(new BackgroundImage(
            imageBackgroundOnWindow,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(1, 1.05, true, true, false, false)
    ));

    @FXML
    private void initialize() {
        AnchorPaneWindowSea.setBackground(BackgroundOnWindow);
        AnchorPaneWindowBackgroundSea.setBackground(BackgroundOnWindow);
        /*Cell cell1 = new Cell(0,0,"right");
        Cell cell2 = new Cell(1,0,"right");
        Cell cell3 = new Cell(2,0,"right");
        Cell cell4 = new Cell(3,0,"right");
        setBG(cell1,1);
        setBG(cell2,2);
        setBG(cell3,3);
        setBG(cell4,4);
        paneOfEnemy.add(cell1,cell1.getPoz_X(),cell1.getPoz_Y());
        paneOfEnemy.add(cell2,cell2.getPoz_X(),cell2.getPoz_Y());
        paneOfEnemy.add(cell3,cell3.getPoz_X(),cell3.getPoz_Y());
        paneOfEnemy.add(cell4,cell4.getPoz_X(),cell4.getPoz_Y());
         */
        /*Ship ship = new Ship(4, 5, 5, Ship.CELL_DOWN);
        for (Cell cell :
                ship.getAllCells()) {
            paneOfEnemy.add(cell, cell.getPoz_X(), cell.getPoz_Y());
        }
        ship = new Ship(3, 4, 5, Ship.CELL_DOWN);
        for (Cell cell :
                ship.getAllCells()) {
            paneOfEnemy.add(cell, cell.getPoz_X(), cell.getPoz_Y());
        }
        ship = new Ship(2, 3, 5, Ship.CELL_DOWN);
        for (Cell cell :
                ship.getAllCells()) {
            paneOfEnemy.add(cell, cell.getPoz_X(), cell.getPoz_Y());
        }
        ship = new Ship(1, 2, 5, Ship.CELL_DOWN);
        for (Cell cell :
                ship.getAllCells()) {
            paneOfEnemy.add(cell, cell.getPoz_X(), cell.getPoz_Y());
        }*/
        /*int i, j;
        for (i = 0; i < paneOfEnemy.getColumnConstraints().size(); i++) {
            for (j = 0; j < paneOfEnemy.getRowConstraints().size(); j++) {
                paneOfEnemy.add(new Cell(i,j,Cell.CELL_RIGHT), i, j);
            }
        }
         */
        Field emptyF = new Field();
        Field playF = new Field();
        for (int i = 4; i > 0; i--) {
            for (int j = 1; j <= 5 - i; j++) {
                emptyF.addShips(new Ship(i, 0, 0, Ship.CELL_RIGHT, true));
                playF.addShips(new Ship(i, 0, 0, Ship.CELL_RIGHT));
            }
        }
        emptyF.creteFieldRandomGO();
        playF.creteFieldRandomGO();
        setBG(emptyF, paneOfEnemy);
        setBG(playF, paneOfYou);
        for (Ship ship :
                emptyF.getAllShip()) {
            onBG_Ship(ship, paneOfEnemy);
        }
        for (Ship ship :
                playF.getAllShip()) {
            for (Cell cell :
                    ship.getAllCells()) {
                cell.setOrientir(ship.getOrientation());
            }
            onBG_Ship(ship, paneOfYou);
        }
        BadeField bd = new BadeField(2);
        bd.addField(emptyF);
        bd.addField(playF);
        bd.onFireShip(0);
        paneOfYou.setMouseTransparent(true);
        bd.addGridPane(paneOfEnemy);
        bd.addGridPane(paneOfYou);
        PanePanel.vvalueProperty().bind(textAreaFight.heightProperty());
        TextTable tx = new TextTable(textAreaFight);
        TextTable.setLabelWin(labelWin);
        AIfight AI = new AIfight(paneOfEnemy,paneOfYou);
        AI.setBd(bd);
        AIfight.randomCell();
        //paneOfEnemy.setMouseTransparent(true);
        //bd.onFireShip(1);
    }

    private void setBG(Field f, GridPane grid_pane) {
        int i, j;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                if (f.getField()[j][i] == 0) {
                    Cell cell = new Cell(i, j, Cell.CELL_RIGHT);
                    cell.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY)
                            BadeField.onMISS(cell, 1);
                        else if (event.getButton() == MouseButton.SECONDARY)
                            BadeField.onMISS(cell, 2);

                    });
                    grid_pane.add(cell, i, j);
                }
            }
        }
    }

    private void onBG_Ship(Ship ship, GridPane grid_pane) {
        for (Cell cell :
                ship.getAllCells()) {
            grid_pane.add(cell, cell.getPoz_X(), cell.getPoz_Y());
        }
    }

    @FXML
    private void newGame(){
        paneOfEnemy.getChildren().remove(1,paneOfEnemy.getChildren().size());
        paneOfYou.getChildren().remove(1,paneOfYou.getChildren().size());
        textAreaFight.getChildren().remove(0,textAreaFight.getChildren().size());
        labelWin.setVisible(false);
        initialize();
    }

    @FXML
    private void exitGame(){
        Platform.exit();
        System.exit(0);
    }

}
