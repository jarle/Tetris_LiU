package common;

/**
 * Created by Jarle on 22.02.14.
 */
public class TetrominoMaker {

    public static int getNumberOfTypes(){
        return SquareType.values().length;
    }


    public Poly getPoly(int n){
        SquareType[][] properties;
        //noinspection EnumSwitchStatementWhichMissesCases
        switch (SquareType.values()[n]){
            case I: properties = new SquareType[][]{
                    {SquareType.EMPTY,SquareType.EMPTY,SquareType.EMPTY,SquareType.EMPTY},
                    {SquareType.I,SquareType.I,SquareType.I,SquareType.I}
            }; break;

            case O: properties = new SquareType[][]{
                    {SquareType.O,SquareType.O},
                    {SquareType.O,SquareType.O}
            }; break;

            case T: properties = new SquareType[][]{
                    {SquareType.EMPTY,SquareType.T, SquareType.EMPTY},
                    {SquareType.T, SquareType.T, SquareType.T}
            }; break;

            case S: properties = new SquareType[][]{
                    {SquareType.EMPTY,SquareType.S,SquareType.S},
                    {SquareType.S,SquareType.S,SquareType.EMPTY}
            }; break;

            case Z: properties = new SquareType[][]{
                    {SquareType.Z,SquareType.Z,SquareType.EMPTY},
                    {SquareType.EMPTY,SquareType.Z,SquareType.Z}
            }; break;

            case J: properties = new SquareType[][]{
                    {SquareType.J,SquareType.EMPTY,SquareType.EMPTY},
                    {SquareType.J,SquareType.J,SquareType.J}
            }; break;

            case L: properties = new SquareType[][]{
                    {SquareType.EMPTY,SquareType.EMPTY,SquareType.L},
                    {SquareType.L,SquareType.L,SquareType.L}
            }; break;
            default: properties = null;
        }

        return new Poly(properties);
    }
}
