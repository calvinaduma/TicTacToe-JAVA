package cpsc2150.extendedTicTacToe.models;

import java.util.*;

/**
 * @invariant: row = #new_row && column = #new_column && GameBoardMem = Map<Character, List<BoardPosition>>
 *               && win_condition = #new_win_condition
 * @correspondence: self = HashMap<Character, List<BoardPosition>>
 */
public class GameBoardMem extends AbsGameBoard implements IGameBoard {

    private int row = 0;
    private int column = 0;
    private int win_condition = 0;
    public HashMap<Character, List<BoardPosition>> GameBoardMem = new HashMap<>();

    public GameBoardMem(int new_row, int new_column, int new_win_condition) {
        win_condition = new_win_condition;
        row = new_row;
        column = new_column;
    }

    public int getNumRows() {
        return row;
    }

    public int getNumColumns() {
        return column;
    }

    public int getNumToWin() {
        return win_condition;
    }

    // needs to fix
    public void placeMarker(BoardPosition pos, char marker) {
        BoardPosition finalPos = pos;

        if (GameBoardMem.containsKey(marker)) {
            for (Map.Entry<Character, List<BoardPosition>> temp_Board_List : GameBoardMem.entrySet()) {
                if (temp_Board_List.getKey() == marker) {
                    List<BoardPosition> tempList = new ArrayList<BoardPosition>();
                    tempList.addAll(temp_Board_List.getValue());
                    tempList.add(finalPos);
                    GameBoardMem.put(marker, tempList);
                }
            }
        } else {
            List<BoardPosition> tempList = new ArrayList<BoardPosition>();
            tempList.add(finalPos);
            GameBoardMem.put(marker, tempList);
        }
    }


    public char whatsAtPos(BoardPosition pos) {
        if (GameBoardMem.isEmpty()) {
            return ' ';
        } else {
            // iterates through each list in map
            for (Map.Entry<Character, List<BoardPosition>> tempBoardList : GameBoardMem.entrySet()) {
                List<BoardPosition> tempList = new ArrayList<BoardPosition>();
                tempList.addAll(tempBoardList.getValue());
                // if pos is found in list
                for (BoardPosition tempPos : tempList){
                    if (tempPos.equals(pos)) {
                        return tempBoardList.getKey();
                    }
                }
            }
        }
        return ' ';
    }
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        if (GameBoardMem.isEmpty()) return false;
        else {
            // iterates through each list in map
            for (Map.Entry<Character, List<BoardPosition>> tempBoardList : GameBoardMem.entrySet()) {
                List<BoardPosition> tempList = new ArrayList<BoardPosition>();
                tempList.addAll(tempBoardList.getValue());
                // if pos is found in list
                for (BoardPosition tempPos : tempList)
                    if (tempPos.equals(pos) && tempBoardList.getKey() == player) return true;
            }
        }
        return false;
    }
}
