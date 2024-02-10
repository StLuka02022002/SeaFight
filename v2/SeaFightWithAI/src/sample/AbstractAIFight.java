package sample;

public abstract class AbstractAIFight implements AIFight {
    protected Field field;

    public AbstractAIFight(Field field) {
        this.field = field;
    }

    protected Cell getCell(Cell cell) {
        if (cell.isShip()) {
            Ship ship = field.getShipByCell(cell);
            Ship newShip = new Ship(ship.getSize(), true);
            newShip.setPoz(ship.getPoz_X(), ship.getPoz_Y());
            newShip.setOrientation(ship.getOrientation());
            Cell newCell = newShip.getCell(cell);
            cell.setImage(newCell.getImage());
        }
        return cell;
    }

    public Field getField() {
        return field;
    }

    @Override
    public void setField(Field field) {
        this.field = field;
    }
}
