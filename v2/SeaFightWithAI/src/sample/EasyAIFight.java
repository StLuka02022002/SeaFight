package sample;

public class EasyAIFight extends AbstractAIFight {

    public static final int OCCUPIED = -1;

    public EasyAIFight(Field field) {
        super(field);
    }

    @Override
    public Cell getCell() {
        int x = RND.random(0, Field.SIZE);
        int y = RND.random(0, Field.SIZE);
        while (isOccupied(x, y)) {
            x = RND.random(0, Field.SIZE);
            y = RND.random(0, Field.SIZE);
        }
        setOccupied(x, y);
        Cell cell = field.getCell(x, y);
        return getCell(cell);
    }

    private boolean isOccupied(int poz_X, int poz_Y) {
        if (poz_X < 0 || poz_X >= Field.SIZE) {
            return true;
        }
        if (poz_Y < 0 || poz_Y >= Field.SIZE) {
            return true;
        }
        return field.getField()[poz_X][poz_Y] == OCCUPIED;
    }

    private void setOccupied(int poz_X, int poz_Y) {
        field.setFieldOnPoz(poz_X, poz_Y, OCCUPIED);
    }
}
