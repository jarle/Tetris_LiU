package common;

/**
 * Created by Jarle on 22.02.14.
 */
public class Poly {
    private SquareType[][] block;

    public Poly(SquareType[][] block) {
        this.block = block;
    }

    public SquareType[][] getBlock() {
        return block;
    }

    public int getHeight(){
        return block.length;
    }

    public int getWidth(){
        return block[0].length;
    }

}

