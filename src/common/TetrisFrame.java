package common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

/**
 * Created by Jarle on 23.02.14.
 */
public class TetrisFrame extends JFrame {

    private Board tetrisBoard;
    private TetrisComponent tetrisComponent;

    public Board getTetrisBoard() {
        return tetrisBoard;
    }

    public TetrisComponent getTetrisComponent() {
        return tetrisComponent;
    }

    public TetrisFrame(Board tetrisBoard, EnumMap<SquareType,Color> tetrisMap){
        super("Tetris");

        //Menubar+Menubuttons
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        final JMenuItem restart = new JMenuItem("New Game");
        file.add(restart);
        final JMenuItem exit = new JMenuItem("Exit");
        mb.add(file);
        file.add(exit);
        this.setJMenuBar(mb);



        this.tetrisBoard = tetrisBoard;
        this.tetrisComponent = new TetrisComponent(this.tetrisBoard, tetrisMap);

        //JFrame design and tetrisComponent
        this.setLayout(new BorderLayout());
        JPanel tetrisPanel = new JPanel();
        tetrisPanel.add(tetrisComponent, BorderLayout.CENTER);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        tetrisPanel.setBackground(Color.BLACK);
        add(tetrisPanel);
        this.setVisible(true);
        this.pack();

        // Actionlistener for our "Exit"-option.
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(exit, "Do you really want to exit?", "Exit?", JOptionPane.YES_NO_OPTION);
                if(answer==JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });

        //Restart-button.

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetrisComponent.getCurrentBoard().initNewBoard();
            }
        });



    }

}
