package org.moriano.gameoflife;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by moriano on 19/10/14.
 */
public class Cell extends JLabel {

    private CellState state;
    private CellState nextState;


    private final Border border = BorderFactory.createLineBorder(Color.WHITE, 1);

    private void paint(CellState state) {
        if(state == CellState.ALIVE) {
            this.setBackground(Color.GREEN);
        } else {
            this.setBackground(Color.RED);
        }
    }

    public Cell(CellState state) {
        super(" ");

        this.setBorder(border);

        this.paint(state);

        this.setOpaque(true);

        this.state = state;

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Click!");
                swapState();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public CellState getState() {
        return state;
    }

    private void swapState() {
        if(this.state == CellState.ALIVE) {
            this.paint(CellState.DEAD);
            this.state = CellState.DEAD;
        } else {
            this.paint(CellState.ALIVE);
            this.state = CellState.ALIVE;
        }
    }

    public void setState(CellState state) {
        this.state = state;
        this.paint(state);
    }

    public void setNextState(CellState nextState) {
        this.nextState = nextState;
    }

    public void applyNextState() {
        if(this.state != this.nextState) {
            this.setState(this.nextState);
        }
        this.nextState = CellState.UNKNOWN;
    }
}
