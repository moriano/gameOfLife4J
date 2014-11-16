package org.moriano.gameoflife;



/**
 * Created by moriano on 25/10/14.
 */
public enum CellPresets {
    RANDOM(getRandom()),
    GLIDER(getGlider()),
    LIGHT_WEIGHT_SPACE_SHIP(getLightWeightSpaceShip())
    ;

    private static final int ROWS = 90;
    private static final int COLUMNS = 160;

    private Cell[][] cells;



    private CellPresets(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    private static void reset(Cell[][] cells) {
        for(int row=0; row<cells.length; row++) {
            for(int column=0; column<cells[0].length; column++) {
                cells[row][column] = new Cell(CellState.DEAD);
            }
        }
    }

    /**
     *
     * +------+
     * |  x   | <--- That one is the start point
     * |   x  |
     * | xxx  |
     * +------+
     * @return
     */
    private static Cell[][] getGlider() {

        Cell[][] cells = new Cell[ROWS][COLUMNS];
        reset(cells);
        int startRow = (cells.length / 2);
        int startColumn = (cells[0].length / 2);

        cells[startRow][startColumn] = new Cell(CellState.ALIVE);
        cells[startRow+1][startColumn+1] = new Cell(CellState.ALIVE);
        cells[startRow+2][startColumn-1] = new Cell(CellState.ALIVE);
        cells[startRow+2][startColumn] = new Cell(CellState.ALIVE);
        cells[startRow+2][startColumn+1] = new Cell(CellState.ALIVE);

        return cells;
    }

    private static Cell[][] getRandom() {

        Cell[][] cells = new Cell[ROWS][COLUMNS];

        for(int row = 0; row < ROWS; row++) {
            for(int column = 0; column < COLUMNS; column++) {
                cells[row][column] = new Cell(Math.random() % 3 == 0 ? CellState.ALIVE : CellState.DEAD);
            }
        }


        return cells;
    }

    /**
     *
     * +----------+
     * |  x  x    | <--- That one is the start point (the leftmost one)
     * |      x   |
     * |  x   x   |
     * |   xxxx   |
     * +----------+
     * @return
     */
    private static Cell[][] getLightWeightSpaceShip() {
        Cell[][] cells = new Cell[ROWS][COLUMNS];
        reset(cells);
        int startRow = (cells.length / 2);
        int startColumn = (cells[0].length / 2);

        cells[startRow][startColumn] = new Cell(CellState.ALIVE);
        cells[startRow][startColumn+3] = new Cell(CellState.ALIVE);
        cells[startRow+1][startColumn+4] = new Cell(CellState.ALIVE);
        cells[startRow+2][startColumn] = new Cell(CellState.ALIVE);
        cells[startRow+2][startColumn+4] = new Cell(CellState.ALIVE);
        cells[startRow+3][startColumn+1] = new Cell(CellState.ALIVE);
        cells[startRow+3][startColumn+2] = new Cell(CellState.ALIVE);
        cells[startRow+3][startColumn+3] = new Cell(CellState.ALIVE);
        cells[startRow+3][startColumn+4] = new Cell(CellState.ALIVE);


        return cells;
    }
}