package cpsc2150.extendedTicTacToe.models;

/**
 * @author Ketki Patel
 * @Version 1.0
 *
 */
public interface IGameBoard {
    int MAX_ROW = 100, MAX_COL = 100, MAX_WIN = 25, MAX_PLAYERS = 10;
    int MIN_ROW = 3, MIN_COL = 3, MIN_WIN = 3, MIN_PLAYERS = 2;
    /**
     * This method returns the number of rows on the board.
     * @return returns the number of rows on the board
     * @pre NONE
     * @post getNumRows = MAX_ROW
     */
    public int getNumRows();

    /**
     * This method returns the number of columns on the board.
     * @return returns the number of columns on the board
     * @pre NONE
     * @post getNumColumns = MAX_COLUMN
     */
    public int getNumColumns();

    /**
     * This method returns the number of tokens in a row needed to win the game.
     * @return returns the number of tokens in a row needed to win the game
     * @pre NONE
     * @post getNumToWin = WIN_COUNT
     */
    public int getNumToWin();
    /**
     * This method will check to see if there is space on the board
     * @param pos the position on the board [row][column]
     * @return will return true if the space is available, otherwise false
     *
     * @pre  0 <= BoardPosition.getRow() <= 5 AND 0 <= BoardPosition.getColumn() <= 8
     * @post checkSpace = true AND false otherwise
     */
    default public boolean checkSpace(BoardPosition pos){
        if(pos.getRow() < getNumRows() || pos.getRow() >= 0 || pos.getColumn() < getNumColumns() || pos.getColumn() >= 0)
            if(whatsAtPos(pos)==' ') return true;
        return false;
    }
    /**
     * This method will place the character in marker on the position specified by marker
     * @param pos the position on the board
     * @param player
     *
     * @pre [player is a valid player] AND checkSpace(marker) = true
     * @post board[marker.getRow()][marker.getColumn()] = player
     */
    public void placeMarker(BoardPosition pos, char player);

    /**
     * This method will check to see if the player in lastPos has won the game
     * @param lastPos
     * @return true if the player in lastPos has won, false otherwise
     *
     * @pre checkSpace(lastPos) = false AND lastPos.getRow() <= MAX_ROW AND lastPos.getColumn() <= MAX_COLUMN
     * @post checkForWinner = true if checkForVerticalWin OR checkForHorizontalWin OR checkForDiagonalWin = true,
     * otherwise checkForWinner = false
     */
    default public boolean checkForWinner(BoardPosition lastPos){
        char player = whatsAtPos(lastPos);
        // if any condition for a win is true, returns true. Else returns false
        return(checkDiagonalWin(lastPos,player) || checkHorizontalWin(lastPos,player) || checkVerticalWin(lastPos, player));
    }

    /**
     * This method will check to see if the game has ended in a tie after last turn only.
     * The game will end in a tie if the board is full and no one has won
     * @return true iff game is tied, otherwise false
     *
     * @pre none
     * @post checkForDraw = true if [board is full AND checkForWinner = false] AND checkForDraw = false if
     * [board is not full OR checkForWinner = true]
     */
    default public boolean checkForDraw(){
        // iterates through board looking for blank space
        for(int r=0; r<getNumRows(); r++){
            for(int c=0; c<getNumColumns(); c++){
                BoardPosition pos = new BoardPosition(r,c);
                if (whatsAtPos(pos) == ' ')
                    return false;
            }
        }
        return true;
    }

    /**
     * This method will check to see if the last marker placed results in 5 in a
     * row horizontally.
     * @param lastPos
     * @param player
     * @return true iff there are 5 in a row horizontally, false otherwise
     *
     * @pre [lastPos is a valid BoardPosition object]
     * @post true iff checkForWinner(lastPos) = true AND false iff checkForWinner(lastPos) = false AND col = #col
     *
     */
    default public boolean checkHorizontalWin(BoardPosition lastPos, char player){
        int counter = 0; // number of identical player tokens found in horizontal line
        int offset = 0; // necessary to not count player token previously accounted for
        int r = lastPos.getRow();
        int c = lastPos.getColumn();

        // checks to the right of lastPos
        while(counter <getNumToWin()){
            // if check goes out of bounds, end
            if(c+offset >= getNumColumns()) break;
            BoardPosition one = new BoardPosition(r,c+offset);
            // correct player token found and within bounds and no win condition yet
            if(isPlayerAtPos(one, player) && c+offset < getNumColumns() && counter != getNumToWin()){
                counter++;
                offset++;
            }
            // break if wrong player found in sequence
            if(!isPlayerAtPos(one, player)) break;
            // win condition
            if(counter==getNumToWin()) return true;
        }
        // checks to the left of lastPos
        offset = 1; // sets to 1 to not count lastPos which was accounted for already
        // only enter if win condition not met already
        while(counter < getNumToWin()){
            // checks if in bounds
            if(c-offset<0) break;
            BoardPosition two =  new BoardPosition(r,c-offset);
            // correct player token found and within bounds and no win condition yet
            if(isPlayerAtPos(two, player) && c-offset>=0 && counter != getNumToWin()){
                counter++;
                offset++;
            }
            // break if wrong player found in sequence
            if(!isPlayerAtPos(two, player)) break;
            // win condition
            if(counter==getNumToWin()) return true;
        }
        // checks win condition as an extra measure
        if(counter<getNumToWin()) return false;
        else return true;
    }

