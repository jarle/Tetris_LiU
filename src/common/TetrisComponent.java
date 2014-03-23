package common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EnumMap;

/**
 * Created by Jarle on 26.02.14.
 */
public class TetrisComponent extends JComponent implements BoardListener {
    final private Board currentBoard;
    @SuppressWarnings("CollectionDeclaredAsConcreteClass")
    private EnumMap<SquareType,Color> tetrisMap;
    final static int blockModifier = 20; //Carefully chosen constant that makes everything scale decently.
    final static int blockLength = blockModifier -3;


    public TetrisComponent(Board currentBoard, EnumMap<SquareType,Color> tetrisMap) {
        this.currentBoard = currentBoard;
        this.tetrisMap = tetrisMap;
        final Board finalBoard = currentBoard;



        // Here we fill in the InputMap and ActionMap of our component, so that we can use keybindings for directional methods in board.
        Action moveLeft = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalBoard.moveLeft();
            }
        };

        Action moveRight = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalBoard.moveRight();
            }
        };

        getInputMap().put(KeyStroke.getKeyStroke("A"),
                "moveLeft");
        getInputMap().put(KeyStroke.getKeyStroke("D"),
                "moveRight");
        getInputMap().put(KeyStroke.getKeyStroke("LEFT"),
                "moveLeft");
        getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),
                "moveRight");

        getActionMap().put("moveLeft",
                moveLeft);
        getActionMap().put("moveRight",
                moveRight);


    }


    @Override
    public Dimension getPreferredSize(){
        double width = currentBoard.getWidth()* blockModifier;
        double height = currentBoard.getHeight()* blockModifier;
        return new Dimension((int)width, (int)height);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        for (int y = 0; y < this.currentBoard.getHeight(); y++) {
            for (int x = 0; x < this.currentBoard.getWidth(); x++) {
                g2d.setColor(tetrisMap.get(currentBoard.getSquareType(x, y)));
                g2d.fillRect(x* blockModifier, y* blockModifier, blockLength, blockLength);
            }
        }
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    @Override
    public void boardChanged() {
        repaint();
    }
}
