package cpsc2150.extendedTicTacToe.models;
/**
 * @author Ketki Patel
 * @Version 1.0
 *
 * @invariant 0 <= row <= 5 AND 0 <= col <= 8
 *
 */

public class BoardPosition {
    private int row;
    private int col;

    /**
     * Constructor to create the objects of BoardPosition class.
     *
     * @param r the row number
     * @param c the column number
     *
     * @pre none
     *
     * @post row = r AND col = c
     */

    public BoardPosition(int r, int c){
        row = r;
        col = c;
    }

    /**
     * A method to get and return the row.
     *
     * @retrun integer value of row
     * @pre none
     * @post row = #row AND col = #col AND getRow = row
     *
     */
    public int getRow() {
        return row;
    }
    /**
     * A method to get and return the column.
     *
     * @return integer value of column
     * @pre none
     * @post row = #row AND col = #col AND getColumn = col
     *
     */
    public int getColumn(){
        return col;
    }
    /**
     * A method to create string that shows BoardPosition
     * @return a string that outputs the row and column "[row],[column]"
     * @pre none
     * @post toString = "[row],[column]" AND
     *       row = #row AND col = #col
     */
    @Override
    public String toString(){
        return row + "," + col;
    }

    /**
     * A method that will check if two BoardPositions are equal.
     * @return if row and column have the same BoardPosition return true
     * @pre none
     * @post true iff row = row AND col = col AND
     *       row = #row AND col = #col
     */
    @Override
    public boolean equals(Object param){
        if(param.getClass() != this.getClass()){
            return false;
        }
        BoardPosition other = (BoardPosition) param;
        return (other.getRow() == this.getRow() && other.getColumn() == this.getColumn());
    }

}