    /**
     * This method will check to see if the last marker placed results in 5 in a
     * row vertically.
     * @param lastPos
     * @param player
     * @return true iff there are 5 in a row vertically, false otherwise
     *
     * @pre [lastPos is a valid BoardPosition object]
     * @post true iff checkForWinner(lastPos) = true AND false iff checkForWinner(lastPos) = false AND row = #row
     *
     */
    default boolean checkVerticalWin(BoardPosition lastPos, char player){
        int counter = 0; // number of identical player tokens found in horizontal line
        int offset = 0; // necessary to not count player token previously accounted for
        int r = lastPos.getRow();
        int c = lastPos.getColumn();
        
        // checks to the top of lastPos
        while(counter <getNumToWin()){
            // if check goes out of bounds, end
            if(r+offset >= getNumRows()) break;
            BoardPosition one = new BoardPosition(r+offset,c);
            // correct player token found and within bounds and no win condition yet
            if(isPlayerAtPos(one, player) && r+offset < getNumRows() && counter != getNumToWin()){
                counter++;
                offset++;
            }
            // break if wrong player found in sequence
            if(!isPlayerAtPos(one, player)) break;
            // win condition
            if(counter==getNumToWin()) return true;
        }
        // checks to the bottom of lastPos
        offset = 1; // sets to 1 to not count lastPos which was accounted for already
        // only enter if win condition not met already
        while(counter < getNumToWin()){
            // checks if in bounds
            if(r-offset<0) break;
            BoardPosition two =  new BoardPosition(r-offset,c);
            // correct player token found and within bounds and no win condition yet
            if(isPlayerAtPos(two, player) && r-offset>=0 && counter != getNumToWin()){
                counter++;
                offset++;
            }
            // break if wrong player found in sequence
            if(!isPlayerAtPos(two, player)) break;
            // win condition
            if(counter==getNumToWin()) return true;
        }
        // checks win condition as an extra measure
        if(counter<getNumToWin()) return false;
        else return true;
    }

    /**
     * This method will check to see if the last marker placed results in 5 in a
     * row diagonally.
     * @param lastPos
     * @param player
     * @return true iff there are 5 in a row diagonally, false otherwise
     *
     * @pre [lastPos is a valid BoardPosition object]
     * @post  true iff checkForWinner(lastPos) = true AND false iff checkForWinner(lastPos) = false AND row = #row
     *       AND col = #col
     */
    default boolean checkDiagonalWin(BoardPosition lastPos, char player){
        int counter = 0;
        int offset = 0;
        int r = lastPos.getRow();
        int c = lastPos.getColumn();
        System.out.format("r = %d, c = %d\n",r,c);
        // diagonal of (+1,+1) bottom right
        while(counter<getNumToWin()){
            if(r+offset>=getNumRows() || c+offset>=getNumColumns()) break;
            BoardPosition one = new BoardPosition(r+offset,c+offset);
            if(isPlayerAtPos(one, player) &&  counter != getNumToWin()){
                counter++;
                offset++;
            }
            if (counter==getNumToWin()) return true;
            if(!isPlayerAtPos(one, player)) break;
        }
        // offset to not recount already counter position
        offset = 1;
        // diagonal of (-1,-1) top left
        while(counter<getNumToWin()){
            if(r-offset<0 || c-offset<0) break;
            BoardPosition two = new BoardPosition(r-offset,c-offset);
            if(isPlayerAtPos(two, player) && counter != getNumToWin()){
                counter++;
                offset++;
            }
            if(!isPlayerAtPos(two, player)) break;
            if(counter==getNumToWin()) return true;
        }

        // check diagonal in different direction if win condition has not been met yet
        offset = 0;
        counter = 0;
        // diagonal of (+1,-1) bottom left
        while(counter<getNumToWin()){
            if(r+offset>=getNumRows() || c-offset<0) break;
            BoardPosition three = new BoardPosition(r+offset,c-offset);
            if(isPlayerAtPos(three, player) && counter!=getNumToWin()){
                counter++;
                offset++;
            }
            if(!isPlayerAtPos(three, player)) break;
            if(counter==getNumToWin()) return true;
        }

        // diagonal of (-1,+1) top right
        offset=1;
        while(counter<getNumToWin()){
            if(r-offset<0 || c+offset>=getNumColumns()) break;
            BoardPosition four = new BoardPosition(r-offset,c+offset);
            if(isPlayerAtPos(four, player) && counter!=getNumToWin()){
                counter++;
                offset++;
            }
            if(!isPlayerAtPos(four, player)) break;
            if(counter==getNumToWin()) return true;
        }

        // if win condition met from first diagonal, go here
        if (counter>=getNumToWin()) return true;
        return false;
    }


    /**
     * This method will check to see what is in the GameBoard position pos
     * and returns it
     * @param pos
     * @return the character at the position specified by pos
     *
     * @pre 0 <= pos.getRow() < MAX_ROW AND 0 <= pos.getColumn() < MAX_COL
     * @post getBoardPosition = board[pos.getRow()][pos.getColumn()]
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * This method will check to see if the player is at position pos
     * @param pos
     * @param player
     * @return true if the player is at position pos, false otherwise
     *
     * @pre 0 <= pos.getRow() < MAX_ROW AND 0 <= pos.getColumn() < MAX_COL
     * @post playerAtPos = board[pos.getRow()][pos.getColumn()] == player
     */
    default public boolean isPlayerAtPos(BoardPosition pos, char player){
        return (whatsAtPos(pos)==player);
    }

}
