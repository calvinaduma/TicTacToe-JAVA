package cpsc2150.extendedTicTacToe;
/**
 * @author Ketki Patel
 * @Version 1.0
 *
 */
import java.util.*;

import cpsc2150.extendedTicTacToe.models.BoardPosition;
import cpsc2150.extendedTicTacToe.models.GameBoardMem;
import cpsc2150.extendedTicTacToe.models.IGameBoard;
import cpsc2150.extendedTicTacToe.models.GameBoard;


import java.lang.*;

import static cpsc2150.extendedTicTacToe.models.IGameBoard.*;

public class GameScreen {
    /**
     * This method is the main class of the game.
     * @param args the command line arguments
     * @pre none
     * @post none
     */
    public static void main(String[] args){
        // control variables
        int board_row, board_col, win_condition, number_of_players;
        char game_type;
        boolean continue_game = true, new_game = true, input_loop_check = true;
        List<Character> player_markers = new ArrayList<Character>();
        String white_space;
        Scanner input = new Scanner(System.in);

        // game variables
        char current_player, marker, play_again;
        int row, column, player_turn = 0;

        // Game starts here
        while(new_game){
            // new game
            System.out.println("NEW GAME");

            // gets number of people playing
            System.out.print("Enter number of players for this round: ");
            number_of_players = input.nextInt();
            white_space = input.nextLine();
            // checks number of players validity
            while (number_of_players < MIN_PLAYERS || number_of_players > MAX_PLAYERS){
                System.out.print("There has to be 2 - 10 players playing. Re-enter number: ");
                number_of_players = input.nextInt();
                white_space = input.nextLine(); // gets rid of white space after reading an int
            }

            // create new player and gets player marker
            for (int i=1; i<=number_of_players; i++){
                System.out.format("Player %d, enter marker: ", i);
                marker = input.nextLine().charAt(0);
                marker = Character.toUpperCase(marker);
                // checks marker validity
                while (player_markers.contains(marker)){
                    System.out.format("ERROR: %s is taken! Player %d, enter marker: ", marker, i);
                    marker = input.nextLine().charAt(0);
                    marker = Character.toUpperCase(marker);
                }
                player_markers.add(marker);
            }

            // GameBoard Design and win condition

            System.out.print("Enter ROW size for GameBoard: ");
            board_row = input.nextInt();
            white_space = input.nextLine(); // gets rid of white space after reading an int
            // checks row size validity
            while (board_row > MAX_ROW || board_row < MIN_ROW){
                System.out.println("ERROR: 3 <= rows <= 100! Re-enter row size: ");
                board_row = input.nextInt();
                white_space = input.nextLine(); // gets rid of white space after reading an int
            }

            System.out.print("Enter COLUMN size for GameBoard: ");
            board_col = input.nextInt();
            white_space = input.nextLine(); // gets rid of white space after reading an int
            // checks column size validity
            while (board_col > MAX_COL || board_col < MIN_COL){
                System.out.println("ERROR: 3 <= columns <= 100! Re-enter column size: ");
                board_col = input.nextInt();
                white_space = input.nextLine(); // gets rid of white space after reading an int
            }

            // checks number to win validity
            System.out.print("Enter number of consecutive MARKERS to win: ");
            win_condition = input.nextInt();
            white_space = input.nextLine(); // gets rid of white space after reading an int
            while (win_condition > MAX_WIN || win_condition < MIN_WIN || win_condition > board_row || win_condition > board_col){
                if (win_condition > board_col && win_condition < MAX_WIN){
                    System.out.format("ERROR: WIN: %d > COLUMN size %d! Re-enter number to win: ", win_condition, board_col);
                    win_condition = input.nextInt();
                    white_space = input.nextLine(); // gets rid of white space after reading an int
                } else if (win_condition > board_row && win_condition < MAX_WIN){
                    System.out.format("ERROR: WIN: %d > ROW size %d! Re-enter number to win: ", win_condition, board_row);
                    win_condition = input.nextInt();
                    white_space = input.nextLine(); // gets rid of white space after reading an int
                } else {
                    System.out.format("ERROR: WIN count has to be between [%d and %d] inclusive! Re-enter number to win: ", MIN_WIN, MAX_WIN);
                    win_condition = input.nextInt();
                    white_space = input.nextLine(); // gets rid of white space after reading an int
                }
            }

            // game type
            System.out.print("Enter game mode! (F/f) Fast or (M/m) Memory efficient: ");
            game_type = input.nextLine().charAt(0);
            // checks game type validity
            while (input_loop_check){
                if (game_type == 'f' || game_type == 'F' || game_type == 'm' || game_type == 'M') {
                    break;
                } else {
                    System.out.println("ERROR: Invalid game mode! Enter game mode! (F/f) Fast or (M/m) Memory efficient: ");
                    game_type = input.nextLine().charAt(0);
                }
            }

            // creates GameBoard
            IGameBoard GameBoard;
            if (game_type == 'F' || game_type == 'f') GameBoard = new GameBoard(board_row, board_col, win_condition);
            else GameBoard = new GameBoardMem(board_row, board_col, win_condition);
            System.out.println(GameBoard.toString());
            // start of player turns
            while(continue_game){
                current_player = player_markers.get(player_turn);
                BoardPosition pos = null;
                while(input_loop_check){
                    System.out.format("\nPlayer %s turn!\n", current_player);
                    System.out.print("Row: ");
                    row = input.nextInt();
                    // gets the white space that comes after pressing Enter to submit input.
                    white_space = input.nextLine();
                    while(row<0 || row>=GameBoard.getNumRows()){
                        System.out.format("\nERROR: Row = %d is out of bounds! 0 to %d ONLY!\n", row, GameBoard.getNumRows()-1);
                        System.out.print("New Row: ");
                        row = input.nextInt();
                        white_space = input.nextLine();
                    }
                    System.out.print("\nColumn: ");;
                    column = input.nextInt();
                    white_space = input.nextLine();
                    while(column<0 || column>=GameBoard.getNumColumns()){
                        System.out.format("\nERROR: Column = %d is out of bounds! 0 to %d ONLY!\n", column, GameBoard.getNumColumns()-1);
                        System.out.print("New Column: ");
                        column = input.nextInt();
                        white_space = input.nextLine();
                    }
                    pos = new BoardPosition(row, column);
                    if(GameBoard.checkSpace(pos)) break;
                    else {
                        System.out.print("ERROR: That space is not valid. Choose another space.\n");
                    }
                }
                GameBoard.placeMarker(pos, current_player);
                System.out.println(GameBoard.toString());
                // checks for win from input
                if(GameBoard.checkForWinner(pos)) {
                    System.out.format("GAME OVER! Player %s won! Do you want to play again? Y/N: \n", current_player);
                    // gets input to play again or not
                    play_again = input.nextLine().charAt(0);
                    input_loop_check = true;
                    // loop to make sure user input is correct
                    while(input_loop_check){
                        if(play_again == 'Y' || play_again == 'y' || play_again == 'N' || play_again == 'n') break;
                        else{
                            System.out.print("Input Y/N please. Would you like to play again? Y/N: \n");
                            play_again = input.nextLine().charAt(0);
                        }
                    }
                    // starts over at the top where new game is
                    if (play_again == 'y' || play_again == 'Y') {
                        player_markers.clear();
                    }
                    else {
                        // will exit end game by setting new game to false and break out of while(continue_game)
                        new_game=false;
                    }
                    break;
                } else if(GameBoard.checkForDraw()){
                    System.out.print("GAME OVER! Tie! Do you want to play again? Y/N: \n");
                    // gets input to play again or not
                    play_again = input.nextLine().charAt(0);
                    input_loop_check = true;
                    // loop to make sure user input is correct
                    while(input_loop_check){
                        if(play_again == 'Y' || play_again== 'y' || play_again == 'N' || play_again == 'n') break;
                        else{
                            System.out.print("Input Y/N please. Would you like to play again? Y/N: \n");
                            play_again = input.nextLine().charAt(0);
                        }
                    }
                    // starts over at the top where new game is
                    if (play_again == 'y' || play_again == 'Y') {
                        player_markers.clear();
                    } else {
                        // will exit end game by setting new game to false and break out of while(continue_game)
                        new_game=false;
                    }
                    break;
                } else {
                    if(player_turn == player_markers.size()-1) player_turn = 0;
                    else player_turn++;
                }
            }
        }
        input.close();
    }
}
