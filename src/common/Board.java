package common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Jarle on 14.02.14.
 */
@SuppressWarnings("ALL")
public class Board{
    private SquareType[][] squares;
    private int height;
    private int width;
    private Poly falling;
    private int fallingX;
    private int fallingY;
    private static List<BoardListener> boardListeners = new ArrayList<>();
    static int maxValue = 100;
    static int initX = 6;
    static int initY = 1;
    private int score;
    private Boolean gameOver = Boolean.FALSE;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        if (height > 0 & height < maxValue & width > 0 & width < maxValue){
            this.squares = new SquareType[this.width][this.height];
            for (int y = 0; y < this.height; y++) {
                for (int x = 0; x < this.width; x++) {
                    if(x == 0 ^ x == this.width-1){
                        this.squares[x][y] = SquareType.OUTSIDE;
                    }
                    else if (y == 0 ^ y == this.height-1){
                        this.squares[x][y] = SquareType.OUTSIDE;
                    }
                    else{
                        this.squares[x][y] = SquareType.EMPTY;
                    }

                }

            }
        }
        else{
            System.out.println("Invalid board values.");
        }
    }

    //Getters/Setters

    public int getFallingX() {
        return fallingX;
    }

    public int getFallingY() {
        return fallingY;
    }

    public void setFallingX(int fallingX) {
        this.fallingX = fallingX;
    }

    public void setFallingY(int fallingY) {
        this.fallingY = fallingY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public SquareType getSquareType(int x, int y){
        if(x < width & y < height & x > 0 & y > 0){
            return this.squares[x][y];
        }
        else{
            return SquareType.OUTSIDE;
        }
    }

    //END Getters/Setters

    public void randomNewPoly(){
        int x = new Random().nextInt(TetrominoMaker.getNumberOfTypes()-2)+1;
        falling = new TetrominoMaker().getPoly(x);
    }



    public void initNewBoard(){
        gameOver = Boolean.FALSE;
        initFalling();
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if(x == 0 ^ x == this.width-1){
                    this.squares[x][y] = SquareType.OUTSIDE;
                }
                else if (y == 0 ^ y == this.height-1){
                    this.squares[x][y] = SquareType.OUTSIDE;
                }
                else{
                    this.squares[x][y] = SquareType.EMPTY;
                }

            }
    }
    }

    public static void addBoardListener(BoardListener bl){
        boardListeners.add(bl);
    }

    private void notifyListeners(){
        for(BoardListener element : boardListeners){
            element.boardChanged();
        }
    }

    public void initFalling(){ //Initiates the new falling block.
        falling = null;
        fallingX = initX;
        fallingY = initY;
    }

    public void clearLine(int i){ //Clears one row where all columns are filled.
        for (int j = 0; j < width; j++) {
            if(getSquareType(j, i) != SquareType.OUTSIDE){
                squares[j][i] = SquareType.EMPTY;
            }
        }

        score = score+40;
    }

    public void moveSquaresDown(int i){ //Moves down all squares except common.SquareType.OUTSIDE.
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < width; k++) {
                if(getSquareType(k, i - j - 1) != SquareType.OUTSIDE){
                    squares[k][i - j] = squares[k][i - j - 1];
                }
            }
        }
    }

    public void checkBoard(){ //Checks the board for any filled rows. If all rows are filled, clearLine and moveSquaresDown is called.
        int result = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(getSquareType(j, i) != SquareType.EMPTY & getSquareType(j, i) != SquareType.OUTSIDE){
                    result++;
                    if(result == 10){
                        clearLine(i);
                        moveSquaresDown(i);
                    }
                }
            }
            result = 0;
        }
    }

    public void fillFalling(){ //A method that is in charge of updating the new values of the squares for each downward movement. Does not tackle movement to the left/right entirely by itself.
        if(null != falling){
            removePreviousBlock();
            addUpdatedBlock();
        }
    }

    private void removePreviousBlock(){
        int x = getFallingX();
        int y = getFallingY();
        int fHeight = falling.getHeight();
        int fWidth = falling.getWidth();

        for (int j = 0; j < falling.getHeight(); j++) {
            for (int i = 0; i < falling.getWidth(); i++) {
                if(getSquareType(x-i,y-j-1) != SquareType.OUTSIDE & falling.getBlock()[(fHeight-1)-j][(fWidth-1)-i] != SquareType.EMPTY){
                    squares[x-i][y-j-1] = SquareType.EMPTY;
                }
            }
        }
    }

    private void addUpdatedBlock(){
        int x = getFallingX();
        int y = getFallingY();
        int fHeight = falling.getHeight();
        int fWidth = falling.getWidth();

        for (int i = 0; i < falling.getHeight(); i++) {
            for (int j = 0; j < falling.getWidth(); j++) {
                if(getSquareType(x-j,y-i) == SquareType.EMPTY & falling.getBlock()[(fHeight-1)-i][(fWidth-1)-j] != SquareType.EMPTY){
                    squares[x-j][y-i] = falling.getBlock()[(fHeight-1)-i][(fWidth-1)-j];
                }
            }
        }
        notifyListeners();
    }

    public void checkLandingConditions(){ //Checks to see if the common.Poly falling can move one more step down.
        int x = getFallingX();
        int y = getFallingY();
	    int fHeight = falling.getHeight();
	    int fWidth = falling.getWidth();
        int predictionY = y+1;


	outerloop:
	for (int i = 0; i < fHeight; i++) { //Traverse poly height
	    for (int j = 0; j < fWidth; j++) { // Traverse poly width
            if(getSquareType(x-j,predictionY-i) != SquareType.EMPTY & falling.getBlock()[(fHeight-1)-i][(fWidth-1)-j] != SquareType.EMPTY
                    & getSquareType(x-j,predictionY-i) != falling.getBlock()[(fHeight-1)-i][(fWidth-1)-j]
                    ^ getSquareType(x-j,predictionY) == falling.getBlock()[(fHeight-1)][(fWidth-1)-j] & falling.getBlock()[(fHeight-1)][(fWidth-1)-j] != SquareType.EMPTY)

            {
                initFalling();
                break outerloop;
            }

	}
    }
        fallingY++;
    }

    public static void tick(TetrisFrame e){
        Board thisBoard = e.getTetrisComponent().getCurrentBoard();

        if(thisBoard.falling == null){ //If there's no falling block in the board.
            for (int i = 0; i < thisBoard.getWidth()-2; i++) {
                if(thisBoard.getSquareType(i+1, initY+1) != SquareType.EMPTY){ //The only situation where it will be game over.
                    System.out.println("Your score was: " + thisBoard.score);
                    thisBoard.gameOver = Boolean.TRUE;
                }
                else{ //If it's not game over, we can first check our board for filled rows and then spawn a new common.Poly falling.
                    thisBoard.checkBoard();
                    thisBoard.randomNewPoly();
//                    thisBoard.falling = new TetrominoMaker().getPoly(1); //uncomment this to get Polys of a certain type(1-7)
                    thisBoard.setFallingX(initX);
                    thisBoard.setFallingY(initY);
                }
            }
        }


        else{ //A falling common.Poly is present, and we can manipulate its movement.
	        thisBoard.checkLandingConditions();
            thisBoard.fillFalling();
        }
    }


    private static void timer(final TetrisFrame tetrisFrame){
        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(tetrisFrame.getTetrisBoard().gameOver == Boolean.TRUE){
                    Timer timer = (Timer)e.getSource();
                    timer.stop();
                    return;
                }
                tick(tetrisFrame);
            }
        };

        final Timer gameTimer = new Timer(60, doOneStep);
        gameTimer.setCoalesce(true);
        gameTimer.start();
    }

    public void moveLeft(){ //Handles movement left and the removal of blocks from the blocks left side. Is called by a keybinding in common.TetrisComponent.
        if(falling != null){
            int fHeight = falling.getHeight();
            int fWidth = falling.getWidth();
            int x = fallingX;
            int y = fallingY;

            if(y > initY & getSquareType(x - fWidth, y) == SquareType.EMPTY){
                for (int i = 0; i < fHeight; i++) {
                    for (int j = 0; j < fWidth; j++) {
                        squares[x - j][y - i] = SquareType.EMPTY;
                    }
            }

            fallingX -= 1;
            addUpdatedBlock();
        }
    }
}

    public void moveRight(){ //Handles movement right and the removal of blocks from the blocks right side. Is called by a keybinding in common.TetrisComponent.
        if(falling != null){
            int fHeight = falling.getHeight();
            int fWidth = falling.getWidth();
            int x = fallingX;
            int y = fallingY;
            if(y > initY & getSquareType(x+1, y) == SquareType.EMPTY){
                for (int j = 0; j < fWidth ; j++) {
                    for (int i = 0; i < fHeight; i++) {
                        squares[x - fWidth +1 + j][y - i] = SquareType.EMPTY;
                    }
                }

                fallingX += 1;
                addUpdatedBlock();
            }
        }
    }

    public static void main(String[] args) {
        final Board testBoard = new Board(22,12);
        final EnumMap<SquareType, Color> testMap = new EnumMap<>(SquareType.class);
        Color borderColor = new Color(255, 255, 195);
        testMap.put(SquareType.OUTSIDE, borderColor);
        testMap.put(SquareType.EMPTY, Color.BLACK);
        testMap.put(SquareType.I, Color.CYAN);
        testMap.put(SquareType.J, Color.BLUE);
        testMap.put(SquareType.L, Color.ORANGE);
        testMap.put(SquareType.O, Color.YELLOW);
        Color limeGreen = new Color(50,205,50);
        testMap.put(SquareType.S, limeGreen);
        Color darkMagenta = new Color(139, 0, 139);
        testMap.put(SquareType.T, darkMagenta);
        testMap.put(SquareType.Z, Color.RED);


        final TetrisFrame finalBoard = new TetrisFrame(testBoard, testMap);

        addBoardListener(finalBoard.getTetrisComponent());
        finalBoard.pack();
        timer(finalBoard);
    }
}