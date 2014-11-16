package org.moriano.gameoflife;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by moriano on 19/10/14.
 */
public class Gui extends JFrame {


    private Container baseContainer;
    private Cell[][] cells = CellPresets.RANDOM.getCells();
    private final int rows = cells.length;
    private final int columns = cells[0].length;
    private JButton button = new JButton("Start");
    private JButton swapButton = new JButton("Swap");
    private BorderLayout baseLayout = new BorderLayout();
    private GridLayout gridLayout = new GridLayout(this.rows, this.columns);
    private JPanel basePanel = new JPanel(baseLayout);
    private JPanel centerPanel = new JPanel(gridLayout);
    private JPanel topPanel = new JPanel(new FlowLayout());

    private Game gameOfLife = new Game();

    private Thread gameThread = new Thread(gameOfLife);
    private JComboBox<CellPresets> cellPresets = new JComboBox(CellPresets.values());

    public Gui() throws HeadlessException {

        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                if (System.currentTimeMillis() % 3 != 0) {
                    cells[row][column] = new Cell(CellState.DEAD);
                } else {
                    cells[row][column] = new Cell(CellState.ALIVE);
                }
            }
        }

        this.baseContainer = this.getContentPane();

        this.basePanel.add(this.centerPanel, BorderLayout.CENTER);
        this.basePanel.add(this.topPanel, BorderLayout.NORTH);

        topPanel.add(this.cellPresets);
        topPanel.add(this.button);
        topPanel.add(this.swapButton);

        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                this.centerPanel.add(cells[row][column]);
            }
        }

        this.baseContainer.add(basePanel);

        this.addComboBoxListener();
        this.addButtonListener();

        this.pack();
        //this.setSize(800, 600);
        this.setTitle("Game of life");

        this.setVisible(true);
    }

    private void addComboBoxListener() {
        this.cellPresets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CellPresets cellPreset = (CellPresets)cellPresets.getSelectedItem();
                Cell[][] newCells = null;
                if(cellPreset == CellPresets.GLIDER) {
                    newCells = cellPreset.getCells();
                } else if(cellPreset == CellPresets.LIGHT_WEIGHT_SPACE_SHIP) {
                    newCells = cellPreset.getCells();
                } else if(cellPreset == CellPresets.RANDOM) {
                    newCells = cellPreset.getCells();
                }
                resetCells(newCells);
             }
        });
    }

    private void addButtonListener() {
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button!");
                System.out.println("Before!");
                gameThread.start();
                System.out.println("After!");

            }
        });

        this.swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CellState newState = cells[0][0].getState() == CellState.DEAD ? CellState.ALIVE : CellState.DEAD;
                for(int i = 0; i < cells.length; i++) {
                    for(int j = 0; j < cells[i].length; j++) {
                        cells[i][j].setState(newState);
                    }
                }
            }
        });
    }

    private class Game implements Runnable {
        @Override
        public void run() {
            this.iterate();
        }


        private void iterate() {
            while(true) {
                try{
                    Thread.sleep(100);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                /*Cell[][] newCells = new Cell[rows][columns];
                for(int row = 0; row < newCells.length; row++) {
                    for(int column = 0; column < newCells[row].length; column++) {
                        newCells[row][column] = cells[row][column];
                    }
                }*/

                for (int row = 0; row < cells.length; row++) {
                    for (int column = 0; column < cells[row].length; column++) {

                        Cell currentCell = cells[row][column];
                        /*
                        Assuming our cell is the one in the center (5).

                        1 2 3
                        4 5 6
                        7 8 9
                         */
                        int topRow = row - 1;
                        int bottomRow = row + 1;
                        int leftColumn = column - 1;
                        int rightColumn = column + 1;

                        if(topRow == -1) {
                            topRow = rows - 1;
                        }

                        if(bottomRow == rows) {
                            bottomRow = 0;
                        }

                        if(leftColumn == -1) {
                            leftColumn = columns - 1;
                        }

                        if(rightColumn == columns) {
                            rightColumn = 0;
                        }

                        Cell cell1 = cells[topRow][leftColumn];
                        Cell cell2 = cells[topRow][column];
                        Cell cell3 = cells[topRow][rightColumn];

                        Cell cell4 = cells[row][leftColumn];
                        Cell cell6 = cells[row][rightColumn];

                        Cell cell7 = cells[bottomRow][leftColumn];
                        Cell cell8 = cells[bottomRow][column];
                        Cell cell9 = cells[bottomRow][rightColumn];

                        int totalAlive = 0;

                        totalAlive += cell1.getState() == CellState.ALIVE ? 1 : 0;

                        totalAlive += cell2.getState() == CellState.ALIVE ? 1 : 0;

                        totalAlive += cell3.getState() == CellState.ALIVE ? 1 : 0;

                        totalAlive += cell4.getState() == CellState.ALIVE ? 1 : 0;

                        totalAlive += cell6.getState() == CellState.ALIVE ? 1 : 0;

                        totalAlive += cell7.getState() == CellState.ALIVE ? 1 : 0;

                        totalAlive += cell8.getState() == CellState.ALIVE ? 1 : 0;

                        totalAlive += cell9.getState() == CellState.ALIVE ? 1 : 0;

                        if (currentCell.getState() == CellState.ALIVE) {
                            if (totalAlive < 2) {
                                cells[row][column].setNextState(CellState.DEAD);
                            } else if (totalAlive == 2 || totalAlive == 3) {
                                cells[row][column].setNextState(CellState.ALIVE);
                            } else if (totalAlive > 3) {
                                cells[row][column].setNextState(CellState.DEAD);
                            }
                        } else if (currentCell.getState() == CellState.DEAD && totalAlive == 3) {
                            cells[row][column].setNextState(CellState.ALIVE);
                        } else {
                            cells[row][column].setNextState(cells[row][column].getState());
                        }
                    }
                }

                for (int row = 0; row < cells.length; row++) {
                    for (int column = 0; column < cells[row].length; column++) {
                        cells[row][column].applyNextState();
                    }
                }

                //centerPanel.revalidate();
                //centerPanel.repaint();

                //resetCells(newCells);
            }
        }
    }

    private void resetCells(Cell[][] newCells) {
        cells = newCells;
        centerPanel.removeAll();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                centerPanel.add(cells[row][column]);
            }
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }
}
